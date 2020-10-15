package HW.HW14.search;

import static HW.HW14.search.BinarySearch.*;

public class BinarySearchSpan {
    public static void main(String[] args) {

        initializeBinarySearchInput(args);
        //lower_bound == R
        int lower_bound = iterativeBinarySearchLowerBound();
        //upper_bound == R
        int upper_bound = iterativeBinarySearchUpperBound();
        System.out.println(lower_bound + " " + (upper_bound - lower_bound));

    }

    //P: initializeBinarySearchInput(args)
    //Q: (a.length > 0 && a[a.length - 1] < x -> R == min(i): a[i] < x (i = 0 ... a.length - 1))
    //   || (a.length == 0 -> R == 0) || (a[a.length - 1] >= x -> R == a.length)
    public static int iterativeBinarySearchUpperBound() {
        if (a.length == 0) {
            //R == 0
            return 0;
        }
        if (a[a.length - 1] >= x) {
            //R == a.length
            return a.length;
        }
        // r >= 0
        int l = -1, r = a.length - 1;
        //I: a[l] >= x && a[r] < x && l <= l' && r' <= r && r >= 0
        //   && r <= a.length - 1 && l >= -1 && l <= a.length - 1
        while (r - l > 1) {
            //I && m' >= 0 && m' < a.length
            int m = (r - l)/2 + l;
            //I
            if (a[m] < x) {
                // I && r' == m
                r = m;
            } else {
                //I && l' == m
                l = m;
            }
        }
        // I & r - l <= 1 => r = min(i): a[i] < x
        //R = r
        return r;
    }


}
