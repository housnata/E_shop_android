package com.example.myapplication.models;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class CartModel extends SugarRecord {
  public   String product_name="";
  @Unique
  public String product_id="";
  public String product_price="";
  public String product_image="";
  public int quantity=1;
}
