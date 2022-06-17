package diabetes;

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

//Emil Pontoppidan Rasmussen, s204441 and Simon Stampe Jensen s204488
@RunWith(SpringRunner.class)
@WebMvcTest(value = PatientController.class)
public class PatientControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientRepository patientRepository;

    @MockBean
    DoctorRepository doctorRepository;

    List<Doctor> jonathanDoctors = new ArrayList<>();
    List<Doctor> emilLDoctors = new ArrayList<>();
    List<Doctor> emilPDoctors = new ArrayList<>();

    Patient Jonathan = new Patient(1L,"Jonathan","Jonathan@gmail.com",null, jonathanDoctors, null);
    Patient EmilL = new Patient(2L,"EmilL","EmilL@gmail.com",null, emilLDoctors, null);
    Patient EmilP = new Patient(3L,"EmilP","EmilP@gmail.com",null, emilPDoctors, null);

    @Test
    public void getAllPatients_success() throws Exception {
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "Simon", "Rigshospitalet", "Simon@gmail.com", simonPatients, null));

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/patients", 1L);

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "[{" +
                                        "\"patientId\":1," +
                                        "\"patientName\":\"Jonathan\"," +
                                        "\"email\":\"Jonathan@gmail.com\"}" +
                                 ",{" +
                                        "\"patientId\":2," +
                                        "\"patientName\":\"EmilL\"," +
                                        "\"email\":\"EmilL@gmail.com\"" +
                                 "}]";

        JSONAssert.assertEquals(expected_result,
                                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getAllPatients_empty() throws Exception {
        List<Patient> simonPatients = new ArrayList(Arrays.asList());
        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "Simon", "Rigshospitalet", "Simon@gmail.com", simonPatients, null));

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/patients", 1L);

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "[]";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/patients", 2L);

        result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void getPatient_success() throws Exception {
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "Simon", "Rigshospitalet", "Simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/patients/{patientId}", 1L, 1L);

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "{" +
                                        "\"patientId\":1," +
                                        "\"patientName\":\"Jonathan\"," +
                                        "\"email\":\"Jonathan@gmail.com\"" +
                                 "}";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getPatient_empty() throws Exception {
        List<Patient> simonPatients = new ArrayList(Arrays.asList());
        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "Simon", "Rigshospitalet", "Simon@gmail.com", simonPatients, null));

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(null));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/patients/{patientId}", 1L, 1L);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

}


















