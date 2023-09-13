package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;


    //-- DOCTOR CLASS --
    @Test
    void shouldCreateDoctorWithArguments() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");

        assertThat(d1.getId()).isNotNull();
        assertThat(d1)
                .hasFieldOrPropertyWithValue("firstName", "Juan")
                .hasFieldOrPropertyWithValue("lastName", "Garcia")
                .hasFieldOrPropertyWithValue("age", 38)
                .hasFieldOrPropertyWithValue("email", "juan@mail.com");
    }


    @Test
    void shouldCreateDoctorWithoutArguments() {
        d1 = new Doctor();

        assertThat(d1).isNotNull();
        assertThat(d1.getId()).isNotNull();
    }

    @Test
    void shouldChangeDoctorFirstName() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");

        d1.setFirstName("Carlos");

        assertThat(d1.getFirstName()).isEqualTo("Carlos");
    }

    @Test
    void shouldChangeDoctorLastName() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");

        d1.setLastName("Rodriguez");

        assertThat(d1.getLastName()).isEqualTo("Rodriguez");
    }

    @Test
    void shouldChangeDoctorAge() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");

        d1.setAge(22);

        assertThat(d1.getAge()).isEqualTo(22);
    }

    @Test
    void shouldChangeDoctorEmail() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");

        d1.setEmail("garciaJuan@mail.com");

        assertThat(d1.getEmail()).isEqualTo("garciaJuan@mail.com");
    }

    @Test
    void shouldChangeDoctorId() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");

        d1.setId(2);

        assertThat(d1.getId()).isEqualTo(2);
    }


    //-- PATIENT CLASS --
    @Test
    void shouldCreatePatientWithArguments() {
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");

        assertThat(p1.getId()).isNotNull();
        assertThat(p1)
                .hasFieldOrPropertyWithValue("firstName", "Marta")
                .hasFieldOrPropertyWithValue("lastName", "Sanchez")
                .hasFieldOrPropertyWithValue("age", 27)
                .hasFieldOrPropertyWithValue("email", "marta@mail.com");
    }

    @Test
    void shouldCreatePatientWithoutArguments() {
        p1 = new Patient();

        assertThat(p1).isNotNull();
        assertThat(p1.getId()).isNotNull();
    }

    @Test
    void shouldChangePatientFirstName() {
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");

        p1.setFirstName("Maria");

        assertThat(p1.getFirstName()).isEqualTo("Maria");
    }

    @Test
    void shouldChangePatientLastName() {
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");

        p1.setLastName("Rodriguez");

        assertThat(p1.getLastName()).isEqualTo("Rodriguez");
    }

    @Test
    void shouldChangePatientAge() {
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");

        p1.setAge(22);

        assertThat(p1.getAge()).isEqualTo(22);
    }

    @Test
    void shouldChangePatientEmail() {
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");

        p1.setEmail("sanchezMarta@mail.com");

        assertThat(p1.getEmail()).isEqualTo("sanchezMarta@mail.com");
    }

    @Test
    void shouldChangePatientId() {
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");

        p1.setId(2);

        assertThat(p1.getId()).isEqualTo(2);
    }


    //-- ROOM CLASS --
    @Test
    void shouldCreateRoomWithArguments() {
        r1 = new Room("Oncology");

        assertThat(r1)
                .hasFieldOrPropertyWithValue("roomName", "Oncology");
    }

    @Test
    void shouldCreateRoomWithoutArguments() {
        r1 = new Room();

        assertThat(r1).isNotNull();
    }

    @Test
    void shouldGetRoomName() {
        r1 = new Room("Oncology");

        String roomName = r1.getRoomName();

        assertThat(roomName).isEqualTo("Oncology");
    }

    //-- APPOINTMENT CLASS --





    /** TODO
     * Implement tests for each Entity class: Doctor, Patient, Room and Appointment.
     * Make sure you are as exhaustive as possible. Coverage is checked ;)
     */


    //    @BeforeAll
//    void setup() {
//        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
//        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");
//        r1 = new Room("Oncology");
//        LocalDateTime startsAt = LocalDate.now().atStartOfDay();
//        LocalDateTime finishesAt = startsAt.plusHours(1);
//        a1 = new Appointment(p1,d1,r1,startsAt,finishesAt);
//
//    }

//    @Test
//    void should_create_doctor(){
//        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
//        Doctor savedDoctor = entityManager.persist(d1);
//        System.out.println(d1.getId());
//
//        assertThat(savedDoctor.getId()).isNotNull();
//        assertThat(savedDoctor.getFirstName().equals("Juan"));
//        assertThat(savedDoctor.getLastName().equals("Garcia"));
//        assertThat(savedDoctor.getEmail().equals("jaun@gmail.com"));
//    }

    //    @Test
//    void shouldGetDoctorFirstName() {
//        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
//
//        String firstName = d1.getFirstName();
//
//        assertThat(firstName).isEqualTo("Juan");
//    }
//
//    @Test
//    void shouldGetDoctorLastName() {
//        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
//
//        String lastName = d1.getLastName();
//
//        assertThat(lastName).isEqualTo("Garcia");
//    }
//
//    @Test
//    void shouldGetDoctorAge() {
//        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
//
//        int age = d1.getAge();
//
//        assertThat(age).isEqualTo(38);
//    }
}
