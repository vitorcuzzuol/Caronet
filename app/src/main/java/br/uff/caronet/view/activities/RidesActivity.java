package br.uff.caronet.view.activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import br.uff.caronet.dao.Dao;
import br.uff.caronet.view.fragments.FindRideFragment;
import br.uff.caronet.view.fragments.MyRidesFragment;

import br.uff.caronet.R;


public class RidesActivity extends AppCompatActivity {

    Toolbar tbMenu;
    DrawerLayout dlRides;
    FloatingActionButton fbButton;
    NavigationView nvMenu;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        initVariables();

        setSupportActionBar(tbMenu);

        bottomNav.setSelectedItemId(R.id.itSearh);

        bottomNav.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,
                new FindRideFragment()).commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                dlRides,
                tbMenu,
                R.string.open_menu,
                R.string.close_menu
        );
        toggle.syncState();

        fbButton.setOnClickListener(v -> {

            if (Dao.get().getUser().isDriver()){

            }

            Intent intent= new Intent(this, NewRideActivity.class);
            startActivity(intent);

        });

        nvMenu.setNavigationItemSelectedListener(menuItem -> {
            Intent intent;

            switch (menuItem.getItemId()){

                case R.id.itEditProfile:
                    intent = new Intent(this,ProfileActivity.class);
                    startActivity(intent);
                    break;

                case R.id.itUseTherms:
                    intent = new Intent(this,TermsOfUseActivity.class);
                    startActivity(intent);
                    break;

                case R.id.itLogout:
                    showDialogLogout();
                    break;
            }

            dlRides.closeDrawer(GravityCompat.START);
            return true;
        });

    }

    private void showDialogLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage(R.string.logout_text)
                .setPositiveButton(R.string.confirm, (dialog1, which) -> logOut())
                .setNegativeButton(R.string.cancel, null)
                .show();

    }

    private void logOut() {
        Dao.get().logOut();
        Intent intent = new Intent(this, GoogleSignInActivity.class);
        startActivity(intent);
        finish();
    }


    private void initVariables() {

        dlRides = findViewById(R.id.ltRides);
        tbMenu = findViewById(R.id.tbMenu);
        fbButton = findViewById(R.id.floatingab);
        nvMenu = findViewById(R.id.nvProfile);
        bottomNav = findViewById(R.id.nvBottomnav);

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
