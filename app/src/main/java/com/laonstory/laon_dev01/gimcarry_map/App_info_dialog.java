package com.laonstory.laon_dev01.gimcarry_map;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

public class App_info_dialog extends Dialog {

    public App_info_dialog(@NonNull Context context) {
        super(context);
    }

    private Button ok_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_info_dialog);

        ok_btn = findViewById(R.id.ok);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
