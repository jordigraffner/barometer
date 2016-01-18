package graffner.jordi.barometer;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import graffner.jordi.barometer.Model.CourseModel;
import graffner.jordi.barometer.database.DatabaseHelper;
import graffner.jordi.barometer.database.DatabaseInfo;
import graffner.jordi.barometer.regex.DecimalDigitsInputFilter;


public class DetailVakActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private CourseModel course;
    public static SQLiteDatabase mSQLDB;
    private boolean btnStatus = true;
    public class InputFilterDecimalMinMax implements InputFilter {

        private double min, max;

        public InputFilterDecimalMinMax(double min, double max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterDecimalMinMax(String min, String max) {
            this.min = Double.parseDouble(min);
            this.max = Double.parseDouble(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            try {
                String value = dest.subSequence(0, dstart).toString()
                        + source.subSequence(start, end)
                        + dest.subSequence(dend, dest.length());
                if (value.isEmpty() || value.equals("-")) {
                    return null;
                }
                double input = Double.parseDouble(value);
                if (isInRange(min, max, input)) {
                    return null;
                }
            } catch (NumberFormatException nfe) {
            }
            return dest.subSequence(dstart, dend);
        }
        private boolean isInRange(double a, double b, double c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_vak);

        Intent myIntent = getIntent();

        //returned Course naam om verder te zoeken in DB
        final String modelName = myIntent.getStringExtra("key");


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
                    btnEdit.setText("   Save   ");
                    btnStatus = false;
                    EditText mEdit = (EditText) findViewById(R.id.subject_grade);
                    mEdit.setFocusableInTouchMode(true);
                    mEdit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2,1),new InputFilterDecimalMinMax(1.0, 10.0) });
                    mEdit.setEnabled(true);
                } else {
                    btnEdit.setText("   Edit   ");
                    btnStatus = true;
                    EditText mEdit = (EditText) findViewById(R.id.subject_grade);
                    mEdit.setEnabled(false);
                    mEdit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2,1), new InputFilterDecimalMinMax(1.0, 10.0)});
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
