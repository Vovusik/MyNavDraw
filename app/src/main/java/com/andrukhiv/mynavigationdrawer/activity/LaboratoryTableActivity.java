package com.andrukhiv.mynavigationdrawer.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.adapters.LaboratoryTableAdapter;
import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.LaboratoryTableModel;
import java.util.ArrayList;


public class LaboratoryTableActivity extends AppCompatActivity {

    DbAdapter mDbHelper;

    private Context mContext;
    private RecyclerView rcv_pro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory_table);

        init();

        bind();
    }


    private void bind() {

        mDbHelper = DbAdapter.getInstance(getApplicationContext());
        ArrayList<LaboratoryTableModel> libraryModels = DbAdapter.getLaboratoryTable();
        LaboratoryTableAdapter propertyListAdapter = new LaboratoryTableAdapter(libraryModels, mContext);
        rcv_pro.setAdapter(propertyListAdapter);
    }


    private void init() {
        mContext = LaboratoryTableActivity.this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rcv_pro = findViewById(R.id.rcv_pro);
        rcv_pro.setLayoutManager(layoutManager);
    }
}
