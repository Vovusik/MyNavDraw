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

        createTable(PreparatyFixHeaders.ORIGINAL);

        mDbHelper = DbAdapter.getInstance(getApplicationContext());
        preparaty = DbAdapter.getPreparaty();
    }


    private void createTable(int type) {
        tableFixHeaders.setAdapter(preparatyFixHeaders.getAdapter(type));
    }


    public class PreparatyFixHeaders {
        public static final int ORIGINAL = 0;
        private Context context;

        public PreparatyFixHeaders(Context context) {
            this.context = context;
        }

        public BaseTableAdapter getAdapter(int type) {
            return new PreparatyHeader(context).getInstance();
        }
    }
}