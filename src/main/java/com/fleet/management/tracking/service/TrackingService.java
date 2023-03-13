package com.fleet.management.tracking.service;

import com.fleet.management.tracking.dto.TrackingDetailsDTO;
import reactor.core.publisher.Mono;

public interface TrackingService {

    Mono<TrackingDetailsDTO> save(TrackingDetailsDTO trackingDetailsDTO);
}
