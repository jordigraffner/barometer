package graffner.jordi.barometer;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import graffner.jordi.barometer.database.DatabaseHelper;
import graffner.jordi.barometer.database.DatabaseInfo;
import lt.lemonlabs.android.expandablebuttonmenu.ExpandableButtonMenu;
import lt.lemonlabs.android.expandablebuttonmenu.ExpandableMenuOverlay;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ExpandableMenuOverlay menuOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = DatabaseHelper.getHelper(this);
        Cursor rs = dbHelper.query(DatabaseInfo.BarometerTables.USER, new String[]{"*"}, null, null, null, null, null);

        rs.moveToFirst();
        String name = (String) rs.getString(rs.getColumnIndex("name"));
        Log.d("Dit is output ", "dit " + name);

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
}