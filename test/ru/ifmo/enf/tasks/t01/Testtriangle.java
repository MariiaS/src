package ru.ifmo.enf.tasks.t01;

import junit.framework.TestCase;

/**
 * Created by May on 08.10.2014.
 */
public class Testtriangle extends TestCase {
    private final Rtriangle testRTriangle = RtriangleProvider.getRtriangle();

    public void testTriangle() {
        if (!realTriangle()) {
            throw new AssertionError("Test failed, cause it's not right triangle. Please, try another coordinates.");
        }
        //   assertEquals(true, realTriangle());
    }

    private boolean realTriangle() {
        boolean tryTriangle = false;
        final double katet = Math.hypot((testRTriangle.getApexX2() - testRTriangle.getApexX1()), (testRTriangle.getApexY2() - testRTriangle.getApexY1()));
        final double katetOrHip = Math.hypot((testRTriangle.getApexX3() - testRTriangle.getApexX2()), (testRTriangle.getApexY3() - testRTriangle.getApexY2()));
        final double katetOrHipo = Math.hypot((testRTriangle.getApexX1() - testRTriangle.getApexX3()), (testRTriangle.getApexY1() - testRTriangle.getApexY3()));
        if (Math.hypot(katet, katetOrHip) == katetOrHipo | Math.hypot(katetOrHip, katetOrHipo) == katet | Math.hypot(katet, katetOrHipo) == katetOrHip) {
            tryTriangle = true;
        }
        if (katet == 0 | katetOrHip == 0 | katetOrHipo == 0) {
            tryTriangle = false;
        }
        return tryTriangle;
    }
}
