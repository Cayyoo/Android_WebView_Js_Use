package com.example.jsuse;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * WebView调用Java方法，步骤2：JS接口类方法
 */
public class ImoocJsInterface {
    private static final String TAG = "ImoocJsInterface";

    private JsBridge jsBridge;

    public ImoocJsInterface(JsBridge jsBridge) {
        this.jsBridge = jsBridge;
    }

    /**
     * 该方法不在主线程执行，且必须定义为public方法，protected或private都不行。
     * 没有JavascriptInterface注解，则WebView无法识别
     */
    @JavascriptInterface
    public void setValue(String value) {
        jsBridge.setTextViewValue(value);

        Log.d(TAG, "value =" + value);
    }

    @JavascriptInterface
    public void getArray(int[] arr) {
        for (int anArr : arr) {
            Log.d(TAG, String.valueOf(anArr));
        }
    }

}
