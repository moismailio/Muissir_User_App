package com.o058team.hajjuserapp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.o058team.hajjuserapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mekawy on 02/08/2018 AD.
 */

public class ServiceRatingDialog extends MaterialDialog.Builder implements View.OnClickListener {
    onRateClicked callback;

    @BindView(R.id.ivOne)
    ImageView ivOne;

    @BindView(R.id.ivTwo)
    ImageView ivTwo;

    @BindView(R.id.ivThree)
    ImageView ivThree;

    @Override
    public void onClick(View view) {
        callback.onButtonClicked();
    }


    public interface onRateClicked{
        public void onButtonClicked();
    }


    public ServiceRatingDialog(@NonNull Context context,onRateClicked callback) {
        super(context);
        this.callback = callback;
        title("please rate our service");
        customView(R.layout.viewholder_rate,false);
        ButterKnife.bind(this,customView);
        positiveText("Dismiss");

        ivOne.setOnClickListener(this);
        ivTwo.setOnClickListener(this);
        ivThree.setOnClickListener(this);
    }



}
