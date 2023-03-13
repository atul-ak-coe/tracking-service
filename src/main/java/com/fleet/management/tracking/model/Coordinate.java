package com.fleet.management.tracking.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Coordinate {

    private Integer stepNum;

    private String latitude;

    private String longitude;
}
