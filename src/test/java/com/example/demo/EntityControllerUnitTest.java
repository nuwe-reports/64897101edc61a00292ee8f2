
package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateDoctor() throws Exception{
        Doctor doctor = new Doctor("Juan", "Garcia", 38, "juan@mail.com");

        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(doctor.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", CoreMatchers.is(doctor.getAge())));

    }

    @Test
    void shouldGetDoctorById() throws Exception{
        Doctor doctor = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
        doctor.setId(1);
        Optional<Doctor> opt = Optional.of(doctor);
        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);

        mockMvc.perform(get("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(doctor.getFirstName())));
    }

    @Test
    void shouldNotGetAnyDoctorById() throws Exception{
        long id = 23;

        mockMvc.perform(get("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetTwoDoctors() throws Exception{
        Doctor doctor = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
        Doctor doctor2 = new Doctor("Marta", "Gonzales", 52, "marta@mail.com");

        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        doctors.add(doctor2);

        when(doctorRepository.findAll()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(doctors.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", CoreMatchers.is(doctors.get(0).getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age", CoreMatchers.is(doctors.get(0).getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", CoreMatchers.is(doctors.get(1).getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].age", CoreMatchers.is(doctors.get(1).getAge())));
    }

    @Test
    void shouldNotGetDoctors() throws Exception{
        List<Doctor> doctors = new ArrayList<>();
        when(doctorRepository.findAll()).thenReturn(doctors);

        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeleteDoctorById() throws Exception{
        Doctor doctor = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
        doctor.setId(1);

        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        doNothing().when(doctorRepository).deleteById(doctor.getId());

        mockMvc.perform(delete("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteDoctorById() throws Exception{
        long id = 23;

        mockMvc.perform(delete("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllDoctors() throws Exception{
        doNothing().when(doctorRepository).deleteAll();

        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }

}

@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePatient() throws Exception{
        Patient patient = new Patient("Carlos", "Lopez", 34, "carlos@mail.com");

        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(patient.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.age", CoreMatchers.is(patient.getAge())));

    }

    @Test
    void shouldGetPatientById() throws Exception{
        Patient patient = new Patient("Carlos", "Lopez", 34, "carlos@mail.com");
        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);
        when(patientRepository.findById(patient.getId())).thenReturn(opt);

        mockMvc.perform(get("/api/patients/" + patient.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(patient.getFirstName())));

    }

    @Test
    void shouldNotGetAnyPatientById() throws Exception{
        long id = 23;

        mockMvc.perform(get("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetTwoPatients() throws Exception{
        Patient patient = new Patient("Carlos", "Lopez", 34, "carlos@mail.com");
        Patient patient2 = new Patient("Marta", "Gonzales", 52, "marta@mail.com");

        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        patients.add(patient2);

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(patients.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", CoreMatchers.is(patients.get(0).getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age", CoreMatchers.is(patients.get(0).getAge())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", CoreMatchers.is(patients.get(1).getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].age", CoreMatchers.is(patients.get(1).getAge())));

    }

    @Test
    void shouldNotGetPatients() throws Exception{
        List<Patient> patients = new ArrayList<>();

        when(patientRepository.findAll()).thenReturn(patients);

        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldDeletePatientById() throws Exception{
        Patient patient = new Patient("Carlos", "Lopez", 34, "carlos@mail.com");
        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        doNothing().when(patientRepository).deleteById(patient.getId());

        mockMvc.perform(delete("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeletePatientById() throws Exception{
        long id = 23;

        mockMvc.perform(delete("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllPatients() throws Exception{
        doNothing().when(patientRepository).deleteAll();
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }

}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateRoom() throws Exception{
        Room room = new Room("Oncology");

        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomName", CoreMatchers.is(room.getRoomName())));
    }

    @Test
    void shouldGetRoomByName() throws Exception{
        Room room = new Room("Oncology");

        Optional<Room> opt = Optional.of(room);

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        mockMvc.perform(get("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.roomName", CoreMatchers.is(room.getRoomName())));

    }

    @Test
    void shouldNotGetRoomByName() throws Exception{
        String roomName = "Rx";

        when(roomRepository.findByRoomName(roomName)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldGetTwoRooms() throws Exception {
        Room room = new Room("Oncology");
        Room room2 = new Room("Rx");
        List<Room> rooms = new ArrayList<>();
        rooms.add(room);
        rooms.add(room2);

        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(rooms.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].roomName", CoreMatchers.is(rooms.get(0).getRoomName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].roomName", CoreMatchers.is(rooms.get(1).getRoomName())));
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
        Room room = new Room("Oncology");

        Optional<Room> opt = Optional.of(room);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room.getRoomName());

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        doNothing().when(roomRepository).deleteByRoomName(room.getRoomName());

        mockMvc.perform(delete("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteRoom() throws Exception{
        String roomName = "Rx";
        mockMvc.perform(delete("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllRooms() throws Exception{
        doNothing().when(roomRepository).deleteAll();
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }


}
