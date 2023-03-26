package shop.mtcoding.project.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import shop.mtcoding.project.config.exception.CustomException;

public class PathUtil {

    static String realFileName = null ;

    private static String getStaticFolder() {
        return System.getProperty("user.dir") + "\\src\\main\\resources\\static\\";
    }

    public static String writeImageFile(MultipartFile profile) {
        UUID uuid = UUID.randomUUID();
        String uuidImageDBName = "/images/" + uuid + "_" + profile.getOriginalFilename();
        System.out.println("테스트 : profile.getOriginalFilename "  + profile.getOriginalFilename());
        String uuidImageRealName = "\\images\\" + uuid + "_" + profile.getOriginalFilename();
        System.out.println("테스트 : uuidImageDBName "  + uuidImageDBName);
        System.out.println("테스트 : uuidImageRealName "  + uuidImageRealName);

        String staticFolder = getStaticFolder();
        System.out.println("테스트 : staticFolder" + staticFolder);

        Path imageFilePath = Paths.get(staticFolder + "\\" + uuidImageRealName);
        System.out.println("테스트 : imageFilePath " + imageFilePath);
        try {
            Files.write(imageFilePath, profile.getBytes()); // 내부적으로 비동기. .. 스레드가 있음
        } catch (Exception e) {
            throw new CustomException("사진을 웹서버에 저장하지 못하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return uuidImageDBName;
    }
    
    public static String writeImageFile2(String base64EncodedImage, String fileName){
        // String base64String = 이미지추출(base64EncodedImage);
        byte[] imageBytes = Base64.getDecoder().decode(base64EncodedImage.split(",")[1]);
        
        UUID uuid = UUID.randomUUID();
        String uuidImageDBName = "/images/" + uuid + "_" + fileName;
        String uuidImageRealName = "\\images\\" + uuid + "_" + fileName;
        String staticFolder = getStaticFolder();
        Path imageFilePath = Paths.get(staticFolder + "\\" + uuidImageRealName);
        
        try {
            Files.write(imageFilePath, imageBytes); 
        } catch (Exception e) {
            throw new CustomException("사진을 웹서버에 저장하지 못하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return uuidImageDBName;

        // try (OutputStream outputStream = new FileOutputStream(new File("C:/Users/piw94/Documents/image.jpg"))) {
        //     outputStream.write(imageBytes);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }
}