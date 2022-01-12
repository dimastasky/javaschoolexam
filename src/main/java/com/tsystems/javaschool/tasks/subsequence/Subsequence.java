package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

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
            boolean isBuildable = false;
            if (x == null || y == null) throw new IllegalArgumentException();
            if (x.isEmpty()) {
                return true; //TRUE When List X is empty, it's always subsequence of List Y (even if List Y is emt too)
            } else if (y.isEmpty() || y.size() < x.size()) {
                return false; //FALSE When List X contains elements and List Y is Empty or Y size less than X size
            } else {
                for(int iX = 0, iY = 0; iX < x.size(); iY++) {
                    if (iY > y.size()-1) return false; //TRUE When List Y doesn't contain any element from List X
                    if (x.get(iX).equals(y.get(iY))) iX++;
                }
            }
        return true; //TRUE When List X is subsequence of List Y
    }
}
