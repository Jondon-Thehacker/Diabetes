package com.example.accessingdatamysql;

import com.example.accessingdatamysql.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin
public class MeasurementController {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MeasurementRepository measurementRepository;

    //Returns all the data of the chosen type
    @GetMapping("/api/v1/Doctors/{doctorId}/Patients/{patientId}/Measurements/{dataType}")
    public ResponseEntity<List<Measurement>> getDataOfType(@PathVariable Long doctorId, @PathVariable Long patientId, @PathVariable String dataType){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);


        if(d.isEmpty() || p.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(d.get().getPatientById(patientId).getMeasurementOfType(dataType));
    }

    //Returns all the data of the chosen type within the specified date interval


}
