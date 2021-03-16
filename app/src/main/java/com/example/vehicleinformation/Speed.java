package com.example.vehicleinformation;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class Speed implements IEntity {
    public static final String TAG = "Vehicle Speed";
    public static final float MAX = (float)1000;
    public static final float MIN = -(float)0.0;
    private boolean synced;
    private float mValue;
    private EditText mEtSpeed;
    private TextView mTvUnit;

    public  Speed (EditText etSpeed, TextView tvUnit) {
        mValue = 0;
        mEtSpeed = etSpeed;
        mTvUnit = tvUnit;
    }

    private boolean isValid (float value) {
        return ((MIN <= value) && (MAX >= value));
    }

    public void updateValue(Object value) {
        try {
            float newValue = (float) value;
            if (isValid(newValue) && (mValue != newValue)) {
                mValue = newValue;
                synced = false;
            }
        } catch (ClassCastException exc) {
            Log.e(TAG, "Invalid Object Type!", exc);
        }
    }

    public void refreshUI() {
        mEtSpeed.setText(String.format("%06.2f", mps2kmh(mValue)));
    }

    public static float mps2kmh(float value) {
        return (value * 3600 / 1000);
    }
}
