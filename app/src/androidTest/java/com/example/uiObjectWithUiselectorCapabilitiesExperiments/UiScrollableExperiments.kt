package com.example.uiObjectWithUiselectorCapabilitiesExperiments

import androidx.test.uiautomator.*
import org.junit.Assert.assertTrue
import org.junit.Test

class UiScrollableExperiments : Experiments() {
    @Test
    // Can I search for direct children with UiScrollable
    fun test1() {
        // PANEL-1 is not scrollable, but UiScrollable is subclass of UiObject so it should work anyway
        val p1 = UiScrollable(UiSelector().description("PANEL-1"))
        val p11 = p1.getChildByInstance(UiSelector(), 0)
        assertTrue(
            "'p1.getChildByInstance(UiSelector(), 1)' does not get direct child for me which should be PANEL-1-2",
            p11.contentDescription == "PANEL-1" // It is PANEL-1? So something is wrong here :(
        )

        // Can I get TextView then from this point?
        val t111 = p1.getChildByInstance(UiSelector().className("android.widget.TextView"), 0)
        assertTrue(
            "'p1.getChildByInstance(UiSelector(), 1)' does not get direct child for me which should be PANEL-1-2",
            t111.text == "TEXT-1-1-1" // It passes so it seems like again it is not about directChild :(
            // It is useless for me
        )
    }

    @Test
    // Can I cast UiObject to UiScrollable
    fun test2() {
        // PANEL-1 is not scrollable, but UiScrollable is subclass of UiObject so it should work anyway
        val p1 = device.findObject(UiSelector().description("PANEL-1"))
        // val s1 = p1 as UiScrollable // Cannot be cast :(
        // But do not need to as we know now how childSelector works
    }

    @Test
    // We cannot use device findObject/ UIObject::getChild to target
    // properly element and het UiCollection/ UiScrollable
    // But as we saw in UiSelectorExperiments::test5_2 we can try to target it properly with childSelector
    // and this solves my problem. I can target Any UiCollection and any UiScrollable
    // so I can now always search for UiScrollable as this is subclass od UiCollection, which is
    // subclass of UiObject, so with this approach I can always get object that have all methods (from the richest subclass) :)
    fun test3() {
        // I am able to target any element in the whole tree
        val sp1 = UiScrollable(UiSelector().description("PANEL-1"))
        assertTrue(
            "sp1 is proper element",
            sp1.className == "android.widget.LinearLayout"
        )
        // I am able to target one in another precisely
        val sp11 = UiScrollable(
            UiSelector().description("PANEL-1").childSelector(
                UiSelector().description("PANEL-1-1")
            )
        )
        assertTrue(
            "sp11 is proper element",
            sp11.className == "android.widget.LinearLayout"
        )
        // I am able to target one in another even more precisely
        val st111 = UiScrollable(
            UiSelector().description("PANEL-1").childSelector(
                UiSelector().description("PANEL-1-1").childSelector(
                    UiSelector().instance(0)
                )
            )
        )
        assertTrue(
            "st111 is proper element",
            st111.className == "android.widget.TextView" &&
                    st111.text == "TEXT-1-1-1"
        )
        // I am able to target one in another even more precisely
        val st112 = UiScrollable(
            UiSelector().description("PANEL-1").childSelector(
                UiSelector().description("PANEL-1-1").childSelector(
                    UiSelector().instance(1)
                )
            )
        )
        assertTrue(
            "st112 is proper element",
            st112.className == "android.widget.TextView" &&
                    st112.text == "TEXT-1-1-2"
        )
        // I am able to target one in another even more precisely
        val st113 = UiScrollable(
            UiSelector().description("PANEL-1").childSelector(
                UiSelector().description("PANEL-1-1").childSelector(
                    UiSelector().instance(2)
                )
            )
        )
        assertTrue(
            "st113 is proper element",
            st113.className == "android.widget.TextView" &&
                    st113.text == "TEXT-1-1-3"
        )
    }
}