package com.example.journey_journal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journey_journal.db.DbHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

public class DescriptionPage extends AppCompatActivity {
    // for debugging
    public static final String TAG = DescriptionPage.class.getSimpleName();
    public static final String ID = "Id";

    public static final int PICK_IMAGE = 100;

    Bundle intentData;
    DbHelper dbHelper;
    TextView txtTitle, txtDesc;
    ImageView imShowImage, imUploadImage, imAddLocation;
    Button btnSumbit;
    Uri imageUri;

    public static Intent getIntentToCreate(Context context) {
        return new Intent(context, DescriptionPage.class);
    }

    public static Intent getIntentToEdit(Context context, int id) {
        Intent intent = new Intent(context, DescriptionPage.class);
        intent.putExtra(ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_page);

        dbHelper = new DbHelper(this);
        intentData = getIntent().getExtras();
        txtTitle = findViewById(R.id.txtTitle);
        txtDesc = findViewById(R.id.txtDesc);
        imShowImage = findViewById(R.id.showImage);
        imUploadImage = findViewById(R.id.imUploadImage);
        imAddLocation = findViewById(R.id.imAddLocation);
        btnSumbit = findViewById(R.id.btn_submit);

        imUploadImage.setOnClickListener(this::promptToChooseImage);
        imAddLocation.setOnClickListener(this::promptToAddLocation);
        btnSumbit.setOnClickListener(this::onSubmitButtonClick);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if(intentData != null && !intentData.isEmpty() && intentData.getInt(ID) > 0) {
//            populateData();
//            getIntent().removeExtra(ID);
//        }
    }

    private void populateData() {
        int id = getIntent().getExtras().getInt(ID);
        Log.d(TAG, "ID="+id);
        ModelClass journey = null;
        Cursor cursor = dbHelper.getElementById(id);
        if (cursor != null) {
            cursor.moveToFirst();
            journey = new ModelClass(
                    id,
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getBlob(4)
            );
        }
        if (journey != null) {
            txtTitle.setText(journey.getJTitle());
            // txtLocation.setText(journey.getJLocation());
            txtDesc.setText(journey.getJDis());
            byte[] imgBlob = journey.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBlob, 0, imgBlob.length);
            imShowImage.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getApplicationContext(), "Could not load data",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void onSubmitButtonClick(View view) {
        String title = txtTitle.getText().toString();
        Log.d(TAG, "Title="+title);
        String desc = txtDesc.getText().toString();
        Log.d(TAG, "Description="+desc);
        String location = "Hard Coded Location";
        Log.d(TAG, "Location="+location);
        byte[] imgBlob = imageViewToByte(imShowImage);
        Log.d(TAG, "Image Blob="+ Arrays.toString(imgBlob));
        if (validateData(title, desc, location, imgBlob)){
            String action = getIntent().getAction();
//            if (action!= null && action.equals(ACTION_EDIT)) {
//                saveEditData(title, desc, location, imgBlob);
//            } else {
                saveNewData(title, desc, location, imgBlob);
//            }
        }
    }

    private void saveNewData(String title, String desc, String location, byte[] imageBlob) {
        if (dbHelper.insert(title, desc, location, imageBlob)) {
            Toast.makeText(getApplicationContext(), "Saved Successfully",
                    Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(getApplicationContext(), Home.class));
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Failed to save", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveEditData(String title, String desc, String location, byte[] imageBlob) {
        int id = getIntent().getExtras().getInt(ID);
        if (dbHelper.update(
                id,
                title,
                desc,
                location,
                imageBlob
        )) {
            Toast.makeText(getApplicationContext(), "Updated Successfully",
                    Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(), Home.class));
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Failed to save", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateData(String title, String desc, String location, byte[] imageBlob) {
        if (title.isEmpty()) {
            txtTitle.setError("Title is required!");
            txtTitle.requestFocus();
            return false;
        }
        if (desc.isEmpty()) {
            txtDesc.setError("Title is required!");
            txtDesc.requestFocus();
            return false;
        }
        if (location.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please choose a location",
                    Toast.LENGTH_SHORT).show();
        }
        if (imageBlob.length == 0) {
            Toast.makeText(getApplicationContext(), "Please select an image with valid format!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @SuppressLint("IntentReset")
    private void promptToChooseImage(View view) {
        // open gallery to select image
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    private void promptToAddLocation(View view) {
        // open map to select location
        Toast.makeText(getApplicationContext(), "Coming Soon",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imShowImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//            String mimeType = getContentResolver().getType(imageUri);
//            Log.d(TAG, "Image format="+mimeType);
//            imShowImage.setTag(mimeType);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Could not pick image!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        String mimeType = image.getTag().toString();
//        if (mimeType.equals("image/PNG") || mimeType.equals("image/png")) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        } else if (
//                mimeType.equals("image/JPG") ||
//                        mimeType.equals("image/jpg") ||
//                        mimeType.equals("image/JPEG") ||
//                        mimeType.equals("image/jpeg")
//        ) {
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        }
        return stream.toByteArray();
    }
}