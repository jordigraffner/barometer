package graffner.jordi.barometer;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import graffner.jordi.barometer.Model.CourseModel;
import graffner.jordi.barometer.database.DatabaseHelper;
import graffner.jordi.barometer.database.DatabaseInfo;

public class DetailVakActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private CourseModel course;
    private int counter = 0;
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
        periode.setText("Periode: " + course.period);
        TextView ects = (TextView)findViewById(R.id.subject_ects);
        ects.setText("ECTS: " + course.ects);

        final Button btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(counter == 0){
                    counter +=1;
                    btnEdit.setText("Save");
                }
                else if(counter == 1){
                    counter += 1;
                
                }
                EditText mEdit = (EditText) findViewById(R.id.subject_grade);
                mEdit.setFocusableInTouchMode(true);

            }
        });






        Log.d("naam", modelName);
    }
}
