package br.uff.caronet.dao;

import androidx.annotation.NonNull;

import android.content.Context;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.uff.caronet.util.Utils;
import br.uff.caronet.models.Ride;
import br.uff.caronet.models.User;
import br.uff.caronet.models.ViewUser;

public class Dao {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private CollectionReference clUsers;
    private CollectionReference clRides;
    private CollectionReference clZones;
    private User user;


    private Dao(){

        this.db = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
        this.clUsers = db.collection("Users");
        this.clRides = db.collection("Rides");
        this.clZones = db.collection("Zones");
    }


    //Singleton Dao
    private static Dao dao;

    public static Dao get() {
        if (dao == null) {
            dao = new Dao();
        }
        return dao;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    /**
     *
     * @return the current logged User
     */
    public FirebaseUser getUserAuth(){
        return auth.getCurrentUser();
    }

    /**
     * @return the user authentication ID
     */
    public String getUId(){
        return auth.getUid();
    }

    /**
     * @return the App database
     */
    public FirebaseFirestore getDb() {
        return db;
    }


    /**
     * Make the user registration using the parameters adding him on both
     * Authenticator and Firestore databases.
     *
     * @param email user e-mail as String
     * @param pass user password as String
     * @param name user name as String
     * @param isDriver true if user is driver, false otherwise
     */
    public void regUser(final String email, String pass, final String name, final boolean isDriver) {
        auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Log.v("auth created!: ", auth.getUid());

                        //creating user Object and setting values typed on screen to store on db
                        User user = new User(name, email, isDriver);
                        user.setId(auth.getUid());

                        //adding user on Firestore db with the same ID as Authenticator
                        clUsers.document(auth.getUid()).set(user)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("DocumentSnapshot written with ID: ",
                                                clUsers.document(auth.getUid()).getId());
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

    /**
     *
     * Calls authentication method to sign in using email and password system
     *
     * @param email e-mail as String
     * @param pass password as String
     */
    public void signIn (String email, String pass) {

        auth.signInWithEmailAndPassword(email,pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        DocumentReference userRef = clUsers.document(authResult.getUser().getUid());

                        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()){
                                    DocumentSnapshot userRef = task.getResult();

                                    User user = userRef.toObject(User.class);
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


    public FirestoreRecyclerOptions<Ride> setOpRides (CollectionReference clRides) {

        Query query = clRides.orderBy("departure", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Ride> opRides = new FirestoreRecyclerOptions.Builder<Ride>()
                .setQuery(query, Ride.class)
                .build();

        return opRides;
    }

    public FirestoreRecyclerOptions<Ride> setOpMyRides (CollectionReference clRides, boolean isDriver) {

        if (isDriver){
            Query query = clRides
                    .whereEqualTo("driver.id", dao.getUId())
                    .orderBy("departure", Query.Direction.DESCENDING);

            FirestoreRecyclerOptions<Ride> opRides = new FirestoreRecyclerOptions.Builder<Ride>()
                    .setQuery(query, Ride.class)
                    .build();

            return opRides;
        }
        else{
            ViewUser user = new ViewUser(dao.getUId(), dao.getUser().getName());
            Query query = clRides
                    .whereArrayContains("passengers", user)
                    .orderBy("departure", Query.Direction.DESCENDING);

            FirestoreRecyclerOptions<Ride> opRides = new FirestoreRecyclerOptions.Builder<Ride>()
                    .setQuery(query, Ride.class)
                    .build();
            return opRides;
        }
    }


    /**
     *
     * @return Rides Collection
     */
    public CollectionReference getClRides (){
        return this.clRides;
    }

    /**
     *
     * @return Users Collection
     */
    public CollectionReference getClUsers () {
        return this.clUsers;
    }

    public CollectionReference getClZones() {
        return clZones;
    }

    public void addPassenger(final Context context, String rideId, ViewUser passenger){

        clRides.document(rideId)
                .update("passengers", FieldValue.arrayUnion(passenger))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Utils.showToast(context, "Added on Ride!");
                        }
                        else{
                            Utils.showToast(context, "error");
                        }
                    }
                });
    }

}

