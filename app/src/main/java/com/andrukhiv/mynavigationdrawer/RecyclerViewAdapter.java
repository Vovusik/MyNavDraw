package com.andrukhiv.mynavigationdrawer;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrukhiv.mynavigationdrawer.models.SpecificationsModel;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.ViewPropertyTransition;

import java.util.ArrayList;

public class RecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerViewAdapter.DataObjectHolder> implements Filterable {

    private static String LOG_TAG = "RecyclerViewAdapter";
    private static MyClickListener myClickListener;
    private Context context;
    private CustomFilter filter;
    protected ArrayList<SpecificationsModel> mDataset, filterList;


    public RecyclerViewAdapter(ArrayList<SpecificationsModel> myDataset) {
        mDataset = myDataset;
        filterList = myDataset;
    }

    // Повернення відфільтрованих об.
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(filterList, this);
        }
        return filter;
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView textName;
        ImageView photoSmall;

        public DataObjectHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            photoSmall = itemView.findViewById(R.id.photo_small);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }


    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.textName.setText(mDataset.get(position).getName());
        //Picasso.get().load(mDataset.get(position).getPhotoSmall()).into(holder.photoSmall);
        Glide
                .with(context)
                .load(mDataset.get(position).getPhotoSmall())
//                .apply(new RequestOptions()
//                        .placeholder(R.drawable.placeholder)
//                        .fallback(R.drawable.ic_520016)
//                        .error(R.drawable.oops))
                .thumbnail(0.5f)// зменшив розмір попередного перегляду фото у 2 рази
                .transition(GenericTransitionOptions.with(animationObject))
                .into(holder.photoSmall);

        // Анімація при завантаженні CardView
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_card, parent, false);

        context = parent.getContext();

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }


    public void addItem(SpecificationsModel dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }


    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }


    // Анімація завантаження картинки Glide
    public ViewPropertyTransition.Animator animationObject = new ViewPropertyTransition.Animator() {
        @Override
        public void animate(View view) {
            view.setAlpha(0f);

            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
            fadeAnim.setDuration(2500);
            fadeAnim.start();
        }
    };
}