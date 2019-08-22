package br.uff.caronet.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import br.uff.caronet.R;
import br.uff.caronet.adapters.RidesAdapter;
import br.uff.caronet.adapters.UserAdapter;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.model.Ride;
import br.uff.caronet.model.User;
import br.uff.caronet.model.ViewUser;

import static java.security.AccessController.getContext;

public class DetailAsDriverActivity extends AppCompatActivity {


    Dao dao = Dao.get();
    RecyclerView rvUsers;
    UserAdapter userAdapter;
    String rideId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_as_driver);

        Log.v("Oncriate", "IN");
        rvUsers = findViewById(R.id.rvUsers);
        rideId = getIntent().getStringExtra("id");
        setUpRecycleView();
    }

    private void setUpRecycleView() {

        dao.getClRides().document(rideId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentSnapshot documentSnapshot = task.getResult();

                Log.v("RV", "in");

                Ride ride = documentSnapshot.toObject(Ride.class);

                List<ViewUser> users = ride.getPassengers();

                userAdapter = new UserAdapter(users, this);
                rvUsers.setHasFixedSize(true);
                rvUsers.setLayoutManager(new LinearLayoutManager(this));
                rvUsers.setAdapter(userAdapter);

            }
        });

    }
}
