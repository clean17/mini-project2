package shop.mtcoding.project.util;

import java.util.Base64;

public class Base64Decoder {
    
    public static String Decode(String base64IncodingString){
       
       // Base64 디코딩
       byte[] decodedBytes = Base64.getDecoder().decode(base64IncodingString);
       String decodedString = new String(decodedBytes);
       
       return decodedString;
    }
}
