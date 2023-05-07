package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**TCP连接线程*/
    private ConnectThread mConnectThread;
    private DisconnectThread mDisconnectThread;
    private SendDataThread mSendDataThread;
    /**Socket套接字*/
    private Socket mSocket;
    /**服务器IP*/
    private EditText mEtIP;
    /**服务器端口*/
    private EditText mEtPort;
    private EditText MyData;
    /**连接按钮*/
    private Button mBtnConnect;
    /**红色LED灯开按钮*/
    private Button mBtnRedOn;
    /**红色LED灯关按钮*/
    private Button mBtnRedOff;

//    private Button mBtnRedLed1On;
//    private Button mBtnRedLed1Off;
//    private Button mBtnBuzzerOn;
//    private Button mBtnBuzzerOff;
//    private Button mBtntest;

    private Button stbk;
    private Button mBtnSend;
    private PrintStream out;
    private Button mRecData;    //  wt
    private String line;    //  wt
    private String  mstr;   //  wt
    private TextView mTv;   //  wt
    private int flage=1;    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnConnect = (Button) findViewById(R.id.btn_connect);
        mEtIP = (EditText) findViewById(R.id.et_ip);
        mEtPort = (EditText) findViewById(R.id.et_port);
        MyData  = (EditText) findViewById(R.id.Data);
        mTv=(TextView) findViewById(R.id.text2) ;   //  wt

        mBtnRedOn = (Button) findViewById(R.id.btn_red_on);
        mBtnRedOff = (Button) findViewById(R.id.btn_red_off);

        mBtnSend = (Button) findViewById(R.id.Send);
        mRecData = (Button) findViewById(R.id.recData);     //  wt
        stbk=(Button)findViewById(R.id.st_bk);
        mBtnConnect.setOnClickListener(this);
        mBtnRedOn.setOnClickListener(this);
        mBtnRedOff.setOnClickListener(this);
        mBtnSend.setOnClickListener(this);
        mRecData.setOnClickListener(this);
        stbk.setOnClickListener(this);
//        mBtnRedLed1On.setOnClickListener(this);
//        mBtnRedLed1Off.setOnClickListener(this);
//        mBtnBuzzerOn.setOnClickListener(this);
//        mBtnBuzzerOff.setOnClickListener(this);
//        mBtntest.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_connect:
                //连接
                //if (mSocket == null && !mSocket.isConnected()) {
                //mConnectThread = null;
                //String ip = mEtIP.getText().toString();
                //int port = Integer.valueOf(mEtPort.getText().toString());
                //mConnectThread = new ConnectThread(ip, port);
                //mConnectThread.start();
                //}else
                if (mSocket != null && mSocket.isConnected()) {
                    String ip = mEtIP.getText().toString();
                    int port = Integer.valueOf(mEtPort.getText().toString());
                    mDisconnectThread = new DisconnectThread(ip, port);
                    mDisconnectThread.start();
                    mBtnConnect.setText("连接");
                }else{
                    String ip = mEtIP.getText().toString();
                    int port = Integer.valueOf(mEtPort.getText().toString());
                    mConnectThread = new ConnectThread(ip, port);
                    mConnectThread.start();
                }
                break;
            case R.id.btn_red_on:
                if (out != null) {
                    String ip = mEtIP.getText().toString();
                    int port = Integer.valueOf(mEtPort.getText().toString());
                    mSendDataThread = new SendDataThread(ip,port,"E");
                    mSendDataThread.start();
                }
                break;
            case R.id.btn_red_off:
                if (out != null) {
                    String ip = mEtIP.getText().toString();
                    int port = Integer.valueOf(mEtPort.getText().toString());
                    mSendDataThread = new SendDataThread(ip,port,"F");
                    mSendDataThread.start();
                }
                break;


            case R.id.recData:  //     wt
                if(mSocket != null && mSocket.isConnected()){
                    if(flage == 1){
                        Toast.makeText(MainActivity.this, "Get Temperature", Toast.LENGTH_SHORT).show();
                        mRecData.setText("Get Temperature");
                        GetTCPstring();
                        flage = (-1)*flage;
                    }else{
                        Toast.makeText(MainActivity.this, "Stop RcvData", Toast.LENGTH_SHORT).show();
                        mRecData.setText("Get Temperature!");
                        try{
                            mSocket.getInputStream().close();
                        }catch(IOException e){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Stop RcvData Failed", Toast.LENGTH_SHORT).show();
                        }
                        flage = (-1)*flage;
                    }
                }
                //GetTCPstring();
                //Toast.makeText(MainActivity.this, "You Checked RcvData", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Send:
                String ip = mEtIP.getText().toString();
                int port = Integer.valueOf(mEtPort.getText().toString());
                String data = MyData.getText().toString();
                mSendDataThread = new SendDataThread(ip,port,data);
                mSendDataThread.start();
                break;
            case R.id.st_bk:
                Intent intent12=new Intent(this,cwbkActivity.class);
                startActivity(intent12);
                break;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.add_item:
//                Toast.makeText(MainActivity.this, "You Checked Add", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.remove_item:
//                Toast.makeText(MainActivity.this, "You Checked Remove", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.check_item:
//                Toast.makeText(MainActivity.this, "You Checked Check", Toast.LENGTH_SHORT).show();
//                break;
//        }
//        return true;
//    }

    //  wt
    private void GetTCPstring(){
        new Thread(){
            public void run(){
                try {
                    char[] cbuf=new char[10];
                    //  获得输入流
                    BufferedReader br=new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    while (true){
                        if(br.ready()){
                            br.read(cbuf,0,10);
                            line=String.valueOf(cbuf);
                            handler.sendMessage(handler.obtainMessage());
                        }
                    }
                }catch(IOException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "RcvData Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }.start();
    }
    //  消息句柄          wt
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            mstr+=line;
            if(mstr.length()>100) {
                mstr="";
            }
            mTv.setText(mstr);

        }
    };
    private class DisconnectThread extends Thread {
        String ip;
        int port;

        public DisconnectThread(String ip,int port){
            this.ip = ip;
            this.port = port;
        }
        @Override
        public void run() {
            try {
                out.close();
                mSocket.close();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "已连接", Toast.LENGTH_SHORT).show();
                    }
                });
                //new HeartBeatThread().start();
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Closed Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    private class SendDataThread extends Thread {
        String ip;
        int port;
        String Senddata;

        public SendDataThread(String ip,int port,String data){
            this.ip = ip;
            this.port = port;
            this.Senddata = data;
        }
        @Override
        public void run() {
            try {
                //out = new PrintStream(mSocket.getOutputStream());
                out.write((Senddata + '\n').getBytes("utf-8"));
                out.flush();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Send :"+Senddata, Toast.LENGTH_SHORT).show();
                    }
                });
                //new HeartBeatThread().start();
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Send Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    //链接线程
    private class ConnectThread extends Thread {
        private String ip;
        private int port;

        public ConnectThread(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                mSocket = new Socket(ip, port);
                out = new PrintStream(mSocket.getOutputStream());

                out.write(("hello world!" + '\n').getBytes("utf-8"));
                out.flush();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBtnConnect.setText("断开");
                    }
                });
                //new HeartBeatThread().start();
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Connect Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private class HeartBeatThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(5000);
                    if (!mSocket.isConnected()) {
                        mBtnConnect.setText("连接");
                        Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
                    }else{
                        mBtnConnect.setText("断开");
                        Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Close Connect Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}