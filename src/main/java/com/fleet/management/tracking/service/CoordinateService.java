package com.fleet.management.tracking.service;

import com.fleet.management.tracking.model.CoordinateDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CoordinateService {

    Mono<CoordinateDetails> save(CoordinateDetails coordinateDetails);

    Mono<CoordinateDetails> getById(Long coordinateId);

    Flux<CoordinateDetails> getAll(Long routeId, Long trackingId);
}
