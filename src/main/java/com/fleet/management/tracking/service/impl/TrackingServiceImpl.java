package com.fleet.management.tracking.service.impl;

import com.fleet.management.tracking.dto.TrackingDetailsDTO;
import com.fleet.management.tracking.model.Coordinate;
import com.fleet.management.tracking.model.CoordinateDetails;
import com.fleet.management.tracking.model.TrackingDetails;
import com.fleet.management.tracking.repository.TrackingRepository;
import com.fleet.management.tracking.service.CoordinateService;
import com.fleet.management.tracking.service.TrackingService;
import com.fleet.management.tracking.service.delegate.ProducerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService {

    @NotNull
    private final TrackingRepository trackingRepository;

    @NotNull
    private final CoordinateService coordinateService;

    @NotNull
    private final ProducerService producerService;

    @NotNull
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Mono<TrackingDetailsDTO> save(TrackingDetailsDTO tracking) {
        return Mono.just(tracking)
                .filter(t -> !ObjectUtils.isEmpty(t.getCoordinate()))
                .flatMapMany(this::addCoordinateDetails)
                .collectList()
                .switchIfEmpty(Mono.defer(() -> Mono.just(Collections.emptyList())))
                .flatMap(coordinates -> addTrackingDetails(tracking, coordinates))
                .map(this::evaluateRoute)
                .map(t -> modelMapper.map(t, TrackingDetailsDTO.class));
    }

    private TrackingDetails evaluateRoute(TrackingDetails trackingDetails) {
        producerService.send(trackingDetails);
        return trackingDetails;
    }

    private Mono<TrackingDetails> addTrackingDetails(TrackingDetailsDTO trackingDetailsDTO, List<CoordinateDetails> coordinates) {
        return Mono.just(this.getTrackingDetails(trackingDetailsDTO, coordinates))
                .flatMap(trackingRepository::save);
    }
    private TrackingDetails getTrackingDetails(TrackingDetailsDTO trackingDetailsDTO, List<CoordinateDetails> coordinates) {
        TrackingDetails trackingDetails = modelMapper.map(trackingDetailsDTO, TrackingDetails.class);
        if(CollectionUtils.isEmpty(coordinates)) {
            return trackingDetails;
        }

        List<Long> coordinateIds = coordinates.stream()
                .map(CoordinateDetails::getCoordinateId)
                .collect(Collectors.toList());
        trackingDetails.setCoordinates(coordinateIds);

        return trackingDetails;
    }

    private Flux<Coordinate> getCoordinates(CoordinateDetails coordinateDetails) {
        return coordinateService.getAll(coordinateDetails.getRouteId(), coordinateDetails.getTrackingId())
                .map(this::getCoordinate);
    }

    private Coordinate getCoordinate(CoordinateDetails coordinateDetails) {
        Coordinate coordinate = Coordinate.builder().latitude(coordinateDetails.getLatitude())
                .longitude(coordinateDetails.getLongitude()).stepNum(coordinateDetails.getStepNum()).build();
        return coordinate;
    }

    private Mono<CoordinateDetails> addCoordinateDetails(TrackingDetailsDTO trackingDetailsDTO) {
        return Mono.just(this.getCoordinateDetails(trackingDetailsDTO))
                .flatMap(coordinateService::save);
    }

    private CoordinateDetails getCoordinateDetails(TrackingDetailsDTO trackingDetailsDTO) {
        CoordinateDetails coordinateDetails = modelMapper.map(trackingDetailsDTO.getCoordinate(), CoordinateDetails.class);
        coordinateDetails.setTrackingId(trackingDetailsDTO.getTrackingId());
        coordinateDetails.setRouteId(trackingDetailsDTO.getRouteId());
        return coordinateDetails;
    }
}
