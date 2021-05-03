/*
package com.example.myapplication.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.myapplication.ProcessDialog;
import com.example.myapplication.R;

import java.io.IOException;

public class AddFooodActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    ImageButton icon_done;
    ImageView photos_itme;
    private Object filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_foood);
        bind_view();
    }
    private void bind_view() {
        icon_done=findViewById(R.id.icon_done);
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
    private Uri imagePath=null;
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
}*/
