package com.fleet.management.tracking.router;

import com.fleet.management.tracking.constant.APIConstant;
import com.fleet.management.tracking.handler.TrackingDetailsHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import javax.validation.constraints.NotNull;

@Configuration
@RequiredArgsConstructor
public class TrackingRouter {

    @Value("${server.context-path}")
    private String contextPath;

    @NotNull
    private final TrackingDetailsHandler trackingDetailsHandler;

    @Bean
    public RouterFunction<ServerResponse> trackingService() {
        RouterFunction<ServerResponse> trackingServiceRoute = initAddTracking();

        return RouterFunctions.nest(RequestPredicates.path(contextPath), trackingServiceRoute);
    }

    private RouterFunction<ServerResponse> initAddTracking() {
        return RouterFunctions.route(
                RequestPredicates.POST(APIConstant.API_ADD_TRACKING), trackingDetailsHandler::addTrackingDetails
        );
    }
}
