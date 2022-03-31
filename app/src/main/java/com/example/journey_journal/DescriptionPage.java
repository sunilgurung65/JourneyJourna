package com.example.journey_journal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.journey_journal.db.DbHelper;
import com.google.android.gms.common.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;

public class DescriptionPage extends AppCompatActivity {
    public static final String ID = "Id";
    public static final String TITLE = "Title";
    public static final String DESC = "Desc";
    public static final String LOCATION = "Location";
    public static final String IMAGE = "Image";
    private static final int PICK_IMAGE = 100;

    DbHelper dbHelper;
    TextView txtTitle, txtDesc;
    ImageView imShowImage, imUploadImage, imAddLocation;
    Button btnSumbit;
    Uri imageUri;

    public static Intent getIntent(Context context, ModelClass journal){
        if (journal != null) {
            Intent intent = new Intent(context, DescriptionPage.class);
            intent.putExtra(ID, journal.getId());
            intent.putExtra(TITLE, journal.getJTitle());
            intent.putExtra(DESC, journal.getJDis());
            intent.putExtra(LOCATION, journal.getJLocation());
            intent.putExtra(IMAGE, journal.getImage());
            return intent;
        }
        return new Intent(context, DescriptionPage.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_page);

        dbHelper = new DbHelper(this);

        txtTitle = findViewById(R.id.txtTitle);
        txtDesc = findViewById(R.id.txtDesc);
        imShowImage = findViewById(R.id.showImage);
        imUploadImage = findViewById(R.id.imUploadImage);
        imAddLocation = findViewById(R.id.imAddLocation);
        btnSumbit = findViewById(R.id.btn_submit);

        imUploadImage.setOnClickListener(this::promptToChooseImage);
        imAddLocation.setOnClickListener(this::promptToAddLocation);
        btnSumbit.setOnClickListener(this::saveData);
    }

    private void saveData(View view) {
        String title = txtTitle.getText().toString().trim();
        String desc = txtDesc.getText().toString().trim();
        byte[] imageBlob =imageViewToByte(imShowImage);

        if (title.isEmpty()){
            txtTitle.setError("Title is required");
            txtTitle.requestFocus();
        }
        else if(desc.isEmpty()) {
            txtDesc.setError("Title is required");
            txtDesc.requestFocus();
        }
        else if (imageBlob.length == 0){
            Toast.makeText(getApplicationContext(), "Please select an image",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            if(dbHelper.insert(title, desc, "Hard Coded Location", imageBlob)){
                Toast.makeText(getApplicationContext(), "Saved Successfully",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
            else{
                Toast.makeText(getApplicationContext(), "Failed to save", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void promptToChooseImage(View view) {
        // open gallery to select image
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
    }

    private void promptToAddLocation(View view) {
        // open map to select location
        Toast.makeText(getApplicationContext(), "Coming Soon",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imShowImage.setImageURI(imageUri);
        } else {
            Toast.makeText(getApplicationContext(),
                    "You don't have permission to access file location!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static  byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

}