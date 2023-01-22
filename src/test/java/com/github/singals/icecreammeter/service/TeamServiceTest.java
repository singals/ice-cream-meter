package com.github.singals.icecreammeter.service;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.singals.icecreammeter.dto.TeamDto;
import com.github.singals.icecreammeter.entity.TeamEntity;
import com.github.singals.icecreammeter.exceptions.BadRequestException;
import com.github.singals.icecreammeter.repository.TeamRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    private TeamDto teamDto;
    private UUID teamId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        teamDto = TeamDto.builder()
                .name("test team")
                .threshold(5)
                .build();
    }

    @Test
    void testCreateTeam() {
        ArgumentCaptor<TeamEntity> teamArgCaptor = ArgumentCaptor.forClass(TeamEntity.class);
        when(teamRepository.save(any()))
                .thenReturn(teamDto.toEntity());

        TeamDto actualDto = teamService.createTeam(teamDto);

        assertEquals(teamDto, actualDto);
        verify(teamRepository).save(teamArgCaptor.capture());
        TeamEntity savedEntity = teamArgCaptor.getValue();
        assertEquals(teamDto.getName(), savedEntity.getName());
        assertEquals(teamDto.getThreshold(), savedEntity.getThreshold());
    }

    @Test
    void testGetTeamWhenIdIsInvalid() {
        when(teamRepository.findById(eq(teamId)))
        .thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> teamService.getTeam(teamId));
    }

    @Test
    void testGetTeam() {
        TeamEntity team = teamDto.toEntity();
        team.setId(teamId);
        team.setCreatedAt(ZonedDateTime.now());
        when(teamRepository.findById(eq(teamId)))
                .thenReturn(Optional.of(team));

        TeamDto actualTeam = teamService.getTeam(teamId);

        assertEquals(team.getId(), actualTeam.getId());
        assertEquals(team.getName(), actualTeam.getName());
        assertEquals(team.getThreshold(), actualTeam.getThreshold());
        assertEquals(team.getCreatedAt(), actualTeam.getCreatedAt());
    }

    @Test
    void testGetTeamsWhenNoTeamExists() {
        when(teamRepository.findAll())
        .thenReturn(Collections.emptyList());
        
        List<TeamDto> teams = teamService.getTeams();

        assertTrue(teams.isEmpty());
    }

    @Test
    void testGetTeams() {
        TeamEntity team = teamDto.toEntity();
        team.setId(teamId);
        team.setCreatedAt(ZonedDateTime.now());
        when(teamRepository.findAll())
                .thenReturn(List.of(team));

        List<TeamDto> teams = teamService.getTeams();

        assertEquals(1, teams.size());
        assertEquals(TeamDto.fromEntity(team), teams.get(0));
    }

    @Test
    void testUpdateTeamWhenIdIsInvalid() {
        teamDto.setId(teamId);
        when(teamRepository.findById(eq(teamId)))
                .thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> teamService.updateTeam(teamDto));
        verify(teamRepository, never()).save(any());
    }

    @Test
    void testUpdateTeam() {
        ArgumentCaptor<TeamEntity> teamCaptor = ArgumentCaptor.forClass(TeamEntity.class);
        teamDto.setId(teamId);
        TeamEntity team = teamDto.toEntity();

        when(teamRepository.findById(eq(teamId)))
                .thenReturn(Optional.of(team));
        when(teamRepository.save(any())).thenReturn(team);

        TeamDto updatedTeamDto = teamService.updateTeam(teamDto);

        verify(teamRepository).save(teamCaptor.capture());
        TeamEntity updatedTeam = teamCaptor.getValue();
        assertEquals(teamDto.getId(), updatedTeam.getId());
        assertEquals(teamDto.getName(), updatedTeam.getName());
        assertEquals(teamDto.getThreshold(), updatedTeam.getThreshold());
        assertEquals(teamDto.getCreatedAt(), updatedTeam.getCreatedAt());
        assertEquals(teamDto.getUpdatedAt(), updatedTeam.getUpdatedAt());
    }

    @Test
    void testDeleteTeamWhenIdIsInvalid() {
        when(teamRepository.findById(eq(teamId)))
        .thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> teamService.deleteTeam(teamId));

        verify(teamRepository, never()).save(any());
    }

    @Test
    void testDeleteTeam() {
        ArgumentCaptor<TeamEntity> teamCaptor = ArgumentCaptor.forClass(TeamEntity.class);
        teamDto.setId(teamId);
        TeamEntity team = teamDto.toEntity();
        when(teamRepository.findById(eq(teamId)))
                .thenReturn(Optional.of(team));

        teamService.deleteTeam(teamId);

        verify(teamRepository).save(teamCaptor.capture());
        TeamEntity deletedEntity = teamCaptor.getValue();
        assertTrue(deletedEntity.getIsDeleted());
        assertEquals(team.getId(), deletedEntity.getId());
        assertEquals(team.getName(), deletedEntity.getName());
        assertEquals(team.getThreshold(), deletedEntity.getThreshold());
    }
}
