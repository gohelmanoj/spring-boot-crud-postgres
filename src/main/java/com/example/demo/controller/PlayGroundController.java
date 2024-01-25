package com.example.demo.controller;

import com.example.demo.dto.PlayGroundDto;
import com.example.demo.service.PlayGroundService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playground")
@AllArgsConstructor
public class PlayGroundController {

    private PlayGroundService playGroundService;

    @GetMapping
    public ResponseEntity<List<PlayGroundDto>> getAllPlayGround() {

        return ResponseEntity.ok(playGroundService.getAllPlayGrounds());
    }

    @GetMapping("/{equip_id}")
    public ResponseEntity<PlayGroundDto> getPlayGroundByEquipId(@PathVariable Integer equip_id) {

        return ResponseEntity.ok(playGroundService.getPlayGroundByEquipId(equip_id));
    }

    @PostMapping
    public ResponseEntity<PlayGroundDto> savePlayGround(@RequestBody PlayGroundDto playGroundDto) {

        return ResponseEntity.ok(playGroundService.savePlayGround(playGroundDto));
    }

    @PutMapping("/{equipId}")
    public ResponseEntity<PlayGroundDto> updatePlayGroundByEquipId(@PathVariable Integer equipId, @RequestBody PlayGroundDto playGroundDto) {

        return ResponseEntity.ok(playGroundService.updatePlayGroundByEquipId(equipId, playGroundDto));
    }

    @DeleteMapping("/{equipId}")
    public ResponseEntity<String> deletePlayGroundByEquipId(@PathVariable Integer equipId) {

        playGroundService.deletePlayGroundByEquipId(equipId);
        return ResponseEntity.ok(String.format("PlayGround Successfully Deleted by equipId as %s", equipId));
    }
}
