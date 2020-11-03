package analyzer;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    public static void mergesort(List<Patterns> patternsList, int left, int right) {
        if (right == left + 1) {
            return;
        }

        int middle = left + (right - left) / 2;
        mergesort(patternsList, left, middle);
        mergesort(patternsList, middle, right);
        merge(patternsList, left, middle, right);
    }

    private static void merge(List<Patterns> patternsList, int left, int middle, int right) {
        int i = left;
        int j = middle;

        List<Patterns> temp = new ArrayList<>();
        while (i < middle && j < right) {
            if (patternsList.get(i).getPriority() > patternsList.get(j).getPriority()) {
                temp.add(patternsList.get(i));
                i += 1;
            } else {
                temp.add(patternsList.get(j));
                j += 1;
            }
        }

        for (;i < middle; ++i) {
            temp.add(patternsList.get(i));
        }

        for (;j < right; ++j) {
            temp.add(patternsList.get(j));
        }

        for (i = 0;i < temp.size(); ++i) {
            patternsList.set(left, temp.get(i));
            left += 1;
        }
    }
}
