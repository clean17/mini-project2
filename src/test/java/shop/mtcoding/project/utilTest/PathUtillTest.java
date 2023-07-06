package shop.mtcoding.project.utilTest;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import shop.mtcoding.project.config.exception.CustomException;

public class PathUtillTest {

    @Test
    private static String getStaticFolder() {
        return System.getProperty("user.dir") + "\\src\\main\\resources\\static\\";
    }

    public static String writeImageFile() {
        UUID uuid = UUID.randomUUID();
        String uuidImageDBName = "/images/" + uuid + "_" + "74551d0c-66f0-48fb-b8dd-94d57b4a388e_aa.jpg";
        String uuidImageRealName = "images\\" + uuid + "_" + "74551d0c-66f0-48fb-b8dd-94d57b4a388e_aa.jpg";
        String staticFolder = getStaticFolder();
        System.out.println("테스트 : " + staticFolder);
        Path imageFilePath = Paths.get(staticFolder + uuidImageRealName);
        try {
            // Files.write(imageFilePath, profile.getBytes()); // 내부적으로 비동기. .. 스레드가 있음
        } catch (Exception e) {
            throw new CustomException("사진을 웹서버에 저장하지 못하였습니다.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return uuidImageDBName;
    }
}
