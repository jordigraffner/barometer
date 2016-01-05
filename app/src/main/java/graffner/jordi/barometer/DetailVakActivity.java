package graffner.jordi.barometer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DetailVakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vak);

        Intent myIntent = getIntent();
        String firstKeyName = myIntent.getStringExtra("key"); // will return "FirstKeyValue"


        Log.d("Yooooo" ,firstKeyName);
    }
}
