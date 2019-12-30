package com.andrukhiv.mynavigationdrawer;

import android.content.Context;

import com.andrukhiv.mynavigationdrawer.adapters.PreparatyAdapter;
import com.andrukhiv.mynavigationdrawer.models.PreparatyModel;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;

import java.util.Arrays;
import java.util.List;

import static com.andrukhiv.mynavigationdrawer.database.DbAdapter.getPreparaty;


public class PreparatyHeader {

    private Context context;

    public PreparatyHeader(Context context) {
        this.context = context;
    }

    public BaseTableAdapter getInstance() {
        PreparatyAdapter adapter =
                new PreparatyAdapter(context);

        List<PreparatyModel> body = getPreparaty();

        adapter.setFirstHeader((String) context.getResources().getText(R.string.nazva_preparatu));
        adapter.setHeader(getHeader());
        adapter.setFirstBody(body);
        adapter.setBody(body);
        adapter.setSection(body);

        return adapter;
    }


    private List<String> getHeader() {

        final String[] headers = {
                (String) context.getResources().getText(R.string.spektr_dii),
                (String) context.getResources().getText(R.string.virobnyk),
                (String) context.getResources().getText(R.string.kratnist_zastosuvannya),
                (String) context.getResources().getText(R.string.stroky_do_spogivannya),
                (String) context.getResources().getText(R.string.diucha_rechovina),
        };

        return Arrays.asList(headers);
    }
}
