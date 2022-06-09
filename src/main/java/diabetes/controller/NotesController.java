package diabetes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    @GetMapping("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes")
    public ResponseEntity<List<Notes>> getAllPatientNotes(@PathVariable Long doctorId, @PathVariable Long patientId){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        if(d.isEmpty() || p.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(d.get().getPatientById(patientId).getNotes());
    }

    @PostMapping("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes")
    public ResponseEntity<Notes> createNote(@RequestBody Notes notes, @PathVariable Long doctorId, @PathVariable Long patientId){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        if(d.isEmpty() || p.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        notes.setPatient(p.get());
        notes.setDoctor(d.get());

        if (d.get().getPatientById(patientId) == null) {
            return ResponseEntity.notFound().build();
        }

        d.get().getPatientById(patientId).addNote(notes);
        d.get().addNote(notes);     

        return ResponseEntity.ok(notesRepository.save(notes));
    }

    //Delete a note from a specific date
    @DeleteMapping("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes/{noteId}")
    public ResponseEntity<?> deleteSpecificNote(@PathVariable Long doctorId, @PathVariable Long patientId, @PathVariable Long noteId){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);
        Optional<Notes> n = notesRepository.findById(noteId);

        if(d.isEmpty() || p.isEmpty() || n.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        notesRepository.deleteById(noteId);
        return ResponseEntity.noContent().build();
    }


}











