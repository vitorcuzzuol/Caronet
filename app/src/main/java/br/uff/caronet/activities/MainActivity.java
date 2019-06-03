package br.uff.caronet.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import br.uff.caronet.R;
import br.uff.caronet.models.Campi;
import br.uff.caronet.models.TestUser;
import br.uff.caronet.models.User;

public class MainActivity extends AppCompatActivity {

    Button btReg, btLog;

    FirebaseAuth auth;
    FirebaseFirestore db;
    CollectionReference users;

    RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btReg = findViewById(R.id.btReg);
        btLog = findViewById(R.id.btLog);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        users = db.collection("Users");


        //if user is already logged then get his attributes from db and store on a new User object
        if (auth.getCurrentUser() != null){
            Log.v("user auth id: ", auth.getUid());

            DocumentReference userRef = users.document(auth.getUid());

            //check if found the user from db
            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){

                        //user is found on db
                        DocumentSnapshot userRef = task.getResult();

                        //store data on created User object
                        TestUser user = userRef.toObject(TestUser.class);
                        Log.v("User auto-logged name: ", user.getName());

                        Intent intent = new Intent(MainActivity.this, RidesActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Log.w("user could not get on db","...");
                    }
                }
            });
        }

        //Called when Register button is clicked
        btReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //show register dialog
                showRegisterDialog();
            }
        });

        //Called when Login button is clicked
        btLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //show login dialog
                showLoginDialog();
            }
        });
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

        dialog.setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                relativeLayout = findViewById(R.id.root_layout);
                //validate

                auth.signInWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                DocumentReference userRef = users.document(authResult.getUser().getUid());

                                userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()){
                                            DocumentSnapshot userRef = task.getResult();

                                            TestUser user = userRef.toObject(TestUser.class);
                                            Log.v("User Logged name: ", user.getName());

                                        }
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
            }
        });

        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

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

        dialog.setPositiveButton(R.string.register, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //dialog.dismiss();

                relativeLayout = findViewById(R.id.root_layout);
                //validate

                Log.v("email: ", etEmail.getText().toString());
                Log.v("name: ", etName.getText().toString());

                //create an user auth
                auth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                Log.v("auth created!: ", auth.getUid());

                                //creating user Object and setting values typed on screen to store on db
                                TestUser user = new TestUser(etName.getText().toString(),
                                        etEmail.getText().toString(),
                                        swDriver.isChecked());

                                //adding user on firestore db with the same ID as authenticator
                                users.document(auth.getUid()).set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("DocumentSnapshot written with ID: ",
                                                        users.document(auth.getUid()).getId());

                                                Toast.makeText(getApplicationContext(), R.string.registered,
                                                        Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("Error adding document", e);
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("error auth", e);
                            }
                        });
            }
        });

        dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

}
