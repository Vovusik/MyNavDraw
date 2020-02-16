package com.andrukhiv.mynavigationdrawer.fragments;

import android.animation.ObjectAnimator;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.database.DbHelper;
import com.andrukhiv.mynavigationdrawer.tables.FormationTable;
import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.bumptech.glide.signature.ObjectKey;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class Budova extends Fragment {

    public Budova() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_budova, container, false);

        SQLiteOpenHelper grapesDatabaseHelper = new DbHelper(getContext());
        try {
            SQLiteDatabase db = grapesDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(FormationTable.STRUCTURE_FORMATION_TABLE,
                    new String[]{FormationTable.STRUCTURE_FORMATION_COLUMN_NAME,
                            FormationTable.STRUCTURE_FORMATION_COLUMN_PHOTO_1,
                            FormationTable.STRUCTURE_FORMATION_COLUMN_PHOTO_2,
                            FormationTable.STRUCTURE_FORMATION_COLUMN_PHOTO_3,
                            FormationTable.STRUCTURE_FORMATION_COLUMN_PHOTO_4,
                            FormationTable.STRUCTURE_FORMATION_COLUMN_PHOTO_5,
                            FormationTable.STRUCTURE_FORMATION_COLUMN_PHOTO_6,
                            FormationTable.STRUCTURE_FORMATION_COLUMN_DESCRIPTION_1,
                            FormationTable.STRUCTURE_FORMATION_COLUMN_DESCRIPTION_2,
                            FormationTable.STRUCTURE_FORMATION_COLUMN_DESCRIPTION_3,
                            FormationTable.STRUCTURE_FORMATION_COLUMN_DESCRIPTION_4,
                            FormationTable.STRUCTURE_FORMATION_COLUMN_DESCRIPTION_5,
                            FormationTable.STRUCTURE_FORMATION_COLUMN_DESCRIPTION_6},
                    "id = ?",
                    new String[]{Integer.toString(1)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                String photo_1 = cursor.getString(1);
                String description_1 = cursor.getString(7);

                ImageView photo = rootView.findViewById(R.id.imageView_1);
                Glide.with(getContext())
                        .load(photo_1)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .skipMemoryCache(true)
                                .transform(new BlurTransformation(1, 1))
                        )
                        .thumbnail(0.5f)
                        .signature(new ObjectKey(System.currentTimeMillis() / (10 * 60 * 1000)))
                        .transition(GenericTransitionOptions.with(animationObject))
                        .into(photo);

                TextView description = rootView.findViewById(R.id.textView_1);
                description.setText(description_1);
            }

            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getContext(), "База данных недоступна", Toast.LENGTH_SHORT);
            toast.show();
        }
        return rootView;
    }

    // Анімація завантаження картинки Glide
    private ViewPropertyTransition.Animator animationObject = view -> {
        view.setAlpha(0f);
        ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        fadeAnim.setDuration(2500);
        fadeAnim.start();
    };
}
