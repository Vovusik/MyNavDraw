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


public class GuyotFragment extends Fragment {

    public GuyotFragment() {
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
                    new String[]{Integer.toString(3)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                String photo_1 = cursor.getString(1);
                String photo_2 = cursor.getString(2);
                String photo_3 = cursor.getString(3);
                String photo_4 = cursor.getString(4);
                String photo_5 = cursor.getString(5);
                String photo_6 = cursor.getString(6);
                String description_1 = cursor.getString(7);
                String description_2 = cursor.getString(8);
                String description_3 = cursor.getString(9);
                String description_4 = cursor.getString(10);
                String description_5 = cursor.getString(11);
                String description_6 = cursor.getString(12);

                ImageView photo1 = rootView.findViewById(R.id.imageView_1);
                Glide.with(getContext()).load(photo_1).into(photo1);
                ImageView photo2 = rootView.findViewById(R.id.imageView_2);
                Glide.with(getContext()).load(photo_2).into(photo2);
                ImageView photo3 = rootView.findViewById(R.id.imageView_3);
                Glide.with(getContext()).load(photo_3).into(photo3);
                ImageView photo4 = rootView.findViewById(R.id.imageView_4);
                Glide.with(getContext()).load(photo_4).into(photo4);
                ImageView photo5 = rootView.findViewById(R.id.imageView_5);
                Glide.with(getContext()).load(photo_5).into(photo5);
                ImageView photo6 = rootView.findViewById(R.id.imageView_6);
                Glide.with(getContext()).load(photo_6).into(photo6);

                TextView description1 = rootView.findViewById(R.id.textView_1);
                description1.setText(description_1);
                TextView description2 = rootView.findViewById(R.id.textView_2);
                description2.setText(description_2);
                TextView description3 = rootView.findViewById(R.id.textView_3);
                description3.setText(description_3);
                TextView description4 = rootView.findViewById(R.id.textView_4);
                description4.setText(description_4);
                TextView description5 = rootView.findViewById(R.id.textView_5);
                description5.setText(description_5);
                TextView description6 = rootView.findViewById(R.id.textView_6);
                description6.setText(description_6);
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
