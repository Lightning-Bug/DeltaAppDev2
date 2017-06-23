package comnitt.boston.huh;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;

public class MainActivity extends AppCompatActivity  {

     Uri uri;
    Bitmap bm;
    String cap;
    int num=0,mark;
    private static ArrayList<MyImage> MyImage = new ArrayList<MyImage>();
    public  static final int RequestPermissionCode  = 1 ;
    private ListView list;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            mark  = bundle.getInt("mark");
            num = bundle.getInt("num");
            cap = bundle.getString("cap");
        }

        Intent intent = getIntent();
        bm = intent.getParcelableExtra("bitmap");
        uri = intent.getParcelableExtra("uri");

        EnableRuntimePermission();



        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.listview);

        if (num != 0) {

            MyImage.add(new MyImage(bm, cap,uri,mark));
            adapter = new MyAdapter(getApplicationContext(), MyImage);
            list.setAdapter(adapter);

        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                MyImage m = (MyImage) list.getItemAtPosition(position);
                Bitmap b = m.getimg();
                Intent intent = new Intent(getApplicationContext(), Activity_3.class);
                intent.putExtra("zzz", MyImage.get(position).getcaption());
                intent.putExtra("xxx",b);
                startActivity(intent);

            }
        });

    }


    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.CAMERA))
        {

            // Toast.makeText(MainActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult)
    {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {



                } else {

                    Toast.makeText(MainActivity.this,"Your application cannot access camera.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void Next (View v)
    {
        Intent intent = new Intent(this, NextActivity.class);
        intent.putExtra("num",num);
        startActivity(intent);

    }

}