package com.example.intecap.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.intecap.BuildConfig;
import com.example.intecap.database.DatabaseHelper;
import com.example.intecap.databinding.ActivityCreateBinding;
import com.example.intecap.models.Provider;
import com.example.intecap.utils.BitmapUtils;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1024;
    private ActivityCreateBinding binding;
    private String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBinding.inflate(getLayoutInflater());

        binding.buttonSave.setOnClickListener(v -> saveProvider());

        binding.imageButtonTakePicture.setOnClickListener(v -> {
            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePicture.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = savePicture();
                } catch (Exception e) {
                    System.out.println(e.getLocalizedMessage());
                }
                if (photoFile != null) {
                    Uri photoUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, photoFile);
                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        setContentView(binding.getRoot());
    }

    private void saveProvider() {
        if (checkInputs()) {
            String name = binding.editTextNameProvider.getText().toString();
            String address = binding.editTextAddressProvider.getText().toString();
            String product = binding.editTextProductProvider.getText().toString();

            DatabaseHelper.getInstance(this).insert(new Provider(name, address, product, photoPath));
            hideSoftKeyboard(binding.getRoot());
            Snackbar snackbar = Snackbar.make(binding.getRoot(), "Producto guardado exitosamente", Snackbar.LENGTH_SHORT);
            snackbar.setAction("Aceptar", v -> this.finish());
            snackbar.show();
        }
    }

    private File savePicture() {
        String pictureName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            File image = File.createTempFile(pictureName, ".jpg", storageDir);
            photoPath = image.getAbsolutePath();
            return image;
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        galleryAddPic();
        return null;
    }

    private void galleryAddPic() {
        Intent mediaScan = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(new File(photoPath));
        mediaScan.setData(contentUri);
        this.sendBroadcast(mediaScan);
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private boolean checkInputs(EditText... editTexts) {
        if (photoPath == null) return false;

        for (EditText item : editTexts) {
            if (item.getText().toString().isEmpty()) {
                item.setError("Campo obligatorio");
                return false;
            }
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            binding.constraintLayoutTakePicture.setVisibility(View.GONE);
            binding.imageView.setImageBitmap(BitmapUtils.getBitmapFromPath(photoPath));
            binding.imageView.setVisibility(View.VISIBLE);
        }

    }
}