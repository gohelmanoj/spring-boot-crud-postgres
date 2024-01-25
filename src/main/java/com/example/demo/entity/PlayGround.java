package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "playground")
public class PlayGround {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equip_id;

    private String type;
    private String color;
    private String location;
    private Date install_date;
}
