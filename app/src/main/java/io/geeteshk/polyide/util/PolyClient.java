package io.geeteshk.polyide.util;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PolyClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
