package nl.mikevanmil.workshopmadbaas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class OverviewActivity extends AppCompatActivity implements LocationListener {

    static final int TAKE_PICTURE_REQUEST = 1;
    static final int UPLOAD_PICTURE_REQUEST = 2;

    Button mTakePicture;
    Button mUploadPicture;
    Button mViewGallery;

    private double latitude;
    private double longitude;

    LocationManager locationManager;
    String mprovider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        mTakePicture = (Button) findViewById(R.id.takePicture);
        mUploadPicture = (Button) findViewById(R.id.uploadPicture);
        mViewGallery = (Button) findViewById(R.id.viewGallery);

        mTakePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                takePicture();
            }
        });

        mUploadPicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                uploadPicture();
            }
        });

        mViewGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getImages();
            }
        });

    }

    private void takePicture() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE_REQUEST);
    }

    private void uploadPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), UPLOAD_PICTURE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        switch (requestCode) {
            case (TAKE_PICTURE_REQUEST):
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    saveImage((Bitmap) data.getExtras().get("data"));
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                } else {
                    // Image capture failed, advise user
                }
                break;
            case (UPLOAD_PICTURE_REQUEST):
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getData() != null) {
                        Uri uri = data.getData();
                        try {
                            saveImage(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (resultCode == RESULT_CANCELED) {
                    // User cancelled the image capture
                } else {
                    // Image capture failed, advise user
                }
                break;
        }
    }

    private void saveImage(Bitmap image) {
        //TODO: Use this method to upload an image to Backendless.

        //TODO: Hint: This function call below is needed for getting an GPS location.
        //TODO: You can use the variables 'latitude' and 'longitude' after you called this method.
        OverviewActivityPermissionsDispatcher.showGPSWithCheck(OverviewActivity.this);

    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    void showGPS() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Needed for tricking the compiler
        }
        Location location = locationManager.getLastKnownLocation("gps");
        locationManager.requestLocationUpdates("gps", 15000, 1, this);

        if (location != null) {
            onLocationChanged(location);
        } else {
            Toast.makeText(getBaseContext(), R.string.gps_turned_off, Toast.LENGTH_SHORT).show();
        }
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    void showRationaleForGPS(PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permission_gps_rationale)
                .show();
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void showDeniedForGPS() {
        Toast.makeText(this, R.string.permission_gps_denied, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAskForGPS() {
        Toast.makeText(this, R.string.permission_gps_neverask, Toast.LENGTH_SHORT).show();
    }

    private void getImages(){
        Intent intent = new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
