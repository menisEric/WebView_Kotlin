package com.ericmenis.myapplication

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.*
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Private
    private val BASE_URL = "https://google.com"
    private val SEARCH_PATH = "/search?q="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let {
                    if (URLUtil.isValidUrl(it)){
                        webView.loadUrl(it)
                    }else{
                        webView.loadUrl("$BASE_URL$SEARCH_PATH$it")
                    }
                }

                return false
            }
        })

        //WebView
        webView.webChromeClient = object : WebChromeClient(){

        }

        webView.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                searchView.setQuery(url, false)
            }
        }

        val settings : WebSettings = webView.settings
        settings.javaScriptEnabled = true

        webView.loadUrl(BASE_URL)
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }
}
