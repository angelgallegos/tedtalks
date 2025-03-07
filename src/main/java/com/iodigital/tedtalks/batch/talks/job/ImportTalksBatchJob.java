package com.iodigital.tedtalks.batch.talks.job;

import com.iodigital.tedtalks.batch.talks.mappers.SpeakerFieldSetMapper;
import com.iodigital.tedtalks.batch.talks.mappers.TalkFieldSetMapper;
import com.iodigital.tedtalks.batch.talks.processors.SpeakerItemProcessor;
import com.iodigital.tedtalks.batch.talks.processors.TalkItemProcessor;
import com.iodigital.tedtalks.dto.SpeakerDTO;
import com.iodigital.tedtalks.dto.TalkDTO;
import com.iodigital.tedtalks.entity.Speaker;
import com.iodigital.tedtalks.entity.Talk;
import com.iodigital.tedtalks.repositories.SpeakerRepository;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ImportTalksBatchJob {

    private final EntityManagerFactory entityManagerFactory;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    private final SpeakerRepository speakerRepository;

    public static String NAME = "importTalksJob";


    @Bean(name = "importTalksJob")
    public Job importTalksJob() {
        return new JobBuilder(NAME, jobRepository)
                .start(importSpeakersStep(jobRepository, transactionManager, entityManagerFactory))
                .next(importTalksStep(jobRepository, transactionManager, entityManagerFactory))
                .build();
    }

    @Bean
    public Step importTalksStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        EntityManagerFactory emf
    ) {
        return new StepBuilder("importTalksStep", jobRepository)
                .<TalkDTO, Talk>chunk(100, transactionManager)
                .reader(flatFileItemReader(null))
                .processor(talkProcessor())
                .writer(talkWriter(emf))
                .faultTolerant()
                .skip(Throwable.class)
                .build();
    }

    @Bean
    public Step importSpeakersStep(
        JobRepository jobRepository,
        PlatformTransactionManager transactionManager,
        EntityManagerFactory emf
    ) {
        return new StepBuilder("importSpeakersStep", jobRepository)
                .<SpeakerDTO, Speaker>chunk(100, transactionManager)
                .reader(speakerFlatFileItemReader(null))
                .processor(speakerProcessor())
                .writer(speakerWriter(emf))
                .faultTolerant()
                .skipLimit(100)
                .skip(Throwable.class)
                .build();
    }

    @Bean
    public ItemProcessor<TalkDTO, Talk> talkProcessor() {
        return new TalkItemProcessor(speakerRepository);
    }

    @Bean
    public ItemProcessor<SpeakerDTO, Speaker> speakerProcessor() {
        return new SpeakerItemProcessor(speakerRepository);
    }

    @Bean
    public JpaItemWriter<Talk> talkWriter(EntityManagerFactory emf) {
        JpaItemWriter<Talk> talkWriter = new JpaItemWriter<Talk>();
        talkWriter.setEntityManagerFactory(emf);

        return talkWriter;
    }

    @Bean
    public JpaItemWriter<Speaker> speakerWriter(EntityManagerFactory emf) {
        JpaItemWriter<Speaker> speakerWriter = new JpaItemWriter<Speaker>();
        speakerWriter.setEntityManagerFactory(emf);

        return speakerWriter;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<TalkDTO> flatFileItemReader(@Value("#{jobParameters['FILE_NAME']}") String fileName){
        FlatFileItemReader<TalkDTO> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("DEVAL");
        flatFileItemReader.setLinesToSkip(1);
        Resource inputFile = new ClassPathResource(fileName);
        flatFileItemReader.setResource(inputFile);
        flatFileItemReader.setLineMapper(talkLineMapper());
        return flatFileItemReader;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<SpeakerDTO> speakerFlatFileItemReader(@Value("#{jobParameters['FILE_NAME']}") String fileName){
        FlatFileItemReader<SpeakerDTO> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setName("DEVAL");
        flatFileItemReader.setLinesToSkip(1);
        Resource inputFile = new ClassPathResource(fileName);
        flatFileItemReader.setResource(inputFile);
        flatFileItemReader.setLineMapper(speakerLineMapper());
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<TalkDTO> talkLineMapper() {
        DefaultLineMapper<TalkDTO> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("title","author","date","views","likes","link");
        lineTokenizer.setStrict(false);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(new TalkFieldSetMapper());

        return defaultLineMapper;
    }

    @Bean
    public LineMapper<SpeakerDTO> speakerLineMapper() {
        DefaultLineMapper<SpeakerDTO> defaultLineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setNames("title","author","date","views","likes","link");
        lineTokenizer.setStrict(false);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(new SpeakerFieldSetMapper());

        return defaultLineMapper;
    }
}
