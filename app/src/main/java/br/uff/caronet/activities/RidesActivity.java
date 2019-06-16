package br.uff.caronet.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import br.uff.caronet.service.Dao;
import br.uff.caronet.R;
import br.uff.caronet.adapters.RidesAdapter;
import br.uff.caronet.models.TestRide;

public class RidesActivity extends AppCompatActivity {

    private RidesAdapter ridesAdapter;
    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        dao = Dao.get();

        setUpRecycleView();

    }

    private void setUpRecycleView() {

        FirestoreRecyclerOptions<TestRide> opRides;
        opRides = dao.setOpRides(dao.getClRides(), "departure");

        ridesAdapter = new RidesAdapter(opRides);

        RecyclerView rvRides = findViewById(R.id.rvRides);
        rvRides.setHasFixedSize(true);
        rvRides.setLayoutManager(new LinearLayoutManager(this));
        rvRides.setAdapter(ridesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ridesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        ridesAdapter.stopListening();
    }
}
