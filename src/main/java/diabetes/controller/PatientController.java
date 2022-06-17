package diabetes.controller;

import diabetes.model.Doctor;
import diabetes.model.Patient;
import diabetes.repositories.DoctorRepository;
import diabetes.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    //Emil Pontoppidan Rasmussen, s204441
    //Returns all patients of the doctor.
    @GetMapping("/api/v1/Doctors/{doctorId}/patients")
    public ResponseEntity<List<Patient>> getAllPatients(@PathVariable Long doctorId) {
        Optional<Doctor> d = doctorRepository.findById(doctorId);

        //Null check.
        if(d.isEmpty()){
            //404
            return ResponseEntity.notFound().build();
        }

        //200. Array of patient objects as content.
        return ResponseEntity.ok(d.get().getPatients());
    }

    //Emil Pontoppidan Rasmussen, s204441
    //Returns the specific patient.
    @GetMapping("/api/v1/Doctors/{doctorId}/patients/{patientId}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long doctorId, @PathVariable Long patientId){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        //Null check.
        if(d.isEmpty() || p.isEmpty()){
            //404
            return ResponseEntity.notFound().build();
        }

        //200. Patient object as content.
        return ResponseEntity.ok(d.get().getPatientById(patientId));
    }
}
