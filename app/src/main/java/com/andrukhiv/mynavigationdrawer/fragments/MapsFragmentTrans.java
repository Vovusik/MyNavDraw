package com.andrukhiv.mynavigationdrawer.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.adapters.MapsAdapterTrans;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;
import java.util.Objects;

import jp.wasabeef.glide.transformations.BlurTransformation;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MapsFragmentTrans extends Fragment implements View.OnClickListener, EasyPermissions.PermissionCallbacks {

    private TextView mTitle;
    private ImageView mImage;
    private int index;

    private static final String TAG = "MapsFragmentZakarpattya";

    private static final int RC_CALL = 101;
    private static final int RC_LOCATION = 102;

    private static final String[] CALL = {Manifest.permission.CALL_PHONE};
    private static final String[] LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};

    private CardView mCardView;
    static BottomSheetDialogFragment myBottomSheet;

    public MapsFragmentTrans() {
    }


    public static MapsFragmentTrans newInstance(int i) {

        MapsFragmentTrans fragment = new MapsFragmentTrans();
        Bundle args = new Bundle();
        args.putInt("INDEX", i);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_maps_pager, container, false);

        mCardView = rootView.findViewById(R.id.card_academy);
        mCardView.setOnClickListener(this);

        mTitle = rootView.findViewById(R.id.location_title);
        mImage = rootView.findViewById(R.id.location_image);

        Button mLink = rootView.findViewById(R.id.web);
        mLink.setOnClickListener(this);

        Button mDirections = rootView.findViewById(R.id.directions);
        mDirections.setOnClickListener(this);

        Button mCall = rootView.findViewById(R.id.call);
        mCall.setOnClickListener(this);

        return rootView;
    }


    // Чтобы начать использовать EasyPermissions, попросите Activity(или Fragment)
    // переопределить onRequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions обрабатывает результат запроса.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) index = args.getInt("INDEX", 0);

        mTitle.setText(MapsAdapterTrans.TITLES.get(index));
        // mDescription.setText(MapsAdapterTrans.getDescripion(index));
        // mAddress.setText(MapsAdapterTrans.getAddress(index));

        Glide
                .with(getContext())
                .load(MapsAdapterTrans.getImage(index))
                .apply(new RequestOptions()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .skipMemoryCache(true)
                                .transform(new BlurTransformation(1, 1))
                )
                .thumbnail(0.5f)
                //.signature(new ObjectKey(System.currentTimeMillis() / (10 * 60 * 1000)))
                // .transition(GenericTransitionOptions.with(animationObject))
                .into(mImage);


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.web:
                Uri.parse("http://" + MapsAdapterTrans.getTextWeb(index));
                openCustomTab();
                break;

            case R.id.call:
                callTask();
                break;

            case R.id.directions:
                locationTask();
                break;

            case R.id.card_academy:
                openBottomSheetDialog();

                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                }
                break;
        }
    }

    // Используется для проверки наличия у приложения необходимых разрешений.
    // Этот метод может принимать любое количество разрешений в качестве последнего аргумента.
    private boolean hasCallPermissions() {
        return EasyPermissions.hasPermissions(Objects.requireNonNull(getContext()), CALL);
    }


    private boolean hasLocationPermissions() {
        return EasyPermissions.hasPermissions(Objects.requireNonNull(getContext()), LOCATION);
    }


    // Использование AfterPermissionGrantedаннотации. Это необязательно,
    // но предусмотрено для удобства.
    // Если все разрешения в данном запросе предоставлены, будут выполнены все методы,
    // отмеченные соответствующим кодом запроса (убедитесь, что у вас есть уникальный код запроса).
    // Аннотированный метод должен быть пустым и без входных параметров (вместо этого вы можете
    // использовать onSaveInstanceState , чтобы сохранить состояние ваших подавленных параметров).
    // Это должно упростить общий поток необходимости запуска запрашивающего метода после того,
    // как все его разрешения были предоставлены. Это также может быть достигнуто путем добавления
    // логики onPermissionsGrantedобратного вызова.
    @AfterPermissionGranted(RC_CALL)
    private void callTask() {
        if (hasCallPermissions()) {
            Toast.makeText(getContext(), R.string.hasPermissions, Toast.LENGTH_SHORT).show();
            callPhone();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_call), RC_CALL, CALL);
        }
    }


    @AfterPermissionGranted(RC_LOCATION)
    private void locationTask() {
        if (hasLocationPermissions()) {
            Toast.makeText(getContext(), R.string.hasPermissions, Toast.LENGTH_LONG).show();
            locationDirections();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_location), RC_LOCATION, LOCATION);
        }
    }


    private void callPhone() {
        Uri call = Uri.parse("tel:" + MapsAdapterTrans.getTextPhone(index));
        Intent callIntent = new Intent(Intent.ACTION_DIAL, call);
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }


    private void locationDirections() {
        Uri location = Uri.parse(MapsAdapterTrans.getNavigationPosition(index));
        Intent locationIntent = new Intent(Intent.ACTION_VIEW, location);
        locationIntent.setPackage("com.google.android.apps.maps");
        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(locationIntent);
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }


    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        // (Необязательно) Проверьте, запретил ли пользователь какие-либо разрешения и
        // отметит «НИКОГДА НЕ СМОЖАТЬ СНОВА».
        // Это отобразит диалоговое окно, в котором они будут отображаться,
        // чтобы включить разрешение в настройках приложения.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }


    @SuppressLint("StringFormatMatches")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                    getContext(),
                    getString(R.string.returned_from_app_settings_to_activity,
                            hasCallPermissions() ? yes : no,
                            hasLocationPermissions() ? yes : no),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }


    private void openCustomTab() {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        // установить цвета панели инструментов
        builder.setToolbarColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary));
        builder.setSecondaryToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        // поширити додаток
        builder.addDefaultShareMenuItem();
        // показувати заголовок
        builder.setShowTitle(true);
        // змінюю хрестик на стрілочку повернення додому
        builder.setCloseButtonIcon(BitmapFactory.decodeResource(
                getResources(), R.drawable.toolbar_ic_up_arrow));

        // настроить анимацию начала и выхода
        builder.setStartAnimations(getContext(), R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(getContext(), android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(getContext(), Uri.parse("http://" + MapsAdapterTrans.getTextWeb(index)));
    }


    private void openBottomSheetDialog() {
        FragmentManager fm = getChildFragmentManager();
        myBottomSheet = MapBottomSheetDialogFragment.newInstance(index);
        myBottomSheet.show(fm, myBottomSheet.getTag());
    }

}
