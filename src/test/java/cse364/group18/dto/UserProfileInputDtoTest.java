package cse364.group18.dto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class UserProfileInputDtoTest {

    @Test
    public void userCreateTest() {
        String gender = "F";
        String age = "56";
        String occupationName = "Artist";
        String genres = "Action";

        List<String> genreList = new ArrayList<>();
        genreList.add("Action");


        UserProfileInputDto inputDto = new UserProfileInputDto(gender, age, occupationName, genres);
        System.out.println(inputDto.getGender());
        System.out.println(inputDto.getAgeGroup());
        System.out.println(inputDto.getOccupationNum());
        System.out.println(inputDto.getGenres());

        Assertions.assertEquals(inputDto.getGender(), "F");
        Assertions.assertEquals(inputDto.getAgeGroup(), 56);
        Assertions.assertEquals(inputDto.getOccupationNum(), 2);

    }
}
