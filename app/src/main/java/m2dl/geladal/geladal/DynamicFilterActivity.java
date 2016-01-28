package m2dl.geladal.geladal;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import m2dl.geladal.geladal.filters.IFilter;
import m2dl.geladal.geladal.filters.IFilterConsumer;
import m2dl.geladal.geladal.filters.impl.BlackAndWhiteFilter;
import m2dl.geladal.geladal.handlers.IMovementDetected;
import m2dl.geladal.geladal.handlers.IShakeDetected;
import m2dl.geladal.geladal.handlers.MovementDetectionListener;
import m2dl.geladal.geladal.handlers.ShakeDetectionListener;
import m2dl.geladal.geladal.services.MessageService;

public class DynamicFilterActivity extends AppCompatActivity implements IShakeDetected, IMovementDetected, IFilterConsumer {

    private int shakes = 0;
    private ShakeDetectionListener shakeDetectionListener;
    private MovementDetectionListener movementDetectionListener;
    private List<IFilter> filters = new ArrayList<>();
    private int currentFilterPos = 0;

    Bitmap resultImage;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_filter);
        resultImage = MessageService.image;

        imageView = (ImageView) findViewById(R.id.basicImage);
        imageView.setImageBitmap(resultImage);

        // Shake detection
        shakeDetectionListener = new ShakeDetectionListener(this);
        SensorManager sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMgr.registerListener(shakeDetectionListener, sensorMgr
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        // Gyroscope detection
        movementDetectionListener = new MovementDetectionListener(this);
        sensorMgr.registerListener(movementDetectionListener, sensorMgr
                .getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);

        // Register filter
        filters.add(new BlackAndWhiteFilter());

        filters.get(currentFilterPos).filter(this, resultImage, 0, 0, 0);
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
        currentFilterPos++;
        if (filters.size() > 0) {
            currentFilterPos = currentFilterPos % filters.size();
        }
    }

    @Override
    public void moved(float x, float y, float z) {
        if (filters.size() > 0) {
            filters.get(currentFilterPos).filter(this, resultImage, x, y, z);
        }
    }

    @Override
    public void setImageFiltered(final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
                imageView.invalidate();
            }
        });
    }

    @Override
    public Context getContext() {
        return this.getBaseContext();
    }
}
