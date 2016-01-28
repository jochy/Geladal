package m2dl.geladal.geladal.handlers;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by Alexandre on 28/01/2016.
 */
public class ShakeDetectionListener implements SensorEventListener {

    private static final float SHAKE_THRESHOLD = 900;
    private static final float INTERVAL_UPDATE = 100;

    private IShakeDetected activity;

    private float x = 0;
    private float y = 0;
    private float z = 0;
    private float last_x = 0;
    private float last_y = 0;
    private float last_z = 0;
    private long lastUpdate = System.currentTimeMillis();
    private final Object lock = new Object();
    private long lastShake = System.currentTimeMillis();

    public ShakeDetectionListener(IShakeDetected activity) {
        this.activity = activity;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long curTime = System.currentTimeMillis();
        // only allow one update every X ms.
        if ((curTime - lastUpdate) > INTERVAL_UPDATE) {
            long diffTime = (curTime - lastUpdate);
            lastUpdate = curTime;

            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

            last_x = x;
            last_y = y;
            last_z = z;

            if (speed > SHAKE_THRESHOLD) {
                synchronized (lock){
                    curTime = System.currentTimeMillis();
                    if(curTime - lastShake > 700){
                        lastShake = curTime;
                    }
                }

                if(curTime == lastShake){
                    activity.shaked();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nothing
    }
}
