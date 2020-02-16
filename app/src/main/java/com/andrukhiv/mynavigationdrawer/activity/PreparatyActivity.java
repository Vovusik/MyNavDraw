package com.andrukhiv.mynavigationdrawer.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.andrukhiv.mynavigationdrawer.PreparationDialog;
import com.andrukhiv.mynavigationdrawer.PreparatyHeader;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.PreparatyModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inqbarna.tablefixheaders.TableFixHeaders;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;
import java.util.ArrayList;


public class PreparatyActivity extends AppCompatActivity implements View.OnClickListener {

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

        FloatingActionButton mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(this);
    }


    private void createTable() {
        tableFixHeaders.setAdapter(preparatyFixHeaders.getAdapter());
    }

    @Override
    public void onClick(View v) {
        PreparationDialog.show(this);
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