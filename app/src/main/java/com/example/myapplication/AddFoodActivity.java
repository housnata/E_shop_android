package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.models.ProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddFoodActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    public final String PRODUCT_TABLE = "PRODUCTS";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProductModel new_product = new ProductModel();
    ImageButton icon_done;
    EditText book_title,book_price,book_deteles;
ImageView photos_itme;
    private Object filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        bind_view();
    }

    private void bind_view() {
        new_product.product_id = db.collection(PRODUCT_TABLE).document().getId();
        icon_done=findViewById(R.id.icon_done);
        book_title=findViewById(R.id.book_title);
        book_price=findViewById(R.id.book_price);
        book_deteles=findViewById(R.id.book_deteles);
        photos_itme=findViewById(R.id.photos_itme);
        processDialog =new ProcessDialog(this);
        photos_itme.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                chooseImage();
            }


        });

        icon_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit_item();
            }


        });
    }
    private void chooseImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SELECT AN IMAGE"),PICK_IMAGE_REQUEST);
    }
    //after select1 the file we call
public Uri imagePath=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && requestCode==RESULT_OK && data!=null && data.getData() !=null){
            imagePath=data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                photos_itme.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();

            }

        }
    }
   // FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProductModel product = new ProductModel();
    Context context;
    ProcessDialog processDialog;
    StorageReference storage_ref_main;

    private void upload_to_firestore() {
        Toast.makeText(this, "Time to uokiad to fire...", Toast.LENGTH_SHORT).show();
        db.collection(PRODUCT_TABLE).document(product.product_id).set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                processDialog.hide();
                processDialog.dismiss();
                Toast.makeText(AddFoodActivity.this, "Uploaded successfully!.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                processDialog.hide();
                Toast.makeText(AddFoodActivity.this, "Failed upload product because " + e.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
        });

    }
    private void submit_item() {
        new_product.title = book_title.getText().toString();
        if (new_product.title.isEmpty()) {
            Toast.makeText(this, "Product title can't be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (book_price.getText().toString().isEmpty()) {
            Toast.makeText(this, "Product price can't be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            new_product.price = Integer.valueOf(book_price.getText().toString());
        } catch (Exception e) {

        }
        if (new_product.price< 0) {
            Toast.makeText(this, "Product price can't be less than zero.", Toast.LENGTH_SHORT).show();
            return;
        }

        new_product.product_details = book_deteles.getText().toString();

        if(imagePath==null){
            Toast.makeText(this," you must Upload image",Toast.LENGTH_LONG).show();
            return;

        }
        //create reference
        processDialog.setTitle("Uploading.....");
        processDialog.show();
        storage_ref_main= FirebaseStorage.getInstance().getReference();
        storage_ref_main.child("product/"+ new_product.product_id).putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddFoodActivity.this,"Uploaded sucesfuly",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddFoodActivity.this,"Uploaded Failled"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                processDialog.hide();


            }
        });
       // ref_main=mStorageRef;
    }
}