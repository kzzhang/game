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
    int values[];
    String opers[];
    Button rNum[];
    Button rOp[];
    Button nPlace[];
    String svaltoget;
    TextView valToGet;

    Boolean timerStat = false;
    long time = 60000;
    long delay = 1000;
    private Button startButton;
    CountDownTimerActivity timer = new CountDownTimerActivity(time, delay);

    int score;

    private LinkedList<Character> ops = new LinkedList<> (Arrays.asList('^','/','*','-','+'));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //blah blah blah
        super.onCreate(savedInstanceState);
        score = 0;

        setContentView(R.layout.activity_main);

        values = new int[4];
        opers = new String[3];
        rNum = new Button[4];
        rOp = new Button[4];
        nPlace = new Button[4];

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

        valToGet = (TextView) findViewById(R.id.strToGetTo);

        startButton = (Button) findViewById(R.id.startButton);

    }

    public class CountDownTimerActivity extends CountDownTimer {
        public CountDownTimerActivity(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            startButton.setText("Time's up!");
            startButton.setBackgroundColor(getResources().getColor(R.color.brightOrange));
            timerStat = false;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            startButton.setText("" + millisUntilFinished/1000);
        }
    }

    public void startTime(View view){
        if(!timerStat){
            score = 0;
            TextView sc = (TextView) findViewById(R.id.textCountDown);
            startButton.setBackgroundColor(getResources().getColor(R.color.white));
            sc.setText(String.valueOf(score));
            time = 60000;
            timer.start();
            timerStat = true;
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
            nPlace[i].setText("");
            rndNum = r.nextInt(lim)+1;
            rNum[setOrder[i]].setText(String.valueOf(rndNum));
            values[setOrder[i]] = rndNum;
        }
        String rndChar;
        for (int i=0; i<3; i++){
            rndChar = intToChar(r.nextInt(4));
            rOp[i].setText(rndChar);
            opers[i] = rndChar;
        }
        String calcStr = String.valueOf(
                values[setOrder[0]])+
                opers[0]+
                String.valueOf(values[setOrder[1]])+
                opers[1]+
                String.valueOf(values[setOrder[2]])+
                opers[2]+
                String.valueOf(values[setOrder[3]]);

        svaltoget = edmas(calcStr).get(0);
        if (Double.parseDouble(svaltoget) % 1.0 != 0.0) {
            setRands(10);
        }


        svaltoget = String.valueOf((int)Double.parseDouble(svaltoget));
        valToGet.setText("="+svaltoget);
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
        if (timerStat) {
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
                if (nPlace[i].getText().equals("") && !rNum[tag].getText().equals("")) {
                    nPlace[i].setText(String.valueOf(values[tag]));
                    rNum[tag].setText("");
                    break;
                }
            }


            if (!nPlace[0].getText().equals("") && !nPlace[1].getText().equals("") && !nPlace[2].getText().equals("") && !nPlace[3].getText().equals("")) {
                if (svaltoget.equals(String.valueOf((int) Double.parseDouble(edmas(
                        nPlace[0].getText().toString() +
                                opers[0] +
                                nPlace[1].getText().toString() +
                                opers[1] +
                                nPlace[2].getText().toString() +
                                opers[2] +
                                nPlace[3].getText().toString()).get(0))))) {
                    score++;
                    TextView sc = (TextView) findViewById(R.id.textCountDown);
                    sc.setText(String.valueOf(score));
                    setRands(10);
                }
            }
        }
    }
    public void returnVal(View view){
        int tag=0;
        if (timerStat) {
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
                if (String.valueOf(values[i]).equals(nPlace[tag].getText().toString()) && rNum[i].getText().equals("")) {
                    rNum[i].setText(String.valueOf(values[i]));
                    nPlace[tag].setText("");
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
