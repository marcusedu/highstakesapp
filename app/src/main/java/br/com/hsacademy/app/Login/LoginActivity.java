package br.com.hsacademy.app.Login;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import br.com.hsacademy.app.R;
import br.com.hsacademy.app.Signup.SignupActivity;
import br.com.hsacademy.app.model.User;
import br.com.hsacademy.app.util.FormValidator;

public class LoginActivity extends YouTubeBaseActivity implements View.OnClickListener, YouTubePlayer.OnInitializedListener {
    private TextInputLayout email, password;
    private Button login, signup;
    private ProgressBar pBar;
    private YouTubePlayerView youTubePlayerView;
    private RequestQueue mRequestQueue;
    private StringRequest loginRequest;
    private YouTubePlayer youTubePlayer;
    private FormValidator validator = new FormValidator();
    private final String URL = "http://www.powerapps.com.br/hs_api/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
        initButtons();
        initRequest();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (youTubePlayer != null) youTubePlayer.setFullscreen(true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (youTubePlayer != null) youTubePlayer.setFullscreen(false);
        }
    }

    private void initButtons() {
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    private void initComponents() {
        email = (TextInputLayout) findViewById(R.id.login_et_email);
        password = (TextInputLayout) findViewById(R.id.login_et_password);
        login = (Button) findViewById(R.id.login_btn_login);
        signup = (Button) findViewById(R.id.login_btn_signup);
        pBar = (ProgressBar) findViewById(R.id.login_pbar);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(getString(R.string.YouTube_API_KEY), this);
    }

    private void initRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                login();
                break;
            case R.id.login_btn_signup:
                signup();
                break;
        }
    }

    private void login() {
        if (!validateForm()) return;
        loginRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            br.com.hsacademy.app.model.Response mResponse;
            Gson gson = new Gson();

            @Override
            public void onResponse(String response) {
                mResponse = gson.fromJson(response, br.com.hsacademy.app.model.Response.class);
                pBar.setVisibility(View.GONE);
                if (response.length() > 1 && mResponse.getCode() == 200) {
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Toast.makeText(getBaseContext(), "Login with " + gson.fromJson(mResponse.getResponse(), User.class).getEmail(), Toast.LENGTH_LONG).show();
                } else {
                    Snackbar.make(getCurrentFocus(), mResponse.getResponse(), 2500).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("HS_APP", "Deu ruim mano, te vira." + error.getMessage());
                Snackbar.make(getCurrentFocus(), "Oops, something is wrong, wait few seconds and try again", 2500).show();
                pBar.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> args = new HashMap<>();
                args.put("email", email.getEditText().getText().toString());
                args.put("password", password.getEditText().getText().toString());
                Log.d("HS_APP", args.toString());
                return args;
            }
        };
        pBar.setVisibility(View.VISIBLE);
        mRequestQueue.add(loginRequest);
    }

    private boolean validateForm() {
        int invalidosCount = 0;
        if (!validator.isValidEmail(email.getEditText().getText().toString())){
            invalidosCount++;
            email.setError("Email is invalid!");
        } else {
            email.setErrorEnabled(false);
        }
        if (!validator.isValidPassword(password.getEditText().getText().toString())){
            invalidosCount++;
            password.setError("Password is so little");
        } else {
            password.setErrorEnabled(false);
        }

        return invalidosCount == 0;
    }

    private void signup() {
        startActivity(new Intent(this, SignupActivity.class));
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.cueVideo("ycPmjdwxIOQ");
            this.youTubePlayer = youTubePlayer;
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
