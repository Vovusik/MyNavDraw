package com.andrukhiv.mynavigationdrawer.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.database.DbHelper;
import com.andrukhiv.mynavigationdrawer.models.LaboratoryModel;
import com.andrukhiv.mynavigationdrawer.tables.LaboratoryInstruction;

import java.util.ArrayList;


public class LaboratoryAcidityActivity extends AppCompatActivity {


    DbAdapter mDbHelper;
    ArrayList<LaboratoryModel> laboratoryInstruction;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory_information);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.toolbar_ic_up_arrow);
        }

        mDbHelper = DbAdapter.getInstance(getApplicationContext());
        laboratoryInstruction = mDbHelper.getLaboratoryInstruction();

        SQLiteOpenHelper laboratoryInstructionDatabaseHelper = new DbHelper(this);

        try {
            SQLiteDatabase db = laboratoryInstructionDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query(LaboratoryInstruction.LABORATORY_NAME_TABLE,
                    new String[]{
                            LaboratoryInstruction.LABORATORY_COLUMN_NAME,
                           LaboratoryInstruction.LABORATORY_COLUMN_INSTRUCTION
                    },
                    "id = ?",
                    new String[]{Integer.toString(2)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                String acidityInstruction = cursor.getString(1);
                TextView instruction = findViewById(R.id.information);
                instruction.setText(acidityInstruction);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "База данных недоступна", Toast.LENGTH_SHORT);
            toast.show();
        }
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
