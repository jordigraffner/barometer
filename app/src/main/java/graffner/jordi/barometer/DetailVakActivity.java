package graffner.jordi.barometer;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
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
    public static SQLiteDatabase mSQLDB;
    private boolean btnStatus = true;
    public class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vak);

        Intent myIntent = getIntent();
       final String modelName = myIntent.getStringExtra("key"); // will return "FirstKeyValue"


        dbHelper = DatabaseHelper.getHelper(this);


        Cursor res = dbHelper.query(DatabaseInfo.BarometerTables.COURSE, new String[] { DatabaseInfo.CourseColumn.ECTS,
                        DatabaseInfo.CourseColumn.PERIOD, DatabaseInfo.CourseColumn.GRADE  }, DatabaseInfo.CourseColumn.NAME + "= '" + modelName + "'"
                , null, null, null, null);
        while (res.moveToNext()) {
            course = new CourseModel(modelName, res.getString(res.getColumnIndex("ects")), res.getString(res.getColumnIndex("grade")), res.getString(res.getColumnIndex("period")));

        }

        TextView naam = (TextView)findViewById(R.id.subject_name);
        naam.setText("Naam: " + course.name);
        final TextView cijfers = (TextView)findViewById(R.id.subject_cijfers);
        cijfers.setText("Cijfer:");
        final TextView cijfer = (TextView)findViewById(R.id.subject_grade);
        cijfer.setText(course.grade);
        TextView periode = (TextView)findViewById(R.id.subject_period);
        periode.setText("Periode: " + course.period);
        TextView ects = (TextView)findViewById(R.id.subject_ects);
        ects.setText("ECTS: " + course.ects);

        final Button btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (btnStatus == true) {
                    btnEdit.setText("Save");
                    btnStatus = false;
                    EditText mEdit = (EditText) findViewById(R.id.subject_grade);
                    mEdit.setFocusableInTouchMode(true);
                    mEdit.setFilters(new InputFilter[]{new InputFilterMinMax("1", "10")});
                    mEdit.setEnabled(true);
                } else {
                    btnEdit.setText("Edit");
                    btnStatus = true;
                    EditText mEdit = (EditText) findViewById(R.id.subject_grade);
                    mEdit.setEnabled(false);
                    mEdit.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "10")});
                    String where = DatabaseInfo.CourseColumn.NAME+ "= '"+ modelName +"'" ;
                    ContentValues values = new ContentValues();
                    mSQLDB = dbHelper.getWritableDatabase();
                    values.put(DatabaseInfo.CourseColumn.GRADE, cijfer.getText().toString());
                    mSQLDB.update(DatabaseInfo.BarometerTables.COURSE, values, where, null);


                }


            }
        });







    }
}
