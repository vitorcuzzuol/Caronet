package br.uff.caronet.common;

import android.view.View;

import br.uff.caronet.models.Ride;

public interface OnItemClickListener {

    void onItemClick (View view, Ride ride, String id);

}
