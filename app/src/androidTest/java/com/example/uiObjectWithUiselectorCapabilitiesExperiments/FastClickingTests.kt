package com.example.uiObjectWithUiselectorCapabilitiesExperiments

import androidx.test.uiautomator.*
import org.junit.Test
import java.time.LocalDateTime


class FastClickingTests : Experiments() {
    @Test
    // Clicking 100 times 25/sec impossible, but it's definitely faster
    fun test1() {
        // val b = device.findObject(By.text("BUTTON")) // Is faster
        val b = device.findObject(UiSelector().text("BUTTON")) // Is slower
        // But it gets good enough speed boost when I added Configurator
        Configurator.getInstance().apply {
            waitForIdleTimeout = 0L
            actionAcknowledgmentTimeout = 0L
            scrollAcknowledgmentTimeout = 0L
            waitForSelectorTimeout = 0L
            keyInjectionDelay = 0L
        }
        println("CUSTOM_LOG START ${LocalDateTime.now()}")
        (1..100).forEach {
            b.click()
            Thread.sleep(40L)
        }
        println("CUSTOM_LOG END ${LocalDateTime.now()}")
        // LOGs I got on my machine
        // START 16:52:18.495
        // END   16:52:41.183
        // ~23 seconds so 0.23 second per click
        // For me fast enough
    }
}