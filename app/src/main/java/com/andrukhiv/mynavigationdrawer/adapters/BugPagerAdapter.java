package com.andrukhiv.mynavigationdrawer.adapters;

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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;


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
                        .fitCenter()
                        .centerCrop()

                )
                //.apply(requestOptions)
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
}
