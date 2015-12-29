package graffner.jordi.barometer;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import graffner.jordi.barometer.database.DatabaseHelper;
import graffner.jordi.barometer.database.DatabaseInfo;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = DatabaseHelper.getHelper(this);
        final Button btnLogin = (Button) findViewById(R.id.btnLogin);
        final EditText txtName   = (EditText)findViewById(R.id.txtName);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(DatabaseInfo.UserColumn.NAME, txtName.getText().toString());
                dbHelper.insert(DatabaseInfo.BarometerTables.USER, null, values);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
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
}
