package com.andrukhiv.mynavigationdrawer;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

// Адаптер виконує дві основні функції: він створює всі уявлення, видимі в RecyclerView,
// і пов'язує кожне уявлення з певним блоком даних.

public class GrapesAdapter extends RecyclerView.Adapter<GrapesAdapter.ViewHolder> { //implements FastScroller.SectionIndexer

    // Змінні для назв і ідентифікаторів ресурсів зображень різних сортів винограду.
    private String[] name;
    private int[] imageId;

    // Додати об'єкт Listener як приватну змінну.
    private Listener listener;

    // Передає дані адаптера в конструкторі.
    public GrapesAdapter(String[] name, int[] imageId) {
        // Змінні для назв і ідентифікаторів ресурсів зображень різних сортів винограду.
        this.name = name;
        this.imageId = imageId;
    }

    // Інтерфейс.
    public interface Listener {
        void onClick(int position);
    }

    // Клас ViewHolder повідомляє з даними повинен працювати адаптер.
    // Надає посилання на уявлення, що використовуються в RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // У компоненті RecyclerView повинні відображатися картки, тому ми вказуємо,
        // що ViewHolder містить уявлення CardView.
        // Кожен об'єкт ViewHolder відображає CardView.
        private CardView cardView;

        public ViewHolder(CardView v) {
            super(v);// Виклик конструктора суперкласу
            cardView = v;
        }
    }

    // Активності і фрагменти використовують цей метод для реєстрації себе в якості слухача.
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    // Метод onCreateViewHolder () використовується для заповнення уявлень
    @Override
    public GrapesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Вказати, який макет повинен використовуватися для вмісту ViewHolder.
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card, parent, false);// Використовувати макет для уявлень CardView.
        return new ViewHolder(cv);
    }

    // Метод onBindViewHolder () заповнює уявлення даними
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        // Зображення виводиться в графічному уявленні ImageView.
        ImageView imageView = cardView.findViewById(R.id.info_image);
        Drawable drawable = cardView.getResources().getDrawable(imageId[position]);
        imageView.setImageDrawable(drawable);// Заповнити даними компоненти ImageView і TextView всередині CardView.
        imageView.setContentDescription(name[position]);
        // Назва виводиться в компоненті TextView.
        TextView textView = cardView.findViewById(R.id.info_text);
        textView.setText(name[position]);

        // При натисканні на CardView викликати метод onClick () інтерфейсу Listener.
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    // Метод getItemCount () повертає кількість варіантів в наборі даних
    // Довжина масиву name дорівнює кількості елементів даних в RecyclerView.
    // Кількість елементів даних.
    @Override
    public int getItemCount() {
        return name.length;
    }
}