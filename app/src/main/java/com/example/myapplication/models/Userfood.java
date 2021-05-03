package com.example.myapplication.models;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class Userfood extends SugarRecord {
    public String firstname="";
    public String lastname="";
    public String pasword="";
    public String email="";
    public String phone_number="";
    public String adress="";
    public String gender="";
    public String reg_date="";
    public String user_type="";



    @Unique
    public String user_id="";
    public String profile="";

}
