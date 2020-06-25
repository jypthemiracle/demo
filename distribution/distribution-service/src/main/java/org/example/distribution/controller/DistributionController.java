package org.example.distribution.controller;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.example.distribution.sdo.DistributionCdo;
import org.example.distribution.sdo.DistributionRdo;
import org.example.distribution.service.FlowService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("distributions")
public class DistributionController {
    private final FlowService flowService;

    @PostMapping
    public String distribution(
        @RequestHeader("X-USER-ID") int userKey,
        @RequestHeader("X-ROOM-ID") String roomKey,
        @Valid @RequestBody DistributionCdo distributionCdo
    ) {
        return flowService.distribution(userKey, roomKey, distributionCdo);
    }

    @GetMapping("{token}")
    public DistributionRdo lookup(
        @RequestHeader("X-USER-ID") int userKey,
        @RequestHeader("X-ROOM-ID") String roomKey,
        @PathVariable("token") String token
    ) {
        return flowService.lookup(userKey, roomKey, token);
    }

    @PutMapping("{token}/receive")
    public int receive(
        @RequestHeader("X-USER-ID") int userKey,
        @RequestHeader("X-ROOM-ID") String roomKey,
        @PathVariable("token") String token
    ) {
        return flowService.receive(userKey, roomKey, token);
    }

}
