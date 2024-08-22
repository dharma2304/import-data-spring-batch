package com.example.SpringBatchLearning.BatchConfig;

import com.example.SpringBatchLearning.Entity.CricketMatch;
import com.example.SpringBatchLearning.Repository.MatchesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    private  final MatchesRepository matchesRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Autowired
    public BatchConfig(MatchesRepository matchesRepository, JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        this.matchesRepository = matchesRepository;
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    private final String[] fieldNames = {
            "id", "season", "city", "date", "match_type",
            "player_of_match", "venue", "team1", "team2",
            "toss_winner", "toss_decision", "winner",
            "result", "result_margin", "target_runs",
            "target_overs", "super_over", "method",
            "umpire1", "umpire2"
    };

    @Bean
    public FlatFileItemReader<CricketMatch> itemReader(){
        return new FlatFileItemReaderBuilder<CricketMatch>()
                .name("IplMatchesReader")
                .resource(new FileSystemResource("src/main/resources/matches.csv"))
                .delimited()
                .names(fieldNames)
                .linesToSkip(1)
                .targetType(CricketMatch.class)
                .build();
    }

    @Bean
    public MatchProcessor processor() {
        return new MatchProcessor();
    }


    @Bean
    public RepositoryItemWriter<CricketMatch> itemWriter(){
        RepositoryItemWriter<CricketMatch> writer = new RepositoryItemWriter<>();
        writer.setRepository(matchesRepository);
        writer.setMethodName("save");
        return writer;
    }

     @Bean
    public Step importStep(){

        return new StepBuilder("MatchesImportStep", jobRepository)
                .<CricketMatch, CricketMatch>chunk(50, platformTransactionManager)
                .reader(itemReader())
                .processor(processor())
                .writer(itemWriter())
                .build();
     }


     @Bean
     public Job runjob(){
        return new JobBuilder("MatchesImport", jobRepository)
                .start(importStep())
                .build();
     }


}
