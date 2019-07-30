package br.uff.caronet.view.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import br.uff.caronet.R;
import br.uff.caronet.controller.RidesController;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.model.Ride;
import br.uff.caronet.model.ViewUser;

public class RideDetail extends AppCompatActivity {

    Dao dao = Dao.get();
    TextView tvDriverName, tvSpots, tvDeparture, tvArrival, tvCampus, tvDescription;
    RidesController ridesController = new RidesController();
    String rideId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        Ride ride;

        // Get ride object from last Activity
        ride = getIntent().getExtras().getParcelable("ride");
        rideId = getIntent().getStringExtra("id");

        showRideOnlog(ride);

        // Set Views
        initVariables();

        // Set Ride detail on text views
        rideToTextView(ride);

        // Button Click
        findViewById(R.id.btCancelRide).setOnClickListener( v -> onBackPressed());
        findViewById(R.id.btConfirmRide).setOnClickListener(v -> showConfirmDialog());



       /* findViewById(R.id.btConfirm).setOnClickListener(v -> {
            ViewUser user = new ViewUser(dao.getUser().getId(), dao.getUser().getName());
            dao.addPassenger(getApplicationContext(),id,user);
        });*/
    }
    private void showConfirmDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Ride")
                .setMessage(R.string.confirm_ride)
                .setPositiveButton(R.string.confirm, (dialog1, which) ->
                        ridesController.addPassenger(rideId, this))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void rideToTextView(Ride ride) {

        tvArrival.setText( ride.getDriver().getName());
        //tvCampus.setText( ride.getCampus());
        tvDeparture.setText( ride.getDeparture().toString());
        tvDriverName.setText( ride.getDriver().getName());
//        tvSpots.setText( ride.getSpots());
        tvDescription.setText( ride.getDescription());

    }

    private void initVariables() {
        tvArrival = findViewById(R.id.tvArrival);
        tvCampus = findViewById(R.id.tvCampus);
        tvDeparture= findViewById(R.id.tvDeparture);
        tvDriverName= findViewById(R.id.tvDriverName);
        tvSpots= findViewById(R.id.tvSpots);
        tvDescription = findViewById(R.id.tvDescription);

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
