package br.uff.caronet.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.util.ApiUtil;
import com.google.firebase.storage.internal.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import br.uff.caronet.R;
import br.uff.caronet.adapters.RidesAdapter;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.models.Car;
import br.uff.caronet.models.Neighborhood;
import br.uff.caronet.models.Ride;
import br.uff.caronet.models.ViewUser;
import br.uff.caronet.models.Zone;

public class NewRideActivity extends AppCompatActivity {

    private Spinner spZone;
    private Spinner spNeighborhood;
    private Spinner spCity;
    private Spinner spSpots;
    private Button btShareRide;
    private Button btCancelRide;
    private List<String> zones = new ArrayList<>();
    private List<String> neighborhoods = new ArrayList<>();
    private List<String> cities = new ArrayList<>();
    private Integer[] spotsList;
    private String city;
    private String zone;
    private String neighborhood;
    private String campus;
    private int spots;
    private ArrayAdapter<String> adapterZone;
    private ArrayAdapter<String> adapterNeighborhood;
    private ArrayAdapter<String> adapterCity;
    private ArrayAdapter<Integer> adapterSpots;
    private ToggleButton tgGoingToUff;
    private ToggleButton tgLeavingUff;
    private boolean isGoingToUff = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ride);

        initVariables();

        tgLeavingUff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tgGoingToUff.setChecked(false);
                }
                else {
                    tgGoingToUff.setChecked(true);
                }
                isGoingToUff = !isChecked;
            }
        });

        tgGoingToUff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tgLeavingUff.setChecked(false);
                }
                else {
                    tgLeavingUff.setChecked(true);
                }
                isGoingToUff = isChecked;
            }
        });

        Dao.get().getCities()
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String name = document.getString("name");
                        cities.add(name);
                    }
                    adapterCity.notifyDataSetChanged();
                }
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
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    zones.clear();
                                    for (DocumentSnapshot document: task.getResult()){
                                        String name = document.getString("name");
                                        Log.v("Zone", name);
                                        zones.add(name);
                                    }
                                    adapterZone.notifyDataSetChanged();
                                }
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
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Zone zone = document.toObject(Zone.class);
                                neighborhoods = zone.getNeighborhoods();
                            }
                            adapterNeighborhood.clear();
                            adapterNeighborhood.addAll(neighborhoods);
                        }
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

        btShareRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Neighborhood neighborhoodObj = new Neighborhood(neighborhood,zone,city);
                ViewUser driver = new ViewUser(Dao.get().getUId(), Dao.get().getUser().getName());


                Ride ride = new Ride(driver, Calendar.getInstance().getTime(),isGoingToUff,"Praia Vermelha",neighborhoodObj,spots);
                ride.setCar(Dao.get().getUser().getCar());
                Dao.get().getClRides().add(ride);
            }
        });

    }

    /*public Ride(ViewUser driver, Date departure, boolean goingToUff,
                String campus, Neighborhood neighborhood, int spots) {*/


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
