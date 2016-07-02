package hnmn3.mechanic.optimist.stockhawk.graph;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.FillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import hnmn3.mechanic.optimist.stockhawk.R;
import hnmn3.mechanic.optimist.stockhawk.data.HistoryColumns;
import hnmn3.mechanic.optimist.stockhawk.data.QuoteColumns;
import hnmn3.mechanic.optimist.stockhawk.data.QuoteProvider;

public class GraphActivity extends FragmentActivity {

    private LineChart mChart;
    String dates[];
    float close[];
    Intent intentReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_line_graph);
        intentReceived = getIntent();
        Toast.makeText(GraphActivity.this, intentReceived.getStringExtra("stock_symbol"), Toast.LENGTH_SHORT).show();

        mChart = (LineChart) findViewById(R.id.chart1);
        mChart.setViewPortOffsets(20, 20 , 20, 20);
        mChart.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        // no description text
        mChart.setDescription("Past One month Stock graph");
        mChart.setDescriptionTextSize(15f);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        mChart.setDrawGridBackground(false);

        XAxis x = mChart.getXAxis();
        x.setEnabled(false);

        YAxis y = mChart.getAxisLeft();
        y.setEnabled(false);

        mChart.getAxisRight().setEnabled(false);

        getData();
        // add data
        setData(dates,close);

        mChart.getLegend().setEnabled(false);

        mChart.animateXY(2000, 2000);

        List<ILineDataSet> sets = mChart.getData()
                .getDataSets();

        for (ILineDataSet iSet : sets) {

            LineDataSet set = (LineDataSet) iSet;
            set.setMode(LineDataSet.Mode.LINEAR);
            set.setDrawCircles(true);
            set.setDrawValues(true);
        }

        mChart.invalidate();
    }

    private  void getData(){
        Cursor c = null ;

        String selectArgs[] = {intentReceived.getStringExtra("stock_symbol")};
        c = getBaseContext().getContentResolver().query(QuoteProvider.HISTORY.CONTENT_URI,
                null,
                QuoteColumns.SYMBOL+"=? ",selectArgs,null);
        if(c!=null && c.moveToFirst()){
            int count = c.getCount();
            close = new float[count];
            dates = new String[count];
            int i=0;
            do{
                close[i] = Float.parseFloat(c.getString(c.getColumnIndex(HistoryColumns.Close)));
                dates[i] = c.getString(c.getColumnIndex(HistoryColumns.Date));
                i++;
            }while(c.moveToNext());
        }else{
            Toast.makeText(GraphActivity.this, "No data Available in Database :(", Toast.LENGTH_SHORT).show();
        }
    }



    private void setData(String[] dates, float[] yAxisValues) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        int len=0;
        if(yAxisValues!=null)
        len = yAxisValues.length;
        else{
            Toast.makeText(GraphActivity.this, "No data available", Toast.LENGTH_SHORT).show();;
            return;
        }
        for (int i = 0; i < len; i++) {
            yVals.add(new Entry(yAxisValues[i], i));
        }

        LineDataSet set1;
        // create a dataset and give it a type
        set1 = new LineDataSet(yVals, "DataSet 1");

        set1.setDrawCubic(true);
        set1.setCubicIntensity(0.2f);
        //set1.setDrawFilled(true);
        set1.setDrawCircles(false);
        set1.setLineWidth(1.8f);
        set1.setCircleRadius(4f);
        set1.setCircleColor(Color.WHITE);
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setColor(Color.WHITE);
        set1.setFillColor(Color.WHITE);
        set1.setFillAlpha(100);
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setFillFormatter(new FillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return -10;
            }
        });

        // create a data object with the datasets
        LineData data = new LineData(dates,set1 );
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

}

