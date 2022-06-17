package diabetes;

import com.fasterxml.jackson.databind.ObjectMapper;
import diabetes.controller.NotesController;
import diabetes.controller.PatientController;
import diabetes.model.Doctor;
import diabetes.model.Notes;
import diabetes.model.Patient;
import diabetes.repositories.DoctorRepository;
import diabetes.repositories.NotesRepository;
import diabetes.repositories.PatientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

//Jonathan Max Michelsen, s204437 and Simon Stampe Jensen, s204488
@RunWith(SpringRunner.class)
@WebMvcTest(value = NotesController.class)
@ContextConfiguration (classes = NotesController.class)
public class NotesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientRepository patientRepository;

    @MockBean
    DoctorRepository doctorRepository;

    @MockBean
    NotesRepository notesRepository;

    List<Doctor> jonathanDoctors = new ArrayList<>();
    List<Doctor> emilLDoctors = new ArrayList<>();

    Patient Jonathan = new Patient(1L,"Jonathan","Jonathan@gmail.com",null, jonathanDoctors, null);
    Patient EmilL = new Patient(2L,"EmilL","EmilL@gmail.com", null, emilLDoctors, null);

    Notes Note1 = new Notes(1L, "Test1", Jonathan, null, null);
    Notes Note2 = new Notes(2L, "Test2", Jonathan, null, null);

    @Test
    public void getAllPatientNotes_sucess() throws Exception{
        List<Notes> jonathanNotes = new ArrayList<>(Arrays.asList(Note1, Note2));
        List<Notes> simonNotes = new ArrayList(Arrays.asList(Note1, Note2));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));

        Jonathan.setNotes(jonathanNotes);

        Doctor Simon = new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, simonNotes);
        Note1.setDoctor(Simon);
        Note2.setDoctor(Simon);

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Simon));
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Jonathan));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes", 1L, 1L);

        MvcResult result = mockMvc.perform(request).andReturn();
        String expected_result = new ObjectMapper().writeValueAsString(Arrays.asList(Note1, Note2));

        JSONAssert.assertEquals(expected_result, result.getResponse().getContentAsString(), false);

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes", 2L, 1L);

        result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void getAllPatientNotes_empty() throws Exception {
        List<Notes> jonathanNotes = new ArrayList<>(Arrays.asList());
        List<Notes> simonNotes = new ArrayList(Arrays.asList());
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));

        Doctor Simon = new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, simonNotes);

        Jonathan.setNotes(jonathanNotes);

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Simon));
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Jonathan));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes", 1L, 1L);
        MvcResult result = mockMvc.perform(request).andReturn();

        String expected_result = "[]";

        JSONAssert.assertEquals(expected_result, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getAllPatientNotes_noAssoc() throws Exception {
        List<Notes> jonathanNotes = new ArrayList<>(Arrays.asList());
        List<Notes> simonNotes = new ArrayList(Arrays.asList());
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));

        Doctor Simon = new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, simonNotes);
        Doctor EmilP = new Doctor(2L, "simon", "rigshospitalet", "simon@gmail.com", Arrays.asList(), Arrays.asList());

        Jonathan.setNotes(jonathanNotes);

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(EmilP));
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Jonathan));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes", 2L, 1L);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void createNote_succes() throws Exception{
        List<Notes> jonathanNotes = new ArrayList<>(Arrays.asList());
        List<Notes> simonNotes = new ArrayList(Arrays.asList());
        Jonathan.setNotes(jonathanNotes);

        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Doctor Simon = new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, simonNotes);

        Note1.setDoctor(Simon);
        Note2.setDoctor(Simon);

        //Optional<Doctor> simonOpt = Optional.ofNullable(Simon);

        //simon with notes and two patients
        //Jonathan with 2 notes

        Optional<Doctor> MockResponse = Optional.ofNullable(Simon);
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        //setting up mock response

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        String mockNote = "{" +
                "\"note\": \"Test3\"," +
                "\"date\": \"1998-04-04\"," +
                "\"doctorId\": 1," +
                "\"patientId\": 1" +
                "}";

        //returning mock responses
        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes", 1L, 1L).accept(MediaType.APPLICATION_JSON).content(mockNote).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void createNote_failure() throws Exception {
        List<Notes> jonathanNotes = new ArrayList<>(Arrays.asList());
        List<Notes> simonNotes = new ArrayList(Arrays.asList());
        Jonathan.setNotes(jonathanNotes);

        List<Patient> simonPatients = new ArrayList(Arrays.asList());
        Doctor Simon = new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, simonNotes);

        Optional<Doctor> MockResponse = Optional.ofNullable(Simon);
        Optional<Patient> MockResponse2 = Optional.ofNullable(null);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        String mockNote = "{" +
                "\"note\": \"Test3\"," +
                "\"date\": \"1998-04-04\"," +
                "\"doctorId\": 1," +
                "\"patientId\": 1" +
                "}";

        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes", 1L, 3L)
                .accept(MediaType.APPLICATION_JSON)
                .content(mockNote)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());

        MockResponse2 = Optional.ofNullable(Jonathan);
        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        request = MockMvcRequestBuilders.post("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes", 1L, 1L)
                .accept(MediaType.APPLICATION_JSON)
                .content(mockNote)
                .contentType(MediaType.APPLICATION_JSON);

        result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void deleteSpecificNote_succes() throws Exception{
        //"/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes
        List<Notes> jonathanNotes = new ArrayList<>(Arrays.asList(Note1, Note2));
        List<Notes> simonNotes = new ArrayList(Arrays.asList(Note1, Note2));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));

        Jonathan.setNotes(jonathanNotes);

        Doctor Simon = new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, simonNotes);
        Note1.setDoctor(Simon);
        Note2.setDoctor(Simon);

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Simon));
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Jonathan));
        Mockito.when(notesRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Note1));

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes/{noteId}", 1L, 1L, 1L);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
    }

    @Test
    public void deleteSpecificNote_empty() throws Exception{
        //"/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes
        List<Notes> jonathanNotes = new ArrayList<>(Arrays.asList(Note1, Note2));
        List<Notes> simonNotes = new ArrayList(Arrays.asList(Note1, Note2));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));

        Jonathan.setNotes(jonathanNotes);

        Doctor Simon = new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, simonNotes);
        Note1.setDoctor(Simon);
        Note2.setDoctor(Simon);

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Simon));
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Jonathan));
        Mockito.when(notesRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes/{noteId}", 1L, 1L, 3L);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void deleteSpecificNote_noAssoc() throws Exception {
        List<Notes> jonathanNotes = new ArrayList<>(Arrays.asList(Note1, Note2));
        List<Notes> simonNotes = new ArrayList(Arrays.asList(Note1, Note2));

        Jonathan.setNotes(jonathanNotes);

        Doctor Simon = new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", Arrays.asList(), simonNotes);
        Note1.setDoctor(Simon);
        Note2.setDoctor(Simon);

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Simon));
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Jonathan));
        Mockito.when(notesRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Note1));

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes/{noteId}", 1L, 1L, 1L);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }





}























