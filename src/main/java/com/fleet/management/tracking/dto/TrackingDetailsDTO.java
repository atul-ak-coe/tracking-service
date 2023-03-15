package com.fleet.management.tracking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fleet.management.tracking.constant.FuelLevel;
import com.fleet.management.tracking.model.Coordinate;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrackingDetailsDTO {

    private Long trackingId;

    private Long routeId;

    private CoordinateDetailsDTO coordinate;

    private Boolean isDiversion;

    private FuelLevel fuelLevel;

    private Boolean emergencyServiceRequire;

    private Double speedRate;

    private Map<Integer, Coordinate> coordinateList;
}
