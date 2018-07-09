package com.jalotsav.shreegurudham;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.jalotsav.shreegurudham.common.AppConstants;

public class ActvtySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lo_actvty_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                int navgtnPosition = getIntent().getIntExtra(AppConstants.PUT_EXTRA_NVGTNVW_POSTN, AppConstants.NVGTNVW_HOME);
                startActivity(new Intent(ActvtySplash.this, ActvtyMain.class)
                        .putExtra(AppConstants.PUT_EXTRA_NVGTNVW_POSTN, navgtnPosition));
                finish();
            }
        }, 3000);
    }
}
