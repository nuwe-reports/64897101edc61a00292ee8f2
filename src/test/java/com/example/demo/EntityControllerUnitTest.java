
package com.example.demo;

import com.example.demo.controllers.DoctorController;
import com.example.demo.controllers.PatientController;
import com.example.demo.controllers.RoomController;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Room;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.PatientRepository;
import com.example.demo.repositories.RoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest {

    private static final String DOCTOR_FIRST_NAME = "Juan";
    private static final String DOCTOR_LAST_NAME = "Garcia";
    private static final String DOCTOR_EMAIL = "juan@mail.com";
    private static final int DOCTOR_AGE = 38;

    private static final String DOCTOR_FIRST_NAME2 = "Marta";
    private static final String DOCTOR_LAST_NAME2 = "Gonzales";
    private static final String DOCTOR_EMAIL2 = "marta@mail.com";
    private static final int DOCTOR_AGE2 = 52;

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateDoctor() throws Exception {
        Doctor doctor = new Doctor(DOCTOR_FIRST_NAME, DOCTOR_LAST_NAME, DOCTOR_AGE, DOCTOR_EMAIL);

        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(doctor.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(doctor.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", CoreMatchers.is(doctor.getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(doctor.getEmail())))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetDoctorById() throws Exception {
        Doctor doctor = new Doctor(DOCTOR_FIRST_NAME, DOCTOR_LAST_NAME, DOCTOR_AGE, DOCTOR_EMAIL);
        doctor.setId(1);
        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);

        mockMvc.perform(get("/api/doctors/" + doctor.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(doctor.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(doctor.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", CoreMatchers.is(doctor.getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(doctor.getEmail())))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyDoctorById() throws Exception {
        long id = 23;
        when(doctorRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetTwoDoctors() throws Exception {
        Doctor doctor = new Doctor(DOCTOR_FIRST_NAME, DOCTOR_LAST_NAME, DOCTOR_AGE, DOCTOR_EMAIL);
        Doctor doctor2 = new Doctor(DOCTOR_FIRST_NAME2, DOCTOR_LAST_NAME2, DOCTOR_AGE2, DOCTOR_EMAIL2);

        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        doctors.add(doctor2);

        when(doctorRepository.findAll()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctors"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(doctors.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", CoreMatchers.is(doctors.get(0).getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age", CoreMatchers.is(doctors.get(0).getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", CoreMatchers.is(doctors.get(1).getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].age", CoreMatchers.is(doctors.get(1).getAge())))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetDoctors() throws Exception {
        List<Doctor> doctors = new ArrayList<>();
        when(doctorRepository.findAll()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteDoctorById() throws Exception {
        Doctor doctor = new Doctor(DOCTOR_FIRST_NAME, DOCTOR_LAST_NAME, DOCTOR_AGE, DOCTOR_EMAIL);
        doctor.setId(1);

        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        doNothing().when(doctorRepository).deleteById(doctor.getId());

        mockMvc.perform(delete("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteDoctorById() throws Exception {
        long id = 23;

        when(doctorRepository.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllDoctors() throws Exception {
        doNothing().when(doctorRepository).deleteAll();

        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }

}

@WebMvcTest(PatientController.class)
class PatientControllerUnitTest {
    private static final String PATIENT_FIRST_NAME = "Carlos";
    private static final String PATIENT_LAST_NAME = "Lopez";
    private static final String PATIENT_EMAIL = "carlos@mail.com";
    private static final int PATIENT_AGE = 34;

    private static final String PATIENT_FIRST_NAME2 = "Maria";
    private static final String PATIENT_LAST_NAME2 = "Fuente";
    private static final String PATIENT_EMAIL2 = "maria@mail.com";
    private static final int PATIENT_AGE2 = 63;

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePatient() throws Exception {
        Patient patient = new Patient(PATIENT_FIRST_NAME, PATIENT_LAST_NAME, PATIENT_AGE, PATIENT_EMAIL);

        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(patient.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(patient.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(patient.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", CoreMatchers.is(patient.getAge())))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetPatientById() throws Exception {
        Patient patient = new Patient(PATIENT_FIRST_NAME, PATIENT_LAST_NAME, PATIENT_AGE, PATIENT_EMAIL);
        patient.setId(1);
        Optional<Patient> opt = Optional.of(patient);

        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);

        mockMvc.perform(get("/api/patients/" + patient.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(patient.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(patient.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", CoreMatchers.is(patient.getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(patient.getEmail())))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyPatientById() throws Exception {
        long id = 23;

        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetTwoPatients() throws Exception {
        Patient patient = new Patient(PATIENT_FIRST_NAME, PATIENT_LAST_NAME, PATIENT_AGE, PATIENT_EMAIL);
        Patient patient2 = new Patient(PATIENT_FIRST_NAME2, PATIENT_LAST_NAME2, PATIENT_AGE2, PATIENT_EMAIL2);

        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        patients.add(patient2);

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(patients.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", CoreMatchers.is(patients.get(0).getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age", CoreMatchers.is(patients.get(0).getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", CoreMatchers.is(patients.get(1).getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].age", CoreMatchers.is(patients.get(1).getAge())))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetPatients() throws Exception {
        List<Patient> patients = new ArrayList<>();

        when(patientRepository.findAll()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeletePatientById() throws Exception {
        Patient patient = new Patient(PATIENT_FIRST_NAME, PATIENT_LAST_NAME, PATIENT_AGE, PATIENT_EMAIL);
        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        doNothing().when(patientRepository).deleteById(patient.getId());

        mockMvc.perform(delete("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeletePatientById() throws Exception {
        long id = 23;

        when(patientRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllPatients() throws Exception {
        doNothing().when(patientRepository).deleteAll();
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }

}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest {

    public static final String ROOM_NAME = "Oncology";
    public static final String ROOM_NAME2 = "Rx";

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateRoom() throws Exception {
        Room room = new Room(ROOM_NAME);

        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomName", CoreMatchers.is(room.getRoomName())))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldGetRoomByName() throws Exception {
        Room room = new Room(ROOM_NAME);

        Optional<Room> opt = Optional.of(room);

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        mockMvc.perform(get("/api/rooms/" + room.getRoomName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomName", CoreMatchers.is(room.getRoomName())))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetRoomByName() throws Exception {
        String roomName = ROOM_NAME2;

        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetTwoRooms() throws Exception {
        Room room = new Room(ROOM_NAME);
        Room room2 = new Room(ROOM_NAME2);
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        rooms.add(room2);

        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(rooms.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].roomName", CoreMatchers.is(rooms.get(0).getRoomName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].roomName", CoreMatchers.is(rooms.get(1).getRoomName())))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyRooms() throws Exception {
        List<Room> rooms = new ArrayList<>();

        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteRoomByRoomName() throws Exception {
        Room room = new Room(ROOM_NAME);

        Optional<Room> opt = Optional.of(room);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room.getRoomName());

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        doNothing().when(roomRepository).deleteByRoomName(room.getRoomName());

        mockMvc.perform(delete("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteRoom() throws Exception {
        String roomName = ROOM_NAME2;
        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllRooms() throws Exception {
        doNothing().when(roomRepository).deleteAll();
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }


}
