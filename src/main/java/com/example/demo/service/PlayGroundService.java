package com.example.demo.service;

import com.example.demo.dto.PlayGroundDto;
import com.example.demo.entity.PlayGround;

import java.util.List;

public interface PlayGroundService {

    List<PlayGroundDto> getAllPlayGrounds();

    PlayGroundDto getPlayGroundByEquipId(Integer equipId);

    PlayGroundDto savePlayGround(PlayGroundDto playGroundDto);

    PlayGroundDto updatePlayGroundByEquipId(Integer equipId, PlayGroundDto playGroundDto);

    void deletePlayGroundByEquipId(Integer equipId);

    PlayGroundDto entityToDto(PlayGround playGround);

    PlayGround dtoToEntity(PlayGroundDto playGroundDto);
}
