package com.example.accessingdatamysql;

import java.io.Serializable;
import java.util.Objects;

public class MeasurementId implements Serializable {
    private Measurement.MeasurementName measurementName;
    private java.sql.Timestamp time;
    private Patient patient;

    public MeasurementId (){


    }

    public MeasurementId (Measurement.MeasurementName measurementName, java.sql.Timestamp time, Patient patient){
        this.measurementName = measurementName;
        this.time = time;
        this.patient = patient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeasurementId that = (MeasurementId) o;
        return time == that.time && measurementName.equals(that.measurementName) && patient.equals(that.patient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(measurementName, time, patient);
    }

    /*
    public String getMeasurementName() {
        return measurementName;
    }

    public void setMeasurementName(String measurementName) {
        this.measurementName = measurementName;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    */
}
