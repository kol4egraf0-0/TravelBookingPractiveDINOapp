package com.example.travelbookingapp.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.travelbookingapp.Domain.ItemDomain;
import com.example.travelbookingapp.R;
import com.example.travelbookingapp.databinding.ActivityDetailBinding;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private ItemDomain object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        binding.titleTxt.setText(object.getTitle());
        binding.priceTxt.setText("₽"+object.getPrice());
        binding.backBtn.setOnClickListener(v -> {
            finish();});
        binding.bedTxt.setText(""+object.getBed());
        binding.durationTxt.setText(object.getDuration());
        binding.descritionTxt.setText(object.getDescription());
        binding.distanceTxt.setText(object.getDistance());
        binding.addressTxt.setText(object.getAddress());
        binding.ratingTxt.setText(object.getScore()+"Рейтинг");
        binding.ratingBar.setRating((float) object.getScore());

        Glide.with(DetailActivity.this)
                .load(object.getPic())
                .into(binding.pic);

        binding.addToCartBtn.setOnClickListener(v -> {
            Intent intent =  new Intent(DetailActivity.this, TicketActivity.class);
            intent.putExtra("object", object);

            Bitmap qrBitmap = generateQRCode(object.getTitle());


            Uri qrUri = saveBitmapToCache(qrBitmap);
            intent.putExtra("qrUri", qrUri.toString());


            startActivity(intent);
        });

    }
    private Bitmap generateQRCode(String data) {
        int size = 500;
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, size, size);
            BarcodeEncoder encoder = new BarcodeEncoder();
            return encoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Метод для сохранения Bitmap во временный файл
    private Uri saveBitmapToCache(Bitmap bitmap) {
        File cachePath = new File(getCacheDir(), "images");
        cachePath.mkdirs();
        File file = new File(cachePath, "qr_code.png");

        try (FileOutputStream stream = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
    }

    private void getIntentExtra() {
        object = (ItemDomain) getIntent().getSerializableExtra("object");
    }
}