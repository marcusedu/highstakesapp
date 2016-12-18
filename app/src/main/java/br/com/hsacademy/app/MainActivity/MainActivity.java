package br.com.hsacademy.app.MainActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import br.com.hsacademy.app.Login.LoginActivity;
import br.com.hsacademy.app.R;
import br.com.hsacademy.app.model.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private WebView webview;
    private final String urlSite = "http://www.hsacademy.com.br";
    private final String urlFacebook = "https://www.facebook.com/goffigabriel";
    private final String urlTwitter = "https://twitter.com/gabrielgoffi";
    private final String urlSnap = "https://www.snapchat.com/add/ggoffi";
    private final String urlInstagram = "https://www.instagram.com/gabrielgoffi/";
    private final String urlYoutube = "https://www.youtube.com/user/gabrielgoffi";
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView();
        Log.d("MainActivity", "Tem o extra USER: " + getIntent().hasExtra("USER"));
        if (getIntent().hasExtra("USER")) {
            u = getIntent().getParcelableExtra("USER");
            Log.d("MainActivity", "Usuario: " + u.toString());
        }
        initNavMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.site) {
            startSite(urlSite);
        } else if (id == R.id.logout) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (id == R.id.facebook) {
            startSite(urlFacebook);
        } else if (id == R.id.twitter) {
            startSite(urlTwitter);
        } else if (id == R.id.instagram) {
            startSite(urlInstagram);
        } else if (id == R.id.snap) {
            startSite(urlSnap);
        } else if (id == R.id.youtube) {
            startSite(urlYoutube);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void initWebView() {
        webview = (WebView) findViewById(R.id.main_webview);
        webview.loadUrl(urlSite);
        webview.setWebChromeClient(new WebChromeClient());
        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
    }

    private void startSite(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webview.canGoBack()) {
                    webview.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initNavMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View v = getLayoutInflater().from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(v);
        ((TextView) v.findViewById(R.id.nav_header_txtNome)).setText(u.getFirst_name());
        ((TextView) v.findViewById(R.id.nav_header_txtEmail)).setText(u.getEmail());
    }
}
