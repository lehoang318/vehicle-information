package com.example.vehicleinformation;

import android.graphics.Color;
import android.util.Log;
import android.widget.Button;

public class Transition implements IEntity {
    public static final String TAG = "Transition";
    public static final int INVALID = -1;
    public static final int N = 0;
    public static final int D = 1;
    private int state;
    private boolean synced;
    Button mButtonN;
    Button mButtonD;
    Button mButtonP;
    Button mButtonR;

    public Transition (Button buttonN, Button buttonD, Button buttonP, Button buttonR) {
        state = N;
        synced = false;
        mButtonN = buttonN;
        mButtonD = buttonD;
        mButtonP = buttonP;
        mButtonR = buttonR;

        mButtonN.setBackgroundColor(Color.rgb(0x98, 0x98, 0x98));
        mButtonD.setBackgroundColor(Color.rgb(0x98, 0x98, 0x98));
        mButtonP.setBackgroundColor(Color.rgb(0x98, 0x98, 0x98));
        mButtonR.setBackgroundColor(Color.rgb(0x98, 0x98, 0x98));

        refreshUI();
    }

    public void updateValue(Object value) {
        try {
            int newState = (int) value;
            if (state != newState) {
                if (D == newState) {
                    state = D;
                } else {
                    state = N;
                }
                synced = false;
            }
        } catch (ClassCastException exc) {
            Log.e(TAG, "Invalid Object Type!", exc);
        }
    }

    public void refreshUI() {
        if (!synced) {
            if (D == state) {
                mButtonN.setBackgroundColor(Color.rgb(0x98, 0x98, 0x98));
                mButtonD.setBackgroundColor(Color.rgb(0x3C, 0xB0, 0x43));
            } else {
                mButtonN.setBackgroundColor(Color.rgb(0x3C, 0xB0, 0x43));
                mButtonD.setBackgroundColor(Color.rgb(0x98, 0x98, 0x98));
            }
            synced = true;
        }
    }
}
