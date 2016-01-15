package graffner.jordi.barometer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.victor.ringbutton.RingButton;

public class CreditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        RingButton ringButton = (RingButton) findViewById(R.id.ringButton);
        ringButton.setOnClickListener(new RingButton.OnClickListener() {
            @Override
            public void clickUp() {

            }

            @Override
            public void clickDown() {
                Toast.makeText(getApplicationContext(), "Click down", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
