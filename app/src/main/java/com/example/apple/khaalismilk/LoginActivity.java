package com.example.apple.khaalismilk;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.transition.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;
import  com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    public static final String LOG_TAG = LoginActivity.class.getSimpleName();

    public static final String LOGIN_URL ="http://crm.organicfoods.pk/ws/index.php";

    public static final String KEY_USER = "user";
    public static final String KEY_PASS = "pass";
    public static final String KEY = "key";
    public static final String DO = "do";



    AutoCompleteTextView mEmailView;
    TextView mPasswordView;

    String id ="";
    String username ="";
    String password = "";
    String  encodedpassword="";
     String key = "khaalisapi100";
     String doing = "login";

    String firstname = "";

    ProgressBar bar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPrefs = getSharedPreferences("Myprefs", MODE_PRIVATE);
        setContentView(R.layout.activity_login);
        final session see = new session(this);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

       bar = (ProgressBar)findViewById(R.id.progressBar);

        bar.setVisibility(View.GONE);




        final Button mEmailSignInButton = (Button) findViewById(R.id.firstlogin);

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                username = mEmailView.getText().toString();
                password = mPasswordView.getText().toString();
                try
                {
                    byte [] data = password.getBytes("UTF-8");
                    encodedpassword = Base64.encodeToString(data,0);
                }
                catch(UnsupportedEncodingException e)
                {
                    Log.d(LOG_TAG,e.getMessage());
                }
                if (!isNetworkAvailable()) {
                    Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginActivity.this, "Fill required fields", Toast.LENGTH_SHORT).show();
                }


                else
                {
                    bar.setVisibility(View.VISIBLE);
                    see.createLoginSession(username,password,id);
                    //userlogin();
                  Intent intent = new Intent(LoginActivity.this,MainScreen.class);
                    startActivity(intent);
                }

            }



        });


    }


    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private void userlogin()
    {
        StringRequest request = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(LOG_TAG , response.toString());


                if(response.startsWith("{"))
                {

                    try
                    {
                        JSONObject obj = new JSONObject(response);
                        JSONObject data = obj.getJSONObject("data");
                        id = data.getString("id");
                        firstname = data.getString("firstname");
                        SharedPreferences.Editor editor = getSharedPreferences("Myprefs", MODE_PRIVATE).edit();
                        editor.putString("firstname",firstname);
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this,MainScreen.class);
                        startActivity(intent);
                        bar.setVisibility(View.GONE);
                        finish();


                    }
                    catch (JSONException e)
                    {
                        Log.d(LOG_TAG,e.getMessage());
                    }

                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.GONE);
                }






            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG ).show();
                        bar.setVisibility(View.GONE);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USER,username);
                map.put(KEY_PASS,encodedpassword);
                map.put(KEY,key);
                map.put(DO,doing);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}

