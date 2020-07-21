package com.example.ex82retrofitboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;

import retrofit2.Retrofit;

public class EditActivity extends AppCompatActivity {

    EditText etName;
    EditText etTitle;
    EditText etMsg;
    EditText etPrice;
    ImageView iv;

    String imgPath; //선택된 이미지의 절대경로

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etName = findViewById(R.id.et_name);
        etTitle = findViewById(R.id.et_title);
        etMsg = findViewById(R.id.et_msg);
        etPrice = findViewById(R.id.et_price);
        iv = findViewById(R.id.iv);

        //외부저장소의 접근 동적퍼미션
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED){
                requestPermissions(permissions, 100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100 && grantResults[0]==PackageManager.PERMISSION_DENIED){
            Toast.makeText(this, "사진파일 전송이 불가합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickSelectImage(View view){
        //업로드할 사진 선택
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==10 && resultCode==RESULT_OK){
            Uri uri= data.getData();
            if(uri!=null){
                Glide.with(this).load(uri).into(iv);

                //이미지 uri를 서버에 전송하려면..실제 경로 주소 필요
                // uri -> 절대경로
                imgPath= getRealPathFromUri(uri);
                //잘 되었는지 확인
                new AlertDialog.Builder(this).setMessage(imgPath).show();
            }
        }
    }

    String getRealPathFromUri(Uri uri){
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colume_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colume_index);
        cursor.close();
        return result;
    }

    public void clickComplete(View view){

        //서버에 전송할 데이터들 [name, title, msg, price, imgPath ]
        String name = etName.getText().toString();
        String title = etTitle.getText().toString();
        String msg = etMsg.getText().toString();
        String price = etPrice.getText().toString();

        //레트로핏 라이브러리로 데이터를 전송
        Retrofit retrofit = RetrofitHelper.getInstance2();
        RetrofitService retrofitService = retrofit.create(RetrofitService.class);

    }




}
