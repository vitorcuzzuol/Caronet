package br.uff.caronet.view.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import br.uff.caronet.view.fragments.FindRideFragment;
import br.uff.caronet.view.fragments.MyRidesFragment;

import br.uff.caronet.R;


public class RidesActivity extends AppCompatActivity {

    Toolbar tbMenu;
    DrawerLayout dlRides;
    FloatingActionButton fbButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        initVariables();

        fbButton.setOnClickListener(v -> {

            Intent intent= new Intent(this, NewRideActivity.class);
            startActivity(intent);

        });

    }

    private void initVariables() {

        dlRides = findViewById(R.id.ltRides);
        tbMenu = findViewById(R.id.tbMenu);
        fbButton = findViewById(R.id.floatingab);

        setSupportActionBar(tbMenu);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                dlRides,
                tbMenu,
                R.string.open_menu,
                R.string.close_menu
        );
        toggle.syncState();

        dlRides.addDrawerListener(toggle);

        BottomNavigationView bottomNav = findViewById(R.id.nvBottomnav);

        bottomNav.setSelectedItemId(R.id.itSearh);

        bottomNav.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,
                new FindRideFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            menuItem -> {

                Fragment fgSelected = null;

                switch (menuItem.getItemId()){
                   /* case R.id.itChat:
                        fgSelected = new ChatFragment();
                        break;*/
                    case R.id.itMyRides:
                        fgSelected = new MyRidesFragment();
                        break;
                    case R.id.itSearh:
                        fgSelected = new FindRideFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,
                       fgSelected).commit();

                return true;
            };

    @Override
    public void onBackPressed() {
        if (dlRides.isDrawerOpen(GravityCompat.START)){
            dlRides.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
