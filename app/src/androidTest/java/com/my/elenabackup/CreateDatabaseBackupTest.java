package com.my.elenabackup;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CreateDatabaseBackupTest {

    private Context instrumentationContext;
    private String DATABASE_NAME="ELENA";

    @Before
    public void setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void makeAppDirectoryTest() {
        boolean dir = CreateDatabaseBackup.makeAppDirectory(instrumentationContext);
        Assert.assertTrue(dir);
    }

    @Test
    public void createDatabaseFileBackupTest() {
        boolean backup = CreateDatabaseBackup.createDatabaseFileBackup(instrumentationContext, DATABASE_NAME);
        Assert.assertTrue(backup);
    }
}