package com.my.elenabackup;

import android.content.Intent;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A utility for performing read/write operations on Drive files via the REST API and opening a
 * file picker UI via Storage Access Framework.
 */
public class DriveServiceHelper {
    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private final Drive mDriveService;

    public DriveServiceHelper(Drive driveService) {
        mDriveService = driveService;
    }

    /**
     * Creates a text file in the user's My Drive folder and returns its file ID.
     */
    public Task<String> createFile(String filePath) throws NullPointerException {
        return Tasks.call(mExecutor, () -> {
            java.io.File filePath1 = new java.io.File(filePath);
            FileContent mediaContent = new FileContent("application/zip", filePath1);
            File metadata = new File()
                    .setParents(Collections.singletonList("root"))
                    .setMimeType("application/zip")
                    .setName("ELENA");
            File googleFile = mDriveService.files().create(metadata, mediaContent).execute();
            if (googleFile == null) {
                throw new IOException("Null result when requesting file creation.");
            }
            return googleFile.getId();
        });
    }

    /**
     * Returns an {@link Intent} for opening the Storage Access Framework file picker.
     */
    public Intent createFilePickerIntent() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        return intent;
    }
}