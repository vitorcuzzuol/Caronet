package br.uff.caronet.view.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.uff.caronet.dao.Dao;
import br.uff.caronet.R;
import br.uff.caronet.adapters.RidesAdapter;
import br.uff.caronet.models.TestRide;


public class MyRidesFragment extends Fragment {


    private Dao dao;
    private RidesAdapter ridesAdapter;
    private RecyclerView rvRides;
    private Button btDriver;
    private Button btPassenger;

    public MyRidesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = Dao.get();
    }

    @Override
    public void onStart() {
        super.onStart();

        ridesAdapter.startListening();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_rides, container, false);

        rvRides = view.findViewById(R.id.rvRides);

        btDriver = view.findViewById(R.id.btDriver);
        btPassenger = view.findViewById(R.id.btPassenger);

        btDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("btDriver: " ,"clicked!");
                ridesAdapter.stopListening();
                setUpRecycleView(true);
                ridesAdapter.startListening();

            }
        });

        btPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("btPassenger: " ,"clicked!");
                ridesAdapter.stopListening();
                setUpRecycleView(false);
                ridesAdapter.startListening();
            }
        });

        setUpRecycleView(false);

        return view;
    }


    private void setUpRecycleView(boolean isDriver) {

        FirestoreRecyclerOptions<TestRide> opRides;
        opRides = dao.setOpMyRides(dao.getClRides(), isDriver);

        ridesAdapter = new RidesAdapter(opRides, false);

        rvRides.setHasFixedSize(true);
        rvRides.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRides.setAdapter(ridesAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        ridesAdapter.stopListening();
    }

}
