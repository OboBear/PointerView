package com.example.obo.pointer;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    PointerView mPointerView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPointerView = (PointerView) findViewById(R.id.point_view);

        new Thread() {

            int degreen = 15;

            @Override
            public void run() {
                super.run();

                while (true) {

                    degreen += 2;

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mPointerView.setDegreen(degreen % 120);
                        }
                    });
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
