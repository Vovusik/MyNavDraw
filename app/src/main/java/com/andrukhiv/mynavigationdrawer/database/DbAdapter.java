package com.andrukhiv.mynavigationdrawer.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.andrukhiv.mynavigationdrawer.models.FormationModel;
import com.andrukhiv.mynavigationdrawer.models.KitchenModel;
import com.andrukhiv.mynavigationdrawer.models.LibraryModel;
import com.andrukhiv.mynavigationdrawer.models.ReproductionModel;
import com.andrukhiv.mynavigationdrawer.models.SpecificationsModel;
import com.andrukhiv.mynavigationdrawer.tables.KitchenTable;
import com.andrukhiv.mynavigationdrawer.tables.LibraryTable;
import com.andrukhiv.mynavigationdrawer.tables.ReproductionTable;
import com.andrukhiv.mynavigationdrawer.tables.SpecificationsTable;
import com.andrukhiv.mynavigationdrawer.tables.FormationTable;

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


    public ArrayList<SpecificationsModel> getAllGrapes() {

        ArrayList<SpecificationsModel> result = new ArrayList<>();
        String sql = "SELECT * FROM " +
                SpecificationsTable.SPECIFICATIONS_TABLE;
        Cursor cursor = mDb.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(new SpecificationsModel(
                        cursor.getLong(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_VARIETIES_ID)),
                        cursor.getString(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_PHOTO_SMALL)),
                        cursor.getString(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_PHOTO_LARGE)),
                        cursor.getString(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_LINK)),
                        cursor.getInt(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_FAVORITE)),
                        cursor.getInt(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_SORT))
                ));
            }
            cursor.close();
        }
        return result;
    }

    public ArrayList<SpecificationsModel> getTableGrapes() {

        ArrayList<SpecificationsModel> result = new ArrayList<>();
        String sql = "SELECT * FROM " +
                SpecificationsTable.SPECIFICATIONS_TABLE +
                " WHERE " +
                SpecificationsTable.SPECIFICATIONS_COLUMN_SORT + " =2";// столові сорти

        Cursor cursor = mDb.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(new SpecificationsModel(
                        cursor.getLong(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_VARIETIES_ID)),
                        cursor.getString(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_PHOTO_SMALL)),
                        cursor.getString(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_PHOTO_LARGE)),
                        cursor.getString(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_LINK)),
                        cursor.getInt(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_FAVORITE)),
                        cursor.getInt(cursor.getColumnIndex(SpecificationsTable.SPECIFICATIONS_COLUMN_SORT))
                ));
            }
            cursor.close();
        }
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
            cursor.close();
        }
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
            cursor.close();
        }
        return result;
    }


    public ArrayList<FormationModel> getFormation() {

        ArrayList<FormationModel> result = new ArrayList<>();
        String sql = "SELECT * FROM " + FormationTable.STRUCTURE_FORMATION_TABLE;
        Cursor cursor = mDb.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(new FormationModel(
                        cursor.getLong(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_PHOTO_1)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_PHOTO_2)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_PHOTO_3)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_PHOTO_4)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_PHOTO_5)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_PHOTO_6)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_DESCRIPTION_1)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_DESCRIPTION_2)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_DESCRIPTION_3)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_DESCRIPTION_4)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_DESCRIPTION_5)),
                        cursor.getString(cursor.getColumnIndex(FormationTable.STRUCTURE_FORMATION_COLUMN_DESCRIPTION_6))
                ));
            }
            cursor.close();
        }
        return result;
    }


    public ArrayList<LibraryModel> getLibrary() {

        ArrayList<LibraryModel> result = new ArrayList<>();
        String sql = "SELECT * FROM " +
                LibraryTable.LIBRARY_NAME_TABLE;
        Cursor cursor = mDb.rawQuery(sql, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result.add(new LibraryModel(
                        cursor.getLong(cursor.getColumnIndex(LibraryTable.LIBRARY_COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(LibraryTable.LIBRARY_COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(LibraryTable.LIBRARY_COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(LibraryTable.LIBRARY_COLUMN_AUTHOR)),
                        cursor.getString(cursor.getColumnIndex(LibraryTable.LIBRARY_COLUMN_LINK))
                ));
            }
            cursor.close();
        }
        return result;
    }
}