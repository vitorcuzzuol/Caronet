package br.uff.caronet.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import br.uff.caronet.R;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.models.Ride;
import br.uff.caronet.models.ViewUser;

public class RideDetail extends AppCompatActivity {

    private Dao dao = Dao.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        Ride ride;
        String id;

        ride = getIntent().getExtras().getParcelable("ride");
        id = getIntent().getStringExtra("id");

        showRideOnlog(ride);

        /*ViewUser user = new ViewUser(
                dao.getUId(),
                dao.getUser().getName()
        );

        dao.addPassenger(getApplicationContext(),id,user);*/
    }

    private void showRideOnlog(Ride ride) {
        if (ride != null) {
            Log.v("ride: ",
                    ride.getCampus()
                            + ride.isGoingToUff()
                            + ride.getNeighborhood()
                            + ride.getDeparture()
            );
            if (ride.getDriver() != null) {
                Log.v("driver: ", ride.getDriver().getName());
            }
            if (ride.getPassengers() != null) {
                for (ViewUser passenger : ride.getPassengers()) {
                    Log.v("passenger: ", passenger.getName());
                }
            }
            if (ride.getCar() != null) {
                Log.v("car plate: ", ride.getCar().getPlate());
                Log.v("car model: ", ride.getCar().getModel());

            }
        }
    }

}
