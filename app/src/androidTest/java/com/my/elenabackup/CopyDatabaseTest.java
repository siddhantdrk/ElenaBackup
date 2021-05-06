package com.my.elenabackup;

import android.content.Context;
import android.support.v4.app.INotificationSideChannel;

import androidx.test.platform.app.InstrumentationRegistry;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class CopyDatabaseTest extends TestCase {

    private Context instrumentationContext;
    private CopyDatabase copyDatabase;
    private String BACK_UP_DB_PATH;
    private File OUTPUT_DB_FILE;
    private String ZIP_FILE;
    private  String DIR_PATH, LOCATION;

    @Before
    public void setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        copyDatabase=new CopyDatabase(instrumentationContext);
        BACK_UP_DB_PATH=instrumentationContext.getExternalFilesDir(null) + "/ELENA/ELENA";
        OUTPUT_DB_FILE=instrumentationContext.getDatabasePath("ELENA");
        ZIP_FILE= instrumentationContext.getExternalFilesDir(null) + "/ELENA.zip";
        DIR_PATH=instrumentationContext.getExternalFilesDir(null).getAbsolutePath() + "/ELENA";
        LOCATION=instrumentationContext.getExternalFilesDir(null).getAbsolutePath();
    }

    @Test
    public void testUnPackZip(){
        boolean unzip=UnZipAndBackup.unpackZip(ZIP_FILE,DIR_PATH,LOCATION);
        Assert.assertTrue(unzip);
    }


    @Test
    public void testCopyBackupDataBase() {
        boolean copied = copyDatabase.copyBackupDataBase(BACK_UP_DB_PATH,OUTPUT_DB_FILE);
        Assert.assertTrue(copied);
    }

    @Test
    public void testCheckDataBase() {
    }
}