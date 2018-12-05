package com.andrukhiv.mynavigationdrawer.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.andrukhiv.mynavigationdrawer.models.KitchenModel;
import com.andrukhiv.mynavigationdrawer.models.ReproductionModel;
import com.andrukhiv.mynavigationdrawer.models.VarietiesModel;
import com.andrukhiv.mynavigationdrawer.tables.KitchenTable;
import com.andrukhiv.mynavigationdrawer.tables.ReproductionTable;
import com.andrukhiv.mynavigationdrawer.tables.VarietiesTable;

import java.io.IOException;
import java.util.ArrayList;

public class DbAdapter {

    private static final String TAG = "DbAdapter";

    private SQLiteDatabase mDb;
    private DbHelper mDbHelper;

    public static final int MODE_ALL = 0;
    public static final int MODE_TABLE = 1;
    public static final int MODE_WINE = 2;
    public static final int MIN_SEEDLESS = 3;

    @SuppressLint("StaticFieldLeak")
    private static DbAdapter sInstance;


    public static synchronized DbAdapter getInstance(Context context) {

        // Используйте контекст приложения, который гарантирует, что вы случайно
        // не протекаете контекст Activity.
        // См. Эту статью для получения дополнительной информации: http://bit.ly/6LRzfx

        if (sInstance == null) {
            sInstance = new DbAdapter(context.getApplicationContext());
        }
        return sInstance;
    }


    public DbAdapter(Context context) {
        Context mContext = context;
        mDbHelper = new DbHelper(mContext);
    }


    public DbAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }


    public DbAdapter open() throws SQLException {
        try {
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getReadableDatabase();
        } catch (SQLException mSQLException) {
            Log.e(TAG, "open >>" + mSQLException.toString());
            throw mSQLException;
        }
        return this;
    }


    public void close() {
        mDbHelper.close();
    }


    public ArrayList<VarietiesModel> getGrapes() {

        ArrayList<VarietiesModel> result = new ArrayList<>();
        String sql = "SELECT * FROM " + VarietiesTable.VARIETIES_TABLE;
        Cursor cursor = mDb.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(new VarietiesModel(
                        cursor.getLong(cursor.getColumnIndex(VarietiesTable.VARIETIES_COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(VarietiesTable.VARIETIES_COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(VarietiesTable.VARIETIES_COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(VarietiesTable.VARIETIES_COLUMN_PHOTO_SMALL)),
                        cursor.getString(cursor.getColumnIndex(VarietiesTable.VARIETIES_COLUMN_PHOTO_LARGE)),
                        cursor.getString(cursor.getColumnIndex(VarietiesTable.VARIETIES_COLUMN_LINK)),
                        cursor.getInt(cursor.getColumnIndex(VarietiesTable.VARIETIES_COLUMN_FAVORITE)),
                        cursor.getInt(cursor.getColumnIndex(VarietiesTable.VARIETIES_COLUMN_SORTY))
                ));
            }
        }
        cursor.close();
        return result;
    }


    public ArrayList<KitchenModel> getKitchen() {

        ArrayList<KitchenModel> result = new ArrayList<>();
        String sql = "SELECT * FROM " + KitchenTable.KITCHEN_TABLE;
        Cursor cursor = mDb.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(new KitchenModel(
                        cursor.getLong(cursor.getColumnIndex(KitchenTable.KITCHEN_COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(KitchenTable.KITCHEN_COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(KitchenTable.KITCHEN_COLUMN_NAME))
                ));
            }
        }
        cursor.close();
        return result;
    }

    public ArrayList<ReproductionModel> getReproduction() {

        ArrayList<ReproductionModel> result = new ArrayList<>();
        String sql = "SELECT * FROM " + ReproductionTable.REPRODUCTION_TABLE;
        Cursor cursor = mDb.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(new ReproductionModel(
                        cursor.getLong(cursor.getColumnIndex(ReproductionTable.REPRODUCTION_COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(ReproductionTable.REPRODUCTION_COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(ReproductionTable.REPRODUCTION_COLUMN_DESCRIPTION))
                ));
            }
        }
        cursor.close();
        return result;
    }



}