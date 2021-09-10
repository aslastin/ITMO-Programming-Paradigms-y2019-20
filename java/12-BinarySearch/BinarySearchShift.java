package search;

public class BinarySearchShift {
    //IM: ∀i = [0 : a.length): a'[i] == a[i]
    //IM_ARGS: ∀i = [0 : args.length): args'[i] == args[i]

    //PRE:  ∃!k :∀i1 = [0 : k - 1) : (int)args[i1] < (int)args[i1 + 1] /\
    //           ∀i2 = [k : args.length - 1) : (int)args[i2] < (int)args[i2 + 1] < (int)args[i] ∀i = [0 : k)
    //POST: System.out.println(ℝ)
    //      (args.length == 0 -> ℝ = 0)
    //      (args.length > 0 -> (index: (int)args[index] = max((int)args[i]) ∀i = [0 : args.length)) -> ℝ = (index + 1) % args.length)
    public static void main(String[] args) {
        //a.length == args.length /\ ∃!k :∀i1 = [0 : k - 1) : a[i1] < a[i1 + 1] /\
        //                                ∀i2 = [k : a.length - 1) : a[i2] < a[i2 + 1] < a[i] ∀i = [0 : k)
        int[] a = initialize(args);
        //index' = ℝ /\ IM_ARGS
        int index = findMaxIndexIterative(a);
        //PRE:  index: index >= 0 /\ index < a.length /\
        //                   a.length > 0: (a[index] = max(a[i]) ∀i = [0 : a.length))
        //                   a.length == 0: index = 0
        //POST: IM_ARGS /\ IM /\ index' == index
        int shift = a.length > 0 ? (index + 1) % a.length : 0;
        //IM /\ IM_ARGS /\ shift' == shift /\ index' == index
        System.out.println(shift);
    }
    //PRE_1:  args != null
    //POST_1: ℝ.length = args.length /\ ∀i = [0 : ℝ.length): ℝ[i] = Integer.parseInt(args[i]) /\ IM_ARGS
    public static int[] initialize(String[] args) {
        //a.length == args.length /\ ∀i = [0 : a.length): a[i] = 0 /\ IM_ARGS
        int[] a = new int[args.length];
        //I: ∀s = [0 : a.length) /\ s < i : a[s] = Integer.parseInt(args[s]) /\ IM_ARGS
        for (int i = 0; i < args.length; i++) {
            //I /\ a[i] = 0
            a[i] = Integer.parseInt(args[i]);
            //I /\ a'[i] = Integer.parseInt(args[i])
        }
        //I /\ i == args.length
        return a;
    }
    //PRE_2:   ∃!k :∀i1 = [0 : k - 1) : a[i1] < a[i1 + 1] /\
    //              ∀i2 = [k : a.length - 1) : a[i2] < a[i2 + 1] < a[i] ∀i = [0 : k)
    //POST_2:  ℝ : ℝ >= 0 /\ ℝ < a.length /\ IM /\
    //             a.length > 0 -> a[ℝ] = max(a[i]) ∀i = [0 : a.length)
    //             a.length == 0 ->  ℝ = 0
    public static int findMaxIndexIterative(int[] a) {
        //IM /\ l' = 0 /\ r' = a.length
        int l = 0, r = a.length;
        //I: a[l'] > a[l] /\ a[r'] < a[r] /\ l >= 0 && l < a.length /\ r > 0 /\ r <= a.length /\ IM /\ l < r
        while (r - l > 1) {
            //I /\ m' >= 0 /\ m' < a.length
            int m = (l + r) >>> 1;
            //I
            if (a[m] > a[l]) {
                // I /\ l' = m
                l = m;
            } else {
                //I /\ r' = m
                r = m;
            }
            //I
        }
        // I /\ (r - l <= 1) -> a[l] = max(a[i]) ∀i = [0 : a.length)
        return l;
    }

    //PRE_3: ∃!k :∀i1 = [l : k - 1) : a[i1] < a[i1 + 1] /\
    //            ∀i2 = [k : r - 1) : a[i2] < a[i2 + 1] < a[i] ∀i = [l : k) /\ l >= 0 /\ r <=  a.length /\ r > 0 /\ l < a.length /\ l < r
    //POST_3:  ℝ : ℝ >= 0 /\ ℝ < a.length /\ IM /\
    //             a.length > 0 -> a[ℝ] = max(a[i]) ∀i = [0 : a.length)
    //             a.length == 0 ->  ℝ = 0
    public static int findMaxIndexRecursive(int[] a, int l, int r) {
        //I: a[l'] > a[l] /\ a[r'] < a[r] /\ l >= 0 /\ l < a.length /\ r > 0 /\ r <= a.length /\ IM
        if (r - l <= 1) {
            //I /\ ℝ = l
            return l;
        }
        //I /\ m' >= 0 /\ m' < a.length
        int m = (l + r) >>> 1;
        //I
        if (a[m] > a[l]) {
            //I /\ l' = m
            return findMaxIndexRecursive(a, r, m);
        } else {
            //I /\ r' = m
            return findMaxIndexRecursive(a, l, m);
        }
    }
}
