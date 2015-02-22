package com.example.patrick.mchacks2015;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends ActionBarActivity {
    int iTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //blah blah blah
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //variables
        final TextView timeCountDown = (TextView) this.findViewById(R.id.textCountDown);

        Button rNum1 = (Button) findViewById(R.id.rNum1);
        Button rNum2 = (Button) findViewById(R.id.rNum2);
        Button rNum3 = (Button) findViewById(R.id.rNum3);
        Button rNum4 = (Button) findViewById(R.id.rNum4);
        //objects


        //gen rand #s
        setRands(10);

        rNum1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRands(10);
            }
        });
    }

    private void setRands(int lim){
        Button rNum1 = (Button) findViewById(R.id.rNum1);
        Button rNum2 = (Button) findViewById(R.id.rNum2);
        Button rNum3 = (Button) findViewById(R.id.rNum3);
        Button rNum4 = (Button) findViewById(R.id.rNum4);

        Button rOp1 = (Button) findViewById(R.id.rOp1);
        Button rOp2 = (Button) findViewById(R.id.rOp2);
        Button rOp3 = (Button) findViewById(R.id.rOp3);


        Random r = new Random();
        rNum1.setText(String.valueOf(r.nextInt(lim)));
        rNum2.setText(String.valueOf(r.nextInt(lim)));
        rNum3.setText(String.valueOf(r.nextInt(lim)));
        rNum4.setText(String.valueOf(r.nextInt(lim)));

        rOp1.setText(intToChar(r.nextInt(4)));
        rOp2.setText(intToChar(r.nextInt(4)));
        rOp3.setText(intToChar(r.nextInt(4)));

    }

    private String intToChar(int c){
        switch (c){
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "/";
            case 3:
                return "*";
            default:
                return "err";
        }

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
