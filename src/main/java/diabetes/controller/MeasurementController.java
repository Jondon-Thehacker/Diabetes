package diabetes.controller;

import diabetes.model.Doctor;
import diabetes.model.Measurement;
import diabetes.repositories.MeasurementRepository;
import diabetes.model.Patient;
import diabetes.repositories.DoctorRepository;
import diabetes.repositories.PatientRepository;
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
    @GetMapping("api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}")
    public ResponseEntity<List<Measurement>> getDataOfTypeAndDate(@PathVariable Long doctorId, @PathVariable Long patientId, @PathVariable String dataType, @PathVariable String startDate, @PathVariable String endDate){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        if(d.isEmpty() || p.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(d.get().getPatientById(patientId).getMeasurementOfTypeAndDate(dataType, startDate, endDate));
    }

    @GetMapping("api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}")
    public ResponseEntity<Double> getAggregatedData(@PathVariable Long doctorId, @PathVariable Long patientId, @PathVariable String dataType, @PathVariable String startDate, @PathVariable String endDate, @PathVariable String aggregateFunction){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        if(d.isEmpty() || p.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(d.get().getPatientById(patientId).applyAggregateFunction(dataType, startDate, endDate, aggregateFunction));
    }

    @GetMapping("api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}/{argument}")
    public ResponseEntity<Double> getAggregatedDataWithArgument(@PathVariable Long doctorId, @PathVariable Long patientId, @PathVariable String dataType, @PathVariable String startDate, @PathVariable String endDate, @PathVariable String aggregateFunction, @PathVariable Long argument){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        if(d.isEmpty() || p.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(d.get().getPatientById(patientId).aggregateFunctionArgument(dataType,startDate,endDate,aggregateFunction,argument));

    }
}
