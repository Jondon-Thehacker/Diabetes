package diabetes;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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

        Optional<Doctor> MockResponse = Optional.ofNullable(Simon);
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);


        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes", 1L, 1L);

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println("result: " + result.getResponse().getContentAsString());

        String expected_result = "[{" +
                "\"noteId\":1, " +
                "\"note\": \"Test1\", " +
                "\"patient\": {" +
                                "\"patientId\": 1, " +
                                "\"patientName\":\"Jonathan\"," +
                                "\"email\":\"Jonathan@gmail.com\"}" +
                                "}" +
                ",{" +
                "\"noteId\":2, " +
                "\"note\": \"Test2\", " +
                "\"patient\": {" +
                                "\"patientId\":1," +
                                "\"patientName\":\"Jonathan\"," +
                                "\"email\":\"Jonathan@gmail.com\"" +
                                "}" +
                "}]";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void createNote_succes() throws Exception{
        List<Notes> jonathanNotes = new ArrayList<>(Arrays.asList(Note1, Note2));
        List<Notes> simonNotes = new ArrayList(Arrays.asList(Note1, Note2));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setNotes(jonathanNotes);
        Doctor Simon = new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, simonNotes);
        Note1.setDoctor(Simon);
        Note2.setDoctor(Simon);

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

        System.out.println("Ran Test");
        System.out.println("result: " + result.getResponse().getContentAsString());

        String expected_result = "[{" +
                "\"noteId\":1, " +
                "\"note\": \"Test1\", " +
                "\"patient\": {" +
                "\"patientId\": 1, " +
                "\"patientName\":\"Jonathan\"," +
                "\"email\":\"Jonathan@gmail.com\"}" +
                "}" +
                ",{" +
                "\"noteId\":2, " +
                "\"note\": \"Test2\", " +
                "\"patient\": {" +
                "\"patientId\":1," +
                "\"patientName\":\"Jonathan\"," +
                "\"email\":\"Jonathan@gmail.com\"" +
                "}" +
                "}," +
                "{" +
                "\"noteId\":3, " +
                "\"note\": \"Test3\", "+
                "\"patient\": {" +
                "\"patientId\":1," +
                "\"patientName\":\"Jonathan\"," +
                "\"email\":\"Jonathan@gmail.com\" }" +
                "}]";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);

    }

    @Test
    public void deleteSpecificNote_succes() throws Exception{
        //"/api/v1/Doctors/{doctorId}/patients/{patientId}/Notes
    }





}























