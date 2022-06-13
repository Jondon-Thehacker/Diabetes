package diabetes.controller;

import diabetes.model.Doctor;
import diabetes.model.Measurement;
import diabetes.repositories.MeasurementRepository;
import diabetes.model.Patient;
import diabetes.repositories.DoctorRepository;
import diabetes.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

        //Null check.
        if(d.isEmpty() || p.isEmpty()){
            //404
            return ResponseEntity.notFound().build();
        }

        //Is doctor associated with the patient? If not, 404.
        if (!checkAssoc(d.get(), patientId)) {return ResponseEntity.notFound().build();}

        //Get measurement data from patient.
        List<Measurement> result = d.get().getPatientById(patientId).getMeasurementOfType(dataType);

        //Null check. Only true if bad path variable is passed.
        if (result == null) {
            //400
            return ResponseEntity.badRequest().build();
        }

        //200. Array of measurement objects as content.
        return ResponseEntity.ok(result);
    }

    //Returns all the data of the chosen type within the specified date interval
    @GetMapping("api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}")
    public ResponseEntity<List<Measurement>> getDataOfTypeAndDate(@PathVariable Long doctorId, @PathVariable Long patientId, @PathVariable String dataType, @PathVariable String startDate, @PathVariable String endDate){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        //Null check.
        if(d.isEmpty() || p.isEmpty()){
            //404
            return ResponseEntity.notFound().build();
        }

        //Is doctor associated with the patient? If not, 404.
        if (!checkAssoc(d.get(), patientId)) {return ResponseEntity.notFound().build();}

        //200. Array of measurement objects in interval as content.
        return ResponseEntity.ok(d.get().getPatientById(patientId).getMeasurementOfTypeAndDate(dataType, startDate, endDate));
    }

    //Returns aggregate data from measurement interval.
    @GetMapping("api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}")
    public ResponseEntity<Double> getAggregatedData(@PathVariable Long doctorId, @PathVariable Long patientId, @PathVariable String dataType, @PathVariable String startDate, @PathVariable String endDate, @PathVariable String aggregateFunction){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        //Null check.
        if(d.isEmpty() || p.isEmpty()){
            //404
            return ResponseEntity.notFound().build();
        }

        //Is doctor associated with the patient? If not, 404.
        if (!checkAssoc(d.get(), patientId)) {return ResponseEntity.notFound().build();}

        //200. Array of aggregate data objects as content.
        return ResponseEntity.ok(d.get().getPatientById(patientId).applyAggregateFunction(dataType, startDate, endDate, aggregateFunction));
    }

    //Returns aggregate data from measurement interval using additional function argument (percentile).
    @GetMapping("api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}/{argument}")
    public ResponseEntity<Double> getAggregatedDataWithArgument(@PathVariable Long doctorId, @PathVariable Long patientId, @PathVariable String dataType, @PathVariable String startDate, @PathVariable String endDate, @PathVariable String aggregateFunction, @PathVariable Long argument){
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        //Null check.
        if(d.isEmpty() || p.isEmpty()){
            //404
            return ResponseEntity.notFound().build();
        }

        //Is doctor associated with the patient? If not, 404.
        if (!checkAssoc(d.get(), patientId)) {return ResponseEntity.notFound().build();}

        //200. Array of aggregate data objects as content.
        return ResponseEntity.ok(d.get().getPatientById(patientId).aggregateFunctionArgument(dataType,startDate,endDate,aggregateFunction,argument));
    }

    //Returns summary statistics from measurement interval using type and step-size.
    @GetMapping("api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/summary/{type}/{stepSize}")
    public ResponseEntity<Map<String, Map<String, Double>>> getSummaryStatistics(@PathVariable Long doctorId, @PathVariable Long patientId, @PathVariable String dataType, @PathVariable String startDate, @PathVariable String endDate, @PathVariable String type, @PathVariable Long stepSize) {
        Optional<Doctor> d = doctorRepository.findById(doctorId);
        Optional<Patient> p = patientRepository.findById(patientId);

        //Null check.
        if(d.isEmpty() || p.isEmpty()){
            //404
            return ResponseEntity.notFound().build();
        }

        //Is doctor associated with the patient? If not, 404.
        if (!checkAssoc(d.get(), patientId)) {return ResponseEntity.notFound().build();}

        //200. Array of summary statistic data objects as content.
        return ResponseEntity.ok(d.get().getPatientById(patientId).getSummary(dataType, startDate, endDate, type, stepSize));
    }

    //Checks association between doctor and patient.
    public boolean checkAssoc(Doctor d, Long pID) {
        return d.getPatientById(pID) != null;
    }
}
