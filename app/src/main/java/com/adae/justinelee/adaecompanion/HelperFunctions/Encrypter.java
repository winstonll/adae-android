package com.adae.justinelee.adaecompanion.HelperFunctions;

import java.util.ArrayList;

/**
 * Created by justinelee on 2016-02-25.
 */
public class Encrypter  {
  public Encrypter() {

  }

  public String Construct(String transactionid, String itemid, String qruserid,String status) {
    ArrayList<String> encoder=new ArrayList<String>();
    String encoded="";
    String[] val={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
    for (int i=0;i<val.length;i++) {
      encoder.add(val[i]);
    }
    System.out.println(encoder.size());

    for(int i=0;i<transactionid.length();i++) {
      String temp=Character.toString(transactionid.charAt(i));
      System.out.println(encoder.indexOf(temp));
      Integer temp2;
      temp2=encoder.indexOf(temp)+8;
      if (temp2>(encoder.size()-1)) {
        temp2=temp2-(encoder.size());
      }

      System.out.println(temp2);
      encoded=encoded+encoder.get(temp2);
      System.out.println(encoded);

    }
    encoded=encoded+"-";
    for(int i=0;i<itemid.length();i++) {
      String temp=Character.toString(itemid.charAt(i));
      Integer temp2;
      temp2=encoder.indexOf(temp)+8;
      if (temp2>(encoder.size()-1)) {
        temp2=temp2-(encoder.size());

      }

      encoded=encoded+encoder.get(temp2);
      System.out.println(encoded);
    }
    encoded=encoded+"-";
    for(int i=0;i<qruserid.length();i++) {
      String temp=Character.toString(qruserid.charAt(i));
      Integer temp2;
      if (encoder.indexOf(temp)==(encoder.size()-1)) {
        temp2=7;
      } else {
        temp2=encoder.indexOf(temp)+8;
        if (temp2>(encoder.size()-1)) {
          temp2=temp2-(encoder.size());

        }
      }
      encoded=encoded+encoder.get(temp2);
      System.out.println(encoded);
    }
    encoded=encoded+"-";
    for(int i=0;i<status.length();i++) {
      String temp=Character.toString(status.charAt(i));
      Integer temp2;

      temp2=encoder.indexOf(temp)+8;
      if (temp2>(encoder.size()-1)) {
        temp2=temp2-(encoder.size());

      }

      encoded=encoded+encoder.get(temp2);
      System.out.println(encoded);
    }
    return encoded;


  }

  public String Decrypter(String encrypted) {
    ArrayList<String> decoder=new ArrayList<String>();
    String decrypted="";
    String[] val={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
    for (int i=0;i<val.length;i++) {
      decoder.add(val[i]);
    }
    for(int i=0;i<encrypted.length();i++) {
      String temp=Character.toString(encrypted.charAt(i));
      if (!(temp.equals("-"))){
        Integer temp2=decoder.indexOf(temp)-8;
        if (temp2==-1) {
          temp2=(decoder.size())+temp2;
          decrypted=decrypted+decoder.get(temp2);

        } else {
          if (temp2<0) {
            temp2=(decoder.size()-1)+temp2;

          }
          decrypted=decrypted+decoder.get(temp2);
          System.out.println(decrypted);
        }
      }else {
        decrypted=decrypted+"-";
      }
    }
    return decrypted;



  }

}