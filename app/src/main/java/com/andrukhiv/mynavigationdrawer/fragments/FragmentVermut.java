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
import com.andrukhiv.mynavigationdrawer.models.KitchenModel;
import com.andrukhiv.mynavigationdrawer.tables.KitchenTable;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentVermut extends Fragment {

    public FragmentVermut() {
    }

    DbAdapter mDbHelper;
    ArrayList<KitchenModel> kitchen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_kitchen, container, false);

        mDbHelper = DbAdapter.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
        kitchen = mDbHelper.getKitchen();

        // Получение ссылки на базу данных
        SQLiteOpenHelper kitchenDatabaseHelper = new DbHelper(getContext());
        // Для получения ссылки на базу данных используются методы помощника SQLite
        // getReadableDatabase() - вызывается в том случае, если  нужен доступ к базе
        // данных только для чтения

        // Курсор обеспечивает чтение и запись информации в базу данных.
        try {
            SQLiteDatabase db = kitchenDatabaseHelper.getReadableDatabase();
            // Курсор создается методом query()
            Cursor cursor = db.query(KitchenTable.KITCHEN_TABLE,
                    new String[]{KitchenTable.KITCHEN_COLUMN_NAME, KitchenTable.KITCHEN_COLUMN_DESCRIPTION},
                    "id = ?",// Мы хотим получить описание, у которых столбец NAME содержит значение «Августин».
                    new String[]{Integer.toString(1)}, // Возвращает записи из таблицы "kitchen", у которых столбец _id содержит значение 1
                    null, null, null);

            // Получение названия, описания и идентификатора ресурса изображения для
            // обновления представлений

            // Чтобы перейти к первой записи набора, возвращенного курсором, используйте метод moveToFirst()
            if (cursor.moveToFirst()) {
                // Это второй столбец в курсоре (индекс 1), он содержит значения String
                String descriptionText = cursor.getString(1);
                // В поля идентификатора ресурса изображения и описания заносятся значения из базы данных.
                TextView description = rootView.findViewById(R.id.description);
                description.setText(descriptionText);
            }
            // Закрыть курсор и базу данных.
            cursor.close();
            db.close();
            // Если получить ссылку на базу данных не удается, Android выдает исключение SQLiteException
        } catch (SQLiteException e) {
            // Если выдается исключение SQLiteException, значит, с базой данных возникли проблемы.
            // В этом случае уведомление используется для вывода сообщения
            Toast toast = Toast.makeText(getContext(), "База данных недоступна", Toast.LENGTH_SHORT);
            toast.show();
        }
        return rootView;
    }
}
