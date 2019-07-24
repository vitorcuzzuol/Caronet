package br.uff.caronet.view.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import br.uff.caronet.dao.Dao;
import br.uff.caronet.R;
import br.uff.caronet.models.User;


public class MainActivity extends AppCompatActivity {

    Button btReg, btLog;
    Dao dao;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = Dao.get();

        btReg = findViewById(R.id.btReg);
        btLog = findViewById(R.id.btLog);

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
                } else {
                    Log.w("user could not get on db", "...");
                }
            });
        }

        //Called when Register button is clicked
        btReg.setOnClickListener(v -> showRegisterDialog());

        //Called when Login button is clicked
        btLog.setOnClickListener(v -> showLoginDialog());
    }


    private void showLoginDialog() {
        AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        dialog.show();
        dialog.setTitle(R.string.login);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View login_layout = layoutInflater.inflate(R.layout.layout_login, null);

        final EditText etEmail = login_layout.findViewById(R.id.etEmail);
        final EditText etPassword = login_layout.findViewById(R.id.etPassword);

        dialog.setView(login_layout);

        dialog.setPositiveButton(R.string.login, (dialog12, which) -> {

            relativeLayout = findViewById(R.id.root_layout);
            //validate

            //sign in
            dao.signIn(etEmail.getText().toString(),etPassword.getText().toString());

            Intent intent = new Intent(MainActivity.this, RidesActivity.class);
            startActivity(intent);
        });

        dialog.setNegativeButton(R.string.cancel, (dialog1, which) -> dialog1.dismiss());

        dialog.show();
    }


    private void showRegisterDialog() {

        AlertDialog.Builder dialog= new AlertDialog.Builder(this);
        dialog.show();
        dialog.setTitle(R.string.register);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View register_layout = layoutInflater.inflate(R.layout.layout_register, null);

        final EditText etName = register_layout.findViewById(R.id.etName);
        final EditText etEmail = register_layout.findViewById(R.id.etEmail);
        final EditText etPassword = register_layout.findViewById(R.id.etPassword);
        final Switch swDriver = register_layout.findViewById(R.id.swDriver);

        dialog.setView(register_layout);

        dialog.setPositiveButton(R.string.register, (dialog1, which) -> {

            //dialog.dismiss();

            relativeLayout = findViewById(R.id.root_layout);
            //validate

            Log.v("email: ", etEmail.getText().toString());
            Log.v("name: ", etName.getText().toString());

            //create an user auth
            dao.regUser(etEmail.getText().toString(),
                    etPassword.getText().toString(),
                    etName.getText().toString(),
                    swDriver.isChecked());

        });

        dialog.setNegativeButton(R.string.cancel, (dialog12, which) -> dialog12.dismiss());

        dialog.show();
    }

}
