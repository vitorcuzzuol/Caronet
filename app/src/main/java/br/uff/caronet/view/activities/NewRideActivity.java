package br.uff.caronet.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.uff.caronet.R;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.models.Neighborhood;
import br.uff.caronet.models.Ride;
import br.uff.caronet.models.ViewUser;
import br.uff.caronet.models.Zone;

public class NewRideActivity extends AppCompatActivity {

    private Spinner spZone, spNeighborhood, spCity, spSpots;
    private Button btShareRide, btCancelRide;
    private List<String> zones = new ArrayList<>();
    private List<String> neighborhoods = new ArrayList<>();
    private List<String> cities = new ArrayList<>();
    private Integer[] spotsList;
    private String city, zone, neighborhood, campus;
    private int spots;
    private ArrayAdapter<String> adapterZone, adapterNeighborhood, adapterCity;
    private ArrayAdapter<Integer> adapterSpots;
    private ToggleButton tgGoingToUff, tgLeavingUff;
    private boolean isGoingToUff = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ride);

        initVariables();

        tgLeavingUff.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                tgGoingToUff.setChecked(false);
            }
            else {
                tgGoingToUff.setChecked(true);
            }
            isGoingToUff = !isChecked;
        });

        tgGoingToUff.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                tgLeavingUff.setChecked(false);
            }
            else {
                tgLeavingUff.setChecked(true);
            }
            isGoingToUff = isChecked;
        });

        Dao.get().getCities()
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String name = document.getString("name");
                            cities.add(name);
                        }
                        adapterCity.notifyDataSetChanged();
                    }
                });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.v("spCity", parent.getSelectedItem().toString());
                city = parent.getSelectedItem().toString();

                Dao.get().getClZones()
                        .whereEqualTo("city", parent.getSelectedItem().toString())
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                zones.clear();
                                for (DocumentSnapshot document: task.getResult()){
                                    String name = document.getString("name");
                                    Log.v("Zone", name);
                                    zones.add(name);
                                }
                                adapterZone.notifyDataSetChanged();
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> parent, View view, int position, long id) {

                Log.v("spZone Clicked", parent.getSelectedItem().toString());
                zone = parent.getSelectedItem().toString();

                Dao.get().getClZones()
                        .whereEqualTo("name",parent.getSelectedItem().toString())
                        .whereEqualTo("city", city)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Zone zone = document.toObject(Zone.class);
                                    neighborhoods = zone.getNeighborhoods();
                                }
                                adapterNeighborhood.clear();
                                adapterNeighborhood.addAll(neighborhoods);
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spNeighborhood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                neighborhood = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSpots.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spots = ((int) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btShareRide.setOnClickListener(v -> {

            Neighborhood neighborhoodObj = new Neighborhood(neighborhood,zone,city);
            ViewUser driver = new ViewUser(Dao.get().getUId(), Dao.get().getUser().getName());


            Ride ride = new Ride(driver, Calendar.getInstance().getTime(),isGoingToUff,"Praia Vermelha",neighborhoodObj,spots);

            ride.setCar(Dao.get().getUser().getCar());

            Dao.get().getClRides().add(ride);
        });

        btCancelRide.setOnClickListener( v -> onBackPressed());

    }

    private void initVariables() {

        tgGoingToUff = findViewById(R.id.tgGoingtoUff);
        tgLeavingUff = findViewById(R.id.tgLeavingUff);
        spZone = findViewById(R.id.spZone);
        spNeighborhood = findViewById(R.id.spNeighborhood);
        spCity = findViewById(R.id.spCity);
        spSpots = findViewById(R.id.spSpots);
        btShareRide = findViewById(R.id.btShareRide);
        btCancelRide = findViewById(R.id.btCancelRide);

        spotsList = new Integer[] {1,2,3,4};

        adapterSpots = new ArrayAdapter<>(
                getApplication(),
                R.layout.support_simple_spinner_dropdown_item,
                spotsList
        );
        adapterSpots.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        adapterZone = new ArrayAdapter<>(
                getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item,
                zones
        );
        adapterZone.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        adapterNeighborhood = new ArrayAdapter<>(
                getApplication(),
                R.layout.support_simple_spinner_dropdown_item,
                neighborhoods
        );
        adapterNeighborhood.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        adapterCity = new ArrayAdapter<>(
                getApplication(),
                R.layout.support_simple_spinner_dropdown_item,
                cities
        );

        spSpots.setAdapter(adapterSpots);
        spZone.setAdapter(adapterZone);
        spNeighborhood.setAdapter(adapterNeighborhood);
        spCity.setAdapter(adapterCity);
    }

}
