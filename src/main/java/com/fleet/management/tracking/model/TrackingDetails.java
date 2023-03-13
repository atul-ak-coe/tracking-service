package com.fleet.management.tracking.model;

import com.fleet.management.tracking.constant.FuelLevel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;

import java.util.List;
import java.util.random.RandomGenerator;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Builder
public class TrackingDetails implements Persistable<Long> {

    @Id
    @Column("id")
    private Long trackingId;

    private Long routeId;

    private Boolean isDiversion;

    private FuelLevel fuelLevel;

    private Boolean emergencyServiceRequire;

    private Double speedRate;

    private List<Long> coordinates;

    @Transient
    private boolean newTrackingDetails;

    @Override
    public Long getId() {
        if(trackingId == null) {
            return RandomGenerator.getDefault().nextLong();
        } else {
            return trackingId;
        }
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.newTrackingDetails || trackingId == null;
    }

    public TrackingDetails setAsNew(){
        this.newTrackingDetails = false;
        return this;
    }
}
