package graffner.jordi.barometer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.uguratar.countingtextview.countingTextView;

import java.util.ArrayList;
import java.util.List;

import graffner.jordi.barometer.Model.CourseModel;
import graffner.jordi.barometer.database.DatabaseHelper;
import graffner.jordi.barometer.database.DatabaseInfo;
import lt.lemonlabs.android.expandablebuttonmenu.ExpandableButtonMenu;
import lt.lemonlabs.android.expandablebuttonmenu.ExpandableMenuOverlay;

public class LoginActivity extends AppCompatActivity {


    private ExpandableMenuOverlay menuOverlay;
    private DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
    private countingTextView points;
    private List<CourseModel> courseModels = new ArrayList<>();    // NEED A METHOD TO FILL THIS. RETRIEVE THE DATA FROM JSON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Cursor rs = dbHelper.query(DatabaseInfo.BarometerTables.USER, new String[]{"*"}, null, null, null, null, null);

        rs.moveToFirst();
        String name = (String) rs.getString(rs.getColumnIndex("name"));
        Log.d("Dit is output ", "dit " + name);



        TextView welcome = ((TextView) findViewById(R.id.txtWelcome));
        welcome.setText("Welkom " + name);

        points = (countingTextView) findViewById(R.id.countingText);
//Animate from 0 to 250
        points.animateText(0,calculateReceivedEct(courseModels));

        menuOverlay = (ExpandableMenuOverlay) findViewById(R.id.button_menu);

        menuOverlay.setOnMenuButtonClickListener(new ExpandableButtonMenu.OnMenuButtonClick() {
            @Override
            public void onClick(ExpandableButtonMenu.MenuButton action) {
                switch (action) {
                    case MID:
                        startActivity(new Intent(LoginActivity.this, OverzichtActivity.class));
                        menuOverlay.getButtonMenu().toggle();
                        break;
                    case LEFT:
                        startActivity(new Intent(LoginActivity.this, InvoerActivity.class));
                        break;
                    case RIGHT:
                        startActivity(new Intent(LoginActivity.this, CreditActivity.class));
                        break;
                }
            }
        });
    }

    public int calculateReceivedEct(List<CourseModel> courses){
        int ect = 0;
        for(CourseModel course: courses) {
            if (Double.parseDouble(course.grade) > 5.4) {
                ect += Integer.parseInt(course.ects);

            }Log.d("cijfer",course.grade);
        }
        return ect;
    }
}