package com.tsystems.javaschool.tasks.subsequence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if (x == null || y == null)
            throw new IllegalArgumentException();

        List listX = new ArrayList(x);
        List listY = new ArrayList(y);

        listY.retainAll(listX);

        if (listY.size() > listX.size()) {
            Set setY = new HashSet(listY);
            if (listX.equals(new ArrayList(setY))) {
                return true;
            } else return false;
        }

        if (listX.equals(listY)) {
           return true;
        } else return false;

    }
}
