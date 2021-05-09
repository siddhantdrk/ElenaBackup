package com.my.elenabackup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.util.Collections;

import static com.my.elenabackup.Constants.PICK_ZIP_REQUEST;
import static com.my.elenabackup.Constants.REQUEST_CODE_OPEN_DOCUMENT;
import static com.my.elenabackup.Constants.REQUEST_CODE_SIGN_IN;
import static com.my.elenabackup.FilePickerUtils.openFileFromFilePicker;
import static com.my.elenabackup.UnZipAndBackup.UnZipAndCopyBackup;

public class HandleUtils {
    public static void handleSignInResult(Intent result, Context context) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
                    Log.d(Constants.TAG, "Signed in as " + googleAccount.getEmail());

                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(context
                                    , Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    Drive googleDriveService =
                            new Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("Drive API Migration")
                                    .build();
                    MainActivity.mDriveServiceHelper = new DriveServiceHelper(googleDriveService);
                })
                .addOnFailureListener(exception -> Log.e(Constants.TAG, "Unable to sign in.", exception));
    }

    public static void handleRequestCode(int requestCode, Intent data, Context context) {
        switch (requestCode) {
            case PICK_ZIP_REQUEST:
                MainActivity.myDbHelper = UnZipAndCopyBackup(data, context);
                break;

            case REQUEST_CODE_SIGN_IN:
                handleSignInResult(data, context);
                break;

            case REQUEST_CODE_OPEN_DOCUMENT:
                Uri uri = data.getData();
                if (uri != null) {
                    MainActivity.myDbHelper = openFileFromFilePicker(uri, context);
                }
                break;
        }
    }
}
