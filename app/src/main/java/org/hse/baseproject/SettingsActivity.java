package org.hse.baseproject;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity implements SensorEventListener {

    private final static String USER_NAME_KEY = "userName";
    private final static int REQUEST_ID_CAMERA_PERMISSION = 1;
    private static final String EMPTY_STRING = "";
    private static final String PROFILE_PIC_FILE_NAME = "profilepic";

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView lightSensorText;
    private EditText userNameText;
    private ImageView profilePic;
    private PreferenceManager preferenceManager;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private ListView sensorsList;

    private boolean isCameraPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        preferenceManager = new PreferenceManager(this);

        lightSensorText = findViewById(R.id.light_sensor_text);
        userNameText = findViewById(R.id.user_name);
        profilePic = findViewById(R.id.profile_pic);
        sensorsList = findViewById(R.id.sensors_list);

        Button saveSettingButton = findViewById(R.id.save_settings_button);
        saveSettingButton.setOnClickListener(this::saveSettings);

        Button takePhotoButton = findViewById(R.id.take_photo_button);
        takePhotoButton.setOnClickListener(this::takePhotoButtonClicked);

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                assert data != null;
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                profilePic.setImageBitmap(photo);
            }
        });

        loadSettings();
        fillSensorsList();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ID_CAMERA_PERMISSION) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Toast toast = Toast.makeText(this, "Без доступа к камере вы не сможете поменять фотографию", Toast.LENGTH_SHORT);
                toast.show();
                isCameraPermissionGranted = false;
            } else {
                isCameraPermissionGranted = true;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float lux = sensorEvent.values[0];
        lightSensorText.setText(String.format(Locale.getDefault(),"%f", lux));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private void fillSensorsList() {
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        List<String> sensorNames = new ArrayList<>();

        for (Sensor sensor:sensors) {
            sensorNames.add(sensor.getName());
        }
        sensorsList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,  sensorNames));
    }

    private void takePhotoButtonClicked(View view) {
        checkCameraPermissions();

        if (isCameraPermissionGranted)
            takePicture();
    }

    private void loadSettings() {
        String userName = preferenceManager.getValue(USER_NAME_KEY, EMPTY_STRING);
        userNameText.setText(userName);

        Bitmap profileImage = getProfilePic();
        if (profileImage != null)
            profilePic.setImageBitmap(profileImage);
    }

    private void saveSettings(View view) {
        String userName = userNameText.getText().toString();
        if (!userName.isEmpty()) preferenceManager.saveValue(USER_NAME_KEY, userName);

        Drawable profileImage = profilePic.getDrawable();
        saveProfileImage(profileImage);

        Toast toast = Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT);
        toast.show();
    }

    private Bitmap getProfilePic() {
        File profilePicFile = getProfilePicFile();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeFile(profilePicFile.getPath(), options);
    }

    private void saveProfileImage(Drawable profileImage) {
        Bitmap bitmap = ((BitmapDrawable) profileImage).getBitmap();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File profilePicFile = getProfilePicFile();
        try {
            boolean isFileCreated = profilePicFile.createNewFile();
            if (!isFileCreated) return;

            FileOutputStream fos = new FileOutputStream(profilePicFile);
            fos.write(bytes.toByteArray());
            fos.close();
        } catch (IOException e) {
            Log.e("file pic", e.getLocalizedMessage());
        }
    }

    private File getProfilePicFile() {
        return new File(this.getFilesDir(), PROFILE_PIC_FILE_NAME);
    }

    private void checkCameraPermissions() {
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        } else {
            isCameraPermissionGranted = true;
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), REQUEST_ID_CAMERA_PERMISSION);
        }
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        activityResultLauncher.launch(takePictureIntent);
    }
}