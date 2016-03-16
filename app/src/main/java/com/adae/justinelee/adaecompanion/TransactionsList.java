package com.adae.justinelee.adaecompanion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionsList extends Activity {

  ListView list;
  private final String USER_AGENT = "Mozilla/5.0";
  private static final String GET_URL = "https://adae.co/api/v1/transaction_detail/";
  ArrayList<String> partnername;
  ArrayList<String> description;
  ArrayList<String> profile;
  ArrayList<String> product;
  ArrayList<Object> totalprice;
  ArrayList<Object> sellerid;
  ArrayList<Object> buyerid;
  ArrayList<Object> transactionid;
  ArrayList<Object> itemid;
  ArrayList<Object> inscandate;
  ArrayList<String> status;
  ArrayList<String> listype;
  ArrayList<Object> deposit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transactions_list);

    RequestQueue queue = Volley.newRequestQueue(this);
    //String url ="http://adae.co/api/v1/users";

    SharedPreferences settings = PreferenceManager
    .getDefaultSharedPreferences(TransactionsList.this);
    String idstring = settings.getString("idkey", "");
    String url ="https://adae.co/api/v1/transaction_detail"+"/"+idstring.substring(0,idstring.length()-2);

// Request a string response from the provided URL.
    System.out.println("ReachedJSON");

    JsonObjectRequest stringRequest = new JsonObjectRequest(url,null,
    new Response.Listener<JSONObject>() {
      @Override
      public void onResponse(JSONObject response) {
        HashMap<String, ArrayList<Map<String,String>>>  trans_detail = new HashMap<String, ArrayList<Map<String,String>>>();
        System.out.println(response.toString());
        Gson gson = new Gson();
        Map<String, Object> map= new HashMap<String, Object>();
        String string=response.toString();
        System.out.println(string);
        Map<String, ArrayList<Map<String,String>>> mapone=gson.fromJson(string, map.getClass());
        trans_detail.put("transaction",mapone.get("transaction"));
        trans_detail.put("user",mapone.get("user"));
        trans_detail.put("item",mapone.get("item"));
        System.out.println(trans_detail.get("user").toString());
        System.out.println(trans_detail.get("item").toString());
        /**System.out.println(items.get("transaction").getClass());
        **/


        partnername=new ArrayList<String>();
        for (Map<String,String> user: trans_detail.get("user")) {
          System.out.println(user.get("name"));
          partnername.add(user.get("name"));
        }
        description=new ArrayList<String>();
        for (Map<String,String> item:trans_detail.get("item")) {
          description.add(item.get("description"));
        }
        //profile=new ArrayList<String>();
        //for (Map<String,String> )
        product=new ArrayList<String>();
        for (Map<String,String> item:trans_detail.get("item")) {
          product.add("https://adae.co" + item.get("photo_url"));
        }
        totalprice=new ArrayList<Object>();
        for (Map<String,String> item:trans_detail.get("transaction")) {
          totalprice.add(item.get("total_price"));
        }
        sellerid=new ArrayList<Object>();
        for (Map<String,String> item:trans_detail.get("transaction")) {
          sellerid.add(item.get("seller_id"));
        }
        buyerid=new ArrayList<>();
        for (Map<String,String> item:trans_detail.get("transaction")) {
          buyerid.add(item.get("buyer_id"));
        }
        transactionid=new ArrayList<>();
        for (Map<String,String> item:trans_detail.get("transaction")) {
          transactionid.add(item.get("id"));
        }
        itemid= new ArrayList<>();
        for (Map<String,String> item:trans_detail.get("transaction")) {
          itemid.add(item.get("item_id"));
        }
        inscandate=new ArrayList<>();
        for (Map<String,String> item:trans_detail.get("transaction")) {
          if (item.get("in_scan_date")==null) {
            inscandate.add("null");
          }
          else {
            inscandate.add(item.get("in_scan_date"));
          };
        }
        status = new ArrayList<>();
        for (Map<String,String> item:trans_detail.get("transaction")) {
          status.add(item.get("status"));
        }
        listype=new ArrayList<>();
        for (Map<String,String> item:trans_detail.get("item")) {
          listype.add(item.get("listing_type"));
        }
        deposit=new ArrayList<>();
        for (Map<String,String> item:trans_detail.get("item")) {
          if (item.get("deposit")==null) {
            deposit.add("null");
          } else {
            deposit.add(item.get("deposit"));
          }
        }
        System.out.println(deposit.toString());

        SharedPreferences settings = PreferenceManager
        .getDefaultSharedPreferences(TransactionsList.this);
        String idstring = settings.getString("idkey", "");


        TransactionAdapter adapter = new
        TransactionAdapter(TransactionsList.this, partnername, description, listype,product,idstring,buyerid,sellerid);
        list = (ListView) findViewById(R.id.transactionlist);
        list.setAdapter(adapter);
        System.out.println("Reached");

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view,
                                  int position, long id) {
            int pos=position;
            Intent intent = new Intent(TransactionsList.this, Receipt.class);
            intent.putExtra("partnerkey",partnername.get(pos));
            intent.putExtra("descriptionkey",description.get(pos));
            intent.putExtra("totalpricekey",totalprice.get(pos).toString());
            intent.putExtra("sellerkey",sellerid.get(pos).toString());
            intent.putExtra("buyerkey",buyerid.get(pos).toString());
            intent.putExtra("transactionkey",transactionid.get(pos).toString().substring(0, transactionid.get(pos).toString().length() - 2));
            intent.putExtra("itemkey", itemid.get(pos).toString().substring(0,itemid.get(pos).toString().length()-2));
            intent.putExtra("inscankey",inscandate.get(pos).toString());
            intent.putExtra("statuskey",status.get(pos));
            intent.putExtra("depositkey",deposit.get(pos).toString());
            intent.putExtra("listypekey",listype.get(pos));
            startActivity(intent);

          }
        });
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        System.out.println(error);
      }
    }) {
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
  public void Refresh(View view) {
    Intent intent = new Intent(this,TransactionsList.class);
    startActivity(intent);
  }



}
