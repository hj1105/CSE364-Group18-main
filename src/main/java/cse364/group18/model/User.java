package cse364.group18.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@NoArgsConstructor
@Setter
@Getter
public class User {

    private Long userId;
    private String gender;
    private int ageGroup;
    private int occupation;
    private String zipcode;

    public User(Long userId, String gender, int ageGroup, int occupation, String zipcode) {
        this.userId = userId;
        this.gender = gender;
        this.ageGroup = ageGroup;
        this.occupation = occupation;
        this.zipcode = zipcode;
    }
}