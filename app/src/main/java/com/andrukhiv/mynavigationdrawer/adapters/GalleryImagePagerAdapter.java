package com.andrukhiv.mynavigationdrawer.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.andrukhiv.mynavigationdrawer.fragments.GalleryImageFragment;

import static com.andrukhiv.mynavigationdrawer.adapters.GalleryImageData.IMAGE_DRAWABLES;

public class GalleryImagePagerAdapter extends FragmentStatePagerAdapter {

  public GalleryImagePagerAdapter(Fragment fragment) {
    super(fragment.getChildFragmentManager());
  }


  @Override
  public int getCount() {
    return IMAGE_DRAWABLES.length;
  }


  @Override
  public Fragment getItem(int position) {
    return GalleryImageFragment.newInstance(IMAGE_DRAWABLES[position]);
  }
}