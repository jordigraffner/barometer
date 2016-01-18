package graffner.jordi.barometer;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import graffner.jordi.barometer.Model.CourseModel;
import graffner.jordi.barometer.database.DatabaseHelper;
import graffner.jordi.barometer.database.DatabaseInfo;
import graffner.jordi.barometer.request.GsonRequest;
import graffner.jordi.barometer.request.VolleyHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = DatabaseHelper.getHelper(this);
        Cursor rs = dbHelper.query(DatabaseInfo.BarometerTables.USER, new String[]{"*"}, null, null, null, null, null);
        if (rs.getCount() > 0) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        } else {
            final Button btnLogin = (Button) findViewById(R.id.btnLogin);
            final EditText txtName = (EditText) findViewById(R.id.txtName);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseInfo.UserColumn.NAME, txtName.getText().toString());
                    dbHelper.insert(DatabaseInfo.BarometerTables.USER, null, values);
                    requestSubjects();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestSubjects(){
        Type type = new TypeToken<List<CourseModel>>(){}.getType();

        GsonRequest<List<CourseModel>> request = new GsonRequest<List<CourseModel>>("http://www.fuujokan.nl/subject_lijst.json",
                type, null, new Response.Listener<List<CourseModel>>() {
            @Override
            public void onResponse(List<CourseModel> response) {
                processRequestSucces(response);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                processRequestError(error);
            }
        });
        VolleyHelper.getInstance(this).addToRequestQueue(request);
    }

    private void processRequestSucces(List<CourseModel> subjects ){

        // putting all received classes in my database.
        for (CourseModel cm : subjects) {
            ContentValues cv = new ContentValues();
            cv.put(DatabaseInfo.CourseColumn.NAME, cm.name);
            cv.put(DatabaseInfo.CourseColumn.GRADE, 0);
            cv.put(DatabaseInfo.CourseColumn.ECTS, cm.ects);
            cv.put(DatabaseInfo.CourseColumn.PERIOD , cm.period);
            dbHelper.insert(DatabaseInfo.BarometerTables.COURSE, null, cv);
            Log.d("naam", cm.name);
        }

        Cursor rs = dbHelper.query(DatabaseInfo.BarometerTables.COURSE, new String[]{"*"}, null, null, null, null, null);
        rs.moveToFirst();   // kan leeg zijn en faalt dan
        DatabaseUtils.dumpCursor(rs);

    }

    private void processRequestError(VolleyError error){
        // WAT ZULLEN WE HIERMEE DOEN ?? - niets..
    }


}

