package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/Patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;
    /*
    @PostMapping(path = "/add")
    public @ResponseBody String addNewPatient(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {

        Patient patient = new Patient();
        patient.setPatientName(firstName);
        patient.setEmail(lastName);
        patientRepository.save(patient);
        return "Patient Created";
    }
    */

    @GetMapping("/Doctors/{doctorId}/patients")
    public ResponseEntity<List<Patient>> getAllPatients(@PathVariable Long doctorId) {
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        if(d.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(d.get().getPatients());
    }


    @GetMapping("Doctors/{doctorId}/patients/{patientId}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long doctorId, @PathVariable Long patientId){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);
        if(d.isEmpty() || p.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(d.get().getPatientById(patientId));
    }

}
