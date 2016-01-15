package graffner.jordi.barometer;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overzicht);

        this.currentEcts = 0;
        unknowntEcts = 60;
        failedEcts = 0;

        dbHelper = DatabaseHelper.getHelper(this);
        Cursor res = dbHelper.query(DatabaseInfo.BarometerTables.COURSE, new String[]{"*"}, null, null, null, null, null);
        //Laad courses in list
        res.moveToFirst();
        while(res.moveToNext()){
            courseModels.add(new CourseModel(res.getString(res.getColumnIndex("name")),res.getString(res.getColumnIndex("ects")), res.getString(res.getColumnIndex("grade")), res.getString(res.getColumnIndex("period"))));
        }

        mChart = (PieChart) findViewById(R.id.chart);
        mChart.setDescription("");
        mChart.setTouchEnabled(false);
        mChart.setDrawSliceText(true);
        mChart.getLegend().setEnabled(false);
        mChart.setTransparentCircleColor(Color.rgb(130, 130, 130));
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        setData(0);
    }

    private void setData(int aantal) {
        currentEcts = aantal;

        Double[] lijst = new Double[3];
        lijst[0] = 5.0;
        lijst[1] = 6.6;
        lijst[2] = 0.0;

        ArrayList<Entry> yValues = new ArrayList<>();
        ArrayList<String> xValues = new ArrayList<>();

        for(CourseModel course: courseModels){
            if(Double.parseDouble(course.grade) > 5.4) {
                currentEcts += 3;
                unknowntEcts -= 3;
            } else if(Double.parseDouble(course.grade) < 5.5 && Double.parseDouble(course.grade) != 0) {
                failedEcts += 3;
                unknowntEcts -= 3;
            }
        }

        if (unknowntEcts > 0) {
            yValues.add(new Entry(unknowntEcts, 0));
            xValues.add("Resterend ECTS");
        }

        if(currentEcts > 0) {
            yValues.add(new Entry(currentEcts, 1));
            xValues.add("Behaalde ECTS");
        }

        if(failedEcts > 0) {
            yValues.add(new Entry(failedEcts, 2));
            xValues.add("Niet behaald");
        }

//        yValues.add(new Entry(60 - currentEcts, 1));
//        xValues.add("Resterend ECTS");
//
//        yValues.add(new Entry(aantal, 0));
//        xValues.add("Behaalde ECTS");
//
//        yValues.add(new Entry(60 - currentEcts, 1));
//        xValues.add("Niet Behaald ECTS");



        //  http://www.materialui.co/colors
        ArrayList<Integer> colors = new ArrayList<>();
       colors.add(Color.rgb(255,255,0));

        colors.add(Color.rgb(0,255,0));

        colors.add(Color.rgb(255,0,0));


        PieDataSet dataSet = new PieDataSet(yValues, "ECTS");
        //  dataSet.setDrawValues(false); //schrijf ook de getallen weg.
        dataSet.setColors(colors);

        PieData data = new PieData(xValues, dataSet);
        mChart.setData(data);        // bind je dataset aan de chart.
        mChart.invalidate();        // Aanroepen van een volledige redraw
        Log.d("aantal =", "" + currentEcts);

    }
}
