package ru.ifmo.enf.tasks.t01;

/**
 * Created by May on 08.10.2014.
 */
public final class RtriangleProvider implements Rtriangle {



    public int getApexX1() { return 0; }
    public int getApexY1() { return 0; }
    public int getApexX2() { return 0; }
    public int getApexY2() { return 0; }
    public int getApexX3() { return 0; }
    public int getApexY3() { return 1; }

    public static Rtriangle getRtriangle () {
        return new Rtriangle(){
            public int getApexX1() { return 0; }
            public int getApexY1() { return 0; }
            public int getApexX2() { return 0; }
            public int getApexY2() { return 0; }
            public int getApexX3() { return 0; }
            public int getApexY3() { return 1; }


        };

    }
}
