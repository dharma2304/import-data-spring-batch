package com.example.SpringBatchLearning.BatchConfig;

import com.example.SpringBatchLearning.Entity.CricketMatch;
import org.springframework.batch.item.ItemProcessor;

public class MatchProcessor implements ItemProcessor<CricketMatch, CricketMatch> {

    @Override
    public CricketMatch process(CricketMatch match) throws Exception {
        return match;
    }
}
