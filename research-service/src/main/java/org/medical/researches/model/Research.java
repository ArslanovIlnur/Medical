package org.medical.researches.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "research")
public class Research {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer patientId;
    private Date date;
    private String initials;
    private String researchType;
    private String researchZone;

    public Research() {
    }
}
