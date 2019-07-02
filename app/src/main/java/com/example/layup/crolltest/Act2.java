package com.example.layup.crolltest;

/**
 * Created by layup on 2019-03-22.
 */

public class Act2 {

    test mTest;
    int test = 1;
    int test2 = 2;

    interface test {
        void avg(int a);
        void two(int c, int d);
    }

    Act2() {

        mTest.avg((test + test2) / 2);
        mTest.two(test2, test);

    }
}
