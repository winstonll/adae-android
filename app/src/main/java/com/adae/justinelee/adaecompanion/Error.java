package com.adae.justinelee.adaecompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Error extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_error);
    Intent intent = getIntent();
    final String totalprice = (String) intent.getSerializableExtra("pricekey");}

  public void toScan(View view) {
    Intent intent = getIntent();
    final String totalprice = (String) intent.getSerializableExtra("pricekey");
    Intent intent2 = new Intent(this,Scan.class);
    intent2.putExtra("pricekey",totalprice);
    startActivity(intent2);


  }

}

