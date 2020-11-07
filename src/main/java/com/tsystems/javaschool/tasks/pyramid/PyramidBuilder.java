package com.tsystems.javaschool.tasks.pyramid;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
        try {
            if (inputNumbers.contains(null)) {
                throw new CannotBuildPyramidException();
            }
            List<Integer> integerList = inputNumbers.stream().sorted()
                    .collect(Collectors.toList());

            int rows = 1;
            int columns = 1;
            int count = 0;

            while (count < integerList.size()) {
                count += rows;
                rows++;
                columns += 2;
            }
            rows -= 1; // необходимое количество строк и столбцов
            columns -= 2;

            int[][] pyramid;
            if (count == integerList.size()) {
                pyramid = new int[rows][columns];
                for (int[] row : pyramid) {
                    Arrays.fill(row, 0);
                }
            } else {
                throw new CannotBuildPyramidException();
            }

            int index = 0;
            count = 1; //количество цифр в строке
            int center = columns / 2;

            for (int i = 0, ofs = 0; i < rows; i++, count++, ofs++) {
                int startNum = center - ofs;
                for (int j = 0; j < count * 2; j += 2, index++) {
                    pyramid[i][startNum + j] = integerList.get(index);
                }
            }
            return pyramid;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new CannotBuildPyramidException();
        }
    }


}
