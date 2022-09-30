package com.app.dorav4.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.dorav4.R;
import com.app.dorav4.bluetoothchat.BluetoothChatActivity;
import com.bluetooth.communicator.BluetoothCommunicator;
import com.bluetooth.communicator.tools.BluetoothTools;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pub.devrel.easypermissions.EasyPermissions;

public class OfflineDashboardActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    MaterialCardView cvGuides, cvHotlines, cvBluetoothChat, cvOnlineFeatures;
    Intent intent;

    public String[] REQUIRED_PERMISSIONS;
    private final int REQUEST_CODE_REQUIRED_PERMISSIONS = 4;

    public static BluetoothCommunicator bluetoothCommunicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_dashboard);

        cvGuides = findViewById(R.id.cvGuides);
        cvHotlines = findViewById(R.id.cvHotlines);
        cvBluetoothChat = findViewById(R.id.cvBluetoothChat);
        cvOnlineFeatures = findViewById(R.id.cvOnlineFeatures);

        // Set permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            REQUIRED_PERMISSIONS = new String[] {
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_CONNECT
            };
        } else {
            REQUIRED_PERMISSIONS = new String[] {
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            };
        }

        // Disaster Preparedness Guides OnClickListener
        cvGuides.setOnClickListener(v -> {
            intent = new Intent(OfflineDashboardActivity.this, GuidesActivity.class);
            startActivity(intent);
        });

        // Emergency Hotlines OnClickListener
        cvHotlines.setOnClickListener(v -> {
            intent = new Intent(OfflineDashboardActivity.this, HotlinesActivity.class);
            startActivity(intent);
        });

        // Bluetooth Chat OnClickListener
        cvBluetoothChat.setOnClickListener(v -> {
            if (EasyPermissions.hasPermissions(this, REQUIRED_PERMISSIONS)) {
                bluetoothChat();
            } else {
                EasyPermissions.requestPermissions(this, "This feature requires bluetooth permission in order to work!", REQUEST_CODE_REQUIRED_PERMISSIONS, REQUIRED_PERMISSIONS);
            }
        });

        // Access Online Features OnClickListener
        cvOnlineFeatures.setOnClickListener(v -> {
            intent = new Intent(OfflineDashboardActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        bluetoothChat();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms)   {
    }

    // Initialize Bluetooth connectivity
    private void bluetoothChat() {
        String name = android.os.Build.MODEL;
        ArrayList<Character> supportedCharacters = BluetoothTools.getSupportedUTFCharacters(this);

        boolean equals = true;

        for (int i = 0; i < name.length() && equals; i++) {
            if (!supportedCharacters.contains(name.charAt(i))) {
                equals = false;
            }
        }

        if (!equals || name.length() > 18) {
            name = "User " + new Random().nextInt(21);
        }

        bluetoothCommunicator = new BluetoothCommunicator(this, name, BluetoothCommunicator.STRATEGY_P2P_WITH_RECONNECTION);

        intent = new Intent(OfflineDashboardActivity.this, BluetoothChatActivity.class);
        startActivity(intent);
    }

    // Get Bluetooth communicator
    public static BluetoothCommunicator getBluetoothCommunicator() {
        return bluetoothCommunicator;
    }
}