
package br.uff.caronet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firestore.v1.WriteResult;

import br.uff.caronet.R;
import br.uff.caronet.models.TestRide;
import br.uff.caronet.models.TestUser;
import br.uff.caronet.service.Dao;

public class ProfileActivity extends AppCompatActivity {

    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TestUser user;

        user = dao.getTestUser();

        dao.getClUsers().document(dao.getUId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

    }
}
