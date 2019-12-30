package com.andrukhiv.mynavigationdrawer.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.database.DbHelper;
import com.andrukhiv.mynavigationdrawer.tables.ReproductionTable;

public class ReproductionChubuk extends Fragment {

    public ReproductionChubuk() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_nested_scroll_tab, container, false);

//        DbAdapter mDbHelper = DbAdapter.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
       // ArrayList<ReproductionModel> reproduction = mDbHelper.getReproduction();

        SQLiteOpenHelper reproductionDatabaseHelper = new DbHelper(getContext());

        try {
            SQLiteDatabase db = reproductionDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(ReproductionTable.REPRODUCTION_TABLE,
                    new String[]{ReproductionTable.REPRODUCTION_COLUMN_NAME, ReproductionTable.REPRODUCTION_COLUMN_DESCRIPTION},
                    "id = ?",
                    new String[]{Integer.toString(1)},
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
