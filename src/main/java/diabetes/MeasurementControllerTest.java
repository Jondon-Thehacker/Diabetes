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
import jdk.jfr.StackTrace;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

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
    public void getMeasurementsOfDatatype_success() throws Exception {
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(MockResponse);
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Jonathan));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{DoctorID}/Patients/{PatientID}/Measurements/{DataType}", 1L,2L, "CGM");

        MvcResult result = mockMvc.perform(request).andReturn();

        String expected_result = "[" +
                                    "{" +
                                        "\"measurementId\":1," +
                                        "\"value\":10.0," +
                                        "\"time\":\"2022-04-14T22:00:00.000+00:00\"," +
                                        "\"patient\":{" +
                                            "\"patientId\":2," +
                                            "\"patientName\":\"Jonathan\"," +
                                            "\"email\":\"Jonathan@gmail.com\"" +
                                        "}," +
                                        "\"measurementName\":\"CGM\"" +
                                    "}," +
                                    "{" +
                                        "\"measurementId\":2," +
                                        "\"value\":20.0," +
                                        "\"time\":\"2022-04-15T22:00:00.000+00:00\"," +
                                        "\"patient\":{" +
                                            "\"patientId\":2," +
                                            "\"patientName\":\"Jonathan\"," +
                                            "\"email\":\"Jonathan@gmail.com\"" +
                                        "}," +
                                        "\"measurementName\":\"CGM\"" +
                                    "}," +
                                    "{" +
                                        "\"measurementId\":3," +
                                        "\"value\":30.0," +
                                        "\"time\":\"2022-04-16T22:00:00.000+00:00\"," +
                                        "\"patient\":{" +
                                            "\"patientId\":2," +
                                            "\"patientName\":\"Jonathan\"," +
                                            "\"email\":\"Jonathan@gmail.com\"" +
                                        "}," +
                                        "\"measurementName\":\"CGM\"" +
                                    "}," +
                                    "{" +
                                        "\"measurementId\":4," +
                                        "\"value\":40.0," +
                                        "\"time\":\"2022-04-17T22:00:00.000+00:00\"," +
                                        "\"patient\":{" +
                                            "\"patientId\":2," +
                                            "\"patientName\":\"Jonathan\"," +
                                            "\"email\":\"Jonathan@gmail.com\"" +
                                        "}," +
                                        "\"measurementName\":\"CGM\"" +
                                    "}" +
                                "]";

        JSONAssert.assertEquals(expected_result, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getMeasurementsOfDatatype_empty() throws Exception {
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList());
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(MockResponse);
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Jonathan));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{DoctorID}/Patients/{PatientID}/Measurements/{DataType}", 1L,2L, "CGM");

        MvcResult result = mockMvc.perform(request).andReturn();

        String expected_result = "[]";

        JSONAssert.assertEquals(expected_result, result.getResponse().getContentAsString(), false);

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        request = MockMvcRequestBuilders.get("/api/v1/Doctors/{DoctorID}/Patients/{PatientID}/Measurements/{DataType}", 2L,2L, "CGM");

        result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void getMeasurementsOfDatatype_invalidParameter() throws Exception {
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(MockResponse);
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Jonathan));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{DoctorID}/Patients/{PatientID}/Measurements/{DataType}", 1L,2L, "INVALIDDATATYPE");

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    public void getMeasurementsOfDatatypeStartDateEndDate_success() throws Exception{
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
    public void getMeasurementsOfDatatypeStartDateEndDate_empty() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList());
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

        String expected_result = "[]";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.ofNullable(null));

        request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}", 2L,2L, "CGM","2022-04-15 00:00","2022-04-18 00:00");

        result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
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

        String expected_result = "130.35675";

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

        String expected_result = "3.0";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getCountBelow() throws Exception{
        Measurement CGM0 = new Measurement(1L, 0, new Timestamp(2022-1900, 4-1, 13+1, 0, 0, 0, 0),Jonathan, Measurement.MeasurementName.CGM);

        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM0, CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-13 00:00","2022-04-19 00:00","countBelow");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "1.0";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getCountSlightlyBelow() throws Exception{
        Measurement CGM0 = new Measurement(1L, 3, new Timestamp(2022-1900, 4-1, 13+1, 0, 0, 0, 0),Jonathan, Measurement.MeasurementName.CGM);

        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM0, CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-13 00:00","2022-04-19 00:00","countSlightlyBelow");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "1.0";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getCountSlightlyAbove() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-14 00:00","2022-04-19 00:00","countSlightlyAbove");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "1.0";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getCountInRange() throws Exception{
        Measurement CGM0 = new Measurement(1L, 5, new Timestamp(2022-1900, 4-1, 13+1, 0, 0, 0, 0),Jonathan, Measurement.MeasurementName.CGM);

        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM0, CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 1L,2L, "CGM","2022-04-13 00:00","2022-04-19 00:00","countInRange");

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println("Ran Test");
        System.out.println(result.getResponse().getContentAsString());

        String expected_result = "1.0";

        JSONAssert.assertEquals(expected_result,
                result.getResponse().getContentAsString(), false);
    }

    @Test
    public void getSummary_barChart() throws Exception{
        List<Measurement> jonathanMeasurement = new ArrayList<>();

        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));

        for (int i = 0; i < 1440; i++) {
            if (i % 5 == 0) {
                jonathanMeasurement.add(new Measurement(1L, 5, new Timestamp(2022-1900, 4-1, 14+1, i / 60, i % 60, 0, 0),Jonathan, Measurement.MeasurementName.CGM));
            }
        }

        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));
        Optional<Patient> MockResponse2 = Optional.ofNullable(Jonathan);

        Mockito.when(doctorRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse);

        Mockito.when(patientRepository.findById(Mockito.anyLong()))
                .thenReturn(MockResponse2);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/summary/{type}/{stepSize}", 1L,2L, "CGM","2022-04-13 00:00","2022-04-19 00:00","barChart", 5L);

        MvcResult result = mockMvc.perform(request).andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void getPercentile() throws Exception {
        List<Measurement> jonathanMeasurement = new ArrayList<>(Arrays.asList(CGM1, CGM2, CGM3, CGM4));
        List<Patient> simonPatients = new ArrayList(Arrays.asList(Jonathan, EmilL));
        Jonathan.setMeasurements(jonathanMeasurement);

        Optional<Doctor> MockResponse = Optional.ofNullable(new Doctor(1L, "simon", "rigshospitalet", "simon@gmail.com", simonPatients, null));

        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(MockResponse);
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(Jonathan));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}/{Argument}", 1L,2L, "CGM","2022-04-13 00:00","2022-04-19 00:00","percentile", 50L);

        MvcResult result = mockMvc.perform(request).andReturn();

        String expected_result = new ObjectMapper().writeValueAsString(20.0);

        JSONAssert.assertEquals(result.getResponse().getContentAsString(), expected_result, false);
    }

    @Test
    public void getAggregate_empty() throws Exception {
        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}", 2L,3L, "CGM","2022-04-14 00:00","2022-04-19 00:00","count");

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void getAggregateWithArgument_empty() throws Exception {
        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/{aggregateFunction}/{Argument}", 2L,3L, "CGM","2022-04-13 00:00","2022-04-19 00:00","percentile", 50L);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    public void getSummary_empty() throws Exception {
        Mockito.when(doctorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
        Mockito.when(patientRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/Doctors/{doctorId}/{patientId}/{dataType}/{startDate}/{endDate}/summary/{type}/{stepSize}", 2L,3L, "CGM","2022-04-13 00:00","2022-04-19 00:00","barChart", 5L);

        MvcResult result = mockMvc.perform(request).andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }
}