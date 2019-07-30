package br.uff.caronet.controller;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.uff.caronet.dao.Dao;
import br.uff.caronet.model.Ride;
import br.uff.caronet.model.User;
import br.uff.caronet.model.ViewUser;
import br.uff.caronet.util.Utils;

public class RidesController {

    private Dao dao = Dao.get();

    public void addPassenger(String RideId, Context context) {
        dao.getClRides().document(RideId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                DocumentSnapshot documentSnapshot = task.getResult();
                Ride ride = documentSnapshot.toObject(Ride.class);

                Log.v("Ride",ride.toString());


                if (ride.getSpots() > 0) {
                    ride.setSpots(ride.getSpots() - 1);
                    ViewUser user = new ViewUser(dao.getUId(),dao.getUser().getName());

                    Log.v("TAG",user.getId() + user.getName());
                    if (ride.getPassengers() == null) ride.setPassengers(new ArrayList<>());
                    ride.getPassengers().add(user);
                    dao.getClRides().document(RideId).set(ride);
                    Utils.showToast(context, "Adicionado à carona!");
                }
                else {
                    Utils.showToast(context, "Sem lugares!");
                }
            }
            else {
                Utils.showToast(context, "Sem lugares!");
            }
        });
    }

}
