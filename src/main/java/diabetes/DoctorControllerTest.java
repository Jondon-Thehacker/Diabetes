package diabetes;

import com.fasterxml.jackson.databind.ObjectMapper;
import diabetes.controller.DoctorController;
import diabetes.controller.PatientController;
import diabetes.model.Doctor;
import diabetes.model.Patient;
import diabetes.repositories.DoctorRepository;
import diabetes.repositories.PatientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.*;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DoctorController.class)
public class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientRepository patientRepository;

    @MockBean
    DoctorRepository doctorRepository;

    List<Doctor> doctors = new ArrayList<>();

    List<Patient> patients = Arrays.asList(new Patient(1L,"Jonathan","Jonathan@gmail.com",null, doctors, null));
    Doctor testDoc = new Doctor(1L, "Sylvester Stallone", "Rigshospitalet", "sylvesterstallone@gmail.com", patients, null);

    @Test
    public void getDoctor_succes() throws Exception {
        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(testDoc));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}", 1L);

        String expected_result = new ObjectMapper().writeValueAsString(testDoc);
        MvcResult result = mockMvc.perform(request).andReturn();

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getDoctor_failure() throws Exception {
        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(null));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}", 1L);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }
}