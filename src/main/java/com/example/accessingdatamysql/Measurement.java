package com.example.accessingdatamysql;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@IdClass(MeasurementId.class)
@Table(name = "measurement")
public class Measurement {

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
