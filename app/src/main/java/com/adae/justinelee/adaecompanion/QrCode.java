package com.adae.justinelee.adaecompanion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by justinelee on 2016-01-28.
 */
public class QrCode extends Activity {
  public final static int WHITE = 0xFFFFFFFF;
  public final static int BLACK = 0xFF000000;
  public final static int WIDTH = 400;
  public final static int HEIGHT = 400;
 ;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.qrcode);
    Intent intent = getIntent();
    final String partnername=(String)intent.getSerializableExtra("partnerkey");
    final String description=(String)intent.getSerializableExtra("descriptionkey");
    final String totalprice =(String) intent.getSerializableExtra("totalpricekey");
    final String sellerid=(String) intent.getSerializableExtra("sellerkey");
    final String buyerid=(String) intent.getSerializableExtra("buyerkey");
    final String transactionid=(String) intent.getSerializableExtra("transactionkey");
    final String itemid=(String) intent.getSerializableExtra("itemkey");
    final String inscandate=(String) intent.getSerializableExtra("inscankey");
    final String STR=(String) intent.getSerializableExtra("messagekey");
    final String deposit=(String) intent.getSerializableExtra("depositkey");
    final String status=(String) intent.getSerializableExtra("statuskey");
    final String listype=(String) intent.getSerializableExtra("listypekey");


    RelativeLayout details= (RelativeLayout) findViewById(R.id.Details);
    details.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {

        Intent intent = new Intent(QrCode.this, Receipt.class);
        intent.putExtra("partnerkey", partnername);
        intent.putExtra("descriptionkey", description);
        intent.putExtra("totalpricekey", totalprice);
        intent.putExtra("sellerkey", sellerid);
        intent.putExtra("buyerkey", buyerid);
        intent.putExtra("transactionkey",transactionid);
        intent.putExtra("itemkey",itemid);
        intent.putExtra("inscankey",inscandate);
        intent.putExtra("depositkey",deposit);
        intent.putExtra("statuskey",status);
        intent.putExtra("listypekey",listype);
        startActivity(intent);
      }
      // do stuff


  });





    ImageView imageView = (ImageView) findViewById(R.id.qrcode);
    try {
      Bitmap bitmap = encodeAsBitmap(STR);
      imageView.setImageBitmap(bitmap);
    } catch (WriterException e) {
      e.printStackTrace();
    }
  }

  Bitmap encodeAsBitmap(String str) throws WriterException {
    BitMatrix result;
    try {
      result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
    } catch (IllegalArgumentException iae) {
      // Unsupported format
      return null;
    }

    int width = result.getWidth();
    int height = result.getHeight();
    int[] pixels = new int[width * height];
    for (int y = 0; y < height; y++) {
      int offset = y * width;
      for (int x = 0; x < width; x++) {
        pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
      }
    }

    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
    return bitmap;
  }

  public void toTransactions(View view) {
    Intent intent = new Intent(this, TransactionsList.class);
    startActivity(intent);
  }





}
