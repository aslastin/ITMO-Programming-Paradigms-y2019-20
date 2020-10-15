package HW.HW6;

public class PairIntSb {
    private int count;
    private StringBuilder sb;

    public PairIntSb() {
        count = 1;
        sb = new StringBuilder();
    }

    public int getCount() {
        return count;
    }

    public void increamentCount() {
        count++;
    }

    public void append(String string) {
        sb.append(string);
    }

    public StringBuilder getSb() {
        return sb;
    }
}
