package diabetes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.yaml.snakeyaml.util.EnumUtils;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Entity
@Table(name = "patient")
public class Patient {

    //Private key.
    @Id
    @Column(name="patientId")
    private Long patientId;

    private String patientName;

    @Column(unique = false)
    private String email;

    //Foreign key.
    @OneToMany(mappedBy = "patient", cascade = CascadeType.MERGE, fetch = FetchType.LAZY) //mapped by name of field
    @JsonIgnore
    private List<Notes> notes;

    //Foreign key.
    @ManyToMany(mappedBy = "patients")
    @JsonIgnore
    private List<Doctor> doctors;

    //Foreign key.
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Measurement> measurements;

    //Default constructor, mainly used for testing
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

    //Return doctors IDs
    public List<Long> getDoctors() {
        return doctors.stream()
                      .map(d -> d.getDoctorId())
                      .collect(Collectors.toList());
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    //Return measurements in interval matching type
    public List<Measurement> getMeasurementOfTypeAndDate(String type, String startDate, String endDate){
        return measurements.stream()
                           .filter(m -> m.getMeasurementName().equals(type) && isBetween(m.getTime(), startDate, endDate))
                           .collect(Collectors.toList());
    }

    //Returns true if timestamp is between start-, and end-date.
    public boolean isBetween(java.sql.Timestamp potential, String start, String end){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime potentialDate = potential.toLocalDateTime();
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);

        return potentialDate.isBefore(endDate) && potentialDate.isAfter(startDate);
    }

    //Returns all measurements matching type
    public List<Measurement> getMeasurementOfType(String type){
        if (type == null) {
            return null;
        }
        try {
            Enum.valueOf(Measurement.MeasurementName.class, type);

            //Filter measurements by type.
            return measurements.stream()
                               .filter(m -> m.getMeasurementName().equals(type))
                               .collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    //Returns aggregate function value.
    public double applyAggregateFunction(String dataType, String startDate, String endDate, String aggregateFunction) {
        //Get data in interval of relevant type
        List<Measurement> measurements = this.getMeasurementOfTypeAndDate(dataType,startDate,endDate);
        //Sentinel value
        double result = -1;

        try {
            //Switch aggregate function, passed as path variable.
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
                    //Maps measurement value onto compare function max.
                    result = measurements.stream()
                                         .map(v -> v.getValue())
                                         .max(Comparator.comparing(Double::valueOf))
                                         .get();
                    break;
                case "min":
                    //Maps measurement value onto compare function min.
                    result = measurements.stream()
                                         .map(v -> v.getValue())
                                         .min(Comparator.comparing(Double::valueOf))
                                         .get();
                    break;
                case "GMI":
                    //result = 3.31 + 0.02392 * average(measurements); // måske 1.627177700 + 0.03484320557*avg ?
                    result = 12.71 + 4.70587 * average(measurements);
                    break;
                case "countAbove":
                    result = measurements.stream()
                                         .map(v -> v.getValue())
                                         .filter(mv -> mv > 13.9)
                                         .count();
                    break;
                case "countBelow":
                    result = measurements.stream()
                                         .map(v -> v.getValue())
                                         .filter(mv -> mv < 3)
                                         .count();
                    break;
                case "countInRange":
                    result = measurements.stream()
                                         .map(v -> v.getValue())
                                         .filter(mv -> mv < 10 && mv >= 3.9)
                                         .count();
                    break;
                case "countSlightlyAbove":
                    result = measurements.stream()
                                         .map(v -> v.getValue())
                                         .filter(mv -> mv <= 13.9 && mv>= 10)
                                         .count();
                    break;
                case "countSlightlyBelow":
                    result = measurements.stream()
                                         .map(v -> v.getValue())
                                         .filter(mv -> mv < 3.9 && mv >= 3)
                                         .count();
                    break;
            }


        } catch (Exception e) {
            System.out.println("Unsupported aggregate function");
        }

        return result;
    }

    //Attempted rewrite without regex.
    public Map<String, Map<String, Double>> getSummary(String dataType, String start, String end, String type, Long stepSize) {
        //Get data in interval of relevant type
        List<Measurement> measurements = this.getMeasurementOfTypeAndDate(dataType,start,end);
        //Output object
        Map<String, Map<String, Double>> out = new LinkedHashMap<>();

        //Specify expected date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        //Convert string values to date-time objects.
        LocalDateTime time = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);

        //While in interval.
        while(time.isBefore(endDate)) {
            //Summary statistics object.
            Map<String, Double> statistics = new LinkedHashMap<String, Double>();

            //Get time of current step start
            LocalTime currentTime = time.toLocalTime();
            List<Measurement> measurementsAtTime;
            if (type.equals("barChart")) {
                //Get time of current step end
                LocalDateTime nextTime = LocalDateTime.from(time).plusMinutes(stepSize);

                //Check if end-time of current step exceeds interval
                if (!nextTime.isBefore(endDate)) {
                    break;
                }

                //Filters measurements of time between current step (inclusive) and next step (exclusive)
                measurementsAtTime = measurements.stream()
                        .filter(v -> {
                            //Get time of measurement bar date
                            LocalTime timeOfMeasurement = v.getTime()
                                                           .toLocalDateTime()
                                                           .toLocalTime();

                            //If time of measurement is within bounds of current step, add it to the list
                            if (timeOfMeasurement.isBefore(nextTime.toLocalTime()) && !timeOfMeasurement.isBefore(currentTime)) {
                                return true;
                            }
                            return false;
                        })
                        .collect(Collectors.toList());
            } else {
                //Filters measurements of time equal to current step (sampling).
                //Should probably take average of measurements between current step and next step.
                measurementsAtTime = measurements.stream()
                        .filter(v -> v.getTime()
                                      .toLocalDateTime()
                                      .toLocalTime()
                                      .equals(currentTime))
                        .collect(Collectors.toList());
            }

            switch (type) {
                case "barChart" -> {
                    //Put summary statistics in the statistics object
                    //List could be streamed and mapped once to a forEach method, but that would shuffle the key order of the linked HashMap
                    statistics.put("Above", (double) measurementsAtTime.stream().map(Measurement::getValue).filter(mv -> mv > 13.9).count());
                    statistics.put("SlightlyAbove", (double) measurementsAtTime.stream().map(Measurement::getValue).filter(mv -> mv <= 13.9 && mv >= 10).count());
                    statistics.put("InRange", (double) measurementsAtTime.stream().map(Measurement::getValue).filter(mv -> mv < 10 && mv >= 3.9).count());
                    statistics.put("SlightlyBelow", (double) measurementsAtTime.stream().map(Measurement::getValue).filter(mv -> mv < 3.9 && mv >= 3).count());
                    statistics.put("Below", (double) measurementsAtTime.stream().map(Measurement::getValue).filter(mv -> mv < 3).count());
                    out.put(currentTime.toString() + ":00", statistics);
                }
                case "lineChart" -> {
                    //Put summary statistics in the statistics object
                    //List could be streamed and mapped once to a forEach method, but that would shuffle the key order of the linked HashMap
                    statistics.put("Min", measurementsAtTime.stream().map(v -> v.getValue()).min(Comparator.comparing(Double::valueOf)).get());
                    statistics.put("Q1", percentile(measurementsAtTime, 25L));
                    statistics.put("Median", percentile(measurementsAtTime, 50L));
                    statistics.put("Q3", percentile(measurementsAtTime, 75L));
                    statistics.put("Max", measurementsAtTime.stream().map(v -> v.getValue()).max(Comparator.comparing(Double::valueOf)).get());
                    out.put(currentTime.toString() + ":00", statistics);
                }
                case "keyValues" -> {
                    //Put summary statistics in the statistics object
                    //List could be streamed and mapped once to a forEach method, but that would shuffle the key order of the linked HashMap
                    statistics.put("GV", standardDeviation(measurementsAtTime)/average(measurementsAtTime));
                    statistics.put("GMI", 3.31 + 0.02392 * average(measurementsAtTime));
                    statistics.put("Sd", standardDeviation(measurementsAtTime));
                    statistics.put("Min", measurementsAtTime.stream().map(Measurement::getValue).min(Comparator.comparing(Double::valueOf)).get());
                    statistics.put("Max", measurementsAtTime.stream().map(Measurement::getValue).max(Comparator.comparing(Double::valueOf)).get());
                    statistics.put("Average", average(measurementsAtTime));
                    out.put(currentTime.toString() + ":00", statistics);
                }
            }

            //Increment time by stepSize
            time = time.plusMinutes(stepSize);
        }

        return out;
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


