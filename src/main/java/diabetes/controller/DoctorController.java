package diabetes.controller;

import diabetes.model.Doctor;
import diabetes.repositories.MeasurementRepository;
import diabetes.repositories.DoctorRepository;
import diabetes.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@CrossOrigin
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    @GetMapping("/api/v1/Doctors/{doctorId}")
    public ResponseEntity getDoctor(@PathVariable Long doctorId) {
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        if(d.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(d.get());
    }
}
