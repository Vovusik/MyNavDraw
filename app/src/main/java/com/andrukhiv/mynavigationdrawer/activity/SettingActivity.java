//package com.andrukhiv.mynavigationdrawer.activity;
//
//import android.content.Context;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//
//import com.andrukhiv.mynavigationdrawer.LocaleHelper;
//import com.andrukhiv.mynavigationdrawer.R;
//import com.andrukhiv.mynavigationdrawer.fragments.SettingFragment;
//
//import java.util.Objects;
//
//public class SettingActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.toolbar_ic_up_arrow);
//
//
//        //getSupportFragmentManager().beginTransaction()
//        getFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new SettingFragment())
//                .commit();
//
//
////        getSupportFragmentManager()
////                .beginTransaction()
////                .replace(R.id.content, new SettingFragment())
////                .commit();
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        if (SettingFragment.onBackButtonRecreateView) {
//            SettingFragment.onBackButtonRecreateView = false;
//            LocaleHelper.updateViewNeeded = true;
//        }
//    }
//
//
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(LocaleHelper.onAttach(base));
//    }
//
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//        getDelegate().onPostCreate(savedInstanceState);
//    }
//
//
//    @NonNull
//    @Override
//    public MenuInflater getMenuInflater() {
//        return getDelegate().getMenuInflater();
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//}
