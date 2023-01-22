package com.github.singals.icecreammeter.dto;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.singals.icecreammeter.entity.TeamEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(Include.NON_NULL)
@Builder
public class TeamDto {

    private UUID id;

    @NotEmpty
    private String name;

    @Min(value = 1, message = "value must be greater than or equal to 1")
    @Max(value = 20, message = "value must be less than or equal to 20")
    private Integer threshold;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    private ZonedDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss z")
    private ZonedDateTime updatedAt;

    public static TeamDto fromEntity(TeamEntity team) {
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .threshold(team.getThreshold())
                .createdAt(team.getCreatedAt())
                .updatedAt(team.getUpdatedAt())
                .build();
    }

    public TeamEntity toEntity() {
        TeamEntity team = new TeamEntity();
        team.setId(id);
        team.setName(name);
        team.setThreshold(threshold);
        team.setCreatedAt(createdAt);
        team.setUpdatedAt(updatedAt);
        return team;
    }
}
