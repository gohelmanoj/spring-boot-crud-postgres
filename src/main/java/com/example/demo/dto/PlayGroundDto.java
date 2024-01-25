package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayGroundDto {

    private Integer equip_id;
    private String type;
    private String color;
    private String location;
    private Date install_date;
}
