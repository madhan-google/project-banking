package com.codekiller.bankingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.codekiller.bankingsystem.DB.MyDataBase;
import com.codekiller.bankingsystem.Utils.UtilClass;
import com.google.android.material.textfield.TextInputLayout;

public class MoneyActivity extends AppCompatActivity {

    Button okBtn;
    TextInputLayout accnoLayout, amountLayout, recLayout;

    UtilClass utilClass;
    MyDataBase myDataBase;
    String code = "", accno = "", amount = "", rec_accno = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        okBtn = findViewById(R.id.btn);
        accnoLayout = findViewById(R.id.accno_text);
        amountLayout = findViewById(R.id.amount_text);
        recLayout = findViewById(R.id.rec_accno_text);
        utilClass = new UtilClass(this);
        myDataBase = new MyDataBase(this);
        code = getIntent().getStringExtra("activity_code");
        if( code.equals("withdraw") || code.equals("deposit") ){
            amountLayout.setVisibility(View.VISIBLE);
        }else if( code.equals("transact") ){
            amountLayout.setVisibility(View.VISIBLE);
            recLayout.setVisibility(View.VISIBLE);
        }
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accno = accnoLayout.getEditText().getText().toString();
                amount = amountLayout.getEditText().getText().toString();
                if( code.equals("deposit") ){
                    if( myDataBase.putAmount(accno, (Integer.parseInt(amount)+Integer.parseInt(myDataBase.getAmount(accno)))+"") ){
                        utilClass.toast("deposited");
                        finish();
                    }else{
                        utilClass.toast("something went wrong");
                    }
                }
                if( code.equals("withdraw") ){
                    long cur_amount = Integer.parseInt(myDataBase.getAmount(accno));
                    if( cur_amount >= Integer.parseInt(amount) ){
                        myDataBase.putAmount(accno,(Integer.parseInt(amount)-cur_amount)+"");
                        utilClass.toast("amount has been withdraw");
                        finish();
                    }else{
                        utilClass.toast("in-sufficient balance");
                    }
                }
                if( code.equals("balance") ){
                    utilClass.toast("Balance Amount : "+myDataBase.getAmount(accno));
                    finish();
                }
                if( code.equals("transact") ){
                    long cur_amount = Integer.parseInt(myDataBase.getAmount(accno));
                    rec_accno = recLayout.getEditText().getText().toString();
                    if( cur_amount >= Integer.parseInt(amount) ){
                        myDataBase.putAmount(rec_accno,(Integer.parseInt(myDataBase.getAmount(rec_accno))+Integer.parseInt(amount))+"");
                        myDataBase.putAmount(accno, (Integer.parseInt(myDataBase.getAmount(accno))-Integer.parseInt(amount))+"");
                        finish();
                    }else{
                        utilClass.toast("in-sufficient balance");
                    }
                }
            }
        });
    }
    public int len(String s){
        return s.length();
    }
}