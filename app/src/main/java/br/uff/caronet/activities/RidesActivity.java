package br.uff.caronet.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.firestore.Query;

import br.uff.caronet.R;
import br.uff.caronet.adapters.RidesAdapter;
import br.uff.caronet.models.TestUser;

public class RidesActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference clUsers = db.collection("Users");

    private RidesAdapter ridesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        setUpRecycleView();
    }

    private void setUpRecycleView() {
        Query query = clUsers.orderBy("name", Query.Direction.DESCENDING);


        FirestoreRecyclerOptions<TestUser> options = new FirestoreRecyclerOptions.Builder<TestUser>()
                .setQuery(query, TestUser.class)
                .build();

        ridesAdapter = new RidesAdapter(options);
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
