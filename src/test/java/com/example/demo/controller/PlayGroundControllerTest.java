package com.example.demo.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.demo.dto.PlayGroundDto;
import com.example.demo.service.PlayGroundService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {PlayGroundController.class})
@ExtendWith(SpringExtension.class)
class PlayGroundControllerTest {
    @Autowired
    private PlayGroundController playGroundController;

    @MockBean
    private PlayGroundService playGroundService;

    /**
     * Method under test:
     * {@link PlayGroundController#getPlayGroundByEquipId(Integer)}
     */
    @Test
    void testGetPlayGroundByEquipId() throws Exception {
        // Arrange
        when(playGroundService.getPlayGroundByEquipId(Mockito.<Integer>any())).thenReturn(new PlayGroundDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playground/{equip_id}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(playGroundController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"equip_id\":null,\"type\":null,\"color\":null,\"location\":null,\"install_date\":null}"));
    }

    /**
     * Method under test:
     * {@link PlayGroundController#getPlayGroundByEquipId(Integer)}
     */
    @Test
    void testGetPlayGroundByEquipId2() throws Exception {
        // Arrange
        when(playGroundService.getAllPlayGrounds()).thenReturn(new ArrayList<>());
        when(playGroundService.getPlayGroundByEquipId(Mockito.<Integer>any())).thenReturn(new PlayGroundDto());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playground/{equip_id}", "",
                "Uri Variables");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(playGroundController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test:
     * {@link PlayGroundController#updatePlayGroundByEquipId(Integer, PlayGroundDto)}
     */
    @Test
    void testUpdatePlayGroundByEquipId() throws Exception {
        // Arrange
        when(playGroundService.updatePlayGroundByEquipId(Mockito.<Integer>any(), Mockito.any()))
                .thenReturn(new PlayGroundDto());

        PlayGroundDto playGroundDto = new PlayGroundDto();
        playGroundDto.setColor("Color");
        playGroundDto.setEquip_id(1);
        playGroundDto
                .setInstall_date(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        playGroundDto.setLocation("Location");
        playGroundDto.setType("Type");
        String content = (new ObjectMapper()).writeValueAsString(playGroundDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/playground/{equipId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(playGroundController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"equip_id\":null,\"type\":null,\"color\":null,\"location\":null,\"install_date\":null}"));
    }

    /**
     * Method under test:
     * {@link PlayGroundController#deletePlayGroundByEquipId(Integer)}
     */
    @Test
    void testDeletePlayGroundByEquipId() throws Exception {
        // Arrange
        doNothing().when(playGroundService).deletePlayGroundByEquipId(Mockito.<Integer>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/playground/{equipId}", 1);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(playGroundController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("PlayGround Successfully Deleted by equipId as 1"));
    }

    /**
     * Method under test:
     * {@link PlayGroundController#deletePlayGroundByEquipId(Integer)}
     */
    @Test
    void testDeletePlayGroundByEquipId2() throws Exception {
        // Arrange
        doNothing().when(playGroundService).deletePlayGroundByEquipId(Mockito.<Integer>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/playground/{equipId}", 1);
        requestBuilder.contentType("https://example.org/example");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(playGroundController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("PlayGround Successfully Deleted by equipId as 1"));
    }

    /**
     * Method under test: {@link PlayGroundController#getAllPlayGround()}
     */
    @Test
    void testGetAllPlayGround() throws Exception {
        // Arrange
        when(playGroundService.getAllPlayGrounds()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playground");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(playGroundController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link PlayGroundController#savePlayGround(PlayGroundDto)}
     */
    @Test
    void testSavePlayGround() throws Exception {
        // Arrange
        when(playGroundService.getAllPlayGrounds()).thenReturn(new ArrayList<>());

        PlayGroundDto playGroundDto = new PlayGroundDto();
        playGroundDto.setColor("Color");
        playGroundDto.setEquip_id(1);
        playGroundDto
                .setInstall_date(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        playGroundDto.setLocation("Location");
        playGroundDto.setType("Type");
        String content = (new ObjectMapper()).writeValueAsString(playGroundDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playground")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(playGroundController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
