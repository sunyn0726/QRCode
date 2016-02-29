package com.tat.nan.qrcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText src;
    private ImageView rlt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        src = (EditText) findViewById(R.id.main_src);
        rlt = (ImageView) findViewById(R.id.main_rlt);
        findViewById(R.id.main_commit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.main_commit:
                String str = src.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    QRCodeWriter writer = new QRCodeWriter();

                    try {
                        HashMap<EncodeHintType, Object> hints = new HashMap<>();
                        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

                        BitMatrix matrix = writer.encode(str, BarcodeFormat.QR_CODE, 500, 500);
                        Bitmap bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.RGB_565);
                        for (int i = 0; i < 500; i++) {
                            for (int j = 0; j < 500; j++) {
                                bitmap.setPixel(i, j, matrix.get(i, j) ? Color.BLACK : Color.WHITE);
                            }
                        }

                        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                        Rect t = new Rect(200, 200, 300, 300);
                        Rect s = new Rect(0, 0, icon.getWidth(), icon.getHeight());
                        new Canvas(bitmap).drawBitmap(icon, s, t, null);
                        rlt.setImageBitmap(bitmap);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
}
