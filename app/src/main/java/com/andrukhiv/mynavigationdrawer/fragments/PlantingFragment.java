package com.andrukhiv.mynavigationdrawer.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.database.DbHelper;
import com.andrukhiv.mynavigationdrawer.tables.FormationTable;
import com.bumptech.glide.Glide;


public class PlantingFragment extends Fragment {

    public PlantingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

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
                    new String[]{Integer.toString(2)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                String photo_1 = cursor.getString(1);
                String description_1 = cursor.getString(7);

                ImageView photo1 = rootView.findViewById(R.id.imageView_1);
                Glide.with(getContext()).load(photo_1).into(photo1);

                TextView description1 = rootView.findViewById(R.id.textView_1);
                description1.setText(description_1);

            }
            cursor.close();
            db.close();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getContext(), "База данных недоступна", Toast.LENGTH_SHORT);
            toast.show();
        }
        return rootView;
    }
}
