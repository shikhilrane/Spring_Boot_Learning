package com.shikhilrane.projection.learnProjection.dto;

import com.shikhilrane.projection.learnProjection.entities.type.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BloodGroupStats {
    private final BloodGroupType bloodGroupType;
    private final Long count;
}
