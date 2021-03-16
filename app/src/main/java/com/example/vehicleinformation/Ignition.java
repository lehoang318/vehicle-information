package com.example.vehicleinformation;

import android.graphics.Color;
import android.util.Log;
import android.widget.Button;

public class Ignition implements IEntity {
    public static final String TAG = "Ignition";
    public static final int INVALID = -1;
    public static final int OFF = 1;
    public static final int ON = 4;
    private int state;
    private boolean synced;
    Button mButton;

    public Ignition (Button button) {
        state = OFF;
        synced = false;
        mButton = button;
        refreshUI();
    }

    public void updateValue(Object value) {
        try {
            int newState = (int) value;
            if (state != newState) {
                if (ON == newState) {
                    state = ON;
                } else {
                    state = OFF;
                }
                synced = false;
            }
        } catch (ClassCastException exc) {
            Log.e(TAG, "Invalid Object Type!", exc);
        }
    }

    public void refreshUI() {
        if (!synced) {
            if (ON == state) {
                mButton.setBackgroundColor(Color.rgb(0x3C, 0xB0, 0x43));
            } else {
                mButton.setBackgroundColor(Color.rgb(0x98, 0x98, 0x98));
            }
            synced = true;
        }
    }
}
