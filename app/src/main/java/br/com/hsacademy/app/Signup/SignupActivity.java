package br.com.hsacademy.app.Signup;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import br.com.hsacademy.app.R;
import br.com.hsacademy.app.util.FormValidator;

public class SignupActivity extends AppCompatActivity {
    private TextInputLayout first_name, last_name, email, password;
    private Button signup;
    private RequestQueue mRequestQueue;
    private StringRequest postUser;
    private FormValidator validator = new FormValidator();
    private ProgressBar progressBarStatus;
    private final String URL = "http://www.powerapps.com.br/hs_api/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initComponents();
        initRequest();
    }

    private void exibeProgressBar(boolean exibir) {
        if (exibir)
            progressBarStatus.setVisibility(View.VISIBLE);
        else
            progressBarStatus.setVisibility(View.INVISIBLE);
    }

    private void initRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.start();
    }

    public void initComponents() {
        first_name = (TextInputLayout) findViewById(R.id.signup_et_first_name);
        last_name = (TextInputLayout) findViewById(R.id.signup_et_last_name);
        email = (TextInputLayout) findViewById(R.id.signup_et_email);
        password = (TextInputLayout) findViewById(R.id.signup_et_password);
        signup = (Button) findViewById(R.id.signup_btn_signup);
        progressBarStatus = (ProgressBar) findViewById(R.id.signup_pbar_status);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        password.getEditText().addTextChangedListener
                (new PasswordStrengh((ProgressBar) findViewById(R.id.signup_pbar),
                        (TextView) findViewById(R.id.signup_tv_strengh)));
    }

    public void signup() {
        if (!validateForm()) return;
        exibeProgressBar(true);
        signup.setEnabled(false);
        postUser = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            Gson gson = new Gson();

            @Override
            public void onResponse(String response) {
                signup.setEnabled(true);
                exibeProgressBar(false);
                br.com.hsacademy.app.model.Response resp = gson.fromJson(response, br.com.hsacademy.app.model.Response.class);
                if (resp.getCode() == 200) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 2500);
                    Snackbar.make(getCurrentFocus(), Html.fromHtml("Welcome to new Life Style with <strong>High Stakes Academy</strong>"), 2500).show();
                } else {
                    Snackbar.make(getCurrentFocus(), resp.getResponse(), 2500).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                signup.setEnabled(true);
                exibeProgressBar(false
                );
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> args = new HashMap<>();
                args.put("first_name", first_name.getEditText().getText().toString());
                args.put("last_name", last_name.getEditText().getText().toString());
                args.put("email", email.getEditText().getText().toString());
                args.put("password", password.getEditText().getText().toString());
                return args;
            }
        };
        mRequestQueue.add(postUser);
    }

    private boolean validateForm() {
        int validate = 0;
        if (!validator.isValidEmail(email.getEditText().getText().toString())) {
            validate++;
            email.setError("Email is invalid.");
        } else email.setErrorEnabled(false);
        if (!validator.isValidPassword(password.getEditText().getText().toString())) {
            validate++;
            password.setError("Password need more than 4 digits");
        } else password.setErrorEnabled(false);
        if (first_name.getEditText().getText().toString().isEmpty()) {
            validate++;
            first_name.setError("This field cannot be empty.");
        } else first_name.setErrorEnabled(false);
        if (last_name.getEditText().getText().toString().isEmpty()) {
            validate++;
            last_name.setError("This field cannot be empty.");
        } else last_name.setErrorEnabled(false);
        return validate == 0;
    }
}
