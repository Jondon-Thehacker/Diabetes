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

    //Returns all patients of the doctor.
    @GetMapping("/api/v1/Doctors/{doctorId}/patients")
    public ResponseEntity<List<Patient>> getAllPatients(@PathVariable Long doctorId) {
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        if(d.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(d.get().getPatients());
    }

    //Returns the specific patient.
    @GetMapping("/api/v1/Doctors/{doctorId}/patients/{patientId}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long doctorId, @PathVariable Long patientId){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        if(d.isEmpty() || p.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(d.get().getPatientById(patientId));
    }
}
