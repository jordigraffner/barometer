package graffner.jordi.barometer;


import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;

import java.util.ArrayList;

import graffner.jordi.barometer.adapter.MenuAdapter;
import graffner.jordi.barometer.database.DatabaseHelper;
import graffner.jordi.barometer.database.DatabaseInfo;


public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    GridView gv;
    Context context;
    ArrayList prgmName;
    public static String [] prgmNameList={"home", "twee"};
    public static int [] prgmImages={R.drawable.lists,R.drawable.overview};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = DatabaseHelper.getHelper(this);
        Cursor rs = dbHelper.query(DatabaseInfo.BarometerTables.USER, new String[]{"*"}, null, null, null, null, null);

        rs.moveToFirst();
        String name = (String) rs.getString(rs.getColumnIndex("name"));
        Log.d("Dit is output ", "dit " + name);

        gv=(GridView) findViewById(R.id.gridView1);
        gv.setAdapter(new MenuAdapter(this, prgmNameList, prgmImages));



    }
}