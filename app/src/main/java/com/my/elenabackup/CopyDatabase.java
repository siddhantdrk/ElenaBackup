package com.my.elenabackup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CopyDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ELENA";
    private static final int DATABASE_VERSION = 1;
    static SQLiteDatabase sqliteDataBase;
    public Context context;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context Parameters of super() are    1. Context
     *                2. Data Base Name.
     *                3. Cursor Factory.
     *                4. Data Base Version.
     */
    public CopyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * By calling this method and empty database will be created into the default system path
     * of your application so we are gonna be able to overwrite that database with our database.
     */
    public Boolean copyBackupDataBase(String backupDatabasePath, File outputDatabaseFile) {
        boolean databaseExist = checkDataBase(backupDatabasePath + ".db");
        if (databaseExist) {
            this.getWritableDatabase();
            try {
                copyDataBase(backupDatabasePath + ".db", outputDatabaseFile);
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
                LogUtils.logE("CopyDatabase", e.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean checkDataBase(String databaseName) throws NullPointerException {
        File databaseFile = new File(databaseName);
        return databaseFile.exists();
    }


    private void copyDataBase(String inputFileName, File outPutFile) throws IOException {
        InputStream mInput = new FileInputStream(inputFileName);
        OutputStream mOutput = new FileOutputStream(outPutFile);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }


    @Override
    public synchronized void close() {
        if (sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
