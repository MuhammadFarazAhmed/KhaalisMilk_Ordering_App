package com.example.apple.khaalismilk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class  MainScreen extends AppCompatActivity {


    public static final String LOG_TAG = LoginActivity.class.getSimpleName();

    public static final String LOGIN_URL ="http://crm.organicfoods.pk/ws/index.php";
    //query parameter
    public static final String KEY_USER = "user";
    public static final String KEY_PASS = "pass";
    public static final String KEY = "key";
    public static final  String ID = "id";
    public static final String QUANTITY = "quantity";
    public static final String DO = "do";
     //query values
    static String id = "";
   static  String username ="";
    static String  password = "";
    String  encodedpassword="";
    String key = "khaalisapi100";
    String quantity = "";
    String doing = "order";

    //getting firstname
    String firstname = "";

    //initializing views
    EditText litres ;
    Button upbtn ;
    Button downbtn;
    Button Ordernow;
    TextView subcribe;
    TextView view;
    FloatingActionButton floatbtn;
    ProgressBar bar;
    int value = 0;
 Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPrefs = getSharedPreferences("Myprefs", MODE_PRIVATE);

        setContentView(R.layout.activity_main_screen);

        bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setVisibility(View.GONE);

        final session session = new session(this);
        session.checkLogin();

        //check weather the user has suscribe or not
        aSwitch = (Switch)findViewById(R.id.switch1);
        aSwitch.setChecked(sharedPrefs.getBoolean("switch", false));
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aSwitch.isChecked())
                {
                    SharedPreferences.Editor editor = getSharedPreferences("Myprefs", MODE_PRIVATE).edit();
                    editor.putBoolean("switch", true);
                    editor.commit();
                    Intent intent = new Intent(MainScreen.this,SubscriptionScreen.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    SharedPreferences.Editor editor = getSharedPreferences("Myprefs", MODE_PRIVATE).edit();
                    editor.putBoolean("switch", false);
                    editor.commit();
                }
            }
        });
        if(aSwitch.isChecked())
        {
            Intent intent = new Intent(MainScreen.this,SubscriptionScreen.class);
            startActivity(intent);
        }

        //getting values from share prefrences...
        HashMap<String,String> user = session.getUserDetails();
        //id
        id = user.get(session.KEY_ID);
        //username
        username = user.get(session.KEY_NAME);
        //password
        password = user.get(session.KEY_PASSWORD);
        //firstname
        firstname = user.get(session.KEY_FIRST_NAME);

        view = (TextView)findViewById(R.id.textView);
        view.setText("Hi..."+firstname+"!");



        floatbtn = (FloatingActionButton) findViewById(R.id.floatingActionButton);
            floatbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainScreen.this, SettingsActivity.class);
                    startActivity(intent);
                }
            });

            subcribe = (TextView) findViewById(R.id.subcription_textview);
            subcribe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainScreen.this, SubscriptionScreen.class);
                    startActivity(intent);
                }
            });

            litres = (EditText) findViewById(R.id.litres);
            upbtn = (Button) findViewById(R.id.btnup);
            downbtn = (Button) findViewById(R.id.btndowm);


            upbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (value == 50) {
                        Toast.makeText(MainScreen.this, "You,ve reached the maximum limit", Toast.LENGTH_SHORT).show();
                    } else {
                        value = Integer.parseInt(litres.getText().toString());
                        value++;
                        litres.setText(String.valueOf(value));

                    }
                }
            });
            downbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (value == 0) {
                        return;
                    } else {
                        value = Integer.parseInt(litres.getText().toString());
                        value--;
                        litres.setText(String.valueOf(value));
                    }
                }
            });


            Ordernow = (Button) findViewById(R.id.orderNow);
            Ordernow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        byte[] data = password.getBytes("UTF-8");
                        encodedpassword = Base64.encodeToString(data, 0);
                    } catch (UnsupportedEncodingException e) {
                        Log.d(LOG_TAG, e.getMessage());
                    }

                    if (!isNetworkAvailable()) {
                        Toast.makeText(MainScreen.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    } else {
                        bar.setVisibility(View.VISIBLE);
                        quantity = litres.getText().toString();
                        order();
                    }
                }
            });
        }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    private void order()
    {
        StringRequest request = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(LOG_TAG , response.toString());
                if(response.startsWith("{"))
                {
                    bar.setVisibility(View.GONE);
                    Toast.makeText(MainScreen.this,"Your "+quantity+" Litres Milk Order is placed successfully.\n We are on our way !",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    bar.setVisibility(View.GONE);
                    Toast.makeText(MainScreen.this,"something went wrong try again.",Toast.LENGTH_SHORT).show();

                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        bar.setVisibility(View.GONE);
                        Toast.makeText(MainScreen.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USER,username);
                map.put(KEY_PASS,encodedpassword);
                map.put(KEY,key);
                map.put(DO,doing);
                map.put(ID,id);
                map.put(QUANTITY,quantity);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
