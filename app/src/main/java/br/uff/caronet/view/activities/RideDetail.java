package br.uff.caronet.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.uff.caronet.R;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.models.Ride;
import br.uff.caronet.models.ViewUser;

public class RideDetail extends AppCompatActivity {

    private Dao dao = Dao.get();
    private Button btConfirm, btCancel;
    private TextView tvDriverName, tvSpots, tvDeparture, tvArrival, tvCampus, tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        Ride ride;
        String id;

        ride = getIntent().getExtras().getParcelable("ride");
        id = getIntent().getStringExtra("id");

        showRideOnlog(ride);

        initVariables();

        rideToTextView(ride);

        btCancel.setOnClickListener( v -> onBackPressed());

        btConfirm.setOnClickListener(v -> {

            ViewUser user = new ViewUser(dao.getUId(), dao.getUser().getName());
            dao.addPassenger(getApplicationContext(),id,user);
        });
    }

    private void rideToTextView(Ride ride) {

        tvArrival.setText( ride.getDriver().getName());
        //tvCampus.setText( ride.getCampus());
        tvDeparture.setText( ride.getDeparture().toString());
        tvDriverName.setText( ride.getDriver().getName());
        tvSpots.setText( ride.getSpots());
        tvDescription.setText( ride.getDescription());

    }

    private void initVariables() {
        tvArrival = findViewById(R.id.tvArrival);
        tvCampus = findViewById(R.id.tvCampus);
        tvDeparture= findViewById(R.id.tvDeparture);
        tvDriverName= findViewById(R.id.tvDriverName);
        tvSpots= findViewById(R.id.tvSpots);
        tvDescription = findViewById(R.id.tvDescription);

        btConfirm = findViewById(R.id.btConfirm);
        btCancel =findViewById(R.id.btCancelRide);
    }

    private void showRideOnlog(Ride ride) {
        if (ride != null) {
            Log.v("ride: ",
                    ride.getCampus()
                            + ride.isGoingToUff()
                            + ride.getNeighborhood().getCity()
                            + ride.getNeighborhood().getName()
                            + ride.getNeighborhood().getZone()
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
