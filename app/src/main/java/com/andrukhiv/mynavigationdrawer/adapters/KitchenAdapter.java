package com.andrukhiv.mynavigationdrawer.adapters;


import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.activity.KitchenDetailsActivity;
import com.andrukhiv.mynavigationdrawer.models.KitchenModel;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.bumptech.glide.signature.ObjectKey;

import java.util.List;
import java.util.Objects;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class KitchenAdapter extends RecyclerView.Adapter<KitchenAdapter.ViewHolder> {

    private RecyclerView parentRecycler;
    private List<KitchenModel> data;
    private static final String TAG = "kitchenLogs";

    public KitchenAdapter(List<KitchenModel> data) {
        this.data = data;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(Objects.requireNonNull(recyclerView));
        parentRecycler = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.kitchen_item_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final KitchenModel forecast = data.get(position);

        holder.textView.setText(forecast.getName());
        Glide.with(holder.itemView.getContext())
                .load(forecast.getImageGlassIcon())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .transform(new BlurTransformation(1, 1))
                )
                .thumbnail(0.5f)
                .signature(new ObjectKey(System.currentTimeMillis() / (10 * 60 * 1000)))
                .transition(GenericTransitionOptions.with(animationObject))
                .into(holder.imageView);
        holder.textView.setText(forecast.getName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView textView;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.city_image);
            textView = itemView.findViewById(R.id.city_name);

            itemView.findViewById(R.id.container).setOnClickListener(this);
        }

        public void showText() {
            int parentHeight = ((View) imageView.getParent()).getHeight();
            float scale = (parentHeight - textView.getHeight()) / (float) imageView.getHeight();
            imageView.setPivotX(imageView.getWidth() * 0.5f);
            imageView.setPivotY(0);
            imageView
                    .animate()
                    .scaleX(scale)
                    .withEndAction(() -> {
                        textView.setVisibility(View.VISIBLE);
                        imageView.setColorFilter(Color.WHITE);
                    })
                    .scaleY(scale)
                    .setDuration(200)
                    .start();
        }

        public void hideText() {
            imageView.setColorFilter(ContextCompat.getColor(imageView.getContext(), R.color.grayIconTint));
            textView.setVisibility(View.INVISIBLE);
            imageView
                    .animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(200)
                    .start();
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.container) {

                Context context = itemView.getContext();
                Intent intent = new Intent(v.getContext(), KitchenDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KitchenDetailsActivity.EXTRA_KITCHEN_ID, data.get(getLayoutPosition()));
                intent.putExtras(bundle);
                context.startActivity(intent);

                Log.d(TAG, "Position " + data.get(getLayoutPosition()).getName());

            }
            parentRecycler.smoothScrollToPosition(getAdapterPosition());


        }
    }


    // Анімація завантаження картинки Glide
    private ViewPropertyTransition.Animator animationObject = view -> {
        view.setAlpha(0f);

        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeAnim.setDuration(2500);
        fadeAnim.start();
    };
}



