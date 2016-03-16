package com.adae.justinelee.adaecompanion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    TextView t2 = (TextView) findViewById(R.id.privacy);
    TextView t3=(TextView)findViewById(R.id.terms);
    t2.setMovementMethod(LinkMovementMethod.getInstance());
    t3.setMovementMethod(LinkMovementMethod.getInstance());
    EditText emailedit= (EditText) findViewById(R.id.login);
    String email= emailedit.getText().toString();
    EditText passwordedit=(EditText) findViewById(R.id.password);
    String password=passwordedit.getText().toString();

  }

  public void toQr(View view) {
    final Intent intent = new Intent(this,TransactionsList.class);
    RequestQueue queue = Volley.newRequestQueue(this);
    final String url= "https://adae.co/api/v1/sessions";

    EditText emailedit= (EditText) findViewById(R.id.login);
    String email= emailedit.getText().toString();
    EditText passwordedit=(EditText) findViewById(R.id.password);
    String password=passwordedit.getText().toString();

    Map<String, Map<String,String>> params= new HashMap<String,Map<String, String>>();
    Map<String,String> creds= new HashMap<String,String>();
    creds.put("email",email);
    creds.put("password",password);
    params.put("sessions",creds);


    JsonObjectRequest stringRequest = new JsonObjectRequest(url,new JSONObject(params),
    new Response.Listener<JSONObject>() {

      @Override
      public void onResponse(JSONObject response) {
        System.out.println(response);
        Gson gson=new Gson();
        Map<String, Object> map= new HashMap<String, Object>();
        String string=response.toString();
        Map<String, Object> info=gson.fromJson(string, map.getClass());
        String auth=(String)info.get("auth_token");
        String id=info.get("id").toString();

        SharedPreferences settings = PreferenceManager
        .getDefaultSharedPreferences(Login.this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("authkey",auth);
        editor.putString("idkey",id);
        editor.commit();


        startActivity(intent);
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {

        Context context = getApplicationContext();
        CharSequence text = "Incorrect Password or Username";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
      }

    }) {

      //@Override
      //public Map<String, String> getParams() throws AuthFailureError {
        /**Map<String,String> params = new HashMap<String,String>();
        Map<String,String> creds=new HashMap<String,String>();
        EditText emailedit= (EditText) findViewById(R.id.login);
        String email= emailedit.getText().toString();
        EditText passwordedit=(EditText) findViewById(R.id.password);
        String password=passwordedit.getText().toString();
        creds.put("email",email);
        creds.put("password",password);
        params.put("sessions",creds.toString());
        return params;**/
      //}
      @Override
      public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String>  params = new HashMap<String, String>();
        params.put("ApiToken", "EHHyVTV44xhMfQXySDiv");

        return params;
      }
    };
// Add the request to the RequestQueue.
    queue.add(stringRequest);

  }


}
