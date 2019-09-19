/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.andrukhiv.mynavigationdrawer.adapters;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.activity.GalleryActivity;
import com.andrukhiv.mynavigationdrawer.fragments.GalleryImagePagerFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import static com.andrukhiv.mynavigationdrawer.adapters.GalleryImageData.IMAGE_DRAWABLES;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Фрагмент для отображения сетки изображений.
 */
public class GalleryGridAdapter extends RecyclerView.Adapter<GalleryGridAdapter.ImageViewHolder> {

  /**
   * Слушатель, прикрепленный ко всем ViewHolders для обработки событий загрузки изображений и кликов.
   */
  private interface ViewHolderListener {

    void onLoadCompleted(ImageView view, int adapterPosition);

    void onItemClicked(View view, int adapterPosition);
  }

  private final RequestManager requestManager;
  private final ViewHolderListener viewHolderListener;

  /**
   * Создает новый сетевой адаптер для данного {@link Fragment}.
   */
  public GalleryGridAdapter(Fragment fragment) {
    this.requestManager = Glide.with(fragment);
    this.viewHolderListener = new ViewHolderListenerImpl(fragment);
  }

  @Override
  public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.image_card_gallery, parent, false);
    return new ImageViewHolder(view, requestManager, viewHolderListener);
  }

  @Override
  public void onBindViewHolder(ImageViewHolder holder, int position) {
    holder.onBind();
  }

  @Override
  public int getItemCount() {
    return IMAGE_DRAWABLES.length;
  }


  /**
   * Реализация по умолчанию {@link ViewHolderListener}.
   */
  private static class ViewHolderListenerImpl implements ViewHolderListener {

    private Fragment fragment;
    private AtomicBoolean enterTransitionStarted;

    ViewHolderListenerImpl(Fragment fragment) {
      this.fragment = fragment;
      this.enterTransitionStarted = new AtomicBoolean();
    }

    @Override
    public void onLoadCompleted(ImageView view, int position) {
      // Вызовите startPostponedEnterTransition только тогда,
      // когда «выбранная» загрузка изображения завершена.
      if (GalleryActivity.currentPosition != position) {
        return;
      }
      if (enterTransitionStarted.getAndSet(true)) {
        return;
      }
      fragment.startPostponedEnterTransition();
    }

    /**
     * Обрабатывает просмотр, устанавливая текущую позицию в заданную {@code position} и
     * запуск {@link GalleryImagePagerFragment}, который отображает изображение в позиции.
     *
    * @param view щелчок {@link ImageView} (представление общего элемента будет повторно отображено на
     * SharedElementCallback GalleryGridFragment)
     * @param position выбранной позиции просмотра
     */
    @Override
    public void onItemClicked(View view, int position) {
      // Обновление позиции.
      GalleryActivity.currentPosition = position;

      // Исключить кликовую карту из перехода на выход (например, карта сразу исчезнет
      // вместо того, чтобы затухать с остальными, чтобы предотвратить перекрывающуюся
      // анимацию затухания и перемещения).
      ((TransitionSet) fragment.getExitTransition()).excludeTarget(view, true);

      ImageView transitioningView = view.findViewById(R.id.card_image);
      fragment.getFragmentManager()
          .beginTransaction()
          .setReorderingAllowed(true) // Optimize for shared element transition
          .addSharedElement(transitioningView, transitioningView.getTransitionName())
          .replace(R.id.fragment_container, new GalleryImagePagerFragment(), GalleryImagePagerFragment.class
              .getSimpleName())
          .addToBackStack(null)
          .commit();
    }
  }

  /**
   * ViewHolder для изображений сетки.
   */
  class ImageViewHolder extends RecyclerView.ViewHolder implements
      View.OnClickListener {

    private final ImageView image;
    private final RequestManager requestManager;
    private final ViewHolderListener viewHolderListener;

    ImageViewHolder(View itemView, RequestManager requestManager,
        ViewHolderListener viewHolderListener) {
      super(itemView);
      this.image = itemView.findViewById(R.id.card_image);
      this.requestManager = requestManager;
      this.viewHolderListener = viewHolderListener;
      itemView.findViewById(R.id.card_view).setOnClickListener(this);
    }

    /**
     * Binds this view holder to the given adapter position.
     *
     * The binding will load the image into the image view, as well as set its transition name for
     * later.
     */
    void onBind() {
      int adapterPosition = getAdapterPosition();
      setImage(adapterPosition);
      // Устанавливаем строковое значение ресурса изображения как уникальное имя перехода для представления.
      image.setTransitionName(String.valueOf(GalleryImageData.IMAGE_DRAWABLES[adapterPosition]));
      //image.setTransitionName(galleryModels.get(adapterPosition).getImageGallery());

    }

    void setImage(final int adapterPosition) {
      // Загрузите изображение с Glide, чтобы предотвратить ошибку OOM, когда размеры изображений очень велики.
      requestManager
          .load(GalleryImageData.IMAGE_DRAWABLES[adapterPosition])
             // .load(galleryModels.get(adapterPosition).getImageGallery())
          .listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                        Target<Drawable> target, boolean isFirstResource) {
              viewHolderListener.onLoadCompleted(image, adapterPosition);
              return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                target, DataSource dataSource, boolean isFirstResource) {
              viewHolderListener.onLoadCompleted(image, adapterPosition);
              return false;
            }
          })
          .into(image);
    }

    @Override
    public void onClick(View view) {
      // Let the listener start the GalleryImagePagerFragment.
      viewHolderListener.onItemClicked(view, getAdapterPosition());
    }
  }

}