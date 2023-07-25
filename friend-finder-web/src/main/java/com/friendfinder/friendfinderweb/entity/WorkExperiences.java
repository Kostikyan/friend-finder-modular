package com.friendfinder.friendfinderweb.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "work_experiences")
public class WorkExperiences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "company")
    private String company;

    @Column(name = "we_designation")
    private String weDesignation;

    @Column(name = "we_from_date")
    private int weFromDate;

    @Column(name = "we_to_date")
    private int weToDate;

    @Column(name = "we_city")
    private String weCity;

    @Column(name = "we_description")
    private String weDescription;

    @ManyToOne
    private User user;

}
