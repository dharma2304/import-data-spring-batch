package com.example.SpringBatchLearning.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class CricketMatch {

    @Id
    private Integer id;

    private String season;

    private String city;

    private String date;

    private String match_type;

    private String player_of_match;

    private String venue;

    private String team1;

    private String team2;

    private String toss_winner;

    private String toss_decision;

    private String winner;

    private String result;

    private String result_margin;

    private String target_runs;

    private String target_overs;

    private String super_over;

    private String method;

    private String umpire1;

    private String umpire2;

}
