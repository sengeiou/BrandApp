package brandapp.isport.com.basicres.commonutil;

public class StringUtil {
    public static String getNumberStr(String str) {
        str = str.substring(0, str.length() - 3);
        return str;
    }

    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }


}
