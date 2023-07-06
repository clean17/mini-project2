package shop.mtcoding.project.util;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import shop.mtcoding.project.config.exception.CustomApiException;
import shop.mtcoding.project.config.exception.CustomException;

public class CheckValid {
    
    public static void isNull(Object obj, String msg){
        if (ObjectUtils.isEmpty(obj)){
            throw new CustomException(msg);
        }
    }
    public static void isNull(Object obj, String msg, HttpStatus status){
        if (ObjectUtils.isEmpty(obj)){
            throw new CustomException(msg, status);
        }
    }
    public static void isNullApi(Object obj, String msg){
        if (ObjectUtils.isEmpty(obj)){
            throw new CustomApiException(msg);
        }
    }
    public static void isNullApi(Object obj, String msg, HttpStatus status){
        if (ObjectUtils.isEmpty(obj)){
            throw new CustomApiException(msg, status);
        }
    }
    
}
