package com.my.elenabackup;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.my.elenabackup.Constants.REQUEST_CODE_SIGN_IN;
import static com.my.elenabackup.DriveUtils.requestSignIn;

@RunWith(AndroidJUnit4.class)
public class DriveUtilsTest {

    @Rule
    public ActivityTestRule<MainActivity> menuActivityTestRule =
            new ActivityTestRule<>(MainActivity.class, true, true);


    @Test
    public void requestSignInTest() {
        DriveServiceHelper driveServiceHelper = requestSignIn(menuActivityTestRule.getActivity(), REQUEST_CODE_SIGN_IN);
        Assert.assertNotNull(driveServiceHelper);
    }
}