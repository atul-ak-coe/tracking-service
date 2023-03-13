package com.fleet.management.tracking.handler;

import com.fleet.management.tracking.constant.APIConstant;
import com.fleet.management.tracking.dto.TrackingDetailsDTO;
import com.fleet.management.tracking.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.net.URI;

@Component
@RequiredArgsConstructor
public class TrackingDetailsHandler {

    @NotNull
    private final TrackingService trackingService;

    public Mono<ServerResponse> addTrackingDetails(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TrackingDetailsDTO.class)
                .flatMap(trackingService::save)
                .flatMap(trackingDetailsDTO -> ServerResponse.created(URI.create(APIConstant.API_ADD_TRACKING))
                        .body(Mono.just(trackingDetailsDTO), TrackingDetailsDTO.class)
                );
    }
}
