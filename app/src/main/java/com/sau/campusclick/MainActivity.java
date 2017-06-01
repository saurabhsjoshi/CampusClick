package com.sau.campusclick;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.sau.campusclick.adapter.ListAdapter;
import com.sau.campusclick.model.Picture;
import com.sau.campusclick.rest.ApiClient;
import com.sau.campusclick.rest.ApiInterface;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Uri photoURI = null;

    String mCurrentPhotoPath;

    //@BindView(R.id.img_test) ImageView mImageView;
    @BindView(R.id.listView) ListView listView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_map:
                startActivity(new Intent(this, MapsActivity.class)); break;
            case R.id.menu_ref:
                loadData(); break;
            case R.id.menu_out:
                SharedPreferences sharedPref = MainActivity.this.getSharedPreferences(
                        getString(R.string.user_pref_file), Context.MODE_PRIVATE);
                sharedPref.edit().putBoolean("isSignedIn", false).apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
            default:
                super.onOptionsItemSelected(item);
        }
        return true;
    }


    public void openCamera(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CANADA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if(photoURI != null) {
                Intent i = new Intent(this, UploadActivity.class);
                i.putExtra("uri" , photoURI);
                startActivity(i);
            }
        }
    }

    public void loadData() {
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.user_pref_file), Context.MODE_PRIVATE);
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Picture>> call =  apiService.getPicsByUser(sharedPref.getInt("id", -1));
        call.enqueue(new Callback<List<Picture>>() {
            @Override
            public void onResponse(Call<List<Picture>> call, Response<List<Picture>> response) {
                listView.setAdapter(new ListAdapter(MainActivity.this, response.body().toArray(new Picture[response.body().size()])));
            }

            @Override
            public void onFailure(Call<List<Picture>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1);
        }
        loadData();
    }


}
