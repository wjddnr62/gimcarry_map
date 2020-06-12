package com.laonstory.laon_dev01.gimcarry_map;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by laon-dev01 on 2018-04-24.
 */

public class daummapapiActivity_air extends AppCompatActivity {

    private WebView webView;
    private Handler handler;
    private Button backbtn;
    private TextView head_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daummapsearchapi);

        backbtn = findViewById(R.id.backbtn);
        head_title = findViewById(R.id.head_title);

        head_title.setText(R.string.도착지주소입력);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), ManuActivity.class);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                } else {
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                }
                startActivity(intent1);
                finish();
            }
        });

        // WebView 초기화
        init_webView();
        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();
    }

    public void init_webView() {
        // WebView 설정
        webView = (WebView) findViewById(R.id.webView);
        // JavaScript 허용
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // JavaScript의 window.open 허용
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        // 두 번째 파라미터는 사용될 php에도 동일하게 사용해야함
        webView.addJavascriptInterface(new daummapapiActivity_air.AndroidBridge(), "TestApp");
        // web client 를 chrome 으로 설정
        webView.setWebViewClient(new WebViewClient());
        // web client 를 chrome 으로 설정
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView newWeb = new WebView(daummapapiActivity_air.this);
                newWeb.getSettings().setJavaScriptEnabled(true);
                final Dialog dialog = new Dialog(daummapapiActivity_air.this);
                dialog.setContentView(newWeb);

                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

                dialog.show();
                newWeb.setWebChromeClient(new WebChromeClient(){
                    @Override
                    public void onCloseWindow(WebView window) {
                        dialog.dismiss();
                    }
                });

                ((WebView.WebViewTransport) resultMsg.obj).setWebView(newWeb);
                resultMsg.sendToTarget();

                return true;
            }
        });
        // webview url load
        webView.loadUrl("http://gimcarry.com/daumapi.php");
    }

    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String resultadd = "("+arg1.toString()+")"+" "+arg2.toString()+" "+arg3.toString();
                    Intent intent = new Intent(getApplicationContext(), DetailInfoActivity_air.class);
                    intent.putExtra("place", resultadd);
                    startActivity(intent);
                    finish();
                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                }
            });
        }
    }

    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),ManuActivity.class);
        startActivity(intent);
        finish();
    }


}
