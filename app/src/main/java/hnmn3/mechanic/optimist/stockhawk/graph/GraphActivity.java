package hnmn3.mechanic.optimist.stockhawk.graph;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import hnmn3.mechanic.optimist.stockhawk.R;

public class GraphActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);
        TextView tv = (TextView)findViewById(R.id.graphTv);
        tv.setText(getIntent().getStringExtra("clicked_position"));
    }
}
