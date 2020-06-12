package com.laonstory.laon_dev01.gimcarry_map;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Reservation_popup extends Dialog {

    TextView card, phone, scene;
    Reservation_listener reservation_listener;

    public Reservation_listener getReservation_listener() {
        return reservation_listener;
    }

    public void setReservation_listener(Reservation_listener reservation_listener) {
        this.reservation_listener = reservation_listener;
    }

    public Reservation_popup(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reservation_dialog);

        card = findViewById(R.id.card);
        phone = findViewById(R.id.phone);
        scene = findViewById(R.id.scene);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservation_listener.onReservationClick(0);
                dismiss();
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservation_listener.onReservationClick(1);
                dismiss();
            }
        });

        scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservation_listener.onReservationClick(2);
                dismiss();
            }
        });
    }
}
