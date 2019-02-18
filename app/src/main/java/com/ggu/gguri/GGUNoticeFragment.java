package com.ggu.gguri;


import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ggu.gguri.databinding.FragmentGgunoticeBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class GGUNoticeFragment extends Fragment {

    FragmentGgunoticeBinding binding;
    ProgressDialog asyncDialog;
    private ProgressBar progressBar;

    public GGUNoticeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ggunotice, container, false);
        View v = binding.getRoot();

        asyncDialog = new ProgressDialog(getActivity());
        WebSettings settings = binding.webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        binding.webview.loadUrl("https://www.ggu.ac.kr/sub01080101");
        binding.webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                asyncDialog.setMessage("잠시만 기다려주세요...");
                asyncDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                asyncDialog.dismiss();
                binding.webview.loadUrl("javascript:(function(){" +
                        "document.getElementById('spot').style.display = 'none';"+
                        "document.getElementById('header').style.display = 'none';"+
                        "document.getElementById('footer').style.display = 'none';"+
                        "})()");
            }
        });
        binding.webview.setOnKeyListener (new View.OnKeyListener () {
            @Override
            public boolean onKey (View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent. KEYCODE_BACK ) {
                    if (binding.webview.canGoBack()) {
                        binding.webview.goBack();
                    } else {
                        getActivity().onBackPressed();
                    }
                    return true;
                }
                return false;
            }
        });

        return v;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        binding.webview.stopLoading();
        ViewGroup webParent = (ViewGroup) binding.webview.getParent();
        if (webParent != null){
            webParent.removeView(binding.webview);
        }
        binding.webview.destroy();
    }

}
