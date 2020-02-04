package com.example.github.ui.auth.registrationUser;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.github.BuildConfig;
import com.example.github.R;
import com.example.github.firebase.FirebaseRequestListener;
import com.example.github.model.User;
import com.example.github.ui.auth.AuthActivity;
import com.example.github.ui.main.MainActivity;
import com.example.github.ui.main.MainViewModel;
import com.example.github.ui.main.ViewModelProviderFactory;
import com.example.github.ui.user.home.UserHomeActivity;
import com.example.github.util.Constants;
import com.example.github.util.SharedPrefUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class RegistrationUser extends DaggerAppCompatActivity {

    private static final String TAG = "RegistrationUser";

    private FusedLocationProviderClient mFusedLocationClient;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private LocationCallback mLocationCallback;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 15;

    private EditText textViewEmail;
    private EditText textViewPassword;
    private EditText textViewContact;
    private EditText textViewAddress;
    private Button buttonSignUp;
    private ProgressBar progressBar;

    @Inject
    ViewModelProviderFactory providerFactory;

    private RegisterUserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);

        setLayout();

        setSignUp();

        getLocation();

    }

    private void setSignUp() {
        String regex = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(regex);

        viewModel = ViewModelProviders.of(this, providerFactory).get(RegisterUserViewModel.class);


        buttonSignUp.setOnClickListener(v -> {

            if(lastLocation == null){
                showMessage("location is required");
                return;
            }

            String email = textViewEmail.getText().toString();
            String password = textViewPassword.getText().toString();
            String contact = textViewContact.getText().toString();
            String address = textViewAddress.getText().toString();

            Matcher matcher = pattern.matcher(email);

            if ((email.isEmpty() || email.equals("")) ||
                    (password.isEmpty() || password.equals("")) ||
                    (contact.isEmpty() || contact.equals("")) ||
                    (address.isEmpty() || address.equals(""))
            ) {
                showMessage("Any of the field cannot be empty");
            } else if (!matcher.matches()) {
                showMessage("Enter valid E-Mail");
            } else {
                register(email, password, contact, address);
            }
        });
    }

    private void setLayout() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        textViewEmail = findViewById(R.id.editText_regUserEmail);
        textViewPassword = findViewById(R.id.editText_regUserPass);
        textViewContact = findViewById(R.id.editText_regUserContact);
        textViewAddress = findViewById(R.id.editText_regUserAddress);

        buttonSignUp = findViewById(R.id.button_regUser);
    }

    private void showMessage(String s) {
        Snackbar.make(buttonSignUp,
                s,
                Snackbar.LENGTH_SHORT)
                .show();
    }

    private void register(String email, String password, String contact, String address) {
        progressBar.setVisibility(View.VISIBLE);
        User user;
        if (lastLocation != null) {
            user = new User(email, password, contact, address,
                    "" + lastLocation.getLatitude(), "" + lastLocation.getLongitude(), "Indore");
        } else {
            user = new User(email, password, contact, address, null, null, "Indore");
        }
        FirebaseRequestListener<String> listener = data -> {
            showMessage(data);

            progressBar.setVisibility(View.GONE);
            if (data.equals(Constants.FIREBASE_SUCCESS)) {
                SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(RegistrationUser.this);
                sharedPrefUtil.saveCredentials(contact);
                sharedPrefUtil.saveType(AuthActivity.INTENT_MESSAGE_AUTH_TYPE_USER);

                Intent intent = new Intent(this, UserHomeActivity.class);
                startActivity(intent);
            }
        };
        viewModel.register(user, listener);
    }

    private boolean getLocation() {
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            lastLocation = location;
                            Toast.makeText(RegistrationUser.this, "Location available", Toast.LENGTH_LONG).show();
                        }
                    });
            locationRequest = LocationRequest.create();
            locationRequest.setInterval(5000);
            locationRequest.setFastestInterval(1000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            mLocationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    for (Location location : locationResult.getLocations()) {
                        lastLocation = location;
                        Toast.makeText(RegistrationUser.this,
                                "Location " + location.getLongitude() + ":" + location.getLatitude(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            };
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            startLocationUpdates();
            requestPermissions();
        } else {
            getLastLocation();
            startLocationUpdates();
        }
    }

    @Override
    public void onPause() {
        stopLocationUpdates();
        super.onPause();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }


    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(
                    R.string.permission_denied_explanation, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            startLocationPermissionRequest();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {

                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }


    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            lastLocation = task.getResult();

                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showMessage("Cannot detect location");

                        }
                    }
                });
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, null);
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(this.findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

}
