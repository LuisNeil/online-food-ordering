package com.ltjeda.web.app.onlinefoodordering.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private User owner;
    private String name;
    private String description;
    private String cuisineType;
    @OneToOne
    private Address address;
    @Embedded
    private ContactInformation contactInformation;
    private String openingHours;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();
    @ElementCollection
    @Column(length = 1000)
    private List<String> images;
    private LocalDateTime registrationDate;
    private boolean open;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Food> foods = new ArrayList<>();
}
