package com.janetschel.backend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "persons")
public class CharacterEntity {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String voicedBy;
    private String role;
    private String firstAppearance;

}
