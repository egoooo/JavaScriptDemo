package com.example.kabarohei.javascriptdemo;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
    }

    private void setupViews() {
        mWebView = (WebView) findViewById(R.id.webview);
        WebSettings mWebSettings = mWebView.getSettings();
        //加上这句话才能使用javascript方法
        mWebSettings.setJavaScriptEnabled(true);
        /**
         * 如果希望点击链接由自己处理，
         * 而不是新开Android的系统browser中响应该链接。
         * 给WebView添加一个事件监听对象（WebViewClient)并重写其中的一些方法：
         * shouldOverrideUrlLoading：对网页中超链接按钮的响应。
         * 当按下某个连接时WebViewClient会调用这个方法，并传递参数
         */
        mWebView.setWebViewClient( new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        mWebView.addJavascriptInterface(new Object(){
            //一定要在这个方法前加@JavaScriptInterface,要不然不能执行方法
            @JavascriptInterface
            public void play(String num){
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+num));
                startActivity(intent);

            }
        },"demo");


        //加载页面
        mWebView.loadUrl("file:///android_asset/demo.html");

        mButton = (Button) findViewById(R.id.button);
        //给button添加事件响应,执行JavaScript的fillContent()方法
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mWebView.loadUrl("javascript:fillContent()");
            }
        });
    }
}
