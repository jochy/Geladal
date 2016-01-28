package m2dl.geladal.geladal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import m2dl.geladal.geladal.filters.impl.MozFilter;
import m2dl.geladal.geladal.utilstmp.ExifUtils;
import m2dl.geladal.geladal.filters.IFilter;
import m2dl.geladal.geladal.filters.IFilterConsumer;
import m2dl.geladal.geladal.filters.impl.BlackAndWhiteFilter;
import m2dl.geladal.geladal.filters.impl.BlurFilter;
import m2dl.geladal.geladal.filters.impl.GridFilter;
import m2dl.geladal.geladal.filters.impl.ColorFilter;
import m2dl.geladal.geladal.filters.impl.LightFilter;
import m2dl.geladal.geladal.handlers.IMovementDetected;
import m2dl.geladal.geladal.handlers.IShakeDetected;
import m2dl.geladal.geladal.handlers.MovementDetectionListener;
import m2dl.geladal.geladal.handlers.ShakeDetectionListener;
import m2dl.geladal.geladal.services.MessageService;
import m2dl.geladal.geladal.services.PhotoUtils;

public class DynamicFilterActivity extends AppCompatActivity implements IShakeDetected, IMovementDetected, IFilterConsumer {

    private int shakes = 0;
    private ShakeDetectionListener shakeDetectionListener;
    private MovementDetectionListener movementDetectionListener;
    private List<IFilter> filters = new ArrayList<>();
    private int currentFilterPos = 0;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    Bitmap resultImage;
    ImageView imageView;
    File photo ;


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
        filters.add(new BlurFilter());
        filters.add(new GridFilter());
        filters.add(new ColorFilter());
        filters.add(new MozFilter());
        filters.add(new LightFilter());
        moved(0, 0, 0);
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
            ((ImageView) findViewById(R.id.imgFilter)).setImageResource(filters.get(currentFilterPos).getIcon());
            ((TextView) findViewById(R.id.tvFilter)).setText(filters.get(currentFilterPos).getName());
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


    public void changePicture(View view)
    {
        // Création de l'intent de type ACTION_IMAGE_CAPTURE
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Random r = new Random();
        int low = 10;
        int high = 100000;
        int result = r.nextInt(high-low)+low;
        photo = new File(Environment.getExternalStorageDirectory(), "Pic"+result+".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                try {
                    Bitmap bitmap = MediaStore.Images.Media
                            .getBitmap(getContentResolver(), Uri.fromFile(photo));
                    bitmap = ExifUtils.rotateBitmap(photo.getAbsolutePath(), bitmap);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                    Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));
                    decoded = PhotoUtils.getResizedBitmap(decoded, decoded.getWidth());
                    MessageService.image = decoded;
                    imageView.setImageBitmap(decoded);
                    imageView.invalidate();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


}
