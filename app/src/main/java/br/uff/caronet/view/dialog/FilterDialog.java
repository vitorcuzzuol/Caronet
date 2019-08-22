package br.uff.caronet.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.uff.caronet.R;
import br.uff.caronet.controller.RidesController;
import br.uff.caronet.controller.UserController;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.model.Neighborhood;
import br.uff.caronet.model.Zone;

public class FilterDialog extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private static String TAG = "filterDialog";

    private RidesController ridesController = new RidesController();
    private UserController userController = new UserController();
    private Spinner spZone, spNeighborhood, spCity, spSpots, spCampus;
    private Button btShareRide, btCancelRide, btDate;
    private List<String> zones = new ArrayList<>(), neighborhoods = new ArrayList<>(),
            cities = new ArrayList<>(), campi = new ArrayList<>();
    private String city, zone, neighborhood, campus;
    private ArrayAdapter<String> adapterZone, adapterNeighborhood, adapterCity, adapterCampus;
    private ToggleButton tgGoingToUff, tgLeavingUff;
    private boolean isGoingToUff = true;

    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Fullscreen);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dialog_filter, container, false);


        view.findViewById(R.id.btCancelFilter).setOnClickListener(v -> dismiss());
        tgGoingToUff = view.findViewById(R.id.tgGoingtoUff);

        initVariables();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

        view.findViewById(R.id.btApply).setOnClickListener(v -> applyDilter());
    }

    private void applyDilter() {


    }

    private void initVariables() {

        tgGoingToUff = view.findViewById(R.id.tgGoingtoUff);
        tgLeavingUff = view.findViewById(R.id.tgLeavingUff);
        spZone = view.findViewById(R.id.spZone);
        spNeighborhood = view.findViewById(R.id.spNeighborhood);
        spCity = view.findViewById(R.id.spCity);
        spCampus = view.findViewById(R.id.spCampus);

        adapterZone = new ArrayAdapter<>(
                getActivity().getApplication(),
                R.layout.support_simple_spinner_dropdown_item,
                zones
        );
        adapterZone.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        adapterNeighborhood = new ArrayAdapter<>(
                getActivity().getApplication(),
                R.layout.support_simple_spinner_dropdown_item,
                neighborhoods
        );
        adapterNeighborhood.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        adapterCity = new ArrayAdapter<>(
                getActivity().getApplication(),
                R.layout.support_simple_spinner_dropdown_item,
                cities
        );

        adapterCampus = new ArrayAdapter<>(
                getActivity().getApplication(),
                R.layout.support_simple_spinner_dropdown_item,
                campi
        );


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
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
    }
}
