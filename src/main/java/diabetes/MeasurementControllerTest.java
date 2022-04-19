package diabetes;


import diabetes.controller.MeasurementController;
import diabetes.controller.PatientController;
import diabetes.model.Doctor;
import diabetes.model.Measurement;
import diabetes.model.Notes;
import diabetes.model.Patient;
import diabetes.repositories.DoctorRepository;
import diabetes.repositories.MeasurementRepository;
import diabetes.repositories.PatientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MeasurementController.class)
public class MeasurementControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientRepository patientRepository;

    @MockBean
    DoctorRepository doctorRepository;

    @MockBean
    MeasurementRepository measurementRepository;

    List<Doctor> jonathanDoctors = new ArrayList<>();

    List<Doctor> emilLDoctors = new ArrayList<>();

    List<Doctor> emilPDoctors = new ArrayList<>();

    Patient Jonathan = new Patient(2L,"Jonathan","Jonathan@gmail.com",null, jonathanDoctors, null);

    Patient EmilL = new Patient(3L,"EmilL","EmilL@gmail.com",null, emilLDoctors, null);

    Measurement CGM1 = new Measurement(1L, 10, new Timestamp(2022-1900, 4-1, 14+1, 0, 0, 0, 0),Jonathan, Measurement.MeasurementName.CGM);
    Measurement CGM2 = new Measurement(2L, 20, new Timestamp(2022-1900, 4-1, 15+1, 0, 0, 0, 0),Jonathan, Measurement.MeasurementName.CGM);
    Measurement CGM3 = new Measurement(3L, 30, new Timestamp(2022-1900, 4-1, 16+1, 0, 0, 0, 0),Jonathan, Measurement.MeasurementName.CGM);
    Measurement CGM4 = new Measurement(4L, 40, new Timestamp(2022-1900, 4-1, 17+1, 0, 0, 0, 0),Jonathan, Measurement.MeasurementName.CGM);

    @Test
    public void getMeasurementsOfDatatype() throws Exception {
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{DoctorID}/Patients/{PatientID}/Measurements/{DataType}", 1L,2L, "CGM");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "[{" +
                                        "\"measurementId\": 1," +
                                        "\"value\":10," +
                                        "\"time\":\"2022-04-14T22:00:00.000+00:00\"," +
                                        "\"patient\":{" +
                                        "\"patientId\":2," +
                                        "\"patientName\":\"Jonathan\"," +
                                        "\"email\":\"Jonathan@gmail.com\"" +
                                        "}," +
                                        "\"measurementName\": \"CGM\"\n" +
                                        "}," +
                                        "{" +
                                        "\"measurementId\":2," +
                                        "\"value\":20," +
                                        "\"time\": \"2022-04-15T22:00:00.000+00:00\"," +
                                        "\"patient\": {" +
                                        "\"patientId\": 2," +
                                        "\"patientName\": \"Jonathan\"," +
                                        "\"email\": \"Jonathan@gmail.com\"" +
                                        "}," +
                                        "\"measurementName\": \"CGM\"" +
                                        "}," +
                                        "{" +
                                        "\"measurementId\": 3," +
                                        "\"value\": 30," +
                                        "\"time\": \"2022-04-16T22:00:00.000+00:00\"," +
                                        "\"patient\": {" +
                                        "\"patientId\": 2," +
                                        "\"patientName\": \"Jonathan\"," +
                                        "\"email\": \"Jonathan@gmail.com\"" +
                                        "}," +
                                        "\"measurementName\": \"CGM\"" +
                                        "}," +
                                        "{" +
                                        "\"measurementId\": 4," +
                                        "\"value\":40," +
                                        "\"time\":\"2022-04-17T22:00:00.000+00:00\"," +
                                        "\"patient\":{" +
                                        "\"patientId\":2," +
                                        "\"patientName\":\"Jonathan\"," +
                                        "\"email\":\"Jonathan@gmail.com\"" +
                                        "}," +
                                        "\"measurementName\":\"CGM\"" +
                                        "}]";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);

    }

    @Test
    public void getMeasurementsOfDatatypeStartDateEndDate() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}", 1L,2L, "CGM","2022-04-15 00:00","2022-04-18 00:00");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "[{" +
                "\"measurementId\":2," +
                "\"value\":20," +
                "\"time\": \"2022-04-15T22:00:00.000+00:00\"," +
                "\"patient\": {" +
                "\"patientId\": 2," +
                "\"patientName\": \"Jonathan\"," +
                "\"email\": \"Jonathan@gmail.com\"" +
                "}," +
                "\"measurementName\": \"CGM\"" +
                "}," +
                "{" +
                "\"measurementId\": 3," +
                "\"value\": 30," +
                "\"time\": \"2022-04-16T22:00:00.000+00:00\"," +
                "\"patient\": {" +
                "\"patientId\": 2," +
                "\"patientName\": \"Jonathan\"," +
                "\"email\": \"Jonathan@gmail.com\"" +
                "}," +
                "\"measurementName\": \"CGM\"" +
                "}]";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getStandardDeviation() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-14 00:00","2022-04-19 00:00","standardDeviation");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "11.180339887498949";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getAverage() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-14 00:00","2022-04-19 00:00","average");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "25.0";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getCount() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-14 00:00","2022-04-19 00:00","count");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "4.0";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getGlucoseVariability() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-14 00:00","2022-04-19 00:00","glucoseVariability");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "0.447213595499958";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getMax() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-14 00:00","2022-04-19 00:00","max");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "40.0";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getMin() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-14 00:00","2022-04-19 00:00","min");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "10.0";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getGMI() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-14 00:00","2022-04-19 00:00","GMI");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "3.908";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getCountAbove() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-14 00:00","2022-04-19 00:00","countAbove");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "0.0";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getCountBelow() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-14 00:00","2022-04-19 00:00","countBelow");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "4.0";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }
}