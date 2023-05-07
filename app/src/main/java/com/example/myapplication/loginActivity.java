package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private Button btn_login;
    private EditText et_account;
    private EditText et_password;
    private String userName="fxjzzyo";
    private String pass="123";
    private CheckBox cb_remember;
    private CheckBox cb_auto_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
//        getSupportActionBar().setTitle("登录页面");
        btn_login = findViewById(R.id.btn_login);
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);
        cb_remember = findViewById(R.id.cb_remember);
        cb_auto_login = findViewById(R.id.cb_auto_login);
        cb_auto_login.setOnCheckedChangeListener(this);
        btn_login.setOnClickListener(this);
        cb_remember.setOnCheckedChangeListener(this);
        findViewById(R.id.set_account).setOnClickListener(this);
        initdata();
    }

    private void initdata() {
        SharedPreferences mm = getSharedPreferences("mm", MODE_PRIVATE);
        boolean isremember = mm.getBoolean("isremember", false);
        boolean islogin = mm.getBoolean("islogin", false);

        String account = mm.getString("account","");
        String password = mm.getString("password","");
        if (islogin){
            Intent intent=new Intent(this,MainActivity.class);
            intent.putExtra("account",account);
            startActivity(intent);
            loginActivity.this.finish();
        }
        userName=account;
        pass=password;
        if(isremember){
            et_account.setText(account);
            et_password.setText(password);
            cb_remember.setChecked(true);
        }


    }

    @Override
    public void onClick(View v ) {
        switch (v.getId()){
            case R.id.btn_login:
                String account = et_account.getText().toString();
                String password = et_password.getText().toString();
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(this,"没有注册账号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.equals(account,userName)&&TextUtils.equals(pass,password)){
                    Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                    if (cb_remember.isChecked()){
                        SharedPreferences mm = getSharedPreferences("mm", MODE_PRIVATE);
                        SharedPreferences.Editor edit = mm.edit();
                        edit.putString("account",account);
                        edit.putString("password",password);
                        edit.putBoolean("isremember",true);
                        if(cb_auto_login.isChecked()){
                            edit.putBoolean("islogin",true);
                        }else {
                            edit.putBoolean("islogin",false);
                        }
                        edit.apply();
                    }else {
                        SharedPreferences mm = getSharedPreferences("mm", MODE_PRIVATE);
                        SharedPreferences.Editor edit = mm.edit();
                        edit.putBoolean("isremember",false);
                        edit.apply();
                    }
                    Intent intent=new Intent(this,MainActivity.class);
                    intent.putExtra("account",account);
                    startActivity(intent);
                    loginActivity.this.finish();

                }else {
                    Toast.makeText(this,"用户名或密码错误",Toast.LENGTH_SHORT).show();

                }

                break;
            case R.id.set_account:
                Intent intent=new Intent(this,MainActivity2.class);
                startActivityForResult(intent,0x11);


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0x11&&resultCode==0&&data!=null){
            Bundle extras = data.getExtras();
            String account = extras.getString("account", "");
            String password = extras.getString("password", "");
            et_account.setText(account);
            et_password.setText(password);
            userName=account;
            pass=password;


        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.cb_auto_login:
                if(isChecked){
                    cb_remember.setChecked(true);
                }
                break;
            case R.id.cb_remember:
                if(isChecked){
                    cb_auto_login.setChecked(false);
                }
                break;


        }

    }


}