package com.hanseltritama.threading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button buttonStartThread;

    // Handler is handling MessageQueue / a queue of tasks
    private Handler mainHandler = new Handler();// need this to access MainThread

    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStartThread = findViewById(R.id.buttonStartThread);
    }

    public void startThread(View view) {
        stopThread = false;
//        for(int i = 0; i < 10; i++) {
//            Log.d(TAG, "startThread: " + i);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }

        // call ExampleThread
//        ExampleThread exampleThread = new ExampleThread(10);
//        exampleThread.start();

        // call ExampleRunnable
        ExampleRunnable runnable = new ExampleRunnable(10);
        new Thread(runnable).start();


    }

    public void stopThread(View view) {
        stopThread = true;
    }

    // Thread
    class ExampleThread extends Thread {

        int seconds;

        ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for(int i = 0; i < seconds; i++) {
                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    // Runnable
    class ExampleRunnable implements Runnable {

        int seconds;

        ExampleRunnable(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {

            for(int i = 0; i < seconds; i++) {

                if(stopThread) return;
                if(i == 5) {
                    // it's failing because textview, button, etc should be executed on MainThread
                    //buttonStartThread.setText("50%");

//                    mainHandler.post(new Runnable() { // sending task to mainThread
//                        @Override
//                        public void run() {
//                            buttonStartThread.setText("50%");
//                        }
//                    });


                    // 2nd Approach
                    // Create Handler here & add getMainLooper to access MainThread looper
                    /* Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });*/

                    // 3rd Approach
                    /* buttonStartThread.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    }); */

                    // 4th Approach
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });
                }

                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
