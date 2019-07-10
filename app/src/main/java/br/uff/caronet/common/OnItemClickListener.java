package br.uff.caronet.common;

import android.view.View;

import br.uff.caronet.models.TestRide;

public interface OnItemClickListener {

    void onItemClick (View view, TestRide ride, String id);

}
