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

public class InvoerActivity extends AppCompatActivity {

    private ListView mListView;
    private InvoerAdapter mAdapter;
    private List<CourseModel> courseModels = new ArrayList<>();    // NEED A METHOD TO FILL THIS. RETRIEVE THE DATA FROM JSON
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoer);
        dbHelper = DatabaseHelper.getHelper(this);

        mListView = (ListView) findViewById(R.id.my_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                                 Toast t = Toast.makeText(InvoerActivity.this, "Click" + position, Toast.LENGTH_LONG);
                                                 t.show();

                                                 String courseName = ((TextView) view.findViewById(R.id.subject_name)).getText().toString();

                                                 Intent myIntent = new Intent(InvoerActivity.this, DetailVakActivity.class);
                                                 myIntent.putExtra("key", courseName);
                                                 startActivity(myIntent);
                                                 //startActivity(new Intent(InvoerActivity.this, DetailVakActivity.class));

                                             }
                                         }
        );
        Cursor res = dbHelper.query(DatabaseInfo.BarometerTables.COURSE, new String[]{"*"}, null, null, null, null, null);
        res.moveToFirst();
        courseModels.add(new CourseModel(res.getString(res.getColumnIndex("name")), res.getString(res.getColumnIndex("ects")), res.getString(res.getColumnIndex("grade")), res.getString(res.getColumnIndex("period"))));
        while (res.moveToNext()) {
            courseModels.add(new CourseModel(res.getString(res.getColumnIndex("name")), res.getString(res.getColumnIndex("ects")), res.getString(res.getColumnIndex("grade")), res.getString(res.getColumnIndex("period"))));
        }
        mAdapter = new InvoerAdapter(InvoerActivity.this, 0, courseModels);
        mListView.setAdapter(mAdapter);
    }
    }


