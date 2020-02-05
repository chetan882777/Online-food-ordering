package com.example.github.ui.auth.registrationRestaurant;


import android.Manifest;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.example.github.BuildConfig;
import com.example.github.R;
import com.example.github.firebase.FirebaseRequestListener;
import com.example.github.model.Restaurant;
import com.example.github.ui.auth.AuthActivity;
import com.example.github.ui.auth.registrationUser.RegisterUserViewModel;
import com.example.github.ui.auth.registrationUser.RegistrationUser;
import com.example.github.ui.main.ViewModelProviderFactory;
import com.example.github.ui.restaurant.menu.MenuActivity;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class RegisterRestaurant extends DaggerAppCompatActivity {

    private static final String TAG = "RegisterRestaurant";

    public static final String RESTAURANT_TYPE_VEG = "Veg only";
    public static final String RESTAURANT_TYPE_NON_VEG = "Non-Veg only";
    public static final String RESTAURANT_TYPE_VEG_AND_NON_VEG = "Veg and Non-Veg";

    private String resType = RESTAURANT_TYPE_VEG;
    private List<String> offDays = new ArrayList<>();
    private int startHour = 10;
    private int endHour = 23;
    private int startMinute = 0;
    private int endMinute = 0;

    private Spinner spinner;
    private TextView textViewOffDays;
    private Button buttonAddOffDays;

    private TextView textViewOpeningTime;
    private Button buttonSetOpeningTime;
    private TextView textViewClosingTime;
    private Button buttonSetClosingTime;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextContact;
    private EditText editTextAddress;
    private EditText editTextName;
    private ProgressBar progressBar;
    private ImageView imageViewRestaurant;

    private Button nextButton;

    @Inject
    ViewModelProviderFactory providerFactory;
    RegisterRestaurantViewModel viewModel;


    private FusedLocationProviderClient mFusedLocationClient;
    private Location lastLocation;
    private LocationRequest locationRequest;
    private LocationCallback mLocationCallback;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 14;
    private int MY_CAMERA_PERMISSION_CODE = 123;
    private int CAMERA_REQUEST = 23;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_restaurant);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        viewModel = ViewModelProviders.of(this, providerFactory).get(RegisterRestaurantViewModel.class);

        setOffDays();
        setSpinner();
        setRestaurantTimings();
        signUp();

        getLocation();

        imageViewRestaurant = findViewById(R.id.imageView_resReg_res);

        imageViewRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        viewModel.getRestuarantImageMutableLiveData().observe(this , b ->{
            imageViewRestaurant.setImageBitmap(b);
        });
    }


    private boolean getLocation() {
        try {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                lastLocation = location;
                                Toast.makeText(RegisterRestaurant.this, "Location available", Toast.LENGTH_LONG).show();
                            }
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
                    }
                }

                ;
            };
        } catch (SecurityException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void signUp() {
        nextButton = findViewById(R.id.button_regRes_next);
        editTextEmail = findViewById(R.id.editText_regResEmail);
        editTextPassword = findViewById(R.id.editText_regResPassword);
        editTextContact = findViewById(R.id.editText_regResContact);
        editTextAddress = findViewById(R.id.editText_regResAddress);
        editTextName = findViewById(R.id.editText_regResName);

        nextButton.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);
            if(lastLocation == null){
                progressBar.setVisibility(View.GONE);
                showMessage("location is required");
                return;
            }

            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String contact = editTextContact.getText().toString();
            String address = editTextAddress.getText().toString();
            String name = editTextName.getText().toString();

            String regex = "^(.+)@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);

            if ((email.isEmpty() || email.equals("")) ||
                    (password.isEmpty() || password.equals("")) ||
                    (contact.isEmpty() || contact.equals("")) ||
                    (address.isEmpty() || address.equals("")) ||
                    (name.isEmpty() || name.equals(""))
            ) {
                showMessage("Any of the field cannot be empty");
            } else if (!matcher.matches()) {
                showMessage("Enter valid E-Mail");
            } else {
                register(name, email, password, contact, address);
            }
        });
    }

    private void register(String name, String email, String password, String contact, String address) {
        String lat;
        String lang;
        Restaurant restaurant;

        if (lastLocation != null) {
            lat = "" + lastLocation.getLatitude();
            lang = "" + lastLocation.getLongitude();

            restaurant = new Restaurant(name, email, password, contact, address,
                    lat, lang, "" + startHour, "" + startMinute,
                    "" + endHour, "" + endMinute, offDays, "Indore");
        } else {
            restaurant = new Restaurant(name, email, password, contact, address,
                    null, null, "" + startHour, "" + startMinute,
                    "" + endHour, "" + endMinute, offDays, "Indore");
        }

        FirebaseRequestListener<String> listener = (s -> {
            progressBar.setVisibility(View.GONE);
            showMessage(s);
            if (s.equals(Constants.FIREBASE_SUCCESS)) {
                SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(RegisterRestaurant.this);
                sharedPrefUtil.saveCredentials(contact);
                sharedPrefUtil.saveType(AuthActivity.INTENT_MESSAGE_AUTH_TYPE_RESTAURANT);

                Intent intent = new Intent(this, MenuActivity.class);
                intent.putExtra(MenuActivity.INTENT_MENU_MSG, MenuActivity.MENU_FRESH_ADD);
                startActivity(intent);
            }
        });
        viewModel.register(restaurant, listener);
    }


    private void setRestaurantTimings() {

        textViewOpeningTime = findViewById(R.id.textView_regRes_activeTimeStart);
        textViewClosingTime = findViewById(R.id.textView_regRes_activeTimeEnd);
        buttonSetOpeningTime = findViewById(R.id.button_regRes_activeTimesStart);
        buttonSetClosingTime = findViewById(R.id.button_regRes_activeTimesEnd);

        buttonSetOpeningTime.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(
                    RegisterRestaurant.this,
                    (timePicker, selectedHour, selectedMinute) -> {
                        textViewOpeningTime.setText
                                (selectedHour + " : " + selectedMinute);
                        startHour = selectedHour;
                        startMinute = selectedMinute;
                    }, hour, minute, true);//Yes 24 hour time

            mTimePicker.setTitle("Select Opening Time");
            mTimePicker.show();

        });

        buttonSetClosingTime.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(
                    RegisterRestaurant.this,
                    (timePicker, selectedHour, selectedMinute) -> {
                        textViewClosingTime.setText(selectedHour + " : " + selectedMinute);
                        endHour = selectedHour;
                        endMinute = selectedMinute;
                    }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Opening Time");
            mTimePicker.show();

        });
    }

    private void setOffDays() {
        textViewOffDays = findViewById(R.id.textView_regRes_noOffDays);
        buttonAddOffDays = findViewById(R.id.button_regRes_addOffDays);

        buttonAddOffDays.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(RegisterRestaurant.this);
            builderSingle.setIcon(R.drawable.ic_menu_send);
            builderSingle.setTitle("Select One Name:-");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    RegisterRestaurant.this, android.R.layout.select_dialog_singlechoice);
            arrayAdapter.add("Sunday");
            arrayAdapter.add("Monday");
            arrayAdapter.add("Tuesday");
            arrayAdapter.add("Wednesday");
            arrayAdapter.add("Friday");
            arrayAdapter.add("Saturday");

            builderSingle.setNegativeButton("cancel", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(RegisterRestaurant.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Your Selected Item is");
                builderInner.setPositiveButton("Ok", (dialog1, which1) -> {
                    if (!offDays.contains(strName)) {
                        offDays.add(strName);
                        String msg = "";
                        for (String s : offDays) {
                            msg = msg + s + "\n";
                        }
                        textViewOffDays.setText(msg);
                    }
                    dialog1.dismiss();
                });
                builderInner.show();
            });
            builderSingle.show();
        });
    }

    private void setSpinner() {
        spinner = findViewById(R.id.spinner_regRes_resType);

        List<String> spinnerList = new ArrayList<>();
        spinnerList.add(RESTAURANT_TYPE_VEG);
        spinnerList.add(RESTAURANT_TYPE_NON_VEG);
        spinnerList.add(RESTAURANT_TYPE_VEG_AND_NON_VEG);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resType = spinnerList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void showMessage(String s) {
        Snackbar.make(nextButton,
                s,
                Snackbar.LENGTH_SHORT)
                .show();
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

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
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
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
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
        }else if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     * <p>
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            lastLocation = task.getResult();

                          //  txtLatitude.setText(String.valueOf(lastLocation.getLatitude()));
                           // txtLongitude.setText(String.valueOf(lastLocation.getLongitude()));

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            viewModel.setRestaurantImage(photo);
        }
    }
}
