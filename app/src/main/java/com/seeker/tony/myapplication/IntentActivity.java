package com.seeker.tony.myapplication;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.meiyou.jet.annotation.JIntent;
import com.meiyou.jet.annotation.JPermission;
import com.meiyou.jet.grant.PermissionsManager;
import com.meiyou.jet.process.Jet;

import java.io.Serializable;

@JPermission(value = Manifest.permission.ACCESS_CHECKIN_PROPERTIES, all = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
public class IntentActivity extends AppCompatActivity {

    private static final String TAG = "IntentActivity";

    @JIntent("stringExtra")
    String stringExtra;
    @JIntent("intExtra")
    int intExtra;
    @JIntent("longExtra")
    long longExtra;
    @JIntent("booleanExtra")
    boolean booleanExtra;
    @JIntent("serializable")
    Serializable serializable;
    @JIntent("bundle")
    Bundle bundle;
    @JIntent("stringArrays")
    String[] stringArrays;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
        Jet.bind(this);
//        Intent intent = getIntent();
//        String stringExtra = intent.getStringExtra("stringExtra");
//        int intExtra = intent.getIntExtra("intExtra", 0);
//        long longExtra = intent.getLongExtra("longExtra", 0);
//        boolean booleanExtra = intent.getBooleanExtra("booleanExtra", false);
//        Serializable serializable = intent.getSerializableExtra("serializable");
//        Bundle bundle = intent.getBundleExtra("bundle");
//        String[] stringArrays = intent.getStringArrayExtra("stringArray");

        Log.e(TAG, String.format("stringExtra: %s; intExtra: %s,longExtra: %s", stringExtra, intExtra, longExtra));
        Log.e(TAG, String.format("booleanExtra: %s, serializable: %s; bundle: %s; stringArrays: %s", booleanExtra, serializable, bundle, stringArrays));
//        View content = findViewById(R.id.fl_content);
        BlankFragment blankFragment = BlankFragment.newInstance(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_content, blankFragment);
        transaction.commit();
    }

//    @JOnClick(R.id.btn_permission)
//    private void showPermission() {
////        Manifest.permission.CAMERA
//        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        PermissionsManager.getInstance()
//                          .requestPermissionsIfNecessaryForResult(this, permission, new PermissionsResultAction() {
//                              @Override
//                              public void onGranted() {
//                                  Toast.makeText(IntentActivity.this, "onGrant Success", Toast.LENGTH_SHORT)
//                                       .show();
//                              }
//
//                              @Override
//                              public void onDenied(String permission) {
//                                  Toast.makeText(IntentActivity.this, "onDenied Success", Toast.LENGTH_SHORT)
//                                       .show();
//                              }
//                          });
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}
