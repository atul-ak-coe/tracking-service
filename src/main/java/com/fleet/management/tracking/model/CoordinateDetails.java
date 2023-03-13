package com.fleet.management.tracking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;
import java.util.random.RandomGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CoordinateDetails extends Coordinate implements Persistable<Long> {

    @Id
    @Column("id")
    private Long coordinateId;

    private Long routeId;

    private Long trackingId;

    @CreatedDate
    private LocalDateTime createdDate;

    @Transient
    private boolean newCoordinateDetails;

    @Override
    public Long getId() {
        return RandomGenerator.getDefault().nextLong();
    }

    @Override
    @Transient
    public boolean isNew() {
        return this.newCoordinateDetails || coordinateId == null;
    }

    public CoordinateDetails setAsNew(){
        this.newCoordinateDetails = true;
        return this;
    }
}
