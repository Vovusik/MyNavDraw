package com.andrukhiv.mynavigationdrawer.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.app.SharedElementCallback;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;

import com.andrukhiv.mynavigationdrawer.activity.GalleryActivity;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.adapters.GalleryGridAdapter;

import java.util.List;
import java.util.Map;

/**
 * Фрагмент для отображения сетки изображений.
 */
public class GalleryGridFragment extends Fragment {

  private RecyclerView recyclerView;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_grid_gallery, container, false);
    recyclerView.setAdapter(new GalleryGridAdapter(this));

    prepareTransitions();
    postponeEnterTransition();

    return recyclerView;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    scrollToPosition();
  }

  /**
   * Прокручивает вид переработчика, чтобы показать последний просмотренный элемент в сетке.
   * Это важно когда навигация назад из сетки.
   */
  private void scrollToPosition() {
    recyclerView.addOnLayoutChangeListener(new OnLayoutChangeListener() {
      @Override
      public void onLayoutChange(View v,
          int left,
          int top,
          int right,
          int bottom,
          int oldLeft,
          int oldTop,
          int oldRight,
          int oldBottom) {
        recyclerView.removeOnLayoutChangeListener(this);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        assert layoutManager != null;
        View viewAtPosition = layoutManager.findViewByPosition(GalleryActivity.currentPosition);
        // Scroll to position if the view for the current position is null (not currently part of
        // layout manager children), or it's not completely visible.
        if (viewAtPosition == null || layoutManager
            .isViewPartiallyVisible(viewAtPosition, false, true)) {
          recyclerView.post(() -> layoutManager.scrollToPosition(GalleryActivity.currentPosition));
        }
      }
    });
  }

  /**
   * Prepares the shared element transition to the pager fragment, as well as the other transitions
   * that affect the flow.
   */
  private void prepareTransitions() {
    setExitTransition(TransitionInflater.from(getContext())
        .inflateTransition(R.transition.grid_exit_transition));

    // A similar mapping is set at the GalleryImagePagerFragment with a setEnterSharedElementCallback.
    setExitSharedElementCallback(
        new SharedElementCallback() {
          @Override
          public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            // Locate the ViewHolder for the clicked position.
            RecyclerView.ViewHolder selectedViewHolder = recyclerView
                .findViewHolderForAdapterPosition(GalleryActivity.currentPosition);
            if (selectedViewHolder == null) {
              return;
            }

            // Map the first shared element name to the child ImageView.
            sharedElements
                .put(names.get(0), selectedViewHolder.itemView.findViewById(R.id.card_image));
          }
        });
  }
}
