package com.laonstory.laon_dev01.gimcarry_map;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URISyntaxException;

public class InicisWebViewClient extends WebViewClient {

    private Activity activity;
    private Context context;

    public InicisWebViewClient(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }



    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
            Intent intent = null;

            try {
                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME); //IntentURI처리
                Uri uri = Uri.parse(intent.getDataString());
                Log.e("uri", String.valueOf(uri));
                activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                return true;
            } catch (URISyntaxException ex) {
                return false;
            } catch (ActivityNotFoundException e) {
                if ( intent == null )	return false;

                String packageName = intent.getPackage();
                if (packageName != null) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    return true;
                }

                return false;
            }
        } else if (url.contains("result")){
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                Uri uri = Uri.parse(intent.getDataString());
                if (String.valueOf(uri).contains("true")){
                    ((DetailInfoActivity2) context).pay_com();
                    view.setVisibility(View.GONE);
                } else if (String.valueOf(uri).contains("false")){
                    view.setVisibility(View.GONE);
                    Toast.makeText(activity, "결제에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return false;
    }


}
