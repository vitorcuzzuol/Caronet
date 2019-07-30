
package br.uff.caronet.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.uff.caronet.dao.Dao;
import br.uff.caronet.R;
import br.uff.caronet.model.User;

public class ProfileActivity extends AppCompatActivity {

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = Dao.get().getUser();

    }
}
