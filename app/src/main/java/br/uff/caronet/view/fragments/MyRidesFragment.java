package br.uff.caronet.view.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.uff.caronet.common.OnItemClickListener;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.R;
import br.uff.caronet.adapters.RidesAdapter;
import br.uff.caronet.model.Ride;
import br.uff.caronet.view.activities.DetailAsDriverActivity;
import br.uff.caronet.view.activities.DetailAsPsgerActivity;


public class MyRidesFragment extends Fragment implements View.OnClickListener, OnItemClickListener {

    private Dao dao;
    private RidesAdapter ridesAdapter;
    private RecyclerView rvRides;

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

        ridesAdapter.setOnItemClickListener(this);

        ridesAdapter.startListening();

        ridesAdapter.setOnItemClickListener(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_rides, container, false);

        // Views
        rvRides = view.findViewById(R.id.rvRides);

        // Button listeners
        view.findViewById(R.id.btDriver).setOnClickListener(this);
        view.findViewById(R.id.btPassenger).setOnClickListener(this);

        setUpRecycleView(false);
        ridesAdapter.setOnItemClickListener(this);


        return view;
    }

    private void setUpRecycleView(boolean isDriver) {

        FirestoreRecyclerOptions<Ride> opRides;
        opRides = dao.setOpMyRides(dao.getClRides(), isDriver);

        ridesAdapter = new RidesAdapter(opRides, false, getContext());

        rvRides.setHasFixedSize(true);
        rvRides.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRides.setAdapter(ridesAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        ridesAdapter.stopListening();
    }

    @Override
    public void onClick(View v) {
        // Clicked on Passenger
        if (v.getId() == R.id.btPassenger) {
            Log.v("btPassenger: ", "clicked!");
            ridesAdapter.stopListening();
            setUpRecycleView(false);
            ridesAdapter.startListening();
        }
        // Clicked on Driver
        else if (v.getId() == R.id.btDriver) {
            Log.v("btDriver: ", "clicked!");
            ridesAdapter.stopListening();
            setUpRecycleView(true);
            ridesAdapter.startListening();
        }

    }

    @Override
    public void onItemClick(View view, Ride ride, String id) {
        Log.v("TAG", "clicked");
        Intent intent;
        if (ride.getDriver().getId().equals(dao.getUId())) {

            Log.v("TAG", "IDDRIVER ==");
            intent = new Intent(getContext(), DetailAsDriverActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        } else {
            intent = new Intent(getContext(), DetailAsPsgerActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }


    }

    @Override
    public void onButtonClick(String id) {

    }
}
