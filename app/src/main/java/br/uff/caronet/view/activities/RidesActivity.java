package br.uff.caronet.view.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import br.uff.caronet.controller.UserController;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.util.Utils;
import br.uff.caronet.view.fragments.FindRideFragment;
import br.uff.caronet.view.fragments.MyRidesFragment;

import br.uff.caronet.R;


public class RidesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar tbMenu;
    DrawerLayout dlRides;
    FloatingActionButton fbButton;
    NavigationView nvMenu;
    BottomNavigationView bnMenu;
    UserController userController = new UserController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);

        Log.v("USER LOGGED: ", Dao.get().getUser().getId());

        // Views
        initVariables();

        setSupportActionBar(tbMenu);

        //  Bottom Navigation menu
        bnMenu.setSelectedItemId(R.id.itSearh);
        bnMenu.setOnNavigationItemSelectedListener(navListner);

        // Set default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_home,
                new FindRideFragment()).commit();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, dlRides, tbMenu,
                R.string.open_menu,
                R.string.close_menu
        );
        toggle.syncState();

        // Floating button
        fbButton.setOnClickListener(v -> {
            if (Dao.get().getUser().isDriver()){
                Intent intent= new Intent(this, NewRideActivity.class);
                startActivity(intent);
            }
            else {
                Utils.showToast(this, getString(R.string.register_a_car));
            }
        });

        // Navigation menu
        nvMenu.setNavigationItemSelectedListener(this);

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
        userController.logOut();
        Intent intent = new Intent(this, GoogleSignInActivity.class);
        startActivity(intent);
        finish();
    }


    private void initVariables() {

        dlRides = findViewById(R.id.ltRides);
        tbMenu = findViewById(R.id.tbMenu);
        fbButton = findViewById(R.id.floatingab);
        nvMenu = findViewById(R.id.nvProfile);
        bnMenu = findViewById(R.id.nvBottomnav);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
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
    }
}
