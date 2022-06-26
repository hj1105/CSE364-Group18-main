package cse364.group18.dto;

import cse364.group18.exception.InvalidInputException;

import java.util.*;


public class UserProfileInputDto {
    /*
        1. All input arguments should be String.
        2. Any argument can be empty String.
        3. Genre input String is separable by ',' delimiter.
    */

    private final HashMap<String, Integer> VALID_OCCUPATIONS;
    private final List<String> VALID_GENRES;

    private final String gender;
    private final String age;
    private final String occupationName;
    private final String genres;

    public UserProfileInputDto(String gender, String age, String occupationName, String genres) {
        VALID_OCCUPATIONS = new HashMap<>(){{
            put("Other", 0);
            put("Academic/Educator", 1);
            put("Artist", 2);
            put("Clerical/Admin", 3);
            put("College/Grad Student", 4);
            put("Customer Service", 5);
            put("Doctor/Health Care", 6);
            put("Executive/Managerial", 7);
            put("Farmer", 8);
            put("Homemaker", 9);
            put("K-12 Student", 10);
            put("Lawyer", 11);
            put("Programmer", 12);
            put("Retired", 13);
            put("Sales/Marketing", 14);
            put("Scientist", 15);
            put("Self-Employed", 16);
            put("Technician/Engineer", 17);
            put("Tradesman/Craftsman", 18);
            put("Unemployed", 19);
            put("Writer", 20);
            put("", 21);
        }};
        String[] GENRES = new String[] {
                "Action", "Adventure", "Animation", "Comedy",
                "Crime", "Documentary", "Drama", "Fantasy",
                "Film-Noir", "Horror", "Musical", "Mystery",
                "Romance", "Sci-Fi", "Thriller", "War",
                "Western", ""
        };

        VALID_GENRES = Arrays.asList(GENRES);
        this.gender = gender;
        this.age = age;
        this.occupationName = occupationName;
        this.genres = genres;
        checkValidity();

    }

    public String getGender() {
        return gender.toUpperCase();
    }
    public int getAgeGroup() {
        int ageGroup = -1;

        if (age.length() > 0) {
            int age_input = Integer.parseInt(age);

            if (age_input < 18) {
                ageGroup = 1;
            } else if (age_input <= 24) {
                ageGroup = 18;
            } else if (age_input <= 34) {
                ageGroup = 25;
            } else if (age_input <= 44) {
                ageGroup = 35;
            } else if (age_input <= 49) {
                ageGroup = 45;
            } else if (age_input <= 55) {
                ageGroup = 50;
            } else {
                ageGroup = 56;
            }
        }
        return ageGroup;
    }

    public int getOccupationNum() {
        /*
            If occupation input is empty string, 21 is returned.
         */
        return VALID_OCCUPATIONS.get(occupationName);
    }
    public List<String> getGenres() {
        /*
            If genre input is empty string, empty list is returned.
         */
        List<String> genreList;
        if (genres.length() > 0)
            genreList = Arrays.asList(genres.split(","));
        else
            genreList = Collections.emptyList();

        return genreList;
    }

    private void checkValidity() throws InvalidInputException {
        checkValidGender(gender);
        checkValidAge(age);
        checkValidOccupationName(occupationName);
        String[] genreList = genres.split(",");

        for (String genre : genreList) {
            checkValidGenre(genre);
        }
    }

    private void checkValidGender(String gender) throws InvalidInputException {
        if (gender.length() == 0) {
            return;
        }

        String Gender = gender.toUpperCase();

        if (!(Gender.equals("M") || Gender.equals("F")))
            throw new InvalidInputException("Invalid gender: ", gender);
    }

    private void checkValidAge(String age) throws InvalidInputException {
        /*
            1. Empty age is allowed.
            2. Age >= 0
         */
        int numAge;
        if (age.length() == 0) {
            return;
        }

        try {
            numAge = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid format: ", age);
        }

        if (numAge < 0) {
            throw new InvalidInputException("Invalid age range: ", age);
        }
    }

    private void checkValidOccupationName(String occupationName) throws InvalidInputException {
        if (occupationName.length() == 0) {
            return;
        }

        if (!VALID_OCCUPATIONS.containsKey(occupationName)) {
            throw new InvalidInputException("Invalid occupation: ", occupationName);
        }
    }

    private void checkValidGenre(String genre) {
        if (genre.length() == 0) {
            return;
        }

        if (!VALID_GENRES.contains(genre)) {
            throw new InvalidInputException("Invalid genre: ", genre);
        }
    }
}



