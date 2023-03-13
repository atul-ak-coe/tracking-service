package com.fleet.management.tracking.repository;

import com.fleet.management.tracking.model.TrackingDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingRepository extends ReactiveCrudRepository<TrackingDetails, Long> {
}
