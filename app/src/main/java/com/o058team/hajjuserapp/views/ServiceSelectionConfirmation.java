package com.o058team.hajjuserapp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.o058team.hajjuserapp.R;

import butterknife.BindView;

/**
 * Created by Mekawy on 02/08/2018 AD.
 */

public class ServiceSelectionConfirmation extends MaterialDialog.Builder {

    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    public interface onConfirmationClicked{
        void onButtonClicked();
    }

    onConfirmationClicked callback;


    public ServiceSelectionConfirmation(@NonNull Context context, final onConfirmationClicked callbacl) {
        super(context);
        this.callback = callbacl;
//        customView(R.layout.dialog_service_confirmation,false);
//        ButterKnife.bind(this,customView);
//        btnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                callbacl.onButtonClicked();
//            }
//        });

        title("Service Type Selection");
        content("you have selected feature x , please confirm to reach nearest volunteer");
        positiveText("Proceed");
        onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                callbacl.onButtonClicked();
            }
        });


    }


}
