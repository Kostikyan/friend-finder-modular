package com.friendfinder.friendfindercommon.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "education")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ed_name")
    private String edName;

    @Column(name = "ed_from_date")
    private int edFromDate;

    @Column(name = "ed_to_date")
    private int edToDate;

    @Column(name = "ed_description")
    private String edDescription;

    @ManyToOne
    private User user;

}
