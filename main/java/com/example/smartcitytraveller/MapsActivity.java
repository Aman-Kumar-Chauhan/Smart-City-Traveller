package com.example.smartcitytraveller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.location.*;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.smartcitytraveller.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
    {
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    int PROXIMITY_RADIUS = 1000;
    double latitude, longitude;
    private static final int REQUEST_USER_LOCATION_CODE=99;
    private GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            checkUserLocationPermission();
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent=getIntent();
        int s=Integer.parseInt(intent.getStringExtra("start_time"));
        int e=Integer.parseInt(intent.getStringExtra("end_time"));
        int temp=e-s;
        if(temp>0 && temp<3)
            PROXIMITY_RADIUS=1000;
        else if(temp>=3 && temp<6)
            PROXIMITY_RADIUS=5000;
        else if(temp>=6 && temp<10)
            PROXIMITY_RADIUS=10000;
        else
            PROXIMITY_RADIUS=20000;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {

            mMap.setMyLocationEnabled(true);
        }
    }

    public boolean checkUserLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_USER_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_USER_LOCATION_CODE);
            }
            return false;
        }
        else
            return true;
    }

    public void onClick(View v)
    {
        Object dataTransfer[]=new Object[2];

        GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();
        switch (v.getId())
        {
            case R.id.B_search:
                EditText addressField=(EditText)findViewById(R.id.location_search);
                String address=addressField.getText().toString();
                List<Address> addressList=null;
                MarkerOptions userMarkerOptions=new MarkerOptions();
                if(!TextUtils.isEmpty(address))
                {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList=geocoder.getFromLocationName(address,6);
                        if(addressList!=null)
                        {
                            for(int i=0;i<addressList.size();i++)
                            {
                                Address userAddress=addressList.get(i);
                                LatLng latLng=new LatLng(userAddress.getLatitude(),userAddress.getLongitude());
                                userMarkerOptions.position(latLng);
                                userMarkerOptions.title(address);
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                mMap.addMarker(userMarkerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                            }
                        }
                        else
                            Toast.makeText(this, "Location not found!!", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                    Toast.makeText(this, "Enter a place name!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.B_hospital:
                mMap.clear();
                String hospital="hospital";
                String url=getUrl(latitude,longitude,hospital);

                dataTransfer[0]=mMap;
                dataTransfer[1]=url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this,"Showing Nearby Hospitals",Toast.LENGTH_LONG).show();
                break;
            case R.id.B_restaurant:
                mMap.clear();
                String restaurant="restaurant";
                url=getUrl(latitude,longitude,restaurant);
                dataTransfer[0]=mMap;
                dataTransfer[1]=url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this,"Showing Nearby Restaurants",Toast.LENGTH_LONG).show();
                break;
            case R.id.B_theater:
                mMap.clear();
                String theater="movie_theater";
                url=getUrl(latitude,longitude,theater);
                dataTransfer[0]=mMap;
                dataTransfer[1]=url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this,"Showing Nearby Cinema Halls",Toast.LENGTH_LONG).show();
                break;
            case R.id.B_park:
                mMap.clear();
                String park="amusement_park";
                url=getUrl(latitude,longitude,park);
                dataTransfer[0]=mMap;
                dataTransfer[1]=url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this,"Showing Nearby Amusement Parks",Toast.LENGTH_LONG).show();
                break;
            case R.id.B_camp:
                mMap.clear();
                String camp="campground";
                url=getUrl(latitude,longitude,camp);
                dataTransfer[0]=mMap;
                dataTransfer[1]=url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this,"Showing Nearby Campgrounds",Toast.LENGTH_LONG).show();
                break;
            case R.id.B_mall:
                mMap.clear();
                String mall="shopping_mall";
                url=getUrl(latitude,longitude,mall);
                dataTransfer[0]=mMap;
                dataTransfer[1]=url;

                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this,"Showing Nearby Shopping Malls",Toast.LENGTH_LONG).show();
                break;
        }
    }

    private String getUrl(double latitude,double longitude,String nearbyPlace)
    {
     //   StringBuilder googlePlaceUrl= new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?query=coffee+shop&location=35.792491,-78.653009&radius=2000&region=us&type=cafe,bakery&key=AIzaSyDlQlr2cMwx4eubRxa2bknxh4y7KaC3F-8");
        gpsTracker = new GpsTracker(MapsActivity.this);
        latitude = gpsTracker.getLatitude();
        longitude = gpsTracker.getLongitude();
        StringBuilder googlePlaceUrl=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key=AIzaSyDlQlr2cMwx4eubRxa2bknxh4y7KaC3F-8");

        Log.d("MapsActivity","url = "+googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }
}