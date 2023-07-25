package com.friendfinder.friendfinderweb.entity;

import com.friendfinder.friendfinderweb.entity.types.UserGender;
import com.friendfinder.friendfinderweb.entity.types.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String surname;
    private String email;
    private String password;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    private UserGender gender;
    private String city;

    @ManyToOne
    private Country country;

    @Column(name = "profile_pic")
    private String profilePicture;

    @Column(name = "profile_bg_pic")
    private String profileBackgroundPic;

    @Column(name = "personal_information")
    private String personalInformation;

    private boolean enabled;

    private String token;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;


    /**
     * The getter that automatically created @Data didn't work, so
     * I wrote a separate one for the date and used it in the
     * edit-profile-basic.html input of the date type to pass the value
     * */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}
