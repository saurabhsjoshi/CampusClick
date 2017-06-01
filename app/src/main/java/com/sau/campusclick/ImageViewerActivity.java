package com.sau.campusclick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.sau.campusclick.model.Picture;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewerActivity extends AppCompatActivity {
    @BindView(R.id.img_pic) ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        ButterKnife.bind(this);
        Picture picture =  getIntent().getParcelableExtra("pic");
        setTitle(picture.getUsername());
        Picasso.with(this).load("http://138.197.137.60/campusclick/uploads/" + picture.getId() + ".jpg").fit().centerCrop().into(imageView);
    }
}
