package br.uff.caronet.view.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import br.uff.caronet.dao.Dao;
import br.uff.caronet.R;
import br.uff.caronet.model.User;


public class MainActivity extends AppCompatActivity {

    Dao dao;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = Dao.get();
        progressBar = findViewById(R.id.pbMainProgBar);


        //if user is already logged then get his attributes from db and store on a new User object
        if (dao.getUserAuth() != null) {
            Log.v("user auth id: ", dao.getUId());

            DocumentReference userRef = dao.getClUsers().document(dao.getUId());

            //check if found the user from db
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    //user is found on db
                    DocumentSnapshot userRef1 = task.getResult();

                    //store data on created User object
                    User user = userRef1.toObject(User.class);

                    dao.setUser(user);
                    Log.v("User auto-logged name: ", user.getName());

                    Intent intent = new Intent(MainActivity.this, RidesActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.w("user could not get on db", "...");
                }
            });
        } else {
            Intent intent = new Intent(MainActivity.this, RegLogActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
