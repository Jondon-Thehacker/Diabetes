package com.example.accessingdatamysql;

import javax.persistence.*;

@Entity
@Table(name = "notes")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "date")
    private int date;
    private String note;

    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;
}
