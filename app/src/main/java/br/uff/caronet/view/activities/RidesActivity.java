package br.uff.caronet.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.uff.caronet.view.fragments.ChatFragment;
import br.uff.caronet.view.fragments.FindRideFragment;
import br.uff.caronet.view.fragments.MyRidesFragment;

import br.uff.caronet.R;


public class RidesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        BottomNavigationView bottomNav = findViewById(R.id.nvBottomnav);

        bottomNav.setSelectedItemId(R.id.itSearh);

        bottomNav.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,
                new FindRideFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment fgSelected = null;

                    switch (menuItem.getItemId()){
                        case R.id.itChat:
                            fgSelected = new ChatFragment();
                            break;
                        case R.id.itMyRides:
                            fgSelected = new MyRidesFragment();
                            break;
                        case R.id.itSearh:
                            fgSelected = new FindRideFragment();
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,
                           fgSelected).commit();

                    return true;
                }
            };

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
