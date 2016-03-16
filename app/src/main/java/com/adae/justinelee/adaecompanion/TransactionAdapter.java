package com.adae.justinelee.adaecompanion;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.adae.justinelee.adaecompanion.HelperFunctions.ImageDownloader;

import java.util.ArrayList;

/**
 * Created by justinelee on 2016-02-01.
 */
public class TransactionAdapter extends ArrayAdapter<String> {
  private final Activity context;
  private final ArrayList<String> partnername;
  private final ArrayList<String> description;
  private final ArrayList<String> listype;
  private final ArrayList<String> product;
  private final String idstring;
  private final ArrayList<Object> buyerid;
  private final ArrayList<Object> sellerid;

  public TransactionAdapter(Activity context, ArrayList<String> partnername, ArrayList<String> description, ArrayList<String> listype, ArrayList<String> product, String idstring,ArrayList<Object> buyerid, ArrayList<Object> sellerid) {
    super(context, R.layout.transaction, partnername);
    this.context = context;
    this.partnername = partnername;
    this.description = description;
    this.listype = listype;
    this.product=product;
    this.idstring=idstring;
    this.buyerid=buyerid;
    this.sellerid=sellerid;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    View rowView = convertView;
    if (rowView == null) {
      LayoutInflater inflater = context.getLayoutInflater();
      rowView = inflater.inflate(R.layout.transaction, null, true);
    }
    TextView nameT = (TextView) rowView.findViewById(R.id.partnername);
    TextView descriptionT=(TextView) rowView.findViewById(R.id.description);

    nameT.setText(partnername.get(position));
    descriptionT.setText(description.get(position));

    new ImageDownloader((ImageView) rowView.findViewById(R.id.picture)).execute(product.get(position));
    TextView user=(TextView) rowView.findViewById(R.id.transactiontitle);



    if (listype.get(position).equals("sell") && idstring.equals(buyerid.get(position).toString())) {
      user.setText("Buying from:");
    } else if (listype.get(position).equals("sell") && idstring.equals(sellerid.get(position).toString())) {
      user.setText("Selling to:");
    } else if (listype.get(position).equals("rent") && idstring.equals(buyerid.get(position).toString())) {
      user.setText("Renting from:");
    } else if(listype.get(position).equals("rent") && idstring.equals(sellerid.get(position).toString())) {
      user.setText("Renting to:");
    } else if (listype.get(position).equals("timeoffer") && idstring.equals(sellerid.get(position).toString())) {
        user.setText("Offering Service to:");
    } else if(listype.get(position).equals("timeoffer") && idstring.equals(buyerid.get(position).toString())) {
        user.setText("Recieving Service from:");
    } else if(listype.get(position).equals("lease") && idstring.equals(sellerid.get(position).toString())) {
      user.setText(("Leasing to"));
    } else if(listype.get(position).equals("lease") && idstring.equals(buyerid.get(position).toString())) {
      user.setText(("Leasing from"));
    }
    System.out.println(listype.toString());
    System.out.println(idstring);
    System.out.println(buyerid.get(position).toString());
    System.out.println(sellerid.get(position).toString());
    return rowView;
  }
}
