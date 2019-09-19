package com.andrukhiv.mynavigationdrawer;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import com.andrukhiv.mynavigationdrawer.models.SpecificationsModel;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;


public class SortableGrapesTableView extends SortableTableView<SpecificationsModel> {

    public SortableGrapesTableView(final Context context) {
        this(context, null);
    }

    public SortableGrapesTableView(final Context context, final AttributeSet attributes) {
        this(context, attributes, android.R.attr.listViewStyle);
    }

    public SortableGrapesTableView(final Context context,
                                   final AttributeSet attributes,
                                   final int styleAttributes) {
        super(context, attributes, styleAttributes);

        final SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(context,
                R.string.name,
                R.string.sort,
                R.string.term,
                R.string.frost,
                R.string.color,
                R.string.growth,
                R.string.weight);

        simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        setHeaderAdapter(simpleTableHeaderAdapter);

        simpleTableHeaderAdapter.setPaddings(20, 50, 0, 50);
        simpleTableHeaderAdapter.setTypeface(1);
        simpleTableHeaderAdapter.setTextSize(14);

        final int rowColorEven = ContextCompat.getColor(context, R.color.windowBackground);
        final int rowColorOdd = ContextCompat.getColor(context, R.color.rowBackground);
        setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(rowColorEven, rowColorOdd));
        setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());

        // Визначаємо кількість стовпців TableView безпосередньо в коді
        final TableColumnWeightModel tableColumnWeightModel = new TableColumnWeightModel(7);
        // Ширина стовпчика
        // Для визначення відносної ширини стовпців визначаємо відносну вагу для кожного з них
        // За замовчуванням вага кожного стовпчика встановлюється рівним 1,
        // таким чином , кожен стовпець має однакову ширину.
        // Для того, щоб зробити перший стовпець (індекс першого стовпчика дорівнює 0)
        // в два рази ширше інших стовпців простий зробити наступний виклик:
        // tableView.SetColumnWeight(0, 2);

        tableColumnWeightModel.setColumnWeight(0, 3);
        tableColumnWeightModel.setColumnWeight(1, 2);
        tableColumnWeightModel.setColumnWeight(2, 2);
        tableColumnWeightModel.setColumnWeight(3, 2);
        tableColumnWeightModel.setColumnWeight(4, 2);
        tableColumnWeightModel.setColumnWeight(5, 2);
        tableColumnWeightModel.setColumnWeight(6, 2);

        setColumnModel(tableColumnWeightModel);;


        /*final TableColumnDpWidthModel tableColumnDpWidthModel = new TableColumnDpWidthModel(context, 7, 600);

        tableColumnDpWidthModel.setColumnWidth(0, 115);
        tableColumnDpWidthModel.setColumnWidth(1, 85);
        tableColumnDpWidthModel.setColumnWidth(2, 100);
        tableColumnDpWidthModel.setColumnWidth(3, 95);
        tableColumnDpWidthModel.setColumnWidth(4, 90);
        tableColumnDpWidthModel.setColumnWidth(5, 80);
        tableColumnDpWidthModel.setColumnWidth(6, 80);

        setColumnModel(tableColumnDpWidthModel);*/


        setColumnComparator(0, SortableComparators.getGrapesNameComparator());
        setColumnComparator(1, SortableComparators.getGrapesSortyComparator());
        setColumnComparator(2, SortableComparators.getGrapesTermComparator());
        setColumnComparator(3, SortableComparators.getGrapesFrostComparator());
        setColumnComparator(4, SortableComparators.getGrapesColorComparator());
        setColumnComparator(5, SortableComparators.getGrapesGrowthComparator());
        setColumnComparator(6, SortableComparators.getGrapesWeightComparator());
    }
}