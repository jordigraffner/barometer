package graffner.jordi.barometer;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import graffner.jordi.barometer.Model.CourseModel;
import graffner.jordi.barometer.database.DatabaseHelper;
import graffner.jordi.barometer.database.DatabaseInfo;

public class DetailVakActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private CourseModel course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vak);

        Intent myIntent = getIntent();
        String modelName = myIntent.getStringExtra("key"); // will return "FirstKeyValue"


        dbHelper = DatabaseHelper.getHelper(this);


        Cursor res = dbHelper.query(DatabaseInfo.BarometerTables.COURSE, new String[] { DatabaseInfo.CourseColumn.ECTS,
                        DatabaseInfo.CourseColumn.PERIOD, DatabaseInfo.CourseColumn.GRADE  }, DatabaseInfo.CourseColumn.NAME + "= '" + modelName + "'"
                , null, null, null, null);
        while (res.moveToNext()) {
            course = new CourseModel(modelName, res.getString(res.getColumnIndex("ects")), res.getString(res.getColumnIndex("grade")), res.getString(res.getColumnIndex("period")));

        }

        TextView naam = (TextView)findViewById(R.id.subject_name);
        naam.setText("Naam: " + course.name);
        TextView cijfer = (TextView)findViewById(R.id.subject_grade);
        cijfer.setText("Cijfer: "+course.grade);
        TextView periode = (TextView)findViewById(R.id.subject_period);
        periode.setText("Periode: "+course.period);
        TextView ects = (TextView)findViewById(R.id.subject_ects);
        ects.setText("ECTS: "+course.ects);



        Log.d("naam" ,modelName);
    }
}
