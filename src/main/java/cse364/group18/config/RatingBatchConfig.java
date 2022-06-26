package cse364.group18.config;

import cse364.group18.model.Rating;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;


@Configuration
@EnableBatchProcessing
public class RatingBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public RatingBatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, MongoTemplate mongoTemplate) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.mongoTemplate = mongoTemplate;
    }

    @Bean
    public Job ratingUser() {
        return jobBuilderFactory.get("ratingUser")
                .incrementer(new RunIdIncrementer())
                .start(ratingStep1())
                .build();
    }

    @Bean
    public Step ratingStep1() {
        return stepBuilderFactory.get("ratingStep1")
                .<Rating, Rating>chunk(100)
                .reader(ratingReader())
                .writer(ratingWriter())
                .build();
    }

    @Bean
    public FlatFileItemReader<Rating> ratingReader() {
        FlatFileItemReader<Rating> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("/data/ratings.csv"));
        reader.setEncoding("UTF-8");
        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("userId", "movieId", "score", "timeStamp");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(Rating.class);
            }});
        }});
        return reader;
    }

    @Bean
    public MongoItemWriter<Rating> ratingWriter() {
        MongoItemWriter<Rating> writer = new MongoItemWriter<>();
        writer.setTemplate(mongoTemplate);
        writer.setCollection("rating");
        return writer;
    }
}