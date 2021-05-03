package com.example.myapplication.Function;

import android.util.Log;

import com.example.myapplication.models.Userfood;

import java.util.List;

public class Functions {
    public static final Userfood get_log_user(){
        try {
            List<Userfood> users=Userfood.listAll(Userfood.class);
            if (users==null){
              //  Log.d(TAG, "user is null");
                return null;

            }
            if (users.isEmpty()){
                return null;
            }
            return  users.get(0);
            //
        }catch (Exception e){
          //  Log.d(TAG, "Fail to saved user "+e.getMessage());
        }
        return null;
    }
}
