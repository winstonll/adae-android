package com.adae.justinelee.adaecompanion.HelperFunctions;

public class ImageStringParser {
  public ImageStringParser() {
  }

  public String avatar(String string) {
    String[] splitted_avatar = string.split("(avatar\\=\\{url\\=)");
    String[] s2 = splitted_avatar[1].split("(\\}\\}$)");
    return s2[0];
  }

  public String fullsize(String string) {
    String[] splitted_fullsize = string.split("(^\\{url\\=)");
    String[] s2 = splitted_fullsize[1].split("(,\\st)");
    return s2[0];
  }

  public String thumb(String string) {
    String[] splitted_thumb = string.split("(\\sthumb\\=\\{url\\=)");
    String[] s2 = splitted_thumb[1].split("(\\},\\sa)");
    return s2[0];
  }
  public String flo_remover(String string) {
    return string.substring(0,string.length()-2);
  }
}
