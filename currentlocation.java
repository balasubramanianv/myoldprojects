package com.android.app;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CurrentLocation extends Activity {

	Button chooseLocation;
	TextView youAre, currentLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.current_location);
		chooseLocation = (Button) findViewById(R.id.btn_ch_location);
		shareFacebook = (Button) findViewById(R.id.btn_share_app);
		youAre = (TextView) findViewById(R.id.location);
		currentLocation = (TextView) findViewById(R.id.location_name);
		MyCurrentLocation();
		
		chooseLocation.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				Intent i = new Intent(CurrentLocation.this,
						ChooseLocation.class);
				startActivity(i);

			}
		});
	}

	private void MyCurrentLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String provider = locationManager.getBestProvider(new Criteria(), true);

		Location locations = locationManager.getLastKnownLocation(provider);
		List<String> providerList = locationManager.getAllProviders();
		if (null != locations && null != providerList
				&& providerList.size() > 0) {
			double longitude = locations.getLongitude();
			double latitude = locations.getLatitude();

			Geocoder geocoder = new Geocoder(getApplicationContext(),
					Locale.getDefault());
			try {
				List<Address> listAddresses = geocoder.getFromLocation(
						latitude, longitude, 1);
				if (null != listAddresses && listAddresses.size() > 0) {

					String currentlocation = listAddresses.get(0)
							.getAddressLine(0);

					int len = currentlocation.length();
					int ind = currentlocation.lastIndexOf(",");
					String finalloc = currentlocation.substring(ind + 1, len);
					Log.i("locations", String.valueOf(currentlocation));
					currentLocation.setText(finalloc);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
