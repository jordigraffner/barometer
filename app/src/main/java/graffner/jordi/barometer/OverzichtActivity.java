package graffner.jordi.barometer;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import graffner.jordi.barometer.Model.CourseModel;
import graffner.jordi.barometer.database.DatabaseHelper;
import graffner.jordi.barometer.database.DatabaseInfo;

public class OverzichtActivity extends AppCompatActivity {
    private PieChart mChart;
    public static int currentEcts;
    public static int unknowntEcts;
    public static int failedEcts;
    private DatabaseHelper dbHelper;
    private List<CourseModel> courseModels = new ArrayList<>();
    private ArrayList<Entry> yValues = new ArrayList<>();
    private ArrayList<String> xValues = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overzicht);

        currentEcts = 0;
        unknowntEcts = 60;
        failedEcts = 0;

        dbHelper = DatabaseHelper.getHelper(this);
        Cursor res = dbHelper.query(DatabaseInfo.BarometerTables.COURSE, new String[]{"*"}, null, null, null, null, null);
        //Laad courses in list
        res.moveToFirst();
        courseModels.add(new CourseModel(res.getString(res.getColumnIndex("name")), res.getString(res.getColumnIndex("ects")), res.getString(res.getColumnIndex("grade")), res.getString(res.getColumnIndex("period"))));
        while(res.moveToNext()){
            courseModels.add(new CourseModel(res.getString(res.getColumnIndex("name")),res.getString(res.getColumnIndex("ects")), res.getString(res.getColumnIndex("grade")), res.getString(res.getColumnIndex("period"))));
        }

        mChart = (PieChart) findViewById(R.id.chart);
        mChart.setDescription("Studievoortgang");
        mChart.setTouchEnabled(true);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(true);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        setData(0);
    }

    public void onBackPressed() {
        // your code.
        startActivity(new Intent(OverzichtActivity.this, LoginActivity.class));

    }

    private void setData(int aantal) {
        currentEcts = aantal;

        Double[] lijst = new Double[3];
        lijst[0] = 5.0;
        lijst[1] = 6.6;
        lijst[2] = 0.0;


        for(CourseModel course: courseModels){
            if(Double.parseDouble(course.grade) > 5.4) {
                currentEcts += 3;
                unknowntEcts -= 3;
            } else if(Double.parseDouble(course.grade) < 5.5 && Double.parseDouble(course.grade) != 0) {
                failedEcts += 3;
                unknowntEcts -= 3;
            }
        }

        int teller = 0;
        ArrayList<Integer> colors = new ArrayList<>();

        if (unknowntEcts > 0) {
            yValues.add(new Entry(unknowntEcts, teller));
            xValues.add("Resterend ECTS");
            colors.add(Color.rgb(255,255,0));
            teller++;
        }

        if(currentEcts > 0) {
            yValues.add(new Entry(currentEcts, teller));
            xValues.add("Behaalde ECTS");
            colors.add(Color.rgb(0, 255, 0));
            teller++;
        }

        if(failedEcts > 0) {
            yValues.add(new Entry(failedEcts, teller));
            xValues.add("Niet behaald");
            colors.add(Color.rgb(255, 0, 0));
            teller++;
        }

        //  http://www.materialui.co/colors








        PieDataSet dataSet = new PieDataSet(yValues, "ECTS");
        //  dataSet.setDrawValues(false); //schrijf ook de getallen weg.
        dataSet.setColors(colors);

        PieData data = new PieData(xValues, dataSet);

        mChart.setData(data);        // bind je dataset aan de chart.
        Log.d("aantal =", "" + currentEcts);
        if(failedEcts > 9){
            showBsaAlert();
        }

        mChart.invalidate();        // Aanroepen van een volledige redraw
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

                if (e == null)
                    return;
                Log.i("VAL SELECTED",
                        "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                                + ", DataSet index: " + dataSetIndex + " " + xValues.get(e.getXIndex()));

                String nameValue = xValues.get(e.getXIndex());

                Intent myIntent = new Intent(OverzichtActivity.this, SelectedCoursesActivity.class);
                myIntent.putExtra("key", nameValue);
                startActivity(myIntent);
            }

            @Override
            public void onNothingSelected() {
                //Doet niks
            }
        });
    }

    public void showBsaAlert(){
        String message;
        if(failedEcts < 16){
            message = "Pas op! Op dit moment heb je " + failedEcts + " ECT's niet gehaald. Bij het aantal van 16 niet gehaalde ECT's en hoger" +
                    " ontvang je een BSA";
        } else {
            message = "Op dit moment heb je " + failedEcts + " ECT's niet behaald. Op dit moment betekent dit een BSA.";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
