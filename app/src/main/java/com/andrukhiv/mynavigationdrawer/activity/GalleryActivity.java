package com.andrukhiv.mynavigationdrawer.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.andrukhiv.mynavigationdrawer.fragments.GalleryGridFragment;
import com.andrukhiv.mynavigationdrawer.R;

import java.util.ArrayList;

/**
 * Основной вид приложения «Gallery».
 */
public class GalleryActivity extends AppCompatActivity {

    /**
     * Удерживает текущее положение изображения, которое должно быть разделено между сеткой и
     * фрагментами пейджера. Эта позиция обновляется при щелчке элемента сетки или при пейджинге пейджера.
     * <p>
     * В этом демонстрационном приложении позиция всегда указывает на индекс изображения в классе
     * {@link * com.google.samples.gridtopager.adapter.ImageData}.
     */
    public static int currentPosition;
    private static final String KEY_CURRENT_POSITION = "com.andrukhiv.gallery.key.currentPosition";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0);
            // Вернитесь сюда, чтобы предотвратить добавление дополнительных GridFragments при изменении ориентации.
            return;
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_up_arrow_icon);// замените своим пользовательским значком
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, new GalleryGridFragment(), GalleryGridFragment.class.getSimpleName())
                .commit();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_POSITION, currentPosition);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
