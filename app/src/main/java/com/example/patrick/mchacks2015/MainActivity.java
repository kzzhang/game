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
    int values[] = new int[4];
    String ops[] = new String[3];
    Button rNum[] = new Button[4];
    Button rOp[] = new Button [3];
    Button nPlace[] = new Button[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //blah blah blah
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rNum[0] = (Button) findViewById(R.id.rNum1);
        rNum[1] = (Button) findViewById(R.id.rNum2);
        rNum[2] = (Button) findViewById(R.id.rNum3);
        rNum[3] = (Button) findViewById(R.id.rNum4);

        rOp[0] = (Button) findViewById(R.id.rOp1);
        rOp[1] = (Button) findViewById(R.id.rOp2);
        rOp[2] = (Button) findViewById(R.id.rOp3);

        nPlace[0] = (Button) findViewById(R.id.nVal1);
        nPlace[1] = (Button) findViewById(R.id.nVal2);
        nPlace[2] = (Button) findViewById(R.id.nVal3);
        nPlace[3] = (Button) findViewById(R.id.nVal4);

        //gen rand #s
        setRands(10);

    }

    private void setRands(int lim){

        Random r = new Random();
        int rndNum;
        for(int i=0; i<4; i++){
            rndNum = r.nextInt(lim)+1;
            rNum[i].setText(String.valueOf(rndNum));
            values[i] = rndNum;
        }
        String rndChar;
        for (int i=0; i<3; i++){
            rndChar = intToChar(r.nextInt(4));
            rOp[i].setText(rndChar);
            ops[i] = rndChar;
        }

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

    public void buttonClick(View view){
        int idNum = view.getId();
        int tag = Integer.valueOf(
                view.getTag(
                        idNum
                ).toString());

        Button button = (Button) findViewById(idNum);

        button.setText("");

        for (int i=0; i<4;i++){
            if (nPlace[i].getText()!=""){
                nPlace[i].setText(values[i]);
            }
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
