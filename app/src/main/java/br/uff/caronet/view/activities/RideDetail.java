package br.uff.caronet.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import br.uff.caronet.R;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.models.TestRide;
import br.uff.caronet.models.ViewUser;

public class RideDetail extends AppCompatActivity {

    private TestRide ride;
    private String id;
    private Dao dao = Dao.get();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        ride = getIntent().getExtras().getParcelable("ride");
        id = getIntent().getStringExtra("id");

        if (id != null) {
            Log.v("ride id: ", id);
        }

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
        }

        ViewUser user = new ViewUser(
                dao.getUId(),
                dao.getTestUser().getName()
        );

        //dao.addPassenger(getApplicationContext(),id,user);
    }

}
