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
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.uff.caronet.dao.Dao;
import br.uff.caronet.util.Utils;
import br.uff.caronet.view.activities.NewRideActivity;
import br.uff.caronet.view.activities.RideDetail;
import br.uff.caronet.common.OnItemClickListener;
import br.uff.caronet.R;
import br.uff.caronet.adapters.RidesAdapter;
import br.uff.caronet.models.Ride;


public class FindRideFragment extends Fragment {

    private Dao dao;
    private RidesAdapter ridesAdapter;
    private RecyclerView rvRides;
    private FloatingActionButton fbButton;

    public FindRideFragment() {
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

        ridesAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, Ride ride, String id) {
                Log.v("item view clicked!", id);

                Intent intent = new Intent(getContext(), RideDetail.class);
                intent.putExtra("ride", ride);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });

        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(getContext(), NewRideActivity.class);
                startActivity(intent);


            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_find_ride, container, false);

        rvRides = view.findViewById(R.id.rvRides);
        fbButton = view.findViewById(R.id.floatingab);

        setUpRecycleView();

        return view;
    }

    private void setUpRecycleView() {

        FirestoreRecyclerOptions<Ride> opRides;
        opRides = dao.setOpRides(dao.getClRides());

        ridesAdapter = new RidesAdapter(opRides, true);

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
