package com.example.anatoly.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PhotoAdapter adapter;
    private ArrayList<Uri> photos;
    private GridView grid_view;
    private static final int REQUEST = 1;
    private static final String KEY_PHOTOS = "PHOTOS";
    private int samples[] = {
            R.mipmap.sample1, R.mipmap.sample2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new PhotoAdapter(this);
        for (int i = 0; i < 3; ++i) {
            adapter.add_item(BitmapFactory.decodeResource(getResources(), samples[0]));
            adapter.add_item(BitmapFactory.decodeResource(getResources(), samples[1]));
        }

        photos = new ArrayList<>();
        grid_view = (GridView) findViewById(R.id.grid_view);
        grid_view.setAdapter(adapter);
        setAddButton();
    }

    private void setAddButton() {
        View button = findViewById(R.id.add_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                Bitmap img = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                photos.add(selectedImage);
                adapter.add_item(img);
                grid_view.setAdapter(adapter);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadAdapter() {
        for (Uri image: photos) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                adapter.add_item(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        grid_view.setAdapter(adapter);
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(KEY_PHOTOS, photos);
    }

    protected void onRestoreInstanceState(Bundle savedState){
        super.onRestoreInstanceState(savedState);
        photos = savedState.getParcelableArrayList(KEY_PHOTOS);
        if (photos != null) {
            loadAdapter();
        }
    }
}
