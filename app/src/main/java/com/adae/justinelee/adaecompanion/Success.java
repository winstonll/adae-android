package com.adae.justinelee.adaecompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Success extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.success);
    Intent intent = getIntent();
    String name1= (String) intent.getSerializableExtra("partnerkey");
    String status=(String) intent.getSerializableExtra("statuskey");
    TextView name=(TextView)findViewById(R.id.name);
    name.setText("You may now exchange the item");

  }
  public void toTransactions(View view) {
    Intent intent = new Intent(this,TransactionsList.class);
    startActivity(intent);
  }
}
