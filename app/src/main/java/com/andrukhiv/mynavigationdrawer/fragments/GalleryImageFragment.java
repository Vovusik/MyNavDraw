package com.andrukhiv.mynavigationdrawer.fragments;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.andrukhiv.mynavigationdrawer.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.IOException;

import static java.lang.String.valueOf;

/**
 * Фрагмент для отображения изображения.
 */
public class GalleryImageFragment extends Fragment {

    private static final String KEY_IMAGE_RES = "com.google.samples.gridtopager.key.imageRes";

    public static GalleryImageFragment newInstance(String imageDrawable) {
        GalleryImageFragment fragment = new GalleryImageFragment();
        Bundle argument = new Bundle();
        argument.putString(KEY_IMAGE_RES, String.valueOf(imageDrawable));
        fragment.setArguments(argument);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_image_galley, container, false);

        Bundle arguments = getArguments();

       final String imageRes = arguments.getString(KEY_IMAGE_RES);

        view.findViewById(R.id.image).setTransitionName(valueOf(imageRes));

        FloatingActionButton mFabWallpaper = view.findViewById(R.id.fab_wallpaper);
        mFabWallpaper.setOnClickListener(new FloatingActionButton.OnClickListener() {

            public void onClick(View view) {
                Glide.with(getContext())
                        .asBitmap()
                        .load(imageRes)
                        .into(new SimpleTarget<Bitmap>() {

                            @SuppressLint("WrongConstant")
                            public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                                Toast.makeText(getActivity(), "Будь ласка, зачекайте....", 1).show();
                                try {
                                    WallpaperManager.getInstance(getContext()).setBitmap(bitmap);
                                    Toast.makeText(getActivity(), "Шпалери встановлено успішно", Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getActivity(), "" + e, 0).show();
                                }
                            }
                        });
            }
        });

        // Загрузите изображение с помощью Glide, чтобы предотвратить ошибку OOM, когда рисунки слишком велики.
        Glide.with(this)
                .load(imageRes)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable>
                            target, boolean isFirstResource) {
                        // PostponeEnterTransition вызывается в родительском GalleryImagePagerFragment,
                        // поэтому startPostponedEnterTransition () также должен быть вызван на него,
                        // чтобы получить переход в случае сбоя.
                        getParentFragment().startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                            target, DataSource dataSource, boolean isFirstResource) {
                        // The postponeEnterTransition is called on the parent GalleryImagePagerFragment, so the
                        // startPostponedEnterTransition() should also be called on it to get the transition
                        // going when the image is ready.
                        getParentFragment().startPostponedEnterTransition();
                        return false;
                    }
                })
                .into((ImageView) view.findViewById(R.id.image));
        return view;
    }
}
