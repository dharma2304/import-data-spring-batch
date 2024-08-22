package com.example.SpringBatchLearning.Repository;

import com.example.SpringBatchLearning.Entity.CricketMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MatchesRepository extends JpaRepository<CricketMatch, Integer> {

}
