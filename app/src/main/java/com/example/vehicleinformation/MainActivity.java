package com.example.vehicleinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private Handler mHandler;
    private Button bIgn;
    private Button bTransN;
    private Button bTransD;
    private Button bTransP;
    private Button bTransR;
    private Button bBrake;
    private Button bAccel;
    private EditText etSpeed;
    private TextView tvSpeedUnit;

    private ArrayList<IEntity> entities;
    private Ignition ign;
    private Transition trans;
    private Pedal brake;
    private Pedal accel;
    private Speed speed;

    class Handler extends android.os.Handler {
        public static final int MSG_UPDATE_UI = 0;

        public Handler (Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage (Message message) {
            super.handleMessage(message);

            switch (message.what) {
                case MSG_UPDATE_UI:
                    for (IEntity entity : entities){
                        entity.refreshUI();
                    }
                    break;
                default:
                    Log.e(TAG, String.format("Unknown message (%d)!", message.what));
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler (this.getMainLooper());

        bIgn = findViewById(R.id.buttonIgn);
        bTransN = findViewById(R.id.buttonTransN);
        bTransD = findViewById(R.id.buttonTransD);
        bTransP = findViewById(R.id.buttonTransP);
        bTransR = findViewById(R.id.buttonTransR);
        bBrake = findViewById(R.id.buttonBrake);
        bAccel = findViewById(R.id.buttonAccel);
        etSpeed = findViewById(R.id.editTextSpeed);
        tvSpeedUnit = findViewById(R.id.textViewSpeedUnit);

        entities = new ArrayList<IEntity>();
        ign = new Ignition(bIgn);
        entities.add(ign);
        trans = new Transition(bTransN, bTransD, bTransP, bTransR);
        entities.add(trans);
        brake = new Pedal("Brake", bBrake);
        entities.add(brake);
        accel = new Pedal("Accelerator", bAccel);
        entities.add(accel);
        speed = new Speed(etSpeed, tvSpeedUnit);
        entities.add(speed);

        Thread testThread = new Thread(new TestThread(mHandler));
        testThread.start();
    }

    public class TestThread implements Runnable {
        public static final String TAG = "TestThread";
        private android.os.Handler mHander;
        public TestThread (android.os.Handler handler) {
            mHander = handler;
        }

        public void run () {
            try {
                Message msg;
                Thread.sleep(5000);
                ign.updateValue(Integer.valueOf(Ignition.ON));
                msg = mHander.obtainMessage(Handler.MSG_UPDATE_UI);
                mHander.sendMessage(msg);
                Thread.sleep(2000);
                trans.updateValue(Integer.valueOf(Transition.D));
                msg = mHander.obtainMessage(Handler.MSG_UPDATE_UI);
                mHander.sendMessage(msg);
                Thread.sleep(5000);
                speed.updateValue(Float.valueOf(10));
                accel.updateValue(Integer.valueOf(Pedal.PRESSED));
                msg = mHander.obtainMessage(Handler.MSG_UPDATE_UI);
                mHander.sendMessage(msg);
                Thread.sleep(5000);
                speed.updateValue(Float.valueOf(25));
                accel.updateValue(Integer.valueOf(Pedal.RELEASED));
                brake.updateValue(Integer.valueOf(Pedal.PRESSED));
                msg = mHander.obtainMessage(Handler.MSG_UPDATE_UI);
                mHander.sendMessage(msg);
                Thread.sleep(3000);
                speed.updateValue(Float.valueOf(0));
                trans.updateValue(Integer.valueOf(Transition.N));
                msg = mHander.obtainMessage(Handler.MSG_UPDATE_UI);
                mHander.sendMessage(msg);
                Thread.sleep(1000);
                brake.updateValue(Integer.valueOf(Pedal.RELEASED));
                ign.updateValue(Integer.valueOf(Ignition.OFF));
                msg = mHander.obtainMessage(Handler.MSG_UPDATE_UI);
                mHander.sendMessage(msg);
            } catch (InterruptedException exc) {
                Log.e(TAG, "", exc);
            }
        }
    }
}