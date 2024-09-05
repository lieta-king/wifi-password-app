package com.example.wifipasswordapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TARGET_SSID = "Your_SSID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            showWifiPassword(textView);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            TextView textView = findViewById(R.id.textView);
            showWifiPassword(textView);
        }
    }

    private void showWifiPassword(TextView textView) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> wifiConfigurations = wifiManager.getConfiguredNetworks();

        for (WifiConfiguration config : wifiConfigurations) {
            if (config.SSID.equals("\"" + TARGET_SSID + "\"")) {
                String password = config.preSharedKey;
                textView.setText("Wi-Fi Password: " + password);
                break;
            }
        }
    }
}
