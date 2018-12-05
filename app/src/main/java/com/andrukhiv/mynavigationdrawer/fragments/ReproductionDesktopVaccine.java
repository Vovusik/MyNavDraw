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
import android.widget.TextView;
import android.widget.Toast;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.database.DbHelper;
import com.andrukhiv.mynavigationdrawer.models.ReproductionModel;
import com.andrukhiv.mynavigationdrawer.tables.ReproductionTable;

import java.util.ArrayList;
import java.util.Objects;

public class ReproductionDesktopVaccine extends Fragment {

    public ReproductionDesktopVaccine() {
    }

    DbAdapter mDbHelper;
    ArrayList<ReproductionModel> reproduction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_kitchen, container, false);

        mDbHelper = DbAdapter.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
        reproduction = mDbHelper.getReproduction();

        SQLiteOpenHelper reproductionDatabaseHelper = new DbHelper(getContext());

        try {
            SQLiteDatabase db = reproductionDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(ReproductionTable.REPRODUCTION_TABLE,
                    new String[]{ReproductionTable.REPRODUCTION_COLUMN_NAME, ReproductionTable.REPRODUCTION_COLUMN_DESCRIPTION},
                    "id = ?",
                    new String[]{Integer.toString(2)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                String descriptionText = cursor.getString(1);
                TextView description = rootView.findViewById(R.id.description);
                description.setText(descriptionText);
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
