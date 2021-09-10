

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SumLong {

    public static void main(String[] args) {
        long ans = 0;
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher;

        for (int i = 0; i < args.length; i++) {
            matcher = pattern.matcher(args[i]);
            while (matcher.find()) {
                ans += Long.parseLong(args[i].substring(matcher.start(), matcher.end()));
            }
        }
        System.out.println(ans);
    }
}