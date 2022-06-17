package diabetes.controller;

import diabetes.model.Doctor;
import diabetes.model.Notes;
import diabetes.model.Patient;
import diabetes.repositories.DoctorRepository;
import diabetes.repositories.NotesRepository;
import diabetes.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin
public class NotesController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private NotesRepository notesRepository;

    //Simon Stampe Jensen, s204488
    //Returns all notes for a given patient
    @GetMapping("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes")
    public ResponseEntity<List<Notes>> getAllPatientNotes(@PathVariable Long doctorId, @PathVariable Long patientId){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        //Null check.
        if(d.isEmpty() || p.isEmpty()){
            //404
            return ResponseEntity.notFound().build();
        }

        //Is doctor not associated with the patient?
        if (d.get().getPatientById(patientId) == null) {
            //404
            return ResponseEntity.notFound().build();
        }

        //200. Array of note objects as content.
        return ResponseEntity.ok(d.get().getPatientById(patientId).getNotes());
    }

    //Jonathan Max Michelsen, s204437
    @PostMapping("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes")
    public ResponseEntity<Notes> createNote(@RequestBody Notes notes, @PathVariable Long doctorId, @PathVariable Long patientId){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        //Null check.
        if(d.isEmpty() || p.isEmpty()){
            //404
            return ResponseEntity.notFound().build();
        }

        //Set associated patient
        notes.setPatient(p.get());
        //Set associated doctor
        notes.setDoctor(d.get());

        //Is doctor not associated with the patient?
        if (d.get().getPatientById(patientId) == null) {
            return ResponseEntity.notFound().build();
        }

        //Add note to patient.
        d.get().getPatientById(patientId).addNote(notes);
        //Add note to doctor.
        d.get().addNote(notes);     

        //200. Should maybe be no content.
        return ResponseEntity.ok(notesRepository.save(notes));
    }

    //Emil Pontoppidan Rasmussen, s204441
    //Delete a note from a specific date
    @DeleteMapping("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes/{noteId}")
    public ResponseEntity<?> deleteSpecificNote(@PathVariable Long doctorId, @PathVariable Long patientId, @PathVariable Long noteId){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);
        Optional<Notes> n = notesRepository.findById(noteId);

        //Null check.
        if(d.isEmpty() || p.isEmpty() || n.isEmpty()){
            //404
            return ResponseEntity.notFound().build();
        }

        //s doctor not associated with the patient?
        if (d.get().getPatientById(patientId) == null) {
            //404
            return ResponseEntity.notFound().build();
        }

        //Remove note from repository. On delete cascade removes it from patient/doctor repositories.
        notesRepository.deleteById(noteId);

        //204.
        return ResponseEntity.noContent().build();
    }
}











