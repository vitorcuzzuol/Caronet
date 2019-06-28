package br.uff.caronet.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.uff.caronet.service.Dao;
import br.uff.caronet.R;
import br.uff.caronet.adapters.RidesAdapter;
import br.uff.caronet.models.TestRide;


public class FindRideFragment extends Fragment {

    private Dao dao;
    private RidesAdapter ridesAdapter;
    private RecyclerView rvRides;


    public FindRideFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dao = Dao.get();
    }

    private void setUpRecycleView() {

        FirestoreRecyclerOptions<TestRide> opRides;
        opRides = dao.setOpRides(dao.getClRides(), "departure");

        ridesAdapter = new RidesAdapter(opRides);

        rvRides.setHasFixedSize(true);
        rvRides.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRides.setAdapter(ridesAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_ride, container, false);

        rvRides = view.findViewById(R.id.rvRides);
        setUpRecycleView();

        return view;

    }

    @Override
    public void onStop() {
        super.onStop();

        ridesAdapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();

        ridesAdapter.startListening();
    }
}
