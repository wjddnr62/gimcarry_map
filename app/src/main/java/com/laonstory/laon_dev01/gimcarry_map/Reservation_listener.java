package com.laonstory.laon_dev01.gimcarry_map;

public interface Reservation_listener {

    public void onReservationClick(int status); // 0 : 카드, 1 : 폰, 2 : 현장
    public void onNotClick();

}
