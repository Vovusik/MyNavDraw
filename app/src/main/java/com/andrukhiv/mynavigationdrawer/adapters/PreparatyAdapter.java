package com.andrukhiv.mynavigationdrawer.adapters;

import android.content.Context;

import com.andrukhiv.mynavigationdrawer.PreperatyViewGroup;
import com.andrukhiv.mynavigationdrawer.R;
import com.andrukhiv.mynavigationdrawer.models.PreparatyModel;

import java.util.Arrays;
import java.util.List;

public class PreparatyAdapter extends PreparatyAdapterFactory<
        String, PreperatyViewGroup,
        String, PreperatyViewGroup,
        PreparatyModel,
        PreperatyViewGroup,
        PreperatyViewGroup,
        PreperatyViewGroup> {

    private Context context;

    public PreparatyAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected PreperatyViewGroup inflateFirstHeader() {
        return new PreperatyViewGroup(context);
    }

    @Override
    protected PreperatyViewGroup inflateHeader() {
        return new PreperatyViewGroup(context);
    }

    @Override
    protected PreperatyViewGroup inflateFirstBody() {
        return new PreperatyViewGroup(context);
    }

    @Override
    protected PreperatyViewGroup inflateBody() {
        return new PreperatyViewGroup(context);
    }

    @Override
    protected PreperatyViewGroup inflateSection() {
        return new PreperatyViewGroup(context);
    }

    @Override
    protected List<Integer> getHeaderWidths() {
        Integer[] witdhs = {
                (int) context.getResources().getDimension(R.dimen._120dp),
                (int) context.getResources().getDimension(R.dimen._110dp),
                (int) context.getResources().getDimension(R.dimen._120dp),
                (int) context.getResources().getDimension(R.dimen._150dp),
                (int) context.getResources().getDimension(R.dimen._130dp),
                (int) context.getResources().getDimension(R.dimen._160dp),
        };

        return Arrays.asList(witdhs);
    }

    @Override
    protected int getHeaderHeight() {
        return (int) context.getResources().getDimension(R.dimen._55dp);
    }

    @Override
    protected int getSectionHeight() {
        return (int) context.getResources().getDimension(R.dimen._30dp);
    }

    @Override
    protected int getBodyHeight() {
        return (int) context.getResources().getDimension(R.dimen._45dp);
    }

    @Override
    protected boolean isSection(List<PreparatyModel> items, int row) {
        return items.get(row).isSection();
    }
}
