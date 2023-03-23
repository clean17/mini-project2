package shop.mtcoding.project;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class tttt {

    @Test
    public List<String> ete_test(List<Integer> input) throws Exception {

        List<Integer> list = Arrays.asList(3, 4, 6, 7);

        List<String> result = list.stream().map(number -> {
            switch (number) {
                case 1:
                    return "가";
                case 2:
                    return "나";
                case 3:
                    return "다";
                case 4:
                    return "라";
                case 5:
                    return "마";
                case 6:
                    return "가2";
                case 7:
                    return "가44";
                case 8:
                    return "가5";
                case 9:
                    return "가ㅇㅇ";
                case 10:
                    return "가7";
                default:
                    return "가8";
            }
        }).collect(Collectors.toList());

        // System.out.println(result);
        return result;
    }
}
