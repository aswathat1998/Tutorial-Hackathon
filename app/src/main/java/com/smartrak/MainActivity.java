package com.smartrak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
     TextView mTvLiftstatus;
     Button mBsummon;
     EditText etEmpId;

    public static final String TAG = "MyTag";
    StringRequest stringRequest; // Assume this exists.
    RequestQueue mRequestQueue;  // Assume this exists.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvLiftstatus = (TextView) findViewById(R.id.tv_lift_satus);
        mBsummon = (Button) findViewById(R.id.b_Summon);
        etEmpId = (EditText) findViewById(R.id.et_emp_id);

        mBsummon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestURL();
            }
        });
    }

    void requestURL(){
        // Instantiate the RequestQueue.
        mRequestQueue = Volley.newRequestQueue(this);
        String empId=etEmpId.getText().toString();
        String nodeMCU  = "";
        String url =nodeMCU+empId;

        // Request a string response from the provided URL.
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTvLiftstatus.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTvLiftstatus.setText("That didn't work!");
            }
        });

        // Set the tag on the request.
        stringRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }
}
