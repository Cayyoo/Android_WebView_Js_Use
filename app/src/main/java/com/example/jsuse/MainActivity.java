package com.example.jsuse;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Android与WebView的js交互
 */
public class MainActivity extends AppCompatActivity implements JsBridge {
    private WebView webView;
    private TextView textView;

    private Button mBtn;
    private EditText mEdittext;

    /**
     * js接口类中的setValue()不在主线程执行，因此需要借助Handler
     */
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets(savedInstanceState);
    }

    private void initWidgets(Bundle savedInstanceState) {
        webView = findViewById(R.id.webview);
        textView = findViewById(R.id.textview);
        mBtn=findViewById(R.id.button);
        mEdittext=findViewById(R.id.edittext);
        mHandler = new Handler();

        //WebView调用Java方法，步骤1：允许webview加载js代码
        webView.getSettings().setJavaScriptEnabled(true);

        /*
        WebView调用Java方法，步骤3：给webview设置js接口
        imoocLauncher是js接口类对象在js中作为对象使用时的名称，可任取，保持前后一致就行
         */
        webView.addJavascriptInterface(new ImoocJsInterface(this), "imoocLauncher");

        //加载assets目录文件
        webView.loadUrl("file:///android_asset/imooc.html");

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string=mEdittext.getText().toString().trim();
                //Android调用js方法
                webView.loadUrl("javascript:if(window.remote){window.remote('"+string+"')}");
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //打开允许Chrome调试开关
            webView.setWebContentsDebuggingEnabled(true);
        }
    }

    @Override
    public void setTextViewValue(final String value) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText(value);
            }
        });
    }

}
