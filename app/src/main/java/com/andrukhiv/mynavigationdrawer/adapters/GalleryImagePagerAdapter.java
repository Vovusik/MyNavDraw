package com.andrukhiv.mynavigationdrawer.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

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