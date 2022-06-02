package diabetes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.yaml.snakeyaml.util.EnumUtils;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @Column(name="patientId")
    private Long patientId;

    private String patientName;

    @Column(unique = false)
    private String email;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.MERGE, fetch = FetchType.LAZY) //mapped by name of field
    @JsonIgnore
    private List<Notes> notes;

    @ManyToMany(mappedBy = "patients")
    @JsonIgnore
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Measurement> measurements;

    public Patient(Long patientId, String patientName, String email, List<Notes> notes, List<Doctor> doctors, List<Measurement> measurements){
        this.patientId = patientId;
        this.patientName = patientName;
        this.email = email;
        this.notes = notes;
        this.doctors = doctors;
        this.measurements = measurements;
    }

    public Patient(){

    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPatientId() {
        return this.patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getEmail() {
        return email;
    }

    public List<Notes> getNotes() {
        return notes;
    }

    public void addNote(Notes note){
        this.notes.add(note);
    }

    public void removeNote(Notes note){
        this.notes.remove(note);
    }

    public void setNotes(List<Notes> notes) {
        this.notes = notes;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public void addMeasurement(Measurement measurement){
        this.measurements.add(measurement);
    }

    public List<Long> getDoctors() {
        return doctors.stream().map(d -> d.getDoctorId()).collect(Collectors.toList());
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Measurement> getMeasurementOfTypeAndDate(String type, String startDate, String endDate){

        return measurements.stream().filter(m -> m.getMeasurementName().equals(type) && isBetween(m.getTime(), startDate, endDate)).collect(Collectors.toList());
    }

    public boolean isBetween(java.sql.Timestamp potential, String start, String end){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime potentialDate = potential.toLocalDateTime();
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);

        return potentialDate.isBefore(endDate) && potentialDate.isAfter(startDate);
    }

    public List<Measurement> getMeasurementOfType(String type){
        if (type == null) {
            return null;
        }
        try {
            Enum.valueOf(Measurement.MeasurementName.class, type);
            return measurements.stream().filter(m -> m.getMeasurementName().equals(type)).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    public double applyAggregateFunction(String dataType, String startDate, String endDate, String aggregateFunction) {
        List<Measurement> measurements = this.getMeasurementOfTypeAndDate(dataType,startDate,endDate);
        double result = -1;

        try {
            switch (aggregateFunction) {
                case "standardDeviation":
                    result = standardDeviation(measurements);
                    break;
                case "average":
                    result = average(measurements);
                    break;
                case "count":
                    result = count(measurements);
                    break;
                case "glucoseVariability":
                    result = standardDeviation(measurements)/average(measurements);
                    break;
                case "max":
                    result = measurements.stream().map(v -> v.getValue()).max(Comparator.comparing(Double::valueOf)).get();
                    break;
                case "min":
                    result = measurements.stream().map(v -> v.getValue()).min(Comparator.comparing(Double::valueOf)).get();
                    break;
                case "GMI":
                    result = 3.31 + 0.02392 * average(measurements); // måske 1.627177700 + 0.03484320557*avg ?
                    break;
                case "countAbove":
                    result = measurements.stream().map(v -> v.getValue()).filter(mv -> mv > 13.9).count();
                    break;
                case "countBelow":
                    result = measurements.stream().map(v -> v.getValue()).filter(mv -> mv < 3).count();
                    break;
                case "countInRange":
                    result = measurements.stream().map(v -> v.getValue()).filter(mv -> mv < 10 && mv >= 3.9).count();
                    break;
                case "countSlightlyAbove":
                    result = measurements.stream().map(v -> v.getValue()).filter(mv -> mv <= 13.9 && mv>= 10).count();
                    break;
                case "countSlightlyBelow":
                    result = measurements.stream().map(v -> v.getValue()).filter(mv -> mv < 3.9 && mv >= 3).count();
                    break;
            }


        } catch (Exception e) {
            System.out.println("Unsupported aggregate function");
        }

        return result;
    }

    public Map<String, Map<String, Double>> getSummary(String dataType, String startDate, String endDate, String type, Long stepSize) {
        List<Measurement> measurements = this.getMeasurementOfTypeAndDate(dataType,startDate,endDate);
        Map<String, Map<String, Double>> out = new LinkedHashMap<>();

        String time = "";
        int i = 0;
        while(!time.matches("(.*)(?<=([3-9][0-9]|2[4-9]))[[0-9]+:]{6}(.*)")) {
            time = getTime(stepSize, i);

            if (time.equals("24:00:00")) {
                break;
            }

            Map<String, Double> statistics = new LinkedHashMap<String, Double>();

            String finalTime = time;
            List<Measurement> measurementsAtTime = measurements.stream()
                                                                .filter(v -> v.getTime()
                                                                                .toString()
                                                                                .contains(finalTime))
                                                                                .collect(Collectors.toList());

            switch (type) {
                case "barChart" -> {
                    statistics.put("Above", (double) measurementsAtTime.stream().map(Measurement::getValue).filter(mv -> mv > 13.9).count());
                    statistics.put("SlightlyAbove", (double) measurementsAtTime.stream().map(Measurement::getValue).filter(mv -> mv <= 13.9 && mv >= 10).count());
                    statistics.put("InRange", (double) measurementsAtTime.stream().map(Measurement::getValue).filter(mv -> mv < 10 && mv >= 3.9).count());
                    statistics.put("SlightlyBelow", (double) measurementsAtTime.stream().map(Measurement::getValue).filter(mv -> mv < 3.9 && mv >= 3).count());
                    statistics.put("Below", (double) measurementsAtTime.stream().map(Measurement::getValue).filter(mv -> mv < 3).count());
                    out.put(time, statistics);
                }
                case "lineChart" -> {
                    statistics.put("Q1", percentile(measurementsAtTime, 25L));
                    statistics.put("Median", percentile(measurementsAtTime, 50L));
                    statistics.put("Q3", percentile(measurementsAtTime, 75L));
                    out.put(time, statistics);
                }
                case "keyValues" -> {
                    statistics.put("GV", standardDeviation(measurementsAtTime)/average(measurementsAtTime));
                    statistics.put("GMI", 3.31 + 0.02392 * average(measurementsAtTime));
                    statistics.put("Sd", standardDeviation(measurementsAtTime));
                    statistics.put("Min", measurementsAtTime.stream().map(Measurement::getValue).min(Comparator.comparing(Double::valueOf)).get());
                    statistics.put("Max", measurementsAtTime.stream().map(Measurement::getValue).max(Comparator.comparing(Double::valueOf)).get());
                    statistics.put("Average", average(measurementsAtTime));

                    out.put(time, statistics);
                }
            }

            i++;
        }

        return out;
    }

    private String getTime(Long stepSize, int step) {
        String out = "";

        int hr = (stepSize.intValue() * step) / 60;
        int min = (stepSize.intValue() * step) % 60;

        if (hr < 10) {
            out = "0" + hr;
        } else {
            out = "" + hr;
        }

        if (min < 10) {
            out += ":" + "0" + min;
        } else {
            out += ":" + min;
        }
        
        return out + ":00";
    }

    private double count(List<Measurement> measurements) {
        return measurements.stream().map(v -> v.getValue()).count();
    }

    private double average(List<Measurement> measurements) {
        double sum = measurements.stream().map(v -> v.getValue()).reduce((double) 0, (s, e) -> s + e );
        double count = count(measurements);

        return sum/count;
    }

    private double standardDeviation(List<Measurement> measurements) {
        double average = average(measurements);
        double count = count(measurements);

        double x = measurements.stream().map(v -> v.getValue()).reduce((double) 0, (s, e) -> (double) s + (e-average)*(e-average));

        return Math.sqrt(x/count);
    }

    public double aggregateFunctionArgument(String dataType, String startDate, String endDate, String aggregateFunction, Long argument) {
        List<Measurement> measurements = this.getMeasurementOfTypeAndDate(dataType, startDate, endDate);

        double result = -1;

        switch (aggregateFunction) {
            case "percentile":
                result = percentile(measurements, argument);
                break;
        }
        return result;
    }
    private double percentile(List<Measurement> measurements, Long argument) {
        double count = count(measurements);
        List<Double> sortedList = measurements.stream().map(m -> m.getValue()).sorted().collect(Collectors.toList());

        return sortedList.get((int) (Math.ceil(((double) argument)/100 * count))-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return patientId.equals(patient.patientId) && patientName.equals(patient.patientName) && Objects.equals(email, patient.email) && Objects.equals(notes, patient.notes) && Objects.equals(doctors, patient.doctors) && Objects.equals(measurements, patient.measurements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, patientName, email, notes, doctors, measurements);
    }


}


