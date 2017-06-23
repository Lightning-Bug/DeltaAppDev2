package comnitt.boston.huh;

/**
 * Created by HP on 21-Jun-17.
 */
import android.widget.EditText;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;
import android.provider.MediaStore;
import java.io.File;
import java.io.IOException;
import android.widget.Button;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.View;




public class NextActivity extends AppCompatActivity {


    File save;
    Uri uri;
    ImageView img;
    int num = 0;
    int mark=-1;
    boolean setcap=false,setim=false;
    Bitmap bm;
    EditText cap;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_activity);


        cap = (EditText) findViewById(R.id.cap);
        img = (ImageView) findViewById(R.id.image);
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            num = extras.getInt("num");}


    public void selectImage(View view)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.setTitle("Alert Dialog View");
        Button btnExit = (Button) dialog.findViewById(R.id.btnExit);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnChoosePath)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //activeGallery();
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 1);

                        dialog.dismiss();
                    }
                });
        dialog.findViewById(R.id.btnTakePhoto)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // activeTakePhoto();
                        
                        
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        String name = "picture_testing" + String.valueOf(num) + ".jpg";
                        save = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), name);
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, 0);
                        dialog.dismiss();
                    }
                });

        // show dialog on screen
        dialog.show();


    }

    public void ToAdd(View v) {
        if (cap.getText().toString().length()==0) setcap = false;
        else setcap = true;

        if (setim==false || setcap==false)
            Toast.makeText(this,"Incomplete details",Toast.LENGTH_LONG).show();

        else
        {
            num++;
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("bitmap",bm);
            intent.putExtra("num",num);
            intent.putExtra("mark",mark);
            intent.putExtra("cap",cap.getText().toString());
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            switch (resultCode) {
                case Activity.RESULT_OK:

                    if (data != null) {
                        bm = (Bitmap) data.getExtras().get("data");
                        uri = data.getData();
                        setim=true;
                        bm = Bitmap.createScaledBitmap(bm, 256, 256, true);
                        img.setImageBitmap(bm);
                        mark = 0;
                        setim = true;
                    }
                    //else
                    //  Toast.makeText(this, "Not Saved", Toast.LENGTH_LONG).show();

                    break;

                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "Unfortunately Cancelled", Toast.LENGTH_LONG).show();

                    break;
            }


        } else if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            uri = data.getData();
            //iv.setImageURI(imageUri);
            try {
                bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                setim = false;
                Intent crop = new Intent("com.android.camera.action.CROP");

                crop.setDataAndType(uri, "image/*");

                crop.putExtra("crop", "true");
                crop.putExtra("outputX", 300);
                crop.putExtra("outputY", 300);
                crop.putExtra("aspectX", 1);
                crop.putExtra("aspectY", 1);
                crop.putExtra("scaleUpIfNeeded", true);
                crop.putExtra("return-data", true);

                startActivityForResult(crop, 3);

                if (!setim)
                    img.setImageBitmap(bm);

                setim = true;
                mark = 1;
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == 3) {

            if (data!=null)
            {

                Bundle bundle = data.getExtras();
                bm = bundle.getParcelable("data");
                bm = Bitmap.createScaledBitmap(bm, 300, 300, true);
                img.setImageBitmap(bm);
                setim=true;

            }
        }

    }
}