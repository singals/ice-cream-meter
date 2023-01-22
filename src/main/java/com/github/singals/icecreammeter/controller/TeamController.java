package com.github.singals.icecreammeter.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.singals.icecreammeter.dto.TeamDto;
import com.github.singals.icecreammeter.service.TeamService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@Valid @RequestBody TeamDto teamDto) {
        return ResponseEntity.ok(teamService.createTeam(teamDto));
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDto> getTeamById(@PathVariable UUID teamId) {
        return ResponseEntity.ok(teamService.getTeam(teamId));
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamDto> updateTeam(@PathVariable UUID teamId, @Valid @RequestBody TeamDto teamDto) {
        teamDto.setId(teamId);
        return ResponseEntity.ok(teamService.updateTeam(teamDto));
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity deleteTeam(@PathVariable UUID teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.ok().build();
    }
}
