package com.github.singals.icecreammeter.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.singals.icecreammeter.dto.TeamDto;
import com.github.singals.icecreammeter.entity.TeamEntity;
import com.github.singals.icecreammeter.exceptions.BadRequestException;
import com.github.singals.icecreammeter.repository.TeamRepository;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public TeamDto createTeam(TeamDto teamDto) {
        TeamEntity team = teamDto.toEntity();
        team = teamRepository.save(team);
        return TeamDto.fromEntity(team);
    }

    public TeamDto getTeam(UUID id) {
        TeamEntity team = teamRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("invalid team id " + id.toString()));
        return TeamDto.fromEntity(team);
    }

    public List<TeamDto> getTeams() {
        return teamRepository.findAll().stream()
        .map(TeamDto::fromEntity)
        .toList();
    }

    public TeamDto updateTeam(TeamDto teamDto) {
        UUID teamId = teamDto.getId();
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BadRequestException("invalid team id " + teamId.toString()));

        team.setName(teamDto.getName());
        team.setThreshold(teamDto.getThreshold());
        team = teamRepository.save(team);
        return TeamDto.fromEntity(team);
    }

    public void deleteTeam(UUID teamId) {
        TeamEntity team = teamRepository.findById(teamId)
                .orElseThrow(() -> new BadRequestException("invalid team id " + teamId.toString()));

        team.setIsDeleted(true);

        teamRepository.save(team);
    }
}
