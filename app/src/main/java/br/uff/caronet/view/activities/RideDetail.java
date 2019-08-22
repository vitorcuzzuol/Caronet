package br.uff.caronet.view.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.concurrent.atomic.AtomicReference;

import br.uff.caronet.R;
import br.uff.caronet.controller.RidesController;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.model.Ride;
import br.uff.caronet.util.Utils;

import static br.uff.caronet.dao.Dao.get;

public class RideDetail extends AppCompatActivity {

    TextView tvDriverName, tvSpots, tvDeparture, tvArrival, tvDate, tvDescription;
    RidesController ridesController = new RidesController();
    String rideId;
    ImageView imProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_detail);

        rideId = getIntent().getStringExtra("id");

        ridesController.getRideById(rideId).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                // Get ride as object
                Ride ride = task.getResult().toObject(Ride.class);

                // Set ride to view
                rideToTextView(ride);
            }
            else {
                // Show error message
                Utils.showToast(this, getApplicationContext().getString(R.string.not_possible));
            }
        });

        // Set Views
        initVariables();

        // Buttons
        findViewById(R.id.btCancelRide).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.btConfirmRide).setOnClickListener(v -> showConfirmDialog());
    }

    private void showConfirmDialog() {

        new AlertDialog.Builder(this)
                .setTitle(getApplicationContext().getString(R.string.get_ride))
                .setMessage(R.string.confirm_ride)
                .setPositiveButton(R.string.confirm, (dialog1, which) ->
                        ridesController.addPassenger(rideId, this))
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void rideToTextView(Ride ride) {

        String departure = getString(R.string.from);
        String arrival = getString(R.string.to);


        if (ride.isGoingToUff()){
            departure += ride.getNeighborhood().getName();
            arrival += ride.getCampus();
        }
        else {
            departure += ride.getCampus();
            arrival += ride.getNeighborhood().getName();
        }


        tvArrival.setText( arrival);
        tvDeparture.setText( departure);
        tvDate.setText( Utils.dateToStringStr(ride.getDeparture()));
        tvDriverName.setText( ride.getDriver().getName());
        tvSpots.setText("Lugares: " + String.valueOf(ride.getSpots()) );
        tvDescription.setText( ride.getDescription());

        // Set image
        if (ride.getDriver().getPhoto() != null) {
            Picasso.with(this)
                    .load(ride.getDriver().getPhoto())
                    .fit()
                    .centerCrop()
                    .into(imProfile);
        }
    }

    private void initVariables() {
        imProfile = findViewById(R.id.imProfile);
        tvArrival = findViewById(R.id.tvArrival);
        tvDate = findViewById(R.id.tvDate);
        tvDeparture= findViewById(R.id.tvDeparture);
        tvDriverName= findViewById(R.id.tvDriverName);
        tvSpots= findViewById(R.id.tvSpots);
        tvDescription = findViewById(R.id.tvDescription);

    }
}
