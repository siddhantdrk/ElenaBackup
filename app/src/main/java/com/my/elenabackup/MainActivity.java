package com.my.elenabackup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static com.my.elenabackup.Constants.REQUEST_CODE_OPEN_DOCUMENT;
import static com.my.elenabackup.Constants.REQUEST_CODE_SIGN_IN;
import static com.my.elenabackup.CreateDatabaseBackup.addDataToDb;
import static com.my.elenabackup.DriveUtils.requestSignIn;
import static com.my.elenabackup.FilePickerUtils.openFilePicker;
import static com.my.elenabackup.uploadZIpUtils.uploadZip;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static MyDbHelper myDbHelper;
    public static DriveServiceHelper mDriveServiceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createBackupDbBtn = findViewById(R.id.createBackupDbBtn);
        Button zipBackupBtn = findViewById(R.id.ZipBackupBtn);
        Button selectZipBackupBtn = findViewById(R.id.select_zip_from_drive);
        Button uploadZipBackupBtn = findViewById(R.id.upload_zip_to_drive);
        PermissionManager.requestPermissionForWriteExternalStorage(this);
        PermissionManager.requestPermissionForReadExternalStorage(this);
        CreateDatabaseBackup.makeAppDirectory(this);
        createBackupDbBtn.setOnClickListener(view -> {
            myDbHelper = addDataToDb(this);
            LogUtils.logD("<<!!!!>>>>>", myDbHelper.getDatabaseName());
            CreateDatabaseBackup.createDatabaseFileBackup(this, myDbHelper.getDatabaseName());
        });
        zipBackupBtn.setOnClickListener(view -> {
            LogUtils.logD("<<!!!!>>>>>", getExternalFilesDir(null) + "/ELENA");
            LogUtils.logD("<<!!!!>>>>>", getExternalFilesDir(null) + "/ELENA.zip");
            if (ZipBackup.zipFileAtPath(getExternalFilesDir(null) + "/ELENA", getExternalFilesDir(null) + "/ELENA.zip")) {
                LogUtils.logV(MainActivity.class.getSimpleName(), "Zip created Successfully");
            } else {
                LogUtils.logV(MainActivity.class.getSimpleName(), "Zip creation Unsuccessful");
            }
        });

        selectZipBackupBtn.setOnClickListener(view ->
                openFilePicker(REQUEST_CODE_OPEN_DOCUMENT, MainActivity.this, mDriveServiceHelper));

        uploadZipBackupBtn.setOnClickListener(view -> uploadZip(this, this, mDriveServiceHelper));

        mDriveServiceHelper = requestSignIn(MainActivity.this, REQUEST_CODE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            HandleUtils.handleRequestCode(requestCode, data, this);
        }
    }
}