package com.example.apple.khaalismilk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SubscriptionScreen extends AppCompatActivity {

    public static final String LOG_TAG = SubscriptionScreen.class.getSimpleName();

    FloatingActionButton floatbtn;
    ProgressBar bar ;

    public static final String LOGIN_URL ="http://crm.organicfoods.pk/ws/index.php";

    public static final String KEY_USER = "user";
    public static final String KEY_PASS = "pass";
    public static final String KEY = "key";
    public static final String DO = "do";
    public static final String ID = "id";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String ADDRESS = "address";
    public static final String TOWN = "town";
    public static final String MOBILE = "mobile";
    public static final String SUBSCRIBE = "subscribed";
    public static final String MONDAY = "monday";
    public static final String TUESDAY = "tuesday";
    public static final String WEDNESDAY = "wednesday";
    public static final String THURSDAY = "thursday";
    public static final String FRIDAY = "friday";
    public static final String SATURDAY = "saturday";
    public static final String SUNDAY = "sunday";
    public static final String BILLING_PERIOD = "billing_period";
    public static final String DILIVERY_TIME = "delivery_time";

    String user ="aqeel60@gmail.com";
    String  password = "khaalis";
    String  encodedpassword="";
    String key = "khaalisapi100";
    String doing = "update";
    String id = "";
    String firstname = "faraz";
    String lastname = "ahmed";
    String address = "DO NOT change";
    String town = "lahore";
    String mobile = "033388880888";
    String subscribed = "1";
    String mon = "";
    String tue = "";
    String wed = "";
    String thur = "";
    String fri = "";
    String sat = "";
    String sun = "";
    String B_P = "M";

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
    String D_T = sdf.format(c.getTime());

    EditText mont ;
    EditText tuet ;
    EditText wedt ;
    EditText thurt ;
    EditText frit ;
    EditText satt ;
    EditText sunt ;

Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcribtion);
        SharedPreferences sharedPrefs = getSharedPreferences("Myprefs", MODE_PRIVATE);

        session session = new session(this);

        session.checkLogin();
         aSwitch = (Switch)findViewById(R.id.switch3);
aSwitch.setChecked(true);


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                SharedPreferences.Editor editor = getSharedPreferences("Myprefs", MODE_PRIVATE).edit();
                editor.putBoolean("switch", false);
                editor.commit();
                Intent intent = new Intent(SubscriptionScreen.this,MainScreen.class);
                startActivity(intent);
            }
        });

        id = MainScreen.id;

        floatbtn = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        bar =(ProgressBar)findViewById(R.id.progressBar);
        bar.setVisibility(View.GONE);


        mont = (EditText)findViewById(R.id.mon);
        tuet = (EditText)findViewById(R.id.tue);
        wedt =(EditText)findViewById(R.id.wed);
        thurt = (EditText)findViewById(R.id.thur);
        frit = (EditText)findViewById(R.id.fri);
        satt =(EditText)findViewById(R.id.sat);




        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubscriptionScreen.this,SettingsActivity.class);
                startActivity(intent);
            }
        });


        Button ordernow = (Button)findViewById(R.id.OrderNow);

        ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    byte [] data = password.getBytes("UTF-8");
                    encodedpassword = Base64.encodeToString(data,0);
                }
                catch(UnsupportedEncodingException e)
                {
                    Log.d(LOG_TAG,e.getMessage());
                }

                    if(!isNetworkAvailable())
                    {
                        Toast.makeText(SubscriptionScreen.this,"No Internet Connection",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        bar.setVisibility(View.VISIBLE);
                      mon =  mont.getText().toString();
                       tue = tuet.getText().toString();
                       wed =  wedt.getText().toString();
                        thur = thurt.getText().toString();
                        fri = frit.getText().toString();
                        sat =satt.getText().toString();


                        if(TextUtils.isEmpty(mon) ||TextUtils.isEmpty(tue)||TextUtils.isEmpty(wed)||TextUtils.isEmpty(thur)||TextUtils.isEmpty(fri)||TextUtils.isEmpty(sat) )
                        {
                              mon = "0";
                            tue = "0";
                            wed = "0";
                            thur = "0";
                            fri = "0";
                            sat = "0";


                        }

                        updatedata();
                    }
            }
        });


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private void updatedata()
    {
        StringRequest request = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.d(LOG_TAG , response.toString());
                Log.d(LOG_TAG , id);

                if(response.startsWith("{"))
                {
                    Toast.makeText(SubscriptionScreen.this,"Your daily subsciption is update.",Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.GONE);
                }
               else
                {
                    Toast.makeText(SubscriptionScreen.this,"something went wrong Try again.",Toast.LENGTH_SHORT).show();
                    bar.setVisibility(View.GONE);
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e(LOG_TAG,B_P);
                        Log.e(LOG_TAG,D_T);
                        Toast.makeText(SubscriptionScreen.this,error.toString(),Toast.LENGTH_LONG ).show();
                        bar.setVisibility(View.GONE);
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_USER,user);
                map.put(KEY_PASS,encodedpassword);
                map.put(KEY,key);
                map.put(DO,doing);
                map.put(ID,id);
                map.put(MONDAY,mon);
                map.put(TUESDAY,tue);
                map.put(WEDNESDAY,wed);
                map.put(THURSDAY,thur);
                map.put(FRIDAY,fri);
                map.put(SATURDAY,sat);
                map.put(SUNDAY,sun);
                map.put(BILLING_PERIOD,B_P);
                map.put(DILIVERY_TIME,D_T);


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
