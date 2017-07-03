package com.seeker.tony.myapplication;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.meiyou.annotation.Test;
import com.meiyou.jet.annotation.JFindView;
import com.meiyou.jet.annotation.JFindViewOnClick;
import com.meiyou.jet.annotation.JLoggable;
import com.meiyou.jet.annotation.JOnClick;
import com.meiyou.jet.grant.PermissionsManager;
import com.meiyou.jet.grant.PermissionsResultAction;
import com.meiyou.jet.process.Jet;
import com.meiyou.jet.proxy.JetProxy;
import com.seeker.tony.myapplication.model.TestBean;
import com.seeker.tony.myapplication.proxy.ITest;

//@JPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    @JFindView(R.id.btn_findView)
    Button btnHello;

    @JFindViewOnClick(R.id.btn_findview_onclick)
    Button btnWorld;

    String temp = "";

    @JFindViewOnClick(R.id.btn_implement)
    Button btn_implement;

    @JFindViewOnClick(R.id.btn_log)
    Button btn_log;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Jet.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String string = intent.getStringExtra("string");
        int intentIntExtra = intent.getIntExtra("int", 0);
        boolean aBoolean = intent.getBooleanExtra("boolean", true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        btnHello.setOnClickListener(this);
//        btnWorld.setOnClickListener(this);

        btnHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "btn_findView: Success", Toast.LENGTH_SHORT)
                     .show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Test("test")
    public void test() {

    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_findview_onclick:
                Toast.makeText(MainActivity.this, "btn_findview_onclick: Success", Toast.LENGTH_SHORT)
                     .show();
                Toast.makeText(this, "To Intent", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, IntentActivity.class);
                intent.putExtra("stringExtra", "stringExtra");
                intent.putExtra("intExtra", 111);
                intent.putExtra("longExtra", (long) 22222222);
                intent.putExtra("booleanExtra", true);
                TestBean testBean = new TestBean();
                testBean.key = "this is key";
                testBean.keyInt = 999;
                intent.putExtra("serializable", testBean);
                Bundle bundle = new Bundle();
                bundle.putString("key", "keyBundle");
                intent.putExtra("bundle", bundle);
                String[] StringArray = {"Hello", "world"};
                intent.putExtra("stringArrays", StringArray);
                this.startActivity(intent);
                break;
            case R.id.btn_implement:
                try {
                    ITest iTest = JetProxy.getInstance().create(ITest.class);
                    iTest.test();

                    Toast.makeText(MainActivity.this, "btn_implement: " + iTest.getValue(), Toast.LENGTH_SHORT)
                         .show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_log:
                showPermission();
//                testLog(10);
//                testAOP();
                break;
        }

    }

    @JLoggable
    public int testLog(int k) {
        int i = k + 100;
        int j = i++;
        return j;
    }

    public void testAOP() {
        Log.d("xuyisheng", "testAOP");
    }

    @JOnClick(R.id.btn_jonclick)
    public void onViewClick(int i,View v,String arr,String[] dd,int[] dds) {
        Toast.makeText(this, "btn_Jonclick: success", Toast.LENGTH_SHORT).show();
    }


    private void showPermission() {
//        Manifest.permission.CAMERA
        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
        PermissionsManager.getInstance()
                          .requestPermissionsIfNecessaryForResult(this, permission, new PermissionsResultAction() {
                              @Override
                              public void onGranted() {
                                  Toast.makeText(MainActivity.this, "onGrant Success", Toast.LENGTH_SHORT)
                                       .show();
                              }

                              @Override
                              public void onDenied(String permission) {
                                  Toast.makeText(MainActivity.this, "onDenied Success", Toast.LENGTH_SHORT)
                                       .show();
                              }
                          });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }


}
