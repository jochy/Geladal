package m2dl.geladal.geladal;

import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import m2dl.geladal.geladal.handlers.IMovementDetected;
import m2dl.geladal.geladal.handlers.IShakeDetected;
import m2dl.geladal.geladal.handlers.ShakeDetectionListener;
import m2dl.geladal.geladal.services.MessageService;

public class DynamicFilterActivity extends AppCompatActivity implements IShakeDetected, IMovementDetected {

    int shakes = 0;
    ShakeDetectionListener shakeDetectionListener;

    Bitmap resultImage ;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_filter);
        resultImage = MessageService.image;

        imageView = (ImageView)findViewById(R.id.basicImage);
        imageView.setImageBitmap(resultImage);

        // Shake detection
        shakeDetectionListener = new ShakeDetectionListener(this);
        SensorManager sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMgr.registerListener(shakeDetectionListener, sensorMgr
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);

        // Gyroscope detection

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dynamic_filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void shaked() {
        Toast.makeText(getBaseContext(), "SHAKED " + shakes++, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void moved(float x, float y, float z) {
        Toast.makeText(getBaseContext(), "Moved", Toast.LENGTH_SHORT).show();
    }
}
