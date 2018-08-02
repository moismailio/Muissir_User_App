package com.o058team.hajjuserapp.views;

import android.content.Context;
import android.database.DataSetObserver;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;

import com.o058team.hajjuserapp.R;

/**
 * Created by Mekawy on 02/08/2018 AD.
 */

public class LangSpinnerAdapter implements SpinnerAdapter{

    LayoutInflater layoutInflater;

    public LangSpinnerAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return generateView(i,view,viewGroup);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return generateView(i,view,viewGroup);
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public View generateView(int i, View view, ViewGroup viewGroup){
        if (view==null){
            view = layoutInflater.inflate(R.layout.viewholder_flag,viewGroup,false);
            ImageView ivSpeaker = view.findViewById(R.id.ivSpeaker);
            ivSpeaker.setImageDrawable(ContextCompat.getDrawable(layoutInflater.getContext(),getFlagByIndex(i)));
        }
        return view;
    }

    public int getFlagByIndex(int index){
        if (index==0)
            return R.drawable.f1;
        if (index==1)
            return R.drawable.f2;
        if (index==2)
            return R.drawable.f3;
        else
            return R.drawable.f4;
    }



}
