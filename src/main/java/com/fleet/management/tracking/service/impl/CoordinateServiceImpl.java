package com.fleet.management.tracking.service.impl;

import com.fleet.management.tracking.model.CoordinateDetails;
import com.fleet.management.tracking.repository.CoordinateRepository;
import com.fleet.management.tracking.service.CoordinateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.random.RandomGenerator;

@Service
@RequiredArgsConstructor
public class CoordinateServiceImpl implements CoordinateService {

    @NotNull
    private final CoordinateRepository coordinateRepository;

    @Override
    public Mono<CoordinateDetails> save(CoordinateDetails coordinateDetails) {
        return coordinateRepository.save(coordinateDetails);
    }

    @Override
    public Mono<CoordinateDetails> getById(Long coordinateId) {
        return coordinateRepository.findById(coordinateId);
    }

    @Override
    public Flux<CoordinateDetails> getAll(Long routeId, Long trackingId) {
        return coordinateRepository.findAllByRouteIdAndTrackingId(routeId, trackingId);
    }
}
