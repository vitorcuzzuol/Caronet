package br.uff.caronet.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.uff.caronet.R;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.models.Zone;

public class NewRideActivity extends AppCompatActivity {

    private Spinner spZone;
    private Spinner spNeighborhood;
    private Spinner spCity;
    private List<String> zones = new ArrayList<>();
    private List<String> neighborhoods = new ArrayList<>();
    private List<String> cities = new ArrayList<>();
    private String city;
    private ArrayAdapter<String> adapterZone;
    private ArrayAdapter<String> adapterNeighborhood;
    private ArrayAdapter<String> adapterCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ride);

        initSpinners();

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
    }

    private void initSpinners() {

        spZone = findViewById(R.id.spZone);
        spNeighborhood = findViewById(R.id.spNeighborhood);
        spCity = findViewById(R.id.spCity);

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

        spZone.setAdapter(adapterZone);
        spNeighborhood.setAdapter(adapterNeighborhood);
        spCity.setAdapter(adapterCity);
    }

}
