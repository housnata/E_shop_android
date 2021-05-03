package com.example.myapplication.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.myapplication.R;
import com.example.myapplication.models.ProductModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductsingleActivity extends AppCompatActivity {
    String id=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context= this;
        bind_view();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productsingle);
        Intent intent=getIntent();
      id=  intent.getStringExtra("id");
      if (id==null||id.length()<1){
          Toast.makeText(this,"ID IS NOT FOUND",Toast.LENGTH_SHORT).show();
          finish();

      }
      get_data();
    }
ImageView product_image;
    TextView product_title,product_price,product_details;
    Button add_to_cart;
    private void bind_view() {
        product_image=   findViewById(R.id.product_image);
        product_title=   findViewById(R.id.product_title);
        product_price=   findViewById(R.id.product_price);
        product_details=   findViewById(R.id.product_details);
        add_to_cart=   findViewById(R.id.add_to_cart);
    }

    private void feed_data() {
        Glide.with(context)
                .load(product.image)
                .animate(R.anim.abc_fade_in)
                .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                .centerCrop()
                .into(product_image);
        product_title.setText(product.title);
        product_price.setText(product.price +"");
        product_details.setText(product.product_details +"");
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Adding to cart",Toast.LENGTH_SHORT).show();
            }
        });
    }
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    ProductModel product=new ProductModel();
    Context context;
   /* List<ProductModel> products=new ArrayList<>();*/
    private void get_data() {
        db.collection("PRODUCTS").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Toast.makeText(context,"product is not found",Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                product=documentSnapshot.toObject(ProductModel.class);
            feed_data();
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"IS NOT FOUND BECOUSE"+e.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        });
    }
}