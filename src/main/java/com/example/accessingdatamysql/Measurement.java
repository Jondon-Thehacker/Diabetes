package com.example.accessingdatamysql;

import javax.persistence.*;
import java.util.Objects;


@Entity
@IdClass(MeasurementId.class)
@Table(name = "measurement")
public class Measurement {


    @Id
    @Column(columnDefinition = "ENUM('BOLUS','BASAL','EXERCISE','MEALS','CGM')")
    @Enumerated(EnumType.STRING)
    private MeasurementName measurementName;

    public enum MeasurementName {
        BOLUS("Bolus"),
        BASAL("Basal"),
        EXERCISE("Exercise"),
        MEALS("Meals"),
        CGM("CGM");

        private String type;
        MeasurementName(String type) {
            this.type = type;
        }

        public String getType(){
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
   /* @Id
    private String measurementName;
*/
    private int value;

    @Id
    private int time;

    @Id
    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    public String getMeasurementName() {
        return measurementName.getType();
    }

    public void setMeasurementName(String measurementName) {
        this.measurementName.setType(measurementName);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurement that = (Measurement) o;
        return value == that.value && time == that.time && measurementName.getType().equals(that.measurementName.getType()) && patient.equals(that.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(measurementName.getType(), value, time, patient);
    }
}
