package com.github.singals.icecreammeter.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.singals.icecreammeter.dto.TeamDto;
import com.github.singals.icecreammeter.service.TeamService;

@ExtendWith(MockitoExtension.class)
public class TeamControllerTest {

    @Mock private TeamService teamService;
    @InjectMocks private TeamController teamController;

    private TeamDto teamDto;
    private UUID teamId = UUID.randomUUID();



    @BeforeEach
    void setUp() {
        teamDto = TeamDto.builder()
        .name("test team name")
        .threshold(10)
        .build();
    }

    @Test
    void testCreateTeam() {
        when(teamService.createTeam(teamDto))
        .thenReturn(teamDto);

        TeamDto actualTeamDto = teamController.createTeam(teamDto).getBody();

        assertEquals(teamDto, actualTeamDto);
        verify(teamService).createTeam(eq(teamDto));
    }

    @Test
    void testDeleteTeam() {
        doNothing().when(teamService).deleteTeam(teamId);

        teamController.deleteTeam(teamId);

        verify(teamService).deleteTeam(teamId);
    }

    @Test
    void testGetAllTeams() {
        when(teamService.getTeams()).thenReturn(List.of(teamDto));

        List<TeamDto> actualTeamDtos = teamController.getAllTeams().getBody();

        assertEquals(1, actualTeamDtos.size());
        assertEquals(teamDto, actualTeamDtos.get(0));
        verify(teamService).getTeams();
    }

    @Test
    void testGetTeamById() {
        when(teamService.getTeam(teamId)).thenReturn(teamDto);

        TeamDto actualTeamDto = teamController.getTeamById(teamId).getBody();

        assertEquals(teamDto, actualTeamDto);
        verify(teamService).getTeam(teamId);
    }

    @Test
    void testUpdateTeam() {
        teamDto.setId(teamId);
        when(teamService.updateTeam(teamDto)).thenReturn(teamDto);

        TeamDto actualTeamDto = teamController.updateTeam(teamId, teamDto).getBody();

        assertEquals(teamDto, actualTeamDto);
        verify(teamService).updateTeam(teamDto);
    }
}
