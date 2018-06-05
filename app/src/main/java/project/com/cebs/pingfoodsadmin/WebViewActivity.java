package project.com.cebs.pingfoodsadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    private WebView browser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        WebView browser = (WebView) findViewById(R.id.web);
        browser.loadUrl("http://10.0.2.2:8010/RestroKart111");
    }
}
