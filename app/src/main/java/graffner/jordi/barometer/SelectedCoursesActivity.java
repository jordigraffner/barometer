package graffner.jordi.barometer;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import graffner.jordi.barometer.Model.CourseModel;
import graffner.jordi.barometer.adapter.InvoerAdapter;
import graffner.jordi.barometer.database.DatabaseHelper;
import graffner.jordi.barometer.database.DatabaseInfo;

public class SelectedCoursesActivity extends AppCompatActivity {

    private double compareGradeStart;
    private double compareGradeEnd;
    private List<CourseModel> courseModels = new ArrayList<>();
    private List<CourseModel> showCourses = new ArrayList<>();
    private  DatabaseHelper dbHelper = DatabaseHelper.getHelper(this);
    private InvoerAdapter mAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_courses);

        Intent myIntent = getIntent();
        TextView title = (TextView)findViewById(R.id.txtSelectedCourseTitle);

        final String valueName = myIntent.getStringExtra("key"); // will return "FirstKeyValue"
        if(valueName.equals("Resterend ECTS")){
            compareGradeStart = 0;
            compareGradeEnd = 0;
            title.setText("Vakken nog niet ingevoerd");
        } else if(valueName.equals(("Behaalde ECTS"))){
            compareGradeStart = 5.5;
            compareGradeEnd = 10;
            title.setText("Behaalde vakken");
        } else{
            compareGradeStart = 1;
            compareGradeEnd = 5.4;
            title.setText("Niet behaalde vakken");
        }
        loadCourses();
        for(CourseModel course: courseModels){
            if(Double.parseDouble(course.grade) >= compareGradeStart && Double.parseDouble(course.grade) <= compareGradeEnd){
                showCourses.add(course);
            }
        }

        mListView = (ListView) findViewById(R.id.courses_list);
        mAdapter = new InvoerAdapter(SelectedCoursesActivity.this, 0, showCourses);
        mListView.setAdapter(mAdapter);
    }

    public void loadCourses(){
        Cursor res = dbHelper.query(DatabaseInfo.BarometerTables.COURSE, new String[]{"*"}, null, null, null, null, null);
        //Laad courses in list
        res.moveToFirst();
        courseModels.add(new CourseModel(res.getString(res.getColumnIndex("name")), res.getString(res.getColumnIndex("ects")), res.getString(res.getColumnIndex("grade")), res.getString(res.getColumnIndex("period"))));
        while(res.moveToNext()){
            courseModels.add(new CourseModel(res.getString(res.getColumnIndex("name")),res.getString(res.getColumnIndex("ects")), res.getString(res.getColumnIndex("grade")), res.getString(res.getColumnIndex("period"))));
        }
    }
}
