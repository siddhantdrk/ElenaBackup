package com.my.elenabackup;

import android.util.Log;

public class LogUtils {
    public static int logD(String tag, String log) {
        return Log.d(tag, log);
    }

    public static int logE(String tag, String log) {
        return Log.e(tag, log);
    }

    public static int logV(String tag, String log) {
        return Log.v(tag, log);
    }
}
