package com.example.accessingdatamysql;

import org.aspectj.weaver.ast.Not;
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

        System.out.println(d.isEmpty());
        System.out.println(p.isEmpty());
        if(d.isEmpty() || p.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        notes.setPatient(p.get());
        notes.setDoctor(d.get());
        d.get().getPatientById(patientId).addNote(notes);
        //System.out.println(p.get().getPatientName());
        //p.get().addNote(note);
        //d.get().addNote(note);


        return ResponseEntity.ok(notesRepository.save(notes));

    }

}
