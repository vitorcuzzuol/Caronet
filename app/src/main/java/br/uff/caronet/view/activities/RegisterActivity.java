package br.uff.caronet.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import java.util.Arrays;

import br.uff.caronet.R;
import br.uff.caronet.controller.UserController;
import br.uff.caronet.dao.Dao;
import br.uff.caronet.model.Car;
import br.uff.caronet.model.User;
import br.uff.caronet.util.Utils;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Switch swDriver;
    private EditText etCarModel, etCarPlate, etPhone;
    private UserController userController = new UserController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        // Views
        swDriver = findViewById(R.id.swDriver);
        etCarPlate = findViewById(R.id.etCarPlate);
        etCarModel = findViewById(R.id.etCarModel);
        etPhone = findViewById(R.id.etPhone);

        // Button listeners
        findViewById(R.id.btConfirmReg).setOnClickListener(this);
        findViewById(R.id.btCancelReg).setOnClickListener(this);

        // Show/hide car details depending on switch
        swDriver.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                showCarDetail();
            }
            else {
                hideCarDetail();
            }
        });
    }

    private void hideCarDetail() {
        etCarModel.setVisibility(View.GONE);
        etCarPlate.setVisibility(View.GONE);
    }

    private void showCarDetail() {
        etCarModel.setVisibility(View.VISIBLE);
        etCarPlate.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();

        switch (i){
            case R.id.btConfirmReg:
                if (isValid()) {
                    regUser();
                    Intent intent = new Intent(this, RidesActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.btCancelReg:
                Dao.get().logOut();
                onBackPressed();
                break;
        }
    }

    private void regUser() {

        // Set user detail on new object
        User user = new User(
                Dao.get().getUId(),
                Dao.get().getUserAuth().getDisplayName(),
                Dao.get().getUserAuth().getEmail(),
                swDriver.isChecked(),
                etPhone.getText().toString()
        );
        // If is a driver, set the car on user
        if (swDriver.isChecked()) user.setCar(new Car(
                etCarPlate.getText().toString(),
                etCarModel.getText().toString()));

        // Add user on DB
        userController.registerUser(user);
    }

    public boolean isValid (){

        // Set variables
        String phone = etPhone.getText().toString();
        String carPlate = etCarPlate.getText().toString();
        String carModel = etCarModel.getText().toString();

        // Validate phone number
        if (phone.length() < 11){
            Utils.showToast(this, "Preencha um número válido!");
            return false;
        }

        // Validate car detail
        if (swDriver.isChecked()) {
            if (carModel == null){
                Utils.showToast(this, "Preencha o modelo do carro!");
                return false;
            }
            if (carPlate.length() < 7){
                Utils.showToast(this, "Preencha a placa do carro!");
                return false;
            }
        }
        return true;
    }

}
