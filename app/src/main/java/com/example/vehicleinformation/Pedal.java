package com.example.vehicleinformation;

import android.graphics.Color;
import android.util.Log;
import android.widget.Button;

public class Pedal implements IEntity {
    public final String TAG;
    public static final int INVALID = -1;
    public static final int RELEASED = 0;
    public static final int PRESSED = 1;
    private int state;
    private boolean synced;
    Button mButton;

    public Pedal(String name, Button button) {
        TAG = name;
        state = RELEASED;
        synced = false;
        mButton = button;
        refreshUI();
    }

    public void updateValue(Object value) {
        try {
            int newState = (int) value;
            if (state != newState) {
                if (PRESSED == newState) {
                    state = PRESSED;
                } else {
                    state = RELEASED;
                }
                synced = false;
            }
        } catch (ClassCastException exc) {
            Log.e(TAG, "Invalid Object Type!", exc);
        }
    }

    public void refreshUI() {
        if (!synced) {
            if (PRESSED == state) {
                mButton.setBackgroundColor(Color.rgb(0xE5, 0x67, 0x17));
            } else {
                mButton.setBackgroundColor(Color.rgb(0x98, 0x98, 0x98));
            }
            synced = true;
        }
    }
}
