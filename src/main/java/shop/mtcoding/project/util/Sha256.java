package shop.mtcoding.project.util;

import java.security.MessageDigest;

public class Sha256 {
    public static String encode(String password1) {
        String password = "salt" + password1;
        String SHA = "";
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(password.getBytes());
            byte bytes[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            SHA = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            SHA = null;
        }
        return SHA;
    }
}
