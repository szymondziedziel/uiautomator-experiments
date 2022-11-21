package com.example.uiObjectWithUiselectorCapabilitiesExperiments

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.*
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiSelectorExperiments : Experiments() {
    @Test
    // Check if all controls are visible when found separately
    fun test1() {
        var o1 = device.findObject(UiSelector().description("PANEL-1"))
        assertTrue("PANEL-1 does not exists", o1.exists())


        o1 = device.findObject(UiSelector().description("PANEL-1-1"))
        assertTrue("PANEL-1-1 does not exists", o1.exists())

        o1 = device.findObject(UiSelector().text("TEXT-1-1-1"))
        assertTrue("TEXT-1-1-1 does not exists", o1.exists())
        o1 = device.findObject(UiSelector().text("TEXT-1-1-2"))
        assertTrue("TEXT-1-1-2 does not exists", o1.exists())
        o1 = device.findObject(UiSelector().text("TEXT-1-1-3"))
        assertTrue("TEXT-1-1-3 does not exists", o1.exists())


        o1 = device.findObject(UiSelector().description("PANEL-1-2"))
        assertTrue("PANEL-1-2 does not exists", o1.exists())

        o1 = device.findObject(UiSelector().text("TEXT-1-2-1"))
        assertTrue("TEXT-1-2-1 does not exists", o1.exists())
        o1 = device.findObject(UiSelector().text("TEXT-1-2-2"))
        assertTrue("TEXT-1-2-2 does not exists", o1.exists())
        o1 = device.findObject(UiSelector().text("TEXT-1-2-3"))
        assertTrue("TEXT-1-2-3 does not exists", o1.exists())


        o1 = device.findObject(UiSelector().description("PANEL-1-3"))
        assertTrue("PANEL-1-3 does not exists", o1.exists())

        o1 = device.findObject(UiSelector().text("TEXT-1-3-1"))
        assertTrue("TEXT-1-3-1 does not exists", o1.exists())
        o1 = device.findObject(UiSelector().text("TEXT-1-3-2"))
        assertTrue("TEXT-1-3-2 does not exists", o1.exists())
        o1 = device.findObject(UiSelector().text("TEXT-1-3-3"))
        assertTrue("TEXT-1-3-3 does not exists", o1.exists())
    }

    @Test
    // Check if PANEL-1-1 really has proper TextViews
    fun test2_1() {
        val p11 = device.findObject(UiSelector().description("PANEL-1-1"))
        assertTrue("PANEL-1-1 does not exists", p11.exists())

        val c111 = p11.getChild(UiSelector().text("TEXT-1-1-1"))
        assertTrue("TEXT-1-1-1 does not exists", c111.exists())

        val c121 = p11.getChild(UiSelector().text("TEXT-1-2-1"))
        assertTrue("TEXT-1-2-1 does not exists", c121.exists().not())

        val c112 = p11.getChild(UiSelector().text("TEXT-1-1-2"))
        assertTrue("TEXT-1-1-2 does not exists", c112.exists())
    }

    @Test
    // I am able to do almost the same as in test2_1, but by instance (by searching globally with device instead parent element)
    fun test2_2() {
        var i = device.findObject(UiSelector().textContains("TEXT"))
        assertTrue(
            "There is no element with instance 0 (variant A) that contains TEXT in text", i.exists()
        )
        assertTrue(
            "First instance of element that contains TEXT in text has no TEXT-1-1-1 text",
            i.text == "TEXT-1-1-1"
        )

        i = device.findObject(UiSelector().textContains("TEXT").instance(0))
        assertTrue(
            "There is no element with instance 0 (variant B) that contains TEXT in text", i.exists()
        )
        assertTrue(
            "First instance of element that contains TEXT in text has no TEXT-1-1-1 text",
            i.text == "TEXT-1-1-1"
        )

        i = device.findObject(UiSelector().textContains("TEXT").instance(1))
        assertTrue("There is no element with instance 1 that contains TEXT in text", i.exists())
        assertTrue(
            "First instance of element that contains TEXT in text has no TEXT-1-1-2 text",
            i.text == "TEXT-1-1-2"
        )
    }

    @Test
    // There are really 9 element that contains TEXT in text no matter in which nesting level they are
    fun test3_1() {
        val size = (0..8).map {
            device.findObject(UiSelector().textContains("TEXT").instance(it))
        }.size
        assertTrue("There is no 9 elements that contain TEXT in text", size == 9)
    }

    @Test
    // There should also be really 9 element that contains TEXT in text inside PANEL-1
    fun test3_2() {
        val p11 = device.findObject(UiSelector().description("PANEL-1"))
        val size = (0..8).map {
            p11.getChild(UiSelector().textContains("TEXT").instance(it))
        }.size
        assertTrue("There is no 9 elements that contain TEXT in text inside PANEL-1", size == 9)
    }

    @Test
    // There should also be really 3 element that contains TEXT in text inside PANEL-1-1
    fun test3_3() {
        val p11 = device.findObject(UiSelector().description("PANEL-1-1"))
        val size = (0..2).map {
            p11.getChild(UiSelector().textContains("TEXT").instance(it))
        }.size
        assertTrue("There is no 3 elements that contain TEXT in text inside PANEL-1-1", size == 3)
    }

    @Test
    // I can really do double search with getChild to go deeper, but more precisely
    fun test4() {
        val p1 = device.findObject(UiSelector().description("PANEL-1"))
        assertTrue("There is no PANEL-1 found", p1.exists())

        val p11A = p1.getChild(UiSelector().description("PANEL-1-1"))
        assertTrue("There is no PANEL-1-1 found inside inside PANEL-1", p11A.exists())

        val p11B = p1.getChild(UiSelector().className("android.widget.LinearLayout").instance(0))
        assertTrue("There is no PANEL-1-1 found inside inside PANEL-1", p11B.exists())
        assertTrue(
            "First LinearLayout inside PANEL-1 does not have PANEL-1-1 in description",
            p11B.contentDescription == "PANEL-1-1"
        )

        var t1 = p11A.getChild(UiSelector().text("TEXT-1-1-1"))
        assertTrue("There is no TEXT-1-1-1 found inside inside PANEL-1-1", t1.exists())
        assertTrue(
            "Element found by text TEXT-1-1-1 inside PANEL-1 has no TEXT-1-1-1 inside text",
            t1.text == "TEXT-1-1-1"
        )

        t1 = p11B.getChild(UiSelector().instance(0))
        assertTrue(
            "There is no TEXT-1-1-1 found inside inside PANEL-1-1 when searching by instance",
            t1.exists()
        )
        assertTrue(
            "Element found by instance inside PANEL-1 has no TEXT-1-1-1 inside text",
            t1.text == "TEXT-1-1-1"
        )
    }

    @Test
    // Searching next/ prev seems to be impossible from what I have below
    fun test5_1() {
        val p1 = device.findObject(UiSelector().description("PANEL-1-1"))
        val p11 = p1.getFromParent(UiSelector().index(0))
        assertTrue(
            "Index 0 from parent is not PANEL-1-1", p11.contentDescription == "PANEL-1-1"
        )
        val p12 = p1.getFromParent(UiSelector().index(1)) // It finds TextView with text TEXT-1-1-2
        assertTrue(
            "Index 1 from parent is not PANEL-1-2, because it goes deeper to TextViews with proper index",
            p12.text == "TEXT-1-1-2"
        )
        val p13 = p1.getFromParent(UiSelector().index(2)) // It finds TextView with text TEXT-1-1-3
        assertTrue(
            "Index 2 from parent is not PANEL-1-3, because it goes deeper to TextViews with proper index",
            p13.text == "TEXT-1-1-3"
        )
        // Cannot get:
        // p1.instance no such property
        // p1.index no such property
        // assertTrue("p1 and p2 are not same", p1.equals(p2)) fails
        // So am I able to get next/ prev somehow?
        // Maybe filtering by bounds? Let's try (but I need to repeat whole process when any swipe, scroll has been performed)
        // Failed, I wanted to do things like p1.getFromParent(UiSelector().index(/* [0, 1, 2] */)) but it searches for
        //   any element inside p1 at any nesting level and miss-find due to this
        //   so it fails if there is like PANEL-1 then PANEL-1-1 etc. I wanted to find PANEL-1-2 (with index=1), but got TEXT-1-1-2
        // See below code for more what I've tried

        // Let's try to find element indexes first
        // All below will not work
        // val p1index = (0 until p1.childCount).first {
        //     val child = p1.getFromParent(UiSelector().index(it))
        //     p1.bounds.top == child.bounds.top &&
        //             p1.bounds.right == child.bounds.right &&
        //             p1.bounds.bottom == child.bounds.bottom &&
        //             p1.bounds.left == child.bounds.left
        // }
        // assertTrue("Found index of p1 is not 0", p1index == 0)
        //
        // val p2 = device.findObject(UiSelector().description("PANEL-1-2"))
        // val p2index = (0 until p2.childCount).first {
        //     val child = p2.getFromParent(UiSelector().index(it))
        //     p2.bounds.top == child.bounds.top &&
        //             p2.bounds.right == child.bounds.right &&
        //             p2.bounds.bottom == child.bounds.bottom &&
        //             p2.bounds.left == child.bounds.left
        // }
        // assertTrue("Found index of p2 is not 1", p2index == 1)

        // val p3 = device.findObject(UiSelector().description("PANEL-1-3"))
        // val p3index = (0 until p3.childCount).first {
        //     val child = p3.getFromParent(UiSelector().instance(it))
        //     p3.bounds.top == child.bounds.top &&
        //             p3.bounds.right == child.bounds.right &&
        //             p3.bounds.bottom == child.bounds.bottom &&
        //             p3.bounds.left == child.bounds.left
        // }
        // assertTrue("Found index of p3 is not 2", p3index == 2)
    }

    @Test
    // Search for panel that have proper child/ descendant
    // No selector for it, let's write extension then
    fun test5_2() {
        val p11 = device.findObject(
            UiSelector().className("android.widget.LinearLayout").childSelector(
                UiSelector().description("PANEL-1-1")
            )
        )
        assertTrue(
            "Adding childSelector searches for element with the childSelector, not the parent that has child defined by childSelector",
            p11.contentDescription == "PANEL-1-1"
        )

        val t111 = device.findObject(
            UiSelector().className("android.widget.LinearLayout").childSelector(
                UiSelector().description("PANEL-1-1").childSelector(UiSelector().text("TEXT-1-1-1"))
            )
        )
        assertTrue(
            "Adding childSelector searches for element with the childSelector, not the parent that has child defined by childSelector, but glad that I can nest them",
            t111.text == "TEXT-1-1-1"
        )
    }

    /*
     *  BELOW THERE ARE NON-BUILT-IN OPERATIONS. I AM TRYING TO WRITE SOME USEFUL EXTENSIONS
     */

    @Test
    // Search for panel that have proper descendant WITH CUSTOM EXTENSION but for UiObject not UiSelector
    // It's easier to split it into steps for me as I can't figure out what extensions should I write
    // for UiSelector to achieve same effect, here it is 'fun UiObject.hasDescendant(selector: UiSelector)'
    fun test6() {
        val p11 = device.findObject(UiSelector().description("PANEL-1-1"))
        assertTrue(
            "p11 has no child with TEXT-1-1-1", p11.hasDescendant(UiSelector().text("TEXT-1-1-1"))
        )
        assertTrue(
            "p11 has no child with TEXT-1-1-1", p11.hasDescendant(UiSelector().text("TEXT-1-1-2"))
        )
        assertTrue(
            "p11 has no child with TEXT-1-1-1", p11.hasDescendant(UiSelector().text("TEXT-1-1-3"))
        )
    }

    @Test
    // There are problems to deal with collections with UIObject
    // It have getChild, but it returns single instance at any nesting level
    // I want to get all descendants (called child by Google)
    // I added another extension for this purpose 'fun UiObject.getAllDescendants(selector: UiSelector)'
    fun test7_1() {
        val p1 = device.findObject(UiSelector().description("PANEL-1"))
        val descendants =
            p1.getAllDescendants(UiSelector().className("android.widget.LinearLayout"))
        assertTrue(
            "There is no exactly 3 LinearLayouts inside PANEL-1", descendants.size == 3
        )
        assertTrue(
            "1st descendant have no PANEL-1-1 description",
            descendants[0].contentDescription == "PANEL-1-1"
        )
        assertTrue(
            "2nd descendant have no PANEL-1-2 description",
            descendants[1].contentDescription == "PANEL-1-2"
        )
        assertTrue(
            "3rd descendant have no PANEL-1-3 description",
            descendants[2].contentDescription == "PANEL-1-3"
        )
    }

    @Test
    // Another try to find all TextViews inside PANEL-1. It should be quite obvious
    fun test7_2() {
        device.findObject(UiSelector().description("PANEL-1"))
            .getAllDescendants(UiSelector().className("android.widget.TextView"))
            .let { descendants ->
                assertTrue(
                    "There is no exactly 9 TextViews inside PANEL-1", descendants.size == 9
                )

                (0..2).forEach { rowIndex ->
                    (0..2).forEach { columnIndex ->
                        assertTrue(
                            "TextView in ${rowIndex + 1} row and ${columnIndex + 1} column has no text TEXT-1-${rowIndex + 1}-${columnIndex + 1}",
                            descendants[rowIndex * 3 + columnIndex].text == "TEXT-1-${rowIndex + 1}-${columnIndex + 1}"
                        )
                    }
                }
            }
    }
}