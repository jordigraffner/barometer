package graffner.jordi.barometer;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

public class OverzichtActivity extends AppCompatActivity {
    private PieChart mChart;
    public static final int MAX_ECTS = 60;
    public static int currentEcts = 0;
    public static int unknowntEcts = 60;
    public static int failedEcts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overzicht);

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

        for(Double getal: lijst){
            if(getal > 5.4){
                currentEcts += 3;
                unknowntEcts -= 3;
            } else {
                failedEcts += 3;
                unknowntEcts -= 3;
            }
        }

        yValues.add(new Entry(unknowntEcts, 0));
        xValues.add("Resterend ECTS");

        yValues.add(new Entry(currentEcts, 1));
        xValues.add("Behaalde ECTS");

        yValues.add(new Entry(failedEcts, 2));
        xValues.add("Niet behaald");

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
