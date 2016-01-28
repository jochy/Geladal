package m2dl.geladal.geladal.handlers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by Alexandre on 28/01/2016.
 */
public class MovementDetectionListener implements SensorEventListener {

    private IMovementDetected activity;
    private long lastUpdate = System.currentTimeMillis();
    private static final int INTERVAL_UPDATE = 50;

    public MovementDetectionListener(IMovementDetected activity) {
        this.activity = activity;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long curTime = System.currentTimeMillis();
        if (curTime - lastUpdate > INTERVAL_UPDATE) {
            lastUpdate = curTime;

            activity.moved(event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
