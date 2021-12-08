package com.example.osrsutilities;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.android.volley.VolleyError;
import com.example.osrsutilities.model.Equipment;

import java.util.Locale;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void checkAttackCrush() {
        int expected = 10;

        Equipment helm = new Equipment();
        helm.setAttackCrush(10);
        int actual = helm.getAttackCrush();
        assertEquals(actual, expected);
    }

    @Test
    public void checkSetAllStats() {
        int expectedDefenceCrush = 3;
        int expectedPrayer = 12;

        Equipment e = new Equipment();
        e.setAllStats(3, 4, 5, 6,
                12, 8, 0, 3,
                5, 6, 23, 0,
                3, 12);

        assertEquals(e.getDefenceCrush(), expectedDefenceCrush);
        assertEquals(e.getPrayer(), expectedPrayer);
    }

    @Test
    public void checkConstructor() {
        String expectedName = "Abyssal whip";
        String expectedSlot = "weapon";

        Equipment e = new Equipment("Abyssal whip", "weapon");

        assertEquals(e.getName(), expectedName);
        assertEquals(e.getSlot(), expectedSlot);
    }
}