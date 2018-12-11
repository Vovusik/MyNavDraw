package com.andrukhiv.mynavigationdrawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.andrukhiv.mynavigationdrawer.database.DbAdapter;
import com.andrukhiv.mynavigationdrawer.models.SpecificationsModel;

import java.util.ArrayList;

import de.codecrafters.tableview.SortState;
import de.codecrafters.tableview.providers.SortStateViewProvider;

public class SortableActivity extends AppCompatActivity {


    DbAdapter mDbHelper;
    ArrayList<SpecificationsModel> sortable;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sortable);

        final SortableGrapesTableView grapesTableView = findViewById(R.id.tableView);
        if (grapesTableView != null) {
            final SortableTableDataAdapter grapesTableDataAdapter =
                    new SortableTableDataAdapter(this, DbAdapter.getAllGrapes(), grapesTableView);

            grapesTableView.setDataAdapter(grapesTableDataAdapter);
            grapesTableView.setHeaderElevation(10);

            grapesTableView.setHeaderBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        grapesTableView.setHeaderSortStateViewProvider(new MySortStateViewProvider());
        grapesTableView.setSaveEnabled( true );

        mDbHelper = DbAdapter.getInstance(getApplicationContext());
        sortable = mDbHelper.getAllGrapes();
    }

    private static class MySortStateViewProvider implements SortStateViewProvider {

        private static final int NO_IMAGE_RES = -1;

        @Override
        public int getSortStateViewResource(SortState state) {
            switch (state) {
                case SORTABLE:
                    return R.drawable.ic_sortable_arrow_defoult;
                case SORTED_ASC:
                    return R.drawable.ic_sortable_arrow_up;
                case SORTED_DESC:
                    return R.drawable.ic_sortable_arrow_down;
                default:
                    return NO_IMAGE_RES;
            }
        }
    }
}