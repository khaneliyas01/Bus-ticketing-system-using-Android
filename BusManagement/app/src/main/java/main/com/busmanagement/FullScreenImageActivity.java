package main.com.busmanagement;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FullScreenImageActivity extends AppCompatActivity {

    LinearLayout ll;
    Bitmap bmp;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        bmp = (Bitmap)this.getIntent().getExtras().get("bitmap");
        ll = (LinearLayout) findViewById(R.id.fullScreenLayout);
        img = (ImageView) findViewById(R.id.fullScreenImage);
        img.setImageBitmap(bmp);

    }
}
