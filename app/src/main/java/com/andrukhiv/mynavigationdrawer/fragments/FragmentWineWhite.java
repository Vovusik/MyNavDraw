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
import com.andrukhiv.mynavigationdrawer.database.DbHelper;
import com.andrukhiv.mynavigationdrawer.tables.KitchenTable;


public class FragmentWineWhite extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_nested_scroll_tab, container, false);

        SQLiteOpenHelper kitchenDatabaseHelper = new DbHelper(getContext());

        try {
            SQLiteDatabase db = kitchenDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(KitchenTable.KITCHEN_TABLE,
                    new String[]{KitchenTable.KITCHEN_COLUMN_NAME, KitchenTable.KITCHEN_COLUMN_DESCRIPTION},
                    KitchenTable.KITCHEN_COLUMN_NAME + "= ?",
                    new String[]{"Вино біле"},
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