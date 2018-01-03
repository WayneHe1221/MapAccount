package com.chun.mapaccount;

/**
 * Created by Wayne on 2017/12/19.
 */

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;


public class AddItem extends Activity {
    private Button finishAdd,btn_setplace,btn_editObj;
    private TextView calView,item_view;
    private Button btnclear,btn9,btn8,btn7,btn6,btn5,btn4,btn3,btn2,btn1,btn0;
    private Button btndel,btndiv,btnmul,btnsub,btnadd,btnpoint,btnequ;
    private Switch switch1;
    private boolean point = false;

    private static  char ADD= '+';
    private static  char SUB = '-';
    private static  char MUL= '*';
    private static  char DIV = '/';
    private static  char MOD = '%';
    private char CALCULATE = '0'; //判斷計算位置0為無狀態1為已完成計算  剩餘為其他計算狀態
    String save;

    private double one = Double.NaN;
    private double two= 0;
    DecimalFormat decimalFormat = new DecimalFormat("#.#");
    MyDB myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        myDb = new MyDB(AddItem.this);
        item_view = (TextView) findViewById(R.id.item_view);

        //支出收入轉換
        switch1 = (Switch) findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(compoundButton.isChecked()){
                    Toast.makeText(AddItem.this,getString(R.string.coast_show),Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(AddItem.this,getString(R.string.income_show),Toast.LENGTH_SHORT).show();
                }
            }
        });

        //完成按鈕
        finishAdd = (Button) findViewById(R.id.finish_edit);
        finishAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //myDb.addCoast("2018/01/02",Double.parseDouble(calView.getText().toString()),item_view.getText().toString(),23.5423,121.234);
                //myDb.delete(1);
                //myDb.storyDB();
                if(switch1.isChecked()) {
                    if (Double.parseDouble(calView.getText().toString()) > 0){
                        myDb.addCoast("2018/01/02", Double.parseDouble(calView.getText().toString()), item_view.getText().toString(), 23.5423, 121.234);
                        Toast.makeText(AddItem.this, getString(R.string.new_finish), Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(AddItem.this,"新增失敗 金額錯誤",Toast.LENGTH_SHORT).show();
                }else{
                    if(Double.parseDouble(calView.getText().toString()) > 0) {
                        myDb.addIncome("2018/01/02", Double.parseDouble(calView.getText().toString()), item_view.getText().toString());
                        Toast.makeText(AddItem.this, getString(R.string.new_finish), Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(AddItem.this,"新增失敗 金額錯誤",Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent();
                intent.setClass(AddItem.this, MapAccount.class);
                startActivity(intent);
                AddItem.this.finish();
            }
        });

        //選項設定按鈕
        btn_editObj = (Button) findViewById(R.id.btn_editObj);
        btn_editObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddItem.this, "設定選項",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(AddItem.this, EditObj.class);
                startActivity(intent);
            }
        });
        //設定地點按鈕
        btn_setplace = (Button) findViewById(R.id.btn_setplace);
        btn_setplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddItem.this, "選擇地點",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(AddItem.this, SetPlace.class);
                startActivity(intent);
            }
        });
        //計算結果顯示
        calView = (TextView) findViewById(R.id.calView);
        //清除按鈕
        btnclear = (Button) findViewById(R.id.btnclear);
        btnclear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                calView.setText("0");
                point = false;
                one = Double.NaN;
                CALCULATE = '0';
                one = 0;
                two = 0;
                save = "";
            }
        });
        //削減按鈕
        btndel = (Button) findViewById(R.id.btndel);
        btndel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(calView.getText().toString().equals("0")  || calView.getText().toString().equals(save) ) {
                    calView.setText(calView.getText().toString().substring(0, calView.getText().length() - 1));
                    CALCULATE = '0';
                }else if(calView.getText().toString().equals("")  ){
                    calView.setText("");
                }else{
                    calView.setText(calView.getText().toString().substring(0, calView.getText().length() - 1));
                }
            }
        });

        //除按鈕
        btndiv = (Button) findViewById(R.id.btndiv);
        btndiv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(CALCULATE == '1'){
                    calView.setText(decimalFormat.format(one));
                    one = 0;
                    CALCULATE = '0';
                    computeCalculation();
                    CALCULATE = DIV;
                    calView.setText(one + "/");
                    save = calView.getText().toString();
                }

                if (calView.getText().toString().equals("")&&CALCULATE != '0') {
                    calView.setText(save.toString().substring(0, save.length() - 1));
                    one = 0;
                    CALCULATE = '0';
                    computeCalculation();
                    CALCULATE = DIV;
                    calView.setText(save.toString().substring(0, save.length() - 1) + "/");
                    save = calView.getText().toString();
                }else if(calView.getText().toString().equals("")&&CALCULATE == '0'){
                    calView.setText("");
                }else if(calView.getText().toString().equals(save)&&CALCULATE != '0'){
                    calView.setText(save.toString().substring(0, save.length() - 1));
                    one = 0;
                    CALCULATE = '0';
                    computeCalculation();
                    CALCULATE = DIV;
                    calView.setText(save.toString().substring(0, save.length() - 1) + "/");
                    save = calView.getText().toString();
                }else {
                    computeCalculation();
                    CALCULATE = DIV;
                    calView.setText(one+"/");
                    save = calView.getText().toString();
                }
            }
        });
        //乘按鈕
        btnmul = (Button) findViewById(R.id.btnmul);
        btnmul.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(CALCULATE == '1'){
                    calView.setText(decimalFormat.format(one));
                    one = 0;
                    CALCULATE = '0';
                    computeCalculation();
                    CALCULATE = MUL;
                    calView.setText(one + "*");
                    save = calView.getText().toString();
                }

                if (calView.getText().toString().equals("")&&CALCULATE != '0') {
                    calView.setText(save.toString().substring(0, save.length() - 1));
                    one = 0;
                    CALCULATE = '0';
                    computeCalculation();
                    CALCULATE = MUL;
                    calView.setText(save.toString().substring(0, save.length() - 1) + "*");
                    save = calView.getText().toString();
                }else if(calView.getText().toString().equals("")&&CALCULATE == '0'){
                    calView.setText("");
                }else if(calView.getText().toString().equals(save)&&CALCULATE != '0'){
                    calView.setText(save.toString().substring(0, save.length() - 1));
                    one = 0;
                    CALCULATE = '0';
                    computeCalculation();
                    CALCULATE = MUL;
                    calView.setText(save.toString().substring(0, save.length() - 1) + "*");
                    save = calView.getText().toString();
                }else {
                    computeCalculation();
                    CALCULATE = MUL;
                    calView.setText(one+"*");
                    save = calView.getText().toString();
                }
            }
        });
        //減按鈕
        btnsub = (Button) findViewById(R.id.btnsub);
        btnsub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(CALCULATE == '1'){
                    calView.setText(decimalFormat.format(one));
                    one = 0;
                    CALCULATE = '0';
                    computeCalculation();
                    CALCULATE = SUB;
                    calView.setText(one + "-");
                    save = calView.getText().toString();
                }

                if (calView.getText().toString().equals("")&&CALCULATE != '0') {
                    calView.setText(save.toString().substring(0, save.length() - 1));
                    one = 0;
                    CALCULATE = '0';
                    computeCalculation();
                    CALCULATE = SUB;
                    calView.setText(save.toString().substring(0, save.length() - 1) + "-");
                    save = calView.getText().toString();
                }else if(calView.getText().toString().equals("")&&CALCULATE == '0'){
                    calView.setText("");
                }else if(calView.getText().toString().equals(save)&&CALCULATE != '0'){
                    calView.setText(save.toString().substring(0, save.length() - 1));
                    one = 0;
                    CALCULATE = '0';
                    computeCalculation();
                    CALCULATE = SUB;
                    calView.setText(save.toString().substring(0, save.length() - 1) + "-");
                    save = calView.getText().toString();
                }else {
                    computeCalculation();
                    CALCULATE = SUB;
                    calView.setText(one+"-");
                    save = calView.getText().toString();
                }
            }
        });
        //加按鈕
        btnadd = (Button) findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(CALCULATE == '1'){
                    calView.setText(decimalFormat.format(one));
                    one = 0;
                    CALCULATE = '0';
                    computeCalculation();
                    CALCULATE = ADD;
                    calView.setText(one + "+");
                    save = calView.getText().toString();
                }

                if (calView.getText().toString().equals("")&&CALCULATE != '0') {
                    calView.setText(save.toString().substring(0, save.length() - 1));
                    one = 0;
                    CALCULATE = '0';
                    computeCalculation();
                    CALCULATE = ADD;
                    calView.setText(save.toString().substring(0, save.length() - 1) + "+");
                    save = calView.getText().toString();
                }else if(calView.getText().toString().equals("")&&CALCULATE == '0'){
                    calView.setText("");
                }else if(calView.getText().toString().equals(save)&&CALCULATE != '0'){
                    calView.setText(save.toString().substring(0, save.length() - 1));
                    one = 0;
                    CALCULATE = '0';
                    computeCalculation();
                    CALCULATE = ADD;
                    calView.setText(save.toString().substring(0, save.length() - 1) + "+");
                    save = calView.getText().toString();
                }else{
                    computeCalculation();
                    CALCULATE = ADD;
                    calView.setText(one+"+");
                    save = calView.getText().toString();
                }
            }
        });
        //點按鈕
        btnpoint = (Button) findViewById(R.id.btnpoint);
        btnpoint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(point)
                    calView.setText(calView.getText());
                else
                    calView.setText(calView.getText()+".");  point = true;
            }
        });
        //等於按鈕
        btnequ = (Button) findViewById(R.id.btnequ);
        btnequ.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                //判斷案等於鍵  CALCULATE = '1' 是以防使用者重複按等於鍵  判斷save是看是否為一開始就按等於
                if(save!=null && CALCULATE != '1'){
                    computeCalculation();
                    calView.setText(decimalFormat.format(one));
                    CALCULATE = '1';
                }else if (CALCULATE == '1'){
                    calView.setText(calView.getText());
                    CALCULATE = '1';
                }else{
                    computeCalculation();
                    calView.setText(decimalFormat.format(one));
                    CALCULATE = '1';
                }
            }
        });


        //數字按鈕
        btn9 = (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(calView.getText().toString().equals("0")  || calView.getText().toString().equals(save) ){
                    calView.setText("9");  point = false;
                }else{calView.append("9");}
            }
        });
        btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(calView.getText().toString().equals("0")  ||calView.getText().toString().equals(save) ){
                    calView.setText("8");  point = false;
                }else{calView.append("8");}
            }
        });
        btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(calView.getText().toString().equals("0")  ||calView.getText().toString().equals(save) ){
                    calView.setText("7");  point = false;
                }else{calView.append("7");}
            }
        });
        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(calView.getText().toString().equals("0")  ||calView.getText().toString().equals(save) ){
                    calView.setText("6");  point = false;
                }else{calView.append("6");}
            }
        });
        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(calView.getText().toString().equals("0")  ||calView.getText().toString().equals(save) ){
                    calView.setText("5");  point = false;
                }else{calView.append("5");}
            }
        });
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(calView.getText().toString().equals("0")  ||calView.getText().toString().equals(save) ){
                    calView.setText("4");  point = false;
                }else{calView.append("4");}
            }
        });
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(calView.getText().toString().equals("0")  ||calView.getText().toString().equals(save) ){
                    calView.setText("3");
                    point = false;
                }else
                    calView.append("3");
            }
        });
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(calView.getText().toString().equals("0")  ||calView.getText().toString().equals(save) ){
                    calView.setText("2");  point = false;
                }else{calView.append("2");}
            }
        });
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(calView.getText().toString().equals("0")  ||calView.getText().toString().equals(save) ){
                    calView.setText("1");  point = false;
                }else{calView.append("1");}
            }
        });
        btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(calView.getText().toString().equals("0")  ||calView.getText().toString().equals(save) ){
                    calView.setText("0");  point = false;
                }else{calView.append("0");}
            }
        });

    }

    private void computeCalculation() {
        if(!Double.isNaN(one)) {
            two = Double.parseDouble(calView.getText().toString());

            if(CALCULATE == ADD)
                one = this.one + two;
            else if(CALCULATE == SUB)
                one = this.one - two;
            else if(CALCULATE == MUL)
                one = this.one * two;
            else if(CALCULATE == DIV)
                one = this.one / two;
            else if(CALCULATE == MOD)
                one = this.one % two;
            else
                one = two;
        }
        else {
            try {
                one= Double.parseDouble(calView.getText().toString());
            }
            catch (Exception e){}
        }
    }

}
