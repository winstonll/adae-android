package com.adae.justinelee.adaecompanion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Scan extends Activity {
  private Button scan;
  String totalprice;
  String partnername;
  String status;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.scan);
    Intent intent = getIntent();
    totalprice=(String) intent.getSerializableExtra("totalpricekey");
    partnername=(String) intent.getSerializableExtra("partnerkey");
    status=(String) intent.getSerializableExtra(("statuskey"));

    new IntentIntegrator(this).initiateScan();
    }

  public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
      if (resultCode == RESULT_OK) {
        System.out.println("ok");
        final String message = intent.getStringExtra("SCAN_RESULT");
        System.out.println(message);


        String uri = "https://adae.co/api/v1/verify_scan?transactions[scan]="+message+"&transactions[balance]="+totalprice;
        //"?transactions[scan]=a-a89-qv0kiv&transactions[balance]=3"
       System.out.println(totalprice+"hi");
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://adae.co/api/v1/verify_scan";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,uri, null,
        new Response.Listener<JSONObject>() {
          @Override
          public void onResponse(JSONObject response) {
            Intent intent = new Intent(Scan.this, Success.class);
            startActivity(intent);


          }
        }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
            System.out.println(error);
            System.out.println(error.networkResponse);

            if (error.networkResponse==null) {
              System.out.println("true");
              Intent intent = new Intent(Scan.this,Success.class);
              intent.putExtra("partnerkey",partnername);
              intent.putExtra("statuskey",status);
              startActivity(intent);
            } else {
              Intent intent = new Intent(Scan.this, Error.class);
              intent.putExtra("pricekey", totalprice);
              intent.putExtra("messagekey", message);
              intent.putExtra("statuskey",status);
              intent.putExtra("partnerkey",partnername);
              startActivity(intent);
            }

          }
        }) {
          @Override
          public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String, String> params = new HashMap<String, String>();
            params.put("ApiToken", "EHHyVTV44xhMfQXySDiv");
            SharedPreferences settings = PreferenceManager
            .getDefaultSharedPreferences(Scan.this);
            String auth = settings.getString("authkey", "");
            params.put("Authorization",auth);

            return params;
          }
        };
        queue.add(stringRequest);
      } else if (resultCode==RESULT_CANCELED) {
          Intent intent45 = new Intent(Scan.this,TransactionsList.class);
        startActivity(intent45);
      }
    }
    public void toTransaction(View view) {
      Intent intent = new Intent(Scan.this, TransactionsList.class);
      startActivity(intent);
    }
  }


