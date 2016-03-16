package com.adae.justinelee.adaecompanion;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.adae.justinelee.adaecompanion.HelperFunctions.Encrypter;

public class Receipt extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.receipt);
    Intent intent = getIntent();
    final String partnername=(String)intent.getSerializableExtra("partnerkey");
    final String description=(String)intent.getSerializableExtra("descriptionkey");
    final String totalprice =(String) intent.getSerializableExtra("totalpricekey");
    final String sellerid=(String) intent.getSerializableExtra("sellerkey");
    final String buyerid=(String) intent.getSerializableExtra("buyerkey");
    final String transactionid=(String) intent.getSerializableExtra("transactionkey");
    final String itemid=(String) intent.getSerializableExtra("itemkey");
    final String inscandate=(String) intent.getSerializableExtra("inscankey");
    final String status=(String) intent.getSerializableExtra("statuskey");
    final String deposit =(String) intent.getSerializableExtra("depositkey");
    final String listype=(String) intent.getSerializableExtra("listypekey");

    SharedPreferences settings = PreferenceManager
    .getDefaultSharedPreferences(Receipt.this);
    final String idstring = settings.getString("idkey", "");

    RelativeLayout qr= (RelativeLayout)findViewById(R.id.Qrcode);
    qr.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {

        if (idstring.equals(sellerid)) {
          String status2="";
          if (inscandate.equals("null"))
            status2=status2+"inscan";
          else {
            status2=status2+"outscan";
          }
          String userid=idstring.substring(0,idstring.length()-2);
          System.out.println(userid);
          System.out.println(itemid);
          System.out.println(transactionid);
          Encrypter encrypt= new Encrypter();
          String message=encrypt.Construct(transactionid,itemid,userid,status2);
          System.out.println(message);

          Intent intent = new Intent(Receipt.this,QrCode.class);
          intent.putExtra("partnerkey",partnername);
          intent.putExtra("descriptionkey",description);
          intent.putExtra("totalpricekey",totalprice);
          intent.putExtra("sellerkey",sellerid);
          intent.putExtra("buyerkey", buyerid);
          intent.putExtra("transactionkey", transactionid);
          intent.putExtra("itemkey",itemid);
          intent.putExtra("inscankey",inscandate);
          intent.putExtra("messagekey",message);
          intent.putExtra("depositkey",deposit);
          intent.putExtra("statuskey",status);
          intent.putExtra("listypekey",listype);

          startActivity(intent);
        }

        if(idstring.equals(buyerid)) {
          Intent intent = new Intent(Receipt.this,Scan.class);
          intent.putExtra("totalpricekey",totalprice);
          intent.putExtra("partnerkey",partnername);
          intent.putExtra("statuskey",status);
          startActivity(intent);


        }
      }

    });

    System.out.println(partnername);

    TextView txt1=(TextView)findViewById(R.id.action);
    String sellsell="Make sure to have the unique QR code scanned by the other party when they pick up your item, you will be paid upon QR code scanning.";
    String sellrent="Make sure to have the unique QR code scanned by the other party when they pick up your item, they must scan it again when they return the item.";
    String sellservice="Make sure to have the unique QR code scanned by the other party at the beginning of your service, they must scan it again at the end of your service.";
    String buysell="Make sure to have the in-app QR Scanner open when you meet. Scan the QR code on their phone to confirm your transaction.";
    String buyrent="Make sure to have the in-app QR Scanner open when you meet. Scan the QR code on their phone to confirm your transaction. Scan the QR code again when you return the item.";
    String buyservice="Make sure to have the in-app QR Scanner open when you meet. Scan the QR code on their phone to confirm your transaction. Scan the QR code again at the end of the service.";
    TextView instruct=(TextView)findViewById(R.id.instructions);
    System.out.println(listype);

    if (listype.equals("sell") && idstring.equals(buyerid)) {
      txt1.setText("I would like to purchase");
      instruct.setText(buysell);
    } else if (listype.equals("sell") && idstring.equals(sellerid)) {
      txt1.setText(partnername+" would like to purchase");
      instruct.setText(sellsell);
    } else if (listype.equals("rent") && idstring.equals(buyerid)) {
      txt1.setText("I would like to try");
      instruct.setText(buyrent);
    } else if(listype.equals("rent") && idstring.equals(sellerid)) {
      txt1.setText(partnername+" would like to try");
      instruct.setText(sellrent);
    } else {
      if (idstring.equals("sellerid")) {
        txt1.setText(partnername+" would like to try");
        instruct.setText(sellservice);
      } else {
        txt1.setText("I would like to try");
        instruct.setText(buyservice);
      }
    }


    TextView txt2=(TextView)findViewById(R.id.description);
    txt2.setText(description);
    String addedprice="$"+totalprice;
    TextView txt3=(TextView) findViewById(R.id.price);
    txt3.setText(addedprice);

    TextView txt4 = (TextView) findViewById(R.id.fee);
    if(!(deposit=="null")) {
      txt4.setText("$" + deposit);
    } else {
      txt4.setText("-");
    }
    TextView txt5=(TextView) findViewById(R.id.statusactual);
    txt5.setText(status);



  }
  public void toScan(View view) {
    Intent intent = new Intent(this,Scan.class);
    startActivity(intent);

  }
  public void toQr(View view) {
    Intent intent = new Intent(this, QrCode.class);
    startActivity(intent);
  }
  public void toTransactions(View view){
    Intent intent = new Intent(this,TransactionsList.class);
    startActivity(intent);
  }
}
