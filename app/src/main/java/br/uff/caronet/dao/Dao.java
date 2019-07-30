package br.uff.caronet.dao;

import android.content.Context;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import br.uff.caronet.util.Utils;
import br.uff.caronet.model.Ride;
import br.uff.caronet.model.User;
import br.uff.caronet.model.ViewUser;

public class Dao {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private CollectionReference clUsers, clRides, clZones, clCities, clCampi;
    private User user;
    private GoogleSignInClient gcClient;

    public GoogleSignInClient getGcClient() {
        return gcClient;
    }

    public void setGcClient(GoogleSignInClient gcClient) {
        this.gcClient = gcClient;
    }

    private Dao(){

        this.db = FirebaseFirestore.getInstance();
        this.auth = FirebaseAuth.getInstance();
        this.clUsers = db.collection("Users");
        this.clRides = db.collection("Rides");
        this.clZones = db.collection("Zones");
        this.clCities = db.collection("Cities");
        this.clCampi = db.collection("Campi");
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
                .addOnSuccessListener(authResult -> {

                    Log.v("auth created!: ", auth.getUid());

                    //creating user Object and setting values typed on screen to store on db
                    User user = new User(auth.getUid(), name, email, isDriver, "234");
                    user.setId(auth.getUid());

                    //adding user on Firestore db with the same ID as Authenticator
                    clUsers.document(auth.getUid()).set(user)
                            .addOnSuccessListener(aVoid -> Log.d("DocumentSnapshot written with ID: ",
                                    clUsers.document(auth.getUid()).getId()))
                            .addOnFailureListener(e -> Log.w("Error adding document", e));
                })
                .addOnFailureListener(e -> Log.w("error auth", e));
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
                .addOnSuccessListener(authResult -> {

                    DocumentReference userRef = clUsers.document(authResult.getUser().getUid());

                    userRef.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            DocumentSnapshot userRef1 = task.getResult();

                            User user = userRef1.toObject(User.class);
                            Log.v("User Logged name: ", user.getName());

                        }
                    });
                })
                .addOnFailureListener(e -> {
                    Log.v("LOGIN: ", "FAIL");

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

    public CollectionReference getCities() {
        return clCities;
    }

    public CollectionReference getClCampi() {
        return clCampi;
    }

    public void setClCampi(CollectionReference clCampi) {
        this.clCampi = clCampi;
    }

    public void addPassenger(final Context context, String rideId, ViewUser passenger){

        clRides.document(rideId)
                .update("passengers", FieldValue.arrayUnion(passenger))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Utils.showToast(context, "Added on Ride!");
                    }
                    else{
                        Utils.showToast(context, "error");
                    }
                });
    }

    public void logOut (){

        auth.signOut();
        gcClient.signOut();
    }

}

