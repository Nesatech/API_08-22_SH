package com.test.api_0822_sh.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * User entity model
 */
@Entity
@Getter
@Setter
@ToString
@Table(name = "`user`") // prevent H2 exception because of 'user' reserved word
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    private Long id;
    private String name;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthday;
    private String country;
    private String phoneNumber;
    private Character gender;

    public User() {
    }

    public User(String name, LocalDate birthday, String country) {
        this.name = name;
        this.birthday = birthday;
        this.country = country;
    }

    public User(String name, LocalDate birthday, String country, String phoneNumber, Character gender) {
        this.name = name;
        this.birthday = birthday;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
