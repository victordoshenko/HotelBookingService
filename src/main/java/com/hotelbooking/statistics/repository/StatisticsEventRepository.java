package com.hotelbooking.statistics.repository;

import com.hotelbooking.statistics.entity.StatisticsEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticsEventRepository extends MongoRepository<StatisticsEvent, String> {
    
    List<StatisticsEvent> findByEventType(String eventType);
    
    List<StatisticsEvent> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    
    List<StatisticsEvent> findByEventTypeAndTimestampBetween(String eventType, LocalDateTime start, LocalDateTime end);
}
