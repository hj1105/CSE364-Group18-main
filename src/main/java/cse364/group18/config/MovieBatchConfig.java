package cse364.group18.config;

import cse364.group18.model.Movie;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;

import java.util.Arrays;


@Configuration
@EnableBatchProcessing
public class MovieBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MovieBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MongoTemplate mongoTemplate) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.mongoTemplate = mongoTemplate;
    }

    @Bean
    public Job readMovie() {
        return jobBuilderFactory.get("readMovie")
                .incrementer(new RunIdIncrementer())
                .start(movieStep1())
                .build();
    }

    @Bean
    public Step movieStep1() {
        return stepBuilderFactory.get("movieStep1")
                .<Movie, Movie>chunk(100)
                .reader(movieReader())
                .writer(movieWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<Movie> movieReader() {
        FlatFileItemReader<Movie> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("/data/movies.csv"));
        reader.setEncoding("UTF-8");
        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("movieId", "title", "year", "rated", "released", "runtime", "genres",
                        "director", "writer", "actors", "plot", "language", "country",
                        "poster", "imdbRating", "imdbVotes", "imdbId");
            }});
            setFieldSetMapper(new MovieMapper());
        }});
        return reader;
    }

    @Bean
    public MongoItemWriter<Movie> movieWriter() {
        MongoItemWriter<Movie> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("movie");
        return writer;
    }

    public static class MovieMapper implements FieldSetMapper<Movie> {

        @Override
        public Movie mapFieldSet(FieldSet fieldSet) {
            Movie movie = new Movie();
            movie.setMovieId(fieldSet.readLong("movieId"));
            movie.setTitle(fieldSet.readString("title"));
            movie.setYear(fieldSet.readString("year"));
            movie.setRated(fieldSet.readString("rated"));
            movie.setReleased(fieldSet.readString("released"));
            movie.setRuntime(fieldSet.readString("runtime"));
            movie.setGenres(Arrays.asList((fieldSet.readString("genres").split(", "))));
            movie.setDirector(fieldSet.readString("director"));
            movie.setWriter(fieldSet.readString("writer"));
            movie.setActors(Arrays.asList((fieldSet.readString("actors").split(", "))));
            movie.setPlot(fieldSet.readString("plot"));
            movie.setLanguage(fieldSet.readString("language"));
            movie.setCountry(fieldSet.readString("country"));
            movie.setPoster(fieldSet.readString("poster"));
            movie.setImdbRating(fieldSet.readString("imdbRating"));
            movie.setImdbVotes(fieldSet.readString("imdbVotes").replace(",", ""));
            movie.setImdbId(fieldSet.readString("imdbId"));
            return movie;
        }
    }
}