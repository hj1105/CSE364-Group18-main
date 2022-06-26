package cse364.group18.service;

import cse364.group18.dto.MovieDto;
import cse364.group18.dto.MovieTitleInputDto;
import cse364.group18.dto.UserProfileInputDto;

import java.util.List;


public interface RecommendationService {

    List<MovieDto> recommendByUserProfile(UserProfileInputDto inputDto);
    List<MovieDto> recommendByMovieTitle(MovieTitleInputDto inputDto);
}
