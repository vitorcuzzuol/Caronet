
package br.uff.caronet.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;

import br.uff.caronet.dao.Dao;
import br.uff.caronet.R;
import br.uff.caronet.models.User;

public class ProfileActivity extends AppCompatActivity {

    private Dao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        User user;

        dao = Dao.get();
        user = dao.getUser();

        dao.getClUsers().document(dao.getUId()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });

    }
}
