package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private EditText et_account2;
    private EditText et_password2;
    private EditText et_password_again;
    private CheckBox rb_agree;
    private Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        getSupportActionBar().setTitle("注册");
        et_account2 = findViewById(R.id.et_account2);
        et_password2 = findViewById(R.id.et_password2);
        et_password_again = findViewById(R.id.et_password_again);
        rb_agree = findViewById(R.id.RB_agree);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                String name = et_account2.getText().toString();
                String password = et_password2.getText().toString();
                String password_again = et_password_again.getText().toString();
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity2.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }  if (TextUtils.isEmpty(password)){
                Toast.makeText(MainActivity2.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                return;
            } if (!TextUtils.equals(password,password_again)){
                Toast.makeText(MainActivity2.this,"密码不一致",Toast.LENGTH_SHORT).show();
                return;
            }
                if (!rb_agree.isChecked()){
                    Toast.makeText(MainActivity2.this,"请勾选用户协议",Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences mm = getSharedPreferences("mm", MODE_PRIVATE);
                SharedPreferences.Editor edit = mm.edit();
                edit.putString("account",name);
                edit.putString("password",password);
                edit.apply();
                Intent intent=new Intent();

                Bundle bundle=new Bundle();
                bundle.putString("account",name);
                bundle.putString("password",password);
                intent.putExtras(bundle);
                setResult(0,intent);

                this.finish();
                break;




        }
    }



}