package com.example.patrick.mchacks2015;

import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity {
    int iQVals[];
    String sOperators[];
    Button bRNums[];
    Button bROps[];
    Button bOperators[];
    String sSolution;
    TextView tvSoloution;

    Boolean boolTimer = false;
    long lTime = 60000;
    long lDelay = 1000;
    private Button startButton;
    CountDownTimerActivity timer = new CountDownTimerActivity(lTime, lDelay);

    int score;

    private LinkedList<Character> ops = new LinkedList<> (Arrays.asList('^','/','*','-','+'));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //blah blah blah
        super.onCreate(savedInstanceState);
        score = 0;

        setContentView(R.layout.activity_main);

        iQVals = new int[4];
        sOperators = new String[3];
        bRNums = new Button[4];
        bROps = new Button[4];
        bOperators = new Button[4];

        bRNums[0] = (Button) findViewById(R.id.rNum1);
        bRNums[1] = (Button) findViewById(R.id.rNum2);
        bRNums[2] = (Button) findViewById(R.id.rNum3);
        bRNums[3] = (Button) findViewById(R.id.rNum4);

        bROps[0] = (Button) findViewById(R.id.rOp1);
        bROps[1] = (Button) findViewById(R.id.rOp2);
        bROps[2] = (Button) findViewById(R.id.rOp3);

        bOperators[0] = (Button) findViewById(R.id.nVal1);
        bOperators[1] = (Button) findViewById(R.id.nVal2);
        bOperators[2] = (Button) findViewById(R.id.nVal3);
        bOperators[3] = (Button) findViewById(R.id.nVal4);

        tvSoloution = (TextView) findViewById(R.id.strToGetTo);

        startButton = (Button) findViewById(R.id.startButton);

    }

    public class CountDownTimerActivity extends CountDownTimer {
        public CountDownTimerActivity(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            startButton.setText("lTime's up!");
            startButton.setBackgroundColor(getResources().getColor(R.color.brightOrange));
            boolTimer = false;
            lTime = 60000;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            startButton.setText("" + millisUntilFinished/1000);
            lTime = millisUntilFinished;
        }
    }

    public void startTime(View view){
        if(!boolTimer){
            score = 0;
            TextView tvScore = (TextView) findViewById(R.id.textScore);
            startButton.setBackgroundColor(getResources().getColor(R.color.white));
            tvScore.setText(String.valueOf(score));
            lTime = 60000;
            timer = new CountDownTimerActivity(lTime, lDelay);
            timer.start();
            boolTimer = true;
            setRands(10);
        }
    }

    private void setRands(int lim){

        Random r = new Random();
        int rndNum;
        int setOrder[] = {0,1,2,3};
        for(int i=3; i>0; i--){
            int index = r.nextInt(i+1);

            int a = setOrder[index];
            setOrder[index] = setOrder[i];
            setOrder[i] = a;
        }
        
        for(int i=0; i<4; i++){
            bOperators[i].setText("");
            rndNum = r.nextInt(lim)+1;
            bRNums[setOrder[i]].setText(String.valueOf(rndNum));
            iQVals[setOrder[i]] = rndNum;
        }
        String rndChar;
        for (int i=0; i<3; i++){
            rndChar = intToChar(r.nextInt(4));
            bROps[i].setText(rndChar);
            sOperators[i] = rndChar;
        }
        //code doesn't work...? you are comparing buttons
        if (sOperators[0].equals(sOperators[1]) && sOperators[1].equals(sOperators[2])){
            while (sOperators[1].equals(sOperators[2])){
                rndChar = intToChar(r.nextInt(4));
                bROps[2].setText(rndChar);
                sOperators[2] = rndChar;
            }
        }

        String calcStr = String.valueOf(
                iQVals[setOrder[0]])+
                sOperators[0]+
                String.valueOf(iQVals[setOrder[1]])+
                sOperators[1]+
                String.valueOf(iQVals[setOrder[2]])+
                sOperators[2]+
                String.valueOf(iQVals[setOrder[3]]);

        sSolution = edmas(calcStr).get(0);
        if (Double.parseDouble(sSolution) % 1.0 != 0.0) {
            setRands(10);
        }


        sSolution = String.valueOf((int)Double.parseDouble(sSolution));
        tvSoloution.setText("="+sSolution);
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
        int tag=0;
        if (boolTimer) {
            switch (view.getId()) {
                case R.id.rNum1:
                    tag = 0;
                    break;
                case R.id.rNum2:
                    tag = 1;
                    break;
                case R.id.rNum3:
                    tag = 2;
                    break;
                case R.id.rNum4:
                    tag = 3;
                    break;
            }


            for (int i = 0; i < 4; i++) {
                if (bOperators[i].getText().equals("") && !bRNums[tag].getText().equals("")) {
                    bOperators[i].setText(String.valueOf(iQVals[tag]));
                    bRNums[tag].setText("");
                    break;
                }
            }


            if (!bOperators[0].getText().equals("") && !bOperators[1].getText().equals("") && !bOperators[2].getText().equals("") && !bOperators[3].getText().equals("")) {
                if (sSolution.equals(String.valueOf((int) Double.parseDouble(edmas(
                        bOperators[0].getText().toString() +
                                sOperators[0] +
                                bOperators[1].getText().toString() +
                                sOperators[1] +
                                bOperators[2].getText().toString() +
                                sOperators[2] +
                                bOperators[3].getText().toString()).get(0))))) {
                    score++;
                    timer.cancel();
                    lTime += 5000;
                    timer = new CountDownTimerActivity(lTime, lDelay);
                    timer.start();
                    TextView tvScore = (TextView) findViewById(R.id.textScore);
                    tvScore.setText(String.valueOf(score));
                    setRands(10);
                }
            }
        }
    }
    public void returnVal(View view){
        int tag=0;
        if (boolTimer) {
            switch (view.getId()) {
                case R.id.nVal1:
                    tag = 0;
                    break;
                case R.id.nVal2:
                    tag = 1;
                    break;
                case R.id.nVal3:
                    tag = 2;
                    break;
                case R.id.nVal4:
                    tag = 3;
                    break;
            }
            for (int i = 0; i < 4; i++) {
                if (String.valueOf(iQVals[i]).equals(bOperators[tag].getText().toString()) && bRNums[i].getText().equals("")) {
                    bRNums[i].setText(String.valueOf(iQVals[i]));
                    bOperators[tag].setText("");
                    break;
                }
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

    private List<String> split_by_char_arr(String s, List<Character> a){
        List<String> split_eq;
        String d = "";
        for (int i=0; i<a.size();i++){
            d += delim(a.get(i))+"|";
        }
        d = d.substring(0,d.length()-1);
        split_eq = new LinkedList<>(Arrays.asList(s.split(d)));
        return split_eq;
    }

    private List<String> edmas(String eq){
        List <String> e = split_by_char_arr(eq,ops);

        e.removeAll(Collections.singleton(""));

        for (int i=0; i<e.size(); i++){
            if (ops.contains(Character.valueOf(e.get(i).charAt(0))) && e.get(i).length()==1 && e.get(i+1).equals("-")){
                e.set(i+1,"-"+e.get(i+2));
                e.remove(i+2);
            }
        }
        if (e.get(0).equals("-")){
            e.set(0,"-"+e.get(1));
            e.remove(1);
        }
        for (int i=0; i<ops.size();i++){
            e = solve(ops.get(i),e);
        }
        return e;
    }
    private String delim(char ch){
        //returns properly formatted delimiters for string splitting
        return "(?=[\\\\" + ch + "])|(?<=[\\\\"+ch+"])";
    }
    private List<String> solve(char op, List<String> eq){
        int i = 0;
        while(eq.contains(String.valueOf(op))){
            String j;
            j = eq.get(i);
            if(j.equals(String.valueOf(op))){
                eq.set(i-1, String.valueOf(operator(op,Double.parseDouble(eq.get(i-1)),Double.parseDouble(eq.get(i+1)))));
                eq.remove(i);
                eq.remove(i);
                i-=2;
            }
            i++;
        }
        return eq;
    }

    private double operator(char op, double n1, double n2){
        if (op == '+'){
            return(n1 + n2);
        }
        if (op == '-'){
            return(n1 - n2);
        }
        if (op == '*'){
            return(n1 * n2);
        }
        if (op == '/'){
            return(n1 / n2);
        }
        if (op == '^'){
            return(Math.pow(n1,n2));
        }
        else{
            return -1;
        }
    }
}
