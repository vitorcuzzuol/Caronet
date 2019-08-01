package br.uff.caronet.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;


import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.uff.caronet.R;
import br.uff.caronet.controller.RidesController;
import br.uff.caronet.controller.UserController;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.model.Neighborhood;
import br.uff.caronet.model.Ride;
import br.uff.caronet.model.ViewUser;
import br.uff.caronet.model.Zone;
import br.uff.caronet.util.Utils;

public class NewRideActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener,
        AdapterView.OnItemSelectedListener,
        View.OnClickListener {

    private RidesController ridesController = new RidesController();
    private UserController userController = new UserController();
    private Spinner spZone, spNeighborhood, spCity, spSpots, spCampus;
    private Button btShareRide, btCancelRide, btDate;
    private List<String> zones = new ArrayList<>(), neighborhoods = new ArrayList<>(),
            cities = new ArrayList<>(), campi = new ArrayList<>();
    private Integer[] spotsList;
    private String city, zone, neighborhood, campus;
    private int spots;
    private ArrayAdapter<String> adapterZone, adapterNeighborhood, adapterCity, adapterCampus;
    private ArrayAdapter<Integer> adapterSpots;
    private ToggleButton tgGoingToUff, tgLeavingUff;
    private boolean isGoingToUff = true;
    private TextView tvDate;
    private int day, month, hour, minute, year;
    private Date date;
    private EditText etDecrpition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ride);

        initVariables();

        tgLeavingUff.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tgGoingToUff.setChecked(false);
            } else {
                tgGoingToUff.setChecked(true);
            }
            isGoingToUff = !isChecked;
        });

        tgGoingToUff.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tgLeavingUff.setChecked(false);
            } else {
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

        Dao.get().getClCampi()
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String name = document.getString("name");
                    campi.add(name);
                }
                adapterCampus.notifyDataSetChanged();
            }
        });

        // Spinners
        spCampus.setOnItemSelectedListener(this);
        spCity.setOnItemSelectedListener(this);
        spZone.setOnItemSelectedListener(this);
        spNeighborhood.setOnItemSelectedListener(this);
        spSpots.setOnItemSelectedListener(this);

        // Buttons
        btShareRide.setOnClickListener(this);
        btCancelRide.setOnClickListener(this);
        btDate.setOnClickListener(this);
    }

    private void initVariables() {

        etDecrpition = findViewById(R.id.etRideDesc);
        tvDate = findViewById(R.id.tvDate);
        tgGoingToUff = findViewById(R.id.tgGoingtoUff);
        tgLeavingUff = findViewById(R.id.tgLeavingUff);
        spZone = findViewById(R.id.spZone);
        spNeighborhood = findViewById(R.id.spNeighborhood);
        spCity = findViewById(R.id.spCity);
        spCampus = findViewById(R.id.spCampus);
        spSpots = findViewById(R.id.spSpots);
        btShareRide = findViewById(R.id.btShareRide);
        btCancelRide = findViewById(R.id.btCancelRide);
        btDate = findViewById(R.id.btDate);

        spotsList = new Integer[]{1, 2, 3, 4};
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

        adapterCampus = new ArrayAdapter<>(
                getApplication(),
                R.layout.support_simple_spinner_dropdown_item,
                campi
        );


        spSpots.setAdapter(adapterSpots);
        spZone.setAdapter(adapterZone);
        spNeighborhood.setAdapter(adapterNeighborhood);
        spCity.setAdapter(adapterCity);
        spCampus.setAdapter(adapterCampus);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            // Campus spinner
            case R.id.spCampus:
                campus = parent.getSelectedItem().toString();
                break;
            case R.id.spCity:
                city = parent.getSelectedItem().toString();
                Dao.get().getClZones()
                        .whereEqualTo("city", parent.getSelectedItem().toString())
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                zones.clear();
                                for (DocumentSnapshot document : task.getResult()) {
                                    String name = document.getString("name");
                                    Log.v("Zone", name);
                                    zones.add(name);
                                }
                                adapterZone.notifyDataSetChanged();
                            }
                        });
                break;

            case R.id.spNeighborhood:
                // Neighborhood spinner
                neighborhood = parent.getSelectedItem().toString();
                break;

            case R.id.spZone:
                // Zone spinner
                zone = parent.getSelectedItem().toString();

                Dao.get().getClZones()
                        .whereEqualTo("name", parent.getSelectedItem().toString())
                        .whereEqualTo("city", city)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Zone zone = document.toObject(Zone.class);
                                    neighborhoods = zone.getNeighborhoods();
                                }
                                adapterNeighborhood.clear();
                                adapterNeighborhood.addAll(neighborhoods);
                            }
                        });
                break;

            case R.id.spSpots:
                // Spots spinner
                spots = ((int) parent.getSelectedItem());

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btShareRide:
                if (isValid()) {
                    Neighborhood neighborhoodObj = new Neighborhood(neighborhood, zone, city);
                    ViewUser driver = new ViewUser(userController.getUser().getId(), userController.getUser().getName());

                    Ride ride = new Ride(driver, date, isGoingToUff, campus, neighborhoodObj, spots);
                    ride.setDescription(etDecrpition.getText().toString());

                    ride.setCar(userController.getUserCar());

                    ridesController.addRide(ride);

                    Intent intent = new Intent(this, RidesActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.btCancelRide:
                onBackPressed();
                break;

            case R.id.btDate:
                int day = Calendar.getInstance().get(Calendar.DATE);
                int month = Calendar.getInstance().get(Calendar.MONDAY);
                int year = Calendar.getInstance().get(Calendar.YEAR);
                new DatePickerDialog(this, this, year, month, day).show();
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.day = dayOfMonth;
        this.month = month + 1;
        this.year = year;

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minutes = Calendar.getInstance().get(Calendar.MINUTE);
        new TimePickerDialog(this, this, hour, minutes, true).show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;
        this.date = Utils.intToDate(this.year, this.month, this.day, this.hour, this.minute);
        tvDate.setText(Utils.dateToString(this.month, this.day, this.hour, this.minute));
    }

    private boolean isValid() {

        if (tvDate.getText().toString().equals("")) {
            Utils.showToast(this, "Escolha uma data!");
            return false;
        }
        else if (Utils.diffInMinutes(date) < 10) {
                Utils.showToast(this, "Escolha uma data válida");
                return false;
        }

        if (etDecrpition.getText().toString().equals("")) {
            Utils.showToast(this, "Falta descrição!");
            return false;
        }

        return true;
    }
}
