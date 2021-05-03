package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Function.Functions;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Userfood;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.myapplication.Function.Functions.get_log_user;
import static com.example.myapplication.Function.Functions.get_log_user;

public class SignupActivity extends AppCompatActivity {
    Userfood getloginuser=null;
    EditText fisrtname,lastname,password_id,phonenumber,adress_id,emailid;
    Button signup_id;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        get_log_user();
        getloginuser= Functions.get_log_user();

        if(getloginuser==null){
           Toast.makeText(this,"YOUR NOT LOGED",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"YOUR  LOGED"+getloginuser.firstname,Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if(getloginuser!=null){
            Toast.makeText(this,"YOUR already LOGED",Toast.LENGTH_LONG).show();
        }
        context=this;
        bid_view();
    }

    private void bid_view() {
        fisrtname=findViewById(R.id.fisrtname);
        lastname=findViewById(R.id.lastname);
        password_id=findViewById(R.id.password_id);
        phonenumber=findViewById(R.id.phonenumber);
        adress_id=findViewById(R.id.adress_id);
        emailid=findViewById(R.id.emailid);
        signup_id=findViewById(R.id.signup_id);
        signup_id.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                validate_data();
            }


        });
    }
    Userfood new_user=new Userfood();
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void validate_data() {
        new_user.firstname=fisrtname.getText().toString();
        if(new_user.firstname.length()<2){
            Toast.makeText(this,"First name is short",Toast.LENGTH_LONG).show();
           fisrtname.requestFocus();
            return;

        }

        if (fisrtname.getText().toString().isEmpty()){
            fisrtname.setError("fisrtname is not set");
            return;
        }else{
            fisrtname.setError("null");
        }

        new_user.lastname=lastname.getText().toString();
        if(new_user.lastname.length()<2){
            Toast.makeText(this,"Last name is short",Toast.LENGTH_LONG).show();
            lastname.requestFocus();
            return;

        }
        if (lastname.getText().toString().isEmpty()){
            lastname.setError("lastnameis not set");
            return;
        }else{
            lastname.setError("null");
        }
        new_user.email=emailid.getText().toString();

        //
        if (emailid.getText().toString().isEmpty()){
            emailid.requestFocus();
            emailid.setError("email is empty");
            return;
        }else{
            emailid.setError("null");
        }
        String email=emailid.getText().toString();
        if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailid.setError("invalid email!");
            emailid.requestFocus();
            return;
        }else {
            Toast.makeText(this,"please wait for a second...",Toast.LENGTH_LONG).show();
            signup_id.setVisibility(View.GONE);
            signup_id.setVisibility(View.VISIBLE);
        }
        new_user.pasword=password_id.getText().toString();
        if (password_id.getText().toString().isEmpty()){
            password_id.setError("password is empty");
            return;
        }else{
            password_id.setError("null");
        }
        new_user.phone_number=phonenumber.getText().toString();
        if (phonenumber.getText().toString().isEmpty()){
            phonenumber.setError("phonenumber is empty");
            return;
        }else{
            phonenumber.setError("null");
        }
        new_user.adress=adress_id.getText().toString();
        if (adress_id.getText().toString().isEmpty()){
            password_id.setError("adress_idis empty");
            return;
        }else{
            adress_id.setError("null");
        }
        new_user.user_type="Customer";
        new_user.gender="";
        new_user.profile="";
        submit_data();
    }
    //create a user
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String users_table = "users";
    ProgressDialog progressDialog;
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void submit_data() {

       /* if (true){
           return;
        }
*/
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Plaise wait a moment");
        progressDialog.setCancelable(false);
        progressDialog.show();


        //if user exist in db
        db.collection(users_table).whereEqualTo("email",new_user.email).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    Toast.makeText(SignupActivity.this,"email is not exist exist ",Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                    progressDialog.dismiss();
                    return;

                }
                new_user.user_id=db.collection(users_table).getId();
                new_user.reg_date=String.valueOf(Calendar.getInstance().getTimeInMillis()+"");
                db.collection(users_table).document(new_user.user_id).set(new_user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(SignupActivity.this,"Sucesfuly create an account",Toast.LENGTH_LONG).show();
                        progressDialog.hide();
                        progressDialog.dismiss();
                        loging_user();
                        if (loging_user()){
                            Toast.makeText(SignupActivity.this,"Your  account is loged sucesfuly",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(SignupActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            SignupActivity.this.startActivity(intent);
                            return;
                        }else {
                            Toast.makeText(SignupActivity.this,"Fail  to  loged your  account ",Toast.LENGTH_LONG).show();
                        }
                       // loging_user();

                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignupActivity.this,"Fail to create an account"+e.getMessage(),Toast.LENGTH_LONG).show();
                        progressDialog.hide();
                        progressDialog.dismiss();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this,"Fail to create an account"+e.getMessage(),Toast.LENGTH_LONG).show();
                progressDialog.hide();
                progressDialog.dismiss();
            }

        });

    }

    private static final String TAG = "SignupActivity";

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean loging_user() {
        /*Toast.makeText(this,"Login user",Toast.LENGTH_LONG).show();
        new_user.user_id=db.collection(users_table).getId();
        new_user.user_id="1";
        new_user.reg_date=String.valueOf(Calendar.getInstance().getTimeInMillis()+"");*/
        try {
            Userfood.save(new_user);
            return true;
           // Log.d(TAG, "loging_user: user has been saved sucesfuly");
        }catch (Exception e){
            Log.d(TAG, "Fail to saved user "+e.getMessage());
            return false;
        }

    }
}