package com.example.accessingdatamysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/Patient")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewPatient(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email) {

        Patient patient = new Patient();
        patient.setPatientName(firstName);
        patient.setEmail(lastName);
        patientRepository.save(patient);
        return "Patient Created";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable <Patient> getAllPatients() {
        return patientRepository.findAll();
    }

}
