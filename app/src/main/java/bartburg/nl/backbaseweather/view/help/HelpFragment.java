package bartburg.nl.backbaseweather.view.help;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import bartburg.nl.backbaseweather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {


    private WebView webview;

    public HelpFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_help, container, false);
        webview = (WebView) parent.findViewById(R.id.help_webview);
        webview.loadUrl("file:///android_asset/help/index.html");
        return parent;
    }

}
