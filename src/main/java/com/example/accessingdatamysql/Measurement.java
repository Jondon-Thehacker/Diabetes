package com.example.accessingdatamysql;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "measurement")
public class Measurement implements Serializable {

    @Id
    private String measurementName;
    private int value;
    @Id
    private int time;

    @Id
    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

}
