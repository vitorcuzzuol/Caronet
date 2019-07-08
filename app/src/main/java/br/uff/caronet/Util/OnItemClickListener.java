package br.uff.caronet.Util;

import android.view.View;

import com.google.firebase.firestore.DocumentSnapshot;

import br.uff.caronet.models.TestRide;

public interface OnItemClickListener {

    void onItemClick (View view, TestRide ride, String id);

}
