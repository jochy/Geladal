package m2dl.geladal.geladal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import m2dl.geladal.geladal.services.MessageService;
import m2dl.geladal.geladal.services.PhotoUtils;
import m2dl.geladal.geladal.utils.ExifUtils;

public class MainActivity extends AppCompatActivity {

    File photo;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intent = new Intent(this, DynamicFilterActivity.class);
        //startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void takePicture(View view) {
        // Création de l'intent de type ACTION_IMAGE_CAPTURE
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
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
                    // Rotate it
                    bitmap = ExifUtils.rotateBitmap(photo.getAbsolutePath(), bitmap);

                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()));

                    bitmap.recycle();
                    decoded = PhotoUtils.getResizedBitmap(decoded, decoded.getWidth() / 2);

                    Intent intent = new Intent(this, DynamicFilterActivity.class);
                    MessageService.image = decoded;
                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }


}
