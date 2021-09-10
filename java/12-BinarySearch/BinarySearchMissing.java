package HW.HW14.search;


import static HW.HW14.search.BinarySearch.*;


public class BinarySearchMissing {
    public static void main(String[] args) {

        initializeBinarySearchInput(args);
        //index == R
        int index = iterativeBinarySearchLowerBound();

        if (a.length > 0 && index < a.length && a[index] > x) {
            //index' = index - 1
            index--;
        }
        if (a.length == 0 || index == a.length || a[index] != x) {
            //index' = -(index) - 1
            index = -(index) - 1;
        }

        System.out.println(index);
    }
}
