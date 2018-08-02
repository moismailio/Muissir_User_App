package com.o058team.hajjuserapp.views;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;
import com.o058team.hajjuserapp.R;

import butterknife.ButterKnife;

/**
 * Created by Mekawy on 02/08/2018 AD.
 */

public class LocalizationDialog extends MaterialDialog.Builder {

    public LocalizationDialog(@NonNull Context context) {
        super(context);
        customView(R.layout.localization_list,false);
        ButterKnife.bind(this,customView);
    }

}
