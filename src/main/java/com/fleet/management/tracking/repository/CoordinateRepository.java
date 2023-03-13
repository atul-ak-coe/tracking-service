package com.fleet.management.tracking.repository;

import com.fleet.management.tracking.model.CoordinateDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CoordinateRepository extends ReactiveCrudRepository<CoordinateDetails, Long> {

    Flux<CoordinateDetails> findAllByRouteIdAndTrackingId(Long routeId, Long trackingId);
}
