package graffner.jordi.barometer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import graffner.jordi.barometer.Model.CourseModel;
import graffner.jordi.barometer.adapter.InvoerAdapter;

public class InvoerActivity extends AppCompatActivity {

    private ListView mListView;
    private InvoerAdapter mAdapter;
    private List<CourseModel> courseModels = new ArrayList<>();    // NEED A METHOD TO FILL THIS. RETRIEVE THE DATA FROM JSON


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoer);

        mListView = (ListView) findViewById(R.id.my_list_view);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                                 Toast t = Toast.makeText(InvoerActivity.this,"Click" + position, Toast.LENGTH_LONG);
                                                 t.show();
                                             }
                                         }
        );
        courseModels.add(new CourseModel("IKPMD", "3", "10", "2"));             // DUMMY DATA
        mAdapter = new InvoerAdapter(InvoerActivity.this, 0, courseModels);
        mListView.setAdapter(mAdapter);
    }
    }


