package com.andrukhiv.mynavigationdrawer.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.models.BugModel;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.bumptech.glide.signature.ObjectKey;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class BugPagerAdapter extends PagerAdapter {

    private List<BugModel> dataObjectList;

    public BugPagerAdapter(FragmentManager fragmentManager, List<BugModel> places) {
        this.dataObjectList = places;
    }

    // Вызывается пейджером если есть необходимость уничтожить view
    @Override
    public void destroyItem(ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

    // Создание view адаптером для педжера из элемента контейнера
    @NonNull
    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        Context context = collection.getContext();
        View layout = LayoutInflater.from(context)
                .inflate(R.layout.item_image_bug, collection, false);

        BugModel p = dataObjectList.get(position);
        String pictureUrl = p.getImageId();
        ImageView picture = layout.findViewById(R.id.imageView);

        // С помощью Picasso
//        Picasso
//
//                .get() // Используя Context
//                .load(pictureUrl) // Загружаем картинку по URL
//                .fit() // Автоматически определяем размеры ImageView
//                .centerCrop() // Масштабируем картинку
//                .into(picture); // в ImageView


        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE) // потому что имя файла всегда одинаково
                .skipMemoryCache(true);
        Glide
                .with(context)
                .load(pictureUrl)
                .apply(new RequestOptions()
                       // .diskCacheStrategy(DiskCacheStrategy.ALL)
                        //.skipMemoryCache(true)
                        .transform(new BlurTransformation(1, 1))
                )
                .thumbnail(0.5f)
               // .signature(new ObjectKey(System.currentTimeMillis() / (10 * 60 * 1000)))
                .transition(GenericTransitionOptions.with(animationObject))
                .into(picture)
        ;

        // Возвращаем "надутый" и настроенный view
        collection.addView(layout);
        return layout;
    }


    // Сколько всего элементов в контейнере
    @Override
    public int getCount() {
        return dataObjectList.size();
    }


    // Чтобы определить, нет ли уже такого view
    @Override
    public boolean isViewFromObject(View view, @NonNull Object object) {
        return view.equals(object);
    }


    // Анімація завантаження картинки Glide
    private ViewPropertyTransition.Animator animationObject = view -> {
        view.setAlpha(0f);

        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeAnim.setDuration(2500);
        fadeAnim.start();
    };
}
