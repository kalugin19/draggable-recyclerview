package com.kalugin.draggablegridview;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

public class MainExecutor implements Executor {
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable command) {
        mainThreadHandler.post(command);
    }
}
