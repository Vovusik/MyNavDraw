package com.andrukhiv.mynavigationdrawer.activity;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.andrukhiv.mynavigationdrawer.PreparatyHeader;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.PreparatyModel;
import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import java.util.ArrayList;


public class PreparatyActivity extends AppCompatActivity {

    private TableFixHeaders tableFixHeaders;
    private PreparatyFixHeaders preparatyFixHeaders;

    DbAdapter mDbHelper;
    ArrayList<PreparatyModel> preparaty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparaty);

        tableFixHeaders = findViewById(R.id.tablefixheaders);
        preparatyFixHeaders = new PreparatyFixHeaders(this);

        createTable();

        mDbHelper = DbAdapter.getInstance(getApplicationContext());
        preparaty = DbAdapter.getPreparaty();
    }


    private void createTable() {
        tableFixHeaders.setAdapter(preparatyFixHeaders.getAdapter());
    }


    public class PreparatyFixHeaders {
        private Context context;

        PreparatyFixHeaders(Context context) {
            this.context = context;
        }

        BaseTableAdapter getAdapter() {
            return new PreparatyHeader(context).getInstance();
        }
    }
}