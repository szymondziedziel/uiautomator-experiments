package com.example.uiObjectWithUiselectorCapabilitiesExperiments

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.*
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.runner.RunWith
import org.junit.Before

@RunWith(AndroidJUnit4::class)
open class Experiments {
    private val BASIC_SAMPLE_PACKAGE = "com.example.uiObjectWithUiselectorCapabilitiesExperiments"
    private val LAUNCH_TIMEOUT = 5000L
    protected lateinit var device: UiDevice

    @Before
    fun startMainActivityFromHomeScreen() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        device.pressHome()

        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT
        )

        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(
            BASIC_SAMPLE_PACKAGE
        )?.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        device.wait(
            Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT
        )
    }

    fun UiObject.hasDescendant(selector: UiSelector) = this.getChild(selector).exists()

    fun UiObject.getAllDescendants(selector: UiSelector) = generateSequence(0) {
        it + 1
    }.takeWhile {
        this.getChild(selector.instance(it)).exists()
    }.map {
        this.getChild(selector.instance(it))
    }.toList()
}