package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.myapplication.util.InputStreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String HTTP_ADDRESS="https://cwbk.net/";
    private TextView tv;
    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        findViewById(R.id.btn_get_web).setOnClickListener(this);
        tv = findViewById(R.id.tv);
        wv = findViewById(R.id.wv);
    }

    @Override
    public void onClick(View v) {
        //2.使用webview控件加载网页内容
        wv.loadUrl(HTTP_ADDRESS);//只写这句话，会开启系统浏览器显示页面
        //页面显示在webview中
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//一定修改为true,方法被执行了

            }
        });
        //httpurlconnection
        //网络操作不可以写在主线程上，耗时操作，阻塞主线程 开启
        new Thread(){

            @Override
            public void run() {

                try {
                    URL url=new URL(HTTP_ADDRESS);
                    HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");//请求方法
                    connection.setConnectTimeout(6000);//请求时长
                    InputStream inputStream = connection.getInputStream();//获取请求的数据
                    final String string = InputStreamUtils.parseIsToString(inputStream);//将流数据转换为字符串
                    //子线程不能更新页面
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(string);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}