package br.uff.caronet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button bt1, bt2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);

        bt1.setOnClickListener(new View.OnClickListener() {

            //Called when Register button is clicked
            @Override
            public void onClick(View v) {

                //Go to Register Activity then finish this one
                Intent intent = new Intent (MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}
