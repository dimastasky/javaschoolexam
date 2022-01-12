package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here
        checkInputNumbers(inputNumbers);
        Collections.sort(inputNumbers);
        int pyramidHeight = getHeight(inputNumbers.size());
        int pyramidWidth = getWidth(pyramidHeight);
        int[][] pyramid = new int[pyramidHeight][pyramidWidth];

        int listIndex = 0;
        for (int currentHeight = 0; currentHeight < pyramidHeight; currentHeight++) {
            for (int currentWidth = 0; currentWidth < currentHeight + 1; currentWidth++) {
                pyramid[currentHeight][pyramidWidth / 2 - currentHeight + currentWidth * 2] = inputNumbers.get(listIndex);
                listIndex++;
            }
        }
        return pyramid;
    }

    //Check Linked List with input numbers for minimal and maximal size range and if contain null elements
    private static void checkInputNumbers(List<Integer> inputNumbers) {
        if (inputNumbers.size() < 3 ||
                inputNumbers.size() >= Integer.MAX_VALUE-1 ||
                inputNumbers.contains(null)) throw new CannotBuildPyramidException();
    }

    //Get height of Pyramid using inputNumbers size
    private static int getHeight(int listSize) {
        int height = 0;
        for (int elementsCount = listSize; elementsCount > 0; elementsCount -= height) {
            //the number of remaining elements cannot be lower than the height of the pyramid
            if (elementsCount < height) throw new CannotBuildPyramidException();
            height++;
        }
        return height;
    }

    //Get width of Pyramid using height of pyramid
    private static int getWidth(int height) {
        return (height * 2) - 1;
    }
}