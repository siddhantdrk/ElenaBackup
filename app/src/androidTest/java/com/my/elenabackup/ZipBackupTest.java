package com.my.elenabackup;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class ZipBackupTest{

    private String SOURCE_PATH;
    private String TO_LOCATION;
    private Context instrumentationContext;

    @Before
    public void setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SOURCE_PATH=instrumentationContext.getExternalFilesDir(null) + "/ELENA";
        TO_LOCATION = instrumentationContext.getExternalFilesDir(null) + "/ELENA.zip";
    }

    @Test
    public void zipFileTest() {
        boolean zipped = ZipBackup.zipFileAtPath(SOURCE_PATH,
                TO_LOCATION);
        Assert.assertTrue(zipped);
    }
}