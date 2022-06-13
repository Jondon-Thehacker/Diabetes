package diabetes.model;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "measurement")
public class Measurement {

    //Private key. Auto-incremented.
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long measurementId;

    private double value;
    private java.sql.Timestamp time;

    //Foreign key.
    @ManyToOne
    @JoinColumn(name = "patientId")
    private Patient patient;

    //Measurement type enum. Private key.
    @Column(columnDefinition = "ENUM('BOLUS','BASAL','EXERCISE','MEALS','CGM')")
    @Enumerated(EnumType.STRING)
    private MeasurementName measurementName;

    public enum MeasurementName {
        BOLUS("BOLUS"),
        BASAL("BASAL"),
        EXERCISE("EXERCISE"),
        MEALS("MEALS"),
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

    //Default constructor, mainly used for testing
    public Measurement(Long MeasurementId, double value, java.sql.Timestamp time, Patient patient, MeasurementName measurementName){
        this.measurementId = MeasurementId;
        this.value = value;
        this.time = time;
        this.patient = patient;
        this.measurementName = measurementName;
    }

    public Measurement(){

    }

    public Long getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(Long measurementId) {
        this.measurementId = measurementId;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setMeasurementName(MeasurementName measurementName) {
        this.measurementName = measurementName;
    }

    public String getMeasurementName() {
        return measurementName.getType();
    }

    public void setMeasurementName(String measurementName) {
        this.measurementName.setType(measurementName);
    }

    public java.sql.Timestamp getTime() {
        return time;
    }

    public void setTime(java.sql.Timestamp time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    //Never used.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measurement that = (Measurement) o;
        return value == that.value && time == that.time && measurementName.getType().equals(that.measurementName.getType()) && patient.equals(that.patient);
    }

    //Never used.
    @Override
    public int hashCode() {
        return Objects.hash(measurementName.getType(), value, time, patient);
    }
}
