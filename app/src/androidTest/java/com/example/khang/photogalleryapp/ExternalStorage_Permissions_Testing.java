package com.example.khang.photogalleryapp;

/**
 * Created by Khang on 05/10/2017.
 */

import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;

@RunWith(AndroidJUnit4.class)
public class ExternalStorage_Permissions_Testing {
    @Before
    public void grantPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getInstrumentation().getUiAutomation().executeShellCommand(
                    "pm grant " + InstrumentationRegistry.getTargetContext().getPackageName()
                            + " android.permission.READ_EXTERNAL_STORAGE");
        }
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.khang.photogalleryapp", appContext.getPackageName());
    }

    @Test
    public void checkTimeFilter() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        //  Feel free to change the folder name below to explore the file system on your phone or in the emulator
        File dir = new File("/sdcard/document/1BE6-1F15:Picture");

        // this is how you list files in a folder
        // use debugger to find the files or subfolders in a folder rather than logcat
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                Log.e("XXXXXXXXXXXXXXXXX", children[i]);
            }
        }
        assertEquals("com.example.khang.photogalleryapp", appContext.getPackageName());
    }
    @After
    public void cleanUp() {
        Log.e("XXXXXXXXXXXXXXXXX", "Test");
    }
}
