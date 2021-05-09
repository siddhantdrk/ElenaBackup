package com.my.elenabackup;

import android.content.Context;
import android.net.Uri;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.my.elenabackup.FilePickerUtils.openFileFromFilePicker;

public class FilePickerUtilsTest {

    private Context instrumentationContext;
    private String DATABASE_NAME="ELENA";

    @Before
    public void setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test
    public void openFileFromFilePickerTest(){
        MyDbHelper myDbHelper = openFileFromFilePicker(Uri.parse("content://com.android.externalstorage.documents/document/primary%3AFiles.zip"), instrumentationContext);
        Assert.assertTrue(myDbHelper != null);
    }
}