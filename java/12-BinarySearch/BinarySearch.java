package HW.HW14.search;

public class BinarySearch {
    public static int x;
    public static int[] a;

    public static void main(String[] args) {

        initializeBinarySearchInput(args);
        //index == R
        int index = recursiveBinarySearchLowerBound(-1, a.length - 1);

        System.out.println(index);
    }

    //P: args.length > 0 && args[0] == "x" &&
    //   (numeric representation) args[1] >= args[2] >= ... >= args[args.length - 1]
    //Q: x = (int)"x" && a.length == args.length - 1 &&
    //   a[0] = (int)args[1] && a[1] = (int)args[2] && ... && a[a.length - 1] = (int)args[args.length - 1] &&
    //   a[0] >= a[1] >= ... >= a[a.length - 1]
    public static void initializeBinarySearchInput(String[] args) {
        // x = (int)args[0]
        x = Integer.parseInt(args[0]);
        // a.length == args.length - 1
        a = new int[args.length - 1];

        int n = a.length - 1;
        //I: k > n && k < a.length && a[k] == (int)args[k + 1] && a[k + 1] == (int)args[k + 2] ... a[a.length - 1] == (int)args[a.length]
        while (n >= 0) {
            // I
            a[n] = Integer.parseInt(args[n + 1]);
            // n' = n - 1
            n--;
            //I
        }
        //I && n < 0 -> a[0] == (int)args[1] && a[1] == (int)args[2] && ... && a[a.length - 1] == (int)args[a.length]
    }

    //P: initializeBinarySearchInput(args)
    //Q: (a.length > 0 && a[a.length - 1] <= x -> R == min(i): a[i] <= x (i = 0 ... a.length - 1))
    //   || (a.length == 0 -> R == 0) || (a[a.length - 1] > x -> R == a.length)
    public static int iterativeBinarySearchLowerBound() {
        if (a.length == 0) {
            //R == 0
            return 0;
        }
        if (a[a.length - 1] > x) {
            //R == a.length
            return a.length;
        }
        // r >= 0
        int l = -1, r = a.length - 1;
        //I: a[l] > x && a[r] <= x && l <= l' && r' <= r && r >= 0
        //   && r <= a.length - 1 && l >= -1 && l <= a.length - 1
        while (r - l > 1) {
            //I && m' >= 0 && m' < a.length
            int m = (r - l)/2 + l;
            //I
            if (a[m] <= x) {
                // I && r' == m
                r = m;
            } else {
                //I && l' == m
                l = m;
            }
        }
        // I & r - l <= 1 => r = min(i): a[i] <= x
        //R = r
        return r;
    }


    //P: initializeBinarySearchInput(args) && l == -1 && r == a.length - 1
    //Q: (a.length > 0 && a[a.length - 1] <= x => R = min(i): a[i] <= x (i = 0 ... a.length - 1))
    //   || (a.length => R = 0) || (a[a.length - 1] > x => R = a.length)
    public static int recursiveBinarySearchLowerBound(int l, int r) {
        //I: a[l] > x && a[r] <= x && l <= l' && r' <= r && r >= 0
        //   && r <= a.length - 1 && l >= -1 && l <= a.length - 1

        if (a.length == 0) {
            //R = 0
            return 0;
        }
        if (a[a.length - 1] > x) {
            //R = a.length
            return a.length;
        }
        if (r - l <= 1) {
            //R = r
            return r;
        }

        //I && m' >= 0 && m' <= a.length - 1
        int m = (r - l)/2 + l;

        //I
        if (a[m] <= x) {
            //I && R = min(i): a[i] <= x && i = (l; m]
            return recursiveBinarySearchLowerBound(l, m);
        } else {
            //I && R = min(i): a[i] <= x && i = (m; r]
            return recursiveBinarySearchLowerBound(m, r);
        }
    }

}
