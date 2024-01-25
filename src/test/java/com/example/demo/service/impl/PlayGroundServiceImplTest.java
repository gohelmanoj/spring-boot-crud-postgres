package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.demo.dto.PlayGroundDto;
import com.example.demo.entity.PlayGround;
import com.example.demo.exception.NoSuchPlayGroundExistException;
import com.example.demo.repository.PlayGroundRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PlayGroundServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PlayGroundServiceImplTest {

    @MockBean
    private PlayGroundRepository playGroundRepository;

    @Autowired
    private PlayGroundServiceImpl playGroundServiceImpl;

    /**
     * Method under test: {@link PlayGroundServiceImpl#getAllPlayGrounds()}
     */
    @Test
    void testGetAllPlayGrounds() {
        // Arrange
        when(playGroundRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<PlayGroundDto> actualAllPlayGrounds = playGroundServiceImpl.getAllPlayGrounds();

        // Assert
        verify(playGroundRepository).findAll();
        assertTrue(actualAllPlayGrounds.isEmpty());
    }

    /**
     * Method under test: {@link PlayGroundServiceImpl#getAllPlayGrounds()}
     */
    @Test
    void testGetAllPlayGrounds2() {
        // Arrange
        PlayGround playGround = new PlayGround();
        playGround.setColor("Color");
        playGround.setEquip_id(1);
        Date install_date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        playGround.setInstall_date(install_date);
        playGround.setLocation("Location");
        playGround.setType("Type");

        ArrayList<PlayGround> playGroundList = new ArrayList<>();
        playGroundList.add(playGround);
        when(playGroundRepository.findAll()).thenReturn(playGroundList);

        // Act
        List<PlayGroundDto> actualAllPlayGrounds = playGroundServiceImpl.getAllPlayGrounds();

        // Assert
        verify(playGroundRepository).findAll();
        assertEquals(1, actualAllPlayGrounds.size());
        PlayGroundDto getResult = actualAllPlayGrounds.get(0);
        assertEquals("Color", getResult.getColor());
        assertEquals("Location", getResult.getLocation());
        assertEquals("Type", getResult.getType());
        assertEquals(1, getResult.getEquip_id().intValue());
        assertSame(install_date, getResult.getInstall_date());
    }

    /**
     * Method under test: {@link PlayGroundServiceImpl#getAllPlayGrounds()}
     */
    @Test
    void testGetAllPlayGrounds3() {
        // Arrange
        PlayGround playGround = new PlayGround();
        playGround.setColor("Color");
        playGround.setEquip_id(1);
        Date install_date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        playGround.setInstall_date(install_date);
        playGround.setLocation("Location");
        playGround.setType("Type");

        PlayGround playGround2 = new PlayGround();
        playGround2.setColor("com.example.demo.entity.PlayGround");
        playGround2.setEquip_id(2);
        Date install_date2 = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        playGround2.setInstall_date(install_date2);
        playGround2.setLocation("com.example.demo.entity.PlayGround");
        playGround2.setType("com.example.demo.entity.PlayGround");

        ArrayList<PlayGround> playGroundList = new ArrayList<>();
        playGroundList.add(playGround2);
        playGroundList.add(playGround);
        when(playGroundRepository.findAll()).thenReturn(playGroundList);

        // Act
        List<PlayGroundDto> actualAllPlayGrounds = playGroundServiceImpl.getAllPlayGrounds();

        // Assert
        verify(playGroundRepository).findAll();
        assertEquals(2, actualAllPlayGrounds.size());
        PlayGroundDto getResult = actualAllPlayGrounds.get(1);
        assertEquals("Color", getResult.getColor());
        assertEquals("Location", getResult.getLocation());
        assertEquals("Type", getResult.getType());
        PlayGroundDto getResult2 = actualAllPlayGrounds.get(0);
        assertEquals("com.example.demo.entity.PlayGround", getResult2.getColor());
        assertEquals("com.example.demo.entity.PlayGround", getResult2.getLocation());
        assertEquals("com.example.demo.entity.PlayGround", getResult2.getType());
        assertEquals(1, getResult.getEquip_id().intValue());
        assertEquals(2, getResult2.getEquip_id().intValue());
        assertSame(install_date2, getResult2.getInstall_date());
        assertSame(install_date, getResult.getInstall_date());
    }

    /**
     * Method under test: {@link PlayGroundServiceImpl#getAllPlayGrounds()}
     */
    @Test
    void testGetAllPlayGrounds4() {
        // Arrange
        when(playGroundRepository.findAll()).thenThrow(new NoSuchPlayGroundExistException("An error occurred"));

        // Act and Assert
        assertThrows(NoSuchPlayGroundExistException.class, () -> playGroundServiceImpl.getAllPlayGrounds());
        verify(playGroundRepository).findAll();
    }

    /**
     * Method under test:
     * {@link PlayGroundServiceImpl#getPlayGroundByEquipId(Integer)}
     */
    @Test
    void testGetPlayGroundByEquipId() {
        // Arrange
        PlayGround playGround = new PlayGround();
        playGround.setColor("Color");
        playGround.setEquip_id(1);
        Date install_date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        playGround.setInstall_date(install_date);
        playGround.setLocation("Location");
        playGround.setType("Type");
        Optional<PlayGround> ofResult = Optional.of(playGround);
        when(playGroundRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        PlayGroundDto actualPlayGroundByEquipId = playGroundServiceImpl.getPlayGroundByEquipId(1);

        // Assert
        verify(playGroundRepository).findById(Mockito.<Integer>any());
        assertEquals("Color", actualPlayGroundByEquipId.getColor());
        assertEquals("Location", actualPlayGroundByEquipId.getLocation());
        assertEquals("Type", actualPlayGroundByEquipId.getType());
        assertEquals(1, actualPlayGroundByEquipId.getEquip_id().intValue());
        assertSame(install_date, actualPlayGroundByEquipId.getInstall_date());
    }

    /**
     * Method under test:
     * {@link PlayGroundServiceImpl#getPlayGroundByEquipId(Integer)}
     */
    @Test
    void testGetPlayGroundByEquipId2() {
        // Arrange
        Optional<PlayGround> emptyResult = Optional.empty();
        when(playGroundRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(NoSuchPlayGroundExistException.class, () -> playGroundServiceImpl.getPlayGroundByEquipId(1));
        verify(playGroundRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link PlayGroundServiceImpl#getPlayGroundByEquipId(Integer)}
     */
    @Test
    void testGetPlayGroundByEquipId3() {
        // Arrange
        when(playGroundRepository.findById(Mockito.<Integer>any()))
                .thenThrow(new NoSuchPlayGroundExistException("An error occurred"));

        // Act and Assert
        assertThrows(NoSuchPlayGroundExistException.class, () -> playGroundServiceImpl.getPlayGroundByEquipId(1));
        verify(playGroundRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link PlayGroundServiceImpl#savePlayGround(PlayGroundDto)}
     */
    @Test
    void testSavePlayGround() {
        // Arrange
        PlayGround playGround = new PlayGround();
        playGround.setColor("Color");
        playGround.setEquip_id(1);
        Date install_date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        playGround.setInstall_date(install_date);
        playGround.setLocation("Location");
        playGround.setType("Type");
        when(playGroundRepository.save(Mockito.any())).thenReturn(playGround);

        // Act
        PlayGroundDto actualSavePlayGroundResult = playGroundServiceImpl.savePlayGround(new PlayGroundDto());

        // Assert
        verify(playGroundRepository).save(Mockito.any());
        assertEquals("Color", actualSavePlayGroundResult.getColor());
        assertEquals("Location", actualSavePlayGroundResult.getLocation());
        assertEquals("Type", actualSavePlayGroundResult.getType());
        assertEquals(1, actualSavePlayGroundResult.getEquip_id().intValue());
        assertSame(install_date, actualSavePlayGroundResult.getInstall_date());
    }

    /**
     * Method under test:
     * {@link PlayGroundServiceImpl#savePlayGround(PlayGroundDto)}
     */
    @Test
    void testSavePlayGround2() {
        // Arrange
        when(playGroundRepository.save(Mockito.any()))
                .thenThrow(new NoSuchPlayGroundExistException("An error occurred"));

        // Act and Assert
        assertThrows(NoSuchPlayGroundExistException.class, () -> playGroundServiceImpl.savePlayGround(new PlayGroundDto()));
        verify(playGroundRepository).save(Mockito.any());
    }

    /**
     * Method under test:
     * {@link PlayGroundServiceImpl#updatePlayGroundByEquipId(Integer, PlayGroundDto)}
     */
    @Test
    void testUpdatePlayGroundByEquipId() {
        // Arrange
        PlayGround playGround = new PlayGround();
        playGround.setColor("Color");
        playGround.setEquip_id(1);
        playGround.setInstall_date(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        playGround.setLocation("Location");
        playGround.setType("Type");
        Optional<PlayGround> ofResult = Optional.of(playGround);

        PlayGround playGround2 = new PlayGround();
        playGround2.setColor("Color");
        playGround2.setEquip_id(1);
        Date install_date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        playGround2.setInstall_date(install_date);
        playGround2.setLocation("Location");
        playGround2.setType("Type");
        when(playGroundRepository.save(Mockito.any())).thenReturn(playGround2);
        when(playGroundRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        PlayGroundDto actualUpdatePlayGroundByEquipIdResult = playGroundServiceImpl.updatePlayGroundByEquipId(1,
                new PlayGroundDto());

        // Assert
        verify(playGroundRepository).findById(Mockito.<Integer>any());
        verify(playGroundRepository).save(Mockito.any());
        assertEquals("Color", actualUpdatePlayGroundByEquipIdResult.getColor());
        assertEquals("Location", actualUpdatePlayGroundByEquipIdResult.getLocation());
        assertEquals("Type", actualUpdatePlayGroundByEquipIdResult.getType());
        assertEquals(1, actualUpdatePlayGroundByEquipIdResult.getEquip_id().intValue());
        assertSame(install_date, actualUpdatePlayGroundByEquipIdResult.getInstall_date());
    }

    /**
     * Method under test:
     * {@link PlayGroundServiceImpl#updatePlayGroundByEquipId(Integer, PlayGroundDto)}
     */
    @Test
    void testUpdatePlayGroundByEquipId2() {
        // Arrange
        PlayGround playGround = new PlayGround();
        playGround.setColor("Color");
        playGround.setEquip_id(1);
        playGround.setInstall_date(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        playGround.setLocation("Location");
        playGround.setType("Type");
        Optional<PlayGround> ofResult = Optional.of(playGround);
        when(playGroundRepository.save(Mockito.any()))
                .thenThrow(new NoSuchPlayGroundExistException("An error occurred"));
        when(playGroundRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(NoSuchPlayGroundExistException.class,
                () -> playGroundServiceImpl.updatePlayGroundByEquipId(1, new PlayGroundDto()));
        verify(playGroundRepository).findById(Mockito.<Integer>any());
        verify(playGroundRepository).save(Mockito.any());
    }

    /**
     * Method under test:
     * {@link PlayGroundServiceImpl#updatePlayGroundByEquipId(Integer, PlayGroundDto)}
     */
    @Test
    void testUpdatePlayGroundByEquipId3() {
        // Arrange
        Optional<PlayGround> emptyResult = Optional.empty();
        when(playGroundRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playGroundServiceImpl.updatePlayGroundByEquipId(1, new PlayGroundDto()));
        verify(playGroundRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link PlayGroundServiceImpl#deletePlayGroundByEquipId(Integer)}
     */
    @Test
    void testDeletePlayGroundByEquipId() {
        // Arrange
        PlayGround playGround = new PlayGround();
        playGround.setColor("Color");
        playGround.setEquip_id(1);
        playGround.setInstall_date(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        playGround.setLocation("Location");
        playGround.setType("Type");
        Optional<PlayGround> ofResult = Optional.of(playGround);
        doNothing().when(playGroundRepository).delete(Mockito.any());
        when(playGroundRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act
        playGroundServiceImpl.deletePlayGroundByEquipId(1);

        // Assert that nothing has changed
        verify(playGroundRepository).delete(Mockito.any());
        verify(playGroundRepository).findById(Mockito.<Integer>any());
        assertTrue(playGroundServiceImpl.getAllPlayGrounds().isEmpty());
    }

    /**
     * Method under test:
     * {@link PlayGroundServiceImpl#deletePlayGroundByEquipId(Integer)}
     */
    @Test
    void testDeletePlayGroundByEquipId2() {
        // Arrange
        PlayGround playGround = new PlayGround();
        playGround.setColor("Color");
        playGround.setEquip_id(1);
        playGround.setInstall_date(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        playGround.setLocation("Location");
        playGround.setType("Type");
        Optional<PlayGround> ofResult = Optional.of(playGround);
        doThrow(new NoSuchPlayGroundExistException("An error occurred")).when(playGroundRepository)
                .delete(Mockito.any());
        when(playGroundRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(NoSuchPlayGroundExistException.class, () -> playGroundServiceImpl.deletePlayGroundByEquipId(1));
        verify(playGroundRepository).delete(Mockito.any());
        verify(playGroundRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test:
     * {@link PlayGroundServiceImpl#deletePlayGroundByEquipId(Integer)}
     */
    @Test
    void testDeletePlayGroundByEquipId3() {
        // Arrange
        Optional<PlayGround> emptyResult = Optional.empty();
        when(playGroundRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> playGroundServiceImpl.deletePlayGroundByEquipId(1));
        verify(playGroundRepository).findById(Mockito.<Integer>any());
    }

    /**
     * Method under test: {@link PlayGroundServiceImpl#entityToDto(PlayGround)}
     */
    @Test
    void testEntityToDto() {
        // Arrange
        PlayGround playGround = new PlayGround();
        playGround.setColor("Color");
        playGround.setEquip_id(1);
        Date install_date = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        playGround.setInstall_date(install_date);
        playGround.setLocation("Location");
        playGround.setType("Type");

        // Act
        PlayGroundDto actualEntityToDtoResult = playGroundServiceImpl.entityToDto(playGround);

        // Assert
        assertEquals("Color", actualEntityToDtoResult.getColor());
        assertEquals("Location", actualEntityToDtoResult.getLocation());
        assertEquals("Type", actualEntityToDtoResult.getType());
        assertEquals(1, actualEntityToDtoResult.getEquip_id().intValue());
        assertSame(install_date, actualEntityToDtoResult.getInstall_date());
    }

    /**
     * Method under test: {@link PlayGroundServiceImpl#entityToDto(PlayGround)}
     */
    @Test
    void testEntityToDto2() {
        // Arrange
        PlayGround playGround = mock(PlayGround.class);
        when(playGround.getEquip_id()).thenReturn(1);
        when(playGround.getColor()).thenReturn("Color");
        when(playGround.getLocation()).thenReturn("Location");
        when(playGround.getType()).thenReturn("Type");
        Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        when(playGround.getInstall_date()).thenReturn(fromResult);
        doNothing().when(playGround).setColor(Mockito.any());
        doNothing().when(playGround).setEquip_id(Mockito.<Integer>any());
        doNothing().when(playGround).setInstall_date(Mockito.any());
        doNothing().when(playGround).setLocation(Mockito.any());
        doNothing().when(playGround).setType(Mockito.any());
        playGround.setColor("Color");
        playGround.setEquip_id(1);
        playGround.setInstall_date(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        playGround.setLocation("Location");
        playGround.setType("Type");

        // Act
        PlayGroundDto actualEntityToDtoResult = playGroundServiceImpl.entityToDto(playGround);

        // Assert
        verify(playGround).getColor();
        verify(playGround).getEquip_id();
        verify(playGround).getInstall_date();
        verify(playGround).getLocation();
        verify(playGround).getType();
        verify(playGround).setColor(Mockito.any());
        verify(playGround).setEquip_id(Mockito.<Integer>any());
        verify(playGround).setInstall_date(Mockito.any());
        verify(playGround).setLocation(Mockito.any());
        verify(playGround).setType(Mockito.any());
        assertEquals("Color", actualEntityToDtoResult.getColor());
        assertEquals("Location", actualEntityToDtoResult.getLocation());
        assertEquals("Type", actualEntityToDtoResult.getType());
        assertEquals(1, actualEntityToDtoResult.getEquip_id().intValue());
        assertSame(fromResult, actualEntityToDtoResult.getInstall_date());
    }

    /**
     * Method under test: {@link PlayGroundServiceImpl#dtoToEntity(PlayGroundDto)}
     */
    @Test
    void testDtoToEntity() {
        // Arrange and Act
        PlayGround actualDtoToEntityResult = playGroundServiceImpl.dtoToEntity(new PlayGroundDto());

        // Assert
        assertNull(actualDtoToEntityResult.getEquip_id());
        assertNull(actualDtoToEntityResult.getColor());
        assertNull(actualDtoToEntityResult.getLocation());
        assertNull(actualDtoToEntityResult.getType());
        assertNull(actualDtoToEntityResult.getInstall_date());
    }

    /**
     * Method under test: {@link PlayGroundServiceImpl#dtoToEntity(PlayGroundDto)}
     */
    @Test
    void testDtoToEntity2() {
        // Arrange
        PlayGroundDto playGroundDto = mock(PlayGroundDto.class);
        when(playGroundDto.getEquip_id()).thenReturn(1);
        when(playGroundDto.getColor()).thenReturn("Color");
        when(playGroundDto.getLocation()).thenReturn("Location");
        when(playGroundDto.getType()).thenReturn("Type");
        Date fromResult = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
        when(playGroundDto.getInstall_date()).thenReturn(fromResult);

        // Act
        PlayGround actualDtoToEntityResult = playGroundServiceImpl.dtoToEntity(playGroundDto);

        // Assert
        verify(playGroundDto).getColor();
        verify(playGroundDto).getEquip_id();
        verify(playGroundDto).getInstall_date();
        verify(playGroundDto).getLocation();
        verify(playGroundDto).getType();
        assertEquals("Color", actualDtoToEntityResult.getColor());
        assertEquals("Location", actualDtoToEntityResult.getLocation());
        assertEquals("Type", actualDtoToEntityResult.getType());
        assertEquals(1, actualDtoToEntityResult.getEquip_id().intValue());
        assertSame(fromResult, actualDtoToEntityResult.getInstall_date());
    }
}
