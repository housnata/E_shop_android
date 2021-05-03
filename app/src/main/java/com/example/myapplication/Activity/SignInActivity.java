package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.Userfood;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import static com.example.myapplication.Activity.SignupActivity.users_table;

public class SignInActivity extends AppCompatActivity {
    EditText password_id,emailid;
    Button signin_id;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        bind_view();
    }
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private void bind_view() {
        emailid=  findViewById(R.id.emailid);
        password_id=  findViewById(R.id.password_id);
        signin_id=  findViewById(R.id.signin_id);
        signin_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_user_form_sginup();
            }


        });
    }
    String email_value="";
    String pass_value="";
    ProgressDialog progressDialog;
    private void get_user_form_sginup() {
        email_value=emailid.getText().toString().trim();
        pass_value=password_id.getText().toString().trim();
        if (email_value.isEmpty()||pass_value.isEmpty()){
            Toast.makeText(this,"password and email are empty" ,Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Plaise wait a moment");
        progressDialog.setCancelable(false);
        progressDialog.show();
        db.collection(users_table).whereEqualTo("email",email_value).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()){
                            Toast.makeText(SignInActivity.this,"email is not exist IN DATABASE ",Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                            progressDialog.dismiss();
                            return;

                        }
                        if(queryDocumentSnapshots==null){
                            Toast.makeText(SignInActivity.this,"email is not  exist IN DATABASE ",Toast.LENGTH_LONG).show();
                            progressDialog.hide();
                            progressDialog.dismiss();
                            return;

                        }
                        List<Userfood> users=queryDocumentSnapshots.toObjects(Userfood.class);
                        if (users.get(0).pasword.equals(pass_value)){
                            Toast.makeText(SignInActivity.this,"WRONG PASSWORD ",Toast.LENGTH_LONG).show();
                            return;
                        }
                        progressDialog.hide();
                        progressDialog.dismiss();
                        if (loging_user(users.get(0))){
                            Toast.makeText(SignInActivity.this,"Your  account is loged sucesfuly",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(SignInActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            SignInActivity.this.startActivity(intent);
                            return;
                        }else {
                            Toast.makeText(SignInActivity.this,"Fail  to  loged your  account ",Toast.LENGTH_LONG).show();
                        }
                        // loging_user();
                        Toast.makeText(SignInActivity.this,"Succesful!! TIME TO LOGING ",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignInActivity.this,"Fail to CONNECT"+e.getMessage(),Toast.LENGTH_LONG).show();
                progressDialog.hide();
                progressDialog.dismiss();
            }
        });
    }
    private boolean loging_user(Userfood us) {
        /*Toast.makeText(this,"Login user",Toast.LENGTH_LONG).show();
        new_user.user_id=db.collection(users_table).getId();
        new_user.user_id="1";
        new_user.reg_date=String.valueOf(Calendar.getInstance().getTimeInMillis()+"");*/
        try {
            Userfood.save(us);
            return true;
            // Log.d(TAG, "loging_user: user has been saved sucesfuly");
        }catch (Exception e){
            return false;
        }

    }
}