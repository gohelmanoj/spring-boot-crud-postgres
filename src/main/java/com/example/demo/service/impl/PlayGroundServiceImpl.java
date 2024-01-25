package com.example.demo.service.impl;

import com.example.demo.dto.PlayGroundDto;
import com.example.demo.entity.PlayGround;
import com.example.demo.exception.NoSuchPlayGroundExistException;
import com.example.demo.repository.PlayGroundRepository;
import com.example.demo.service.PlayGroundService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayGroundServiceImpl implements PlayGroundService {

    private PlayGroundRepository playGroundRepository;

    @Override
    public List<PlayGroundDto> getAllPlayGrounds() {

        List<PlayGround> playGrounds = playGroundRepository.findAll();
        return playGrounds.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    @Override
    public PlayGroundDto getPlayGroundByEquipId(Integer equipId) {

        Optional<PlayGround> playGround = playGroundRepository.findById(equipId);
        if (playGround.isEmpty()) {

            throw new NoSuchPlayGroundExistException(String.format("Playground is not exist with equipId as %s", equipId));
        }
        return entityToDto(playGround.get());
    }

    @Override
    public PlayGroundDto savePlayGround(PlayGroundDto playGroundDto) {

        PlayGround playGround = dtoToEntity(playGroundDto);
        playGround = playGroundRepository.save(playGround);
        return entityToDto(playGround);
    }

    @Override
    public PlayGroundDto updatePlayGroundByEquipId(Integer equipId, PlayGroundDto playGroundDto) {

        Optional<PlayGround> playGroundOptional = playGroundRepository.findById(equipId);

        if(playGroundOptional.isEmpty()) {
            throw new RuntimeException(String.format("Playground is not exist with equipId as %s", equipId));
        }

        PlayGround playGround = playGroundOptional.get();
        playGround.setColor(playGroundDto.getColor());
        playGround.setType(playGroundDto.getType());
        playGround.setLocation(playGroundDto.getLocation());
        playGround.setInstall_date(playGroundDto.getInstall_date());
        playGround = playGroundRepository.save(playGround);

        return entityToDto(playGround);
    }

    @Override
    public void deletePlayGroundByEquipId(Integer equipId) {

        Optional<PlayGround> playGroundOptional = playGroundRepository.findById(equipId);

        if(playGroundOptional.isEmpty()) {
            throw new RuntimeException(String.format("Playground is not exist with equipId as %s", equipId));
        }
        playGroundRepository.delete(playGroundOptional.get());
    }

    @Override
    public PlayGroundDto entityToDto(PlayGround playGround) {

        return new PlayGroundDto(
                playGround.getEquip_id(),
                playGround.getType(),
                playGround.getColor(),
                playGround.getLocation(),
                playGround.getInstall_date()
        );
    }

    @Override
    public PlayGround dtoToEntity(PlayGroundDto playGroundDto) {
        return new PlayGround(
                playGroundDto.getEquip_id(),
                playGroundDto.getType(),
                playGroundDto.getColor(),
                playGroundDto.getLocation(),
                playGroundDto.getInstall_date()
        );
    }
}
