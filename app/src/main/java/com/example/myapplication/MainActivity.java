package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.myapplication.ADAPTER.AdapterProduct;
import com.example.myapplication.Activity.CheckoutActivity;
import com.example.myapplication.Activity.ProductsingleActivity;
import com.example.myapplication.Activity.SignInActivity;
import com.example.myapplication.Activity.SignupActivity;
import com.example.myapplication.models.ProductModel;
import com.example.myapplication.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView  recyvleview;
    private AdapterProduct madapter;
    private static final String TAG = "me";
    EditText username_id,email_id,adress_id;
    Button  submit_id,edite_id,delete_id;
    TextView data_of_usrs;
    Context context;

    String user_data="";
    List<UserModel> uesrlist=new ArrayList<>();
    private static final String users = "users";
    private static final String main = "main";
    UserModel new_user =new UserModel();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressDialog progressDialog;
    private Object Tools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;

        /*initToolbar();*/

        getdata();
        //first part

        bind_views();
      /* get_online_data();*/


        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
      db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
// CREATE option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_cart){
            Intent intent=new Intent(this, CheckoutActivity.class);
            this.startActivity(intent);
            //Toast.makeText(this,"you click on the cart",Toast.LENGTH_SHORT).show();

        }else if (item.getItemId() == R.id.add_product){
            Toast.makeText(this,"you click on add product",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(),item.getTitle(),Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

    //second part
    FirebaseFirestore Fb= FirebaseFirestore.getInstance();
List<ProductModel> products=new ArrayList<>();
    private void getdata() {
        db.collection("PRODUCTS").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                products = queryDocumentSnapshots.toObjects(ProductModel.class);
               /* initComponents();*/
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
               /* initComponents();*/
            }
        });
    }
    ProgressBar progressBar;
   /* private void initComponents() {
        recyvleview.setLayoutManager(new GridLayoutManager(this, 2));
        //recyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(this, 8), true));
       recyvleview.setHasFixedSize(true);
        recyvleview.setNestedScrollingEnabled(false);

        progressBar.setVisibility(View.GONE);
        recyvleview.setVisibility(View.VISIBLE);

        madapter = new AdapterProduct(products,this);
        recyvleview.setAdapter(madapter);


        // on item list clicked
       madapter.setOnItemClickListener(new AdapterProduct.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ProductModel obj, int position) {
             Intent i = new Intent(MainActivity.this, AddFoodActivity.class);
                i.putExtra("id", obj.product_id);
                MainActivity.this.startActivity(i);
               *//* Intent i = new Intent(MainActivity.this, ProductsingleActivity.class);
                i.putExtra("id", obj.product_id);
                MainActivity.this.startActivity(i);*//*
            }
        });
    }*/
   /* private void initToolbar() {
        try {
            *//*progressBar = findViewById(R.id.progressBar);*//*
            recyvleview= (RecyclerView) findViewById(R.id.recyclerView);
            recyvleview.setVisibility(View.GONE);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("chebli shop");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setSystemBarColor(this);
        } catch (Exception e) {

        }
    }*/
    public static void setSystemBarColor(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    //first part
 private void get_online_data() {
        Toast.makeText(this,"Loading",Toast.LENGTH_SHORT).show();
        db.collection(main).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots==null){
                    return;

                }
                if (queryDocumentSnapshots.isEmpty()){
                    return;

                }

                uesrlist=queryDocumentSnapshots.toObjects(UserModel.class);
                Toast.makeText(context,"sucesful Loading" +uesrlist.size(),Toast.LENGTH_SHORT).show();
                for (UserModel usernaee: uesrlist){
                    user_data+=usernaee.username+","+usernaee.email+","+usernaee.adress+"\n\n";
                    data_of_usrs.setText(user_data);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Fail to  Loading" ,Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void bind_views() {
      progressDialog = new ProgressDialog(context);
        username_id=findViewById(R.id.username_id);
        email_id=findViewById(R.id.email_id);
        adress_id=findViewById(R.id.adress_id);
        submit_id=findViewById(R.id.submit_id);
        delete_id=findViewById(R.id.delete_id);
        edite_id=findViewById(R.id.edite_id);
        data_of_usrs=findViewById(R.id.data_of_usrs);
        delete_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uesrlist==null){
                    Toast.makeText(context,"user not found" ,Toast.LENGTH_SHORT).show();
                }
                if (uesrlist.isEmpty()){
                    Toast.makeText(context,"user is empty" ,Toast.LENGTH_SHORT).show();
                    progressDialog.setTitle("Delete");
                    db.collection(main).document(uesrlist.get((uesrlist.size()-1)).id).delete()

                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progressDialog.show();
                                    Toast.makeText(context,"sucesfuly deleted" ,Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(MainActivity.this,MainActivity.class);
                                    context.startActivity(intent);
                                    finish();
                                }
                            });
                    Log.d(TAG, "onClick: me");
                }
            }
        });
        submit_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              new_user.username=username_id.getText().toString();
                new_user.adress=adress_id.getText().toString();
                new_user.email=email_id.getText().toString();
                submit_data();


            }
        });
    }

    private void submit_data() {
        new_user.username=username_id.getText().toString();
        if (new_user.username.isEmpty() ){
            Toast.makeText(this,"name is not set",Toast.LENGTH_SHORT).show();
            username_id.findFocus();
            return;
        }
        new_user.adress=adress_id.getText().toString();
        if (new_user.adress.isEmpty() ){
            Toast.makeText(this,"email is not set",Toast.LENGTH_SHORT).show();
            adress_id.findFocus();
            return;
        }
        new_user.email=email_id.getText().toString();
        if (new_user.email.isEmpty() ){
            Toast.makeText(this,"email is not set",Toast.LENGTH_SHORT).show();
            email_id.findFocus();
            return;
        }
        //let ask firebase to crete a id
        new_user.id=db.collection("main").document().getId();
        progressDialog.setTitle("PROGRESSING");
        progressDialog.setCancelable(false);
        progressDialog.show();
        db.collection(main).document(new_user.id).set(new_user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"sucesful",Toast.LENGTH_SHORT).show();
                progressDialog.hide();
                return;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Fail",Toast.LENGTH_SHORT).show();
                progressDialog.hide();
                Log.d(main, "onFailure: failed bcz"+e.getMessage());
                return;
            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.hide();
                //restart activity
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                context.startActivity(intent);
                finish();
            }
        });





    }

    public void addfood(View view) {
        Intent intent=new Intent(this, AddFoodActivity.class);
        this.startActivity(intent);
    }

    public void Signup(View view) {
        Intent intent=new Intent(this, SignupActivity.class);
        this.startActivity(intent);
    }
    public void Sign_In(View view) {
        Intent intent=new Intent(this, SignInActivity.class);
        this.startActivity(intent);
    }

}