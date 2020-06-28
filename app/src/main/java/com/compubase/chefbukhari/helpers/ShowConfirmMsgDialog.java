package com.compubase.chefbukhari.helpers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.compubase.chefbukhari.R;

public class ShowConfirmMsgDialog extends Dialog {

    public Button btn_ok;

    public ShowConfirmMsgDialog(Context context) {
        super(context);
    }

    @SuppressLint("InflateParams")
    public void createDilog (Context context , String msg){

        AlertDialog builder = new AlertDialog.Builder(context).create();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm_msg, null);
        builder.setView(view);
        builder.setCanceledOnTouchOutside(false);

        TextView txt_error = view.findViewById(R.id.txt_error_msg);
        txt_error.setText(msg);
        btn_ok = view.findViewById(R.id.ok_dialog);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });

        builder.show();

    }
}
