package diabetes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "doctor")
public class Doctor {

    //Private key. The ID of the doctor.
    @Id
    private Long doctorId;

    private String doctorName;
    private String hospital;
    private String email;

    //Maps doctorID and patientID as foreign keys in assignment table.
    @ManyToMany(cascade = { CascadeType.ALL })  //TODO: added mappedBy
    @JoinTable(
            name = "Assignment",
            joinColumns = { @JoinColumn(name = "doctorId")},
            inverseJoinColumns = { @JoinColumn(name = "patientId") }
    )
    @JsonIgnore
    private List<Patient> patients;

    //Maps doctorID as foreign key in notes table.
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.MERGE, fetch = FetchType.LAZY) //mapped by name of field
    @JsonIgnore
    private List<Notes> notes;

    //Maps doctorID as foreign key in users table.
    //Unused.
    @OneToOne(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User user;

    //standard constructor, mainly for testing
    public Doctor(Long doctorId, String doctorName, String hospital, String email, List<Patient> patients, List<Notes> notes){
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.hospital = hospital;
        this.email = email;
        this.patients = patients;
        this.notes = notes;
    }

    public Doctor(){

    }

    public void setDoctorName(String firstName) {
        this.doctorName = doctorName;
    }

    public void setHospital(String lastName) {
        this.hospital = hospital;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getHospital() {
        return hospital;
    }

    public String getEmail() {
        return email;
    }

    public List<Patient> getPatients(){
        return this.patients;
    }

    public Patient getPatientById(Long patientId){
        //Find first patient matching patient ID.
        return this.patients.stream()
                            .filter(patient -> patient.getPatientId() == patientId)
                            .findFirst()
                            .orElse(null);
    }

    public void setEmail(String email) {
        this.email = email;
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

    //This is never used. Consider removing.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return doctorId.equals(doctor.doctorId) && doctorName.equals(doctor.doctorName) && hospital.equals(doctor.hospital) && email.equals(doctor.email) && Objects.equals(patients, doctor.patients) && Objects.equals(notes, doctor.notes) && Objects.equals(user, doctor.user);
    }

    //Also never used.
    @Override
    public int hashCode() {
        return Objects.hash(doctorId, doctorName, hospital, email, patients, notes, user);
    }
}
