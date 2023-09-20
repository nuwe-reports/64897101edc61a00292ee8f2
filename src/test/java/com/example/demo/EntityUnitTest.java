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
    void shouldCreateDoctorWithNoArguments() {
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
    void shouldCreatePatientWithNoArguments() {
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
    void shouldCreateRoomWithNoArguments() {
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
    @Test
    void shouldCreateAppointmentWithArguments() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");
        r1 = new Room("Oncology");
        LocalDateTime startsAt = LocalDate.now().atStartOfDay();
        LocalDateTime finishesAt = startsAt.plusHours(1);

        a1 = new Appointment(p1,d1,r1,startsAt,finishesAt);

        assertThat(a1).isNotNull();
        assertThat(a1).hasNoNullFieldsOrProperties();
    }

    @Test
    void shouldCreateAppointmentWithNoArguments() {
        a1 = new Appointment();

        assertThat(a1)
                .hasAllNullFieldsOrPropertiesExcept("id");
    }

    @Test
    void shouldGetAppointmentProperties() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");
        r1 = new Room("Oncology");
        LocalDateTime starts = LocalDate.now().atStartOfDay();
        LocalDateTime finishes = starts.plusHours(1);
        a1 = new Appointment(p1,d1,r1,starts,finishes);

        Long id = a1.getId();
        Doctor doctor = a1.getDoctor();
        Patient patient= a1.getPatient();
        Room room = a1.getRoom();
        LocalDateTime startTime = a1.getStartsAt();
        LocalDateTime finishTime = a1.getFinishesAt();

        assertThat(id).isNotNull();
        assertThat(doctor)
                .hasFieldOrPropertyWithValue("firstName", "Juan");
        assertThat(patient)
                .hasFieldOrPropertyWithValue("firstName", "Marta");
        assertThat(room)
                .hasFieldOrPropertyWithValue("roomName", "Oncology");
        assertThat(startTime).isNotNull();
        assertThat(finishTime).isNotNull();
    }

    @Test
    void shouldSetAppointmentProperties() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");
        r1 = new Room("Oncology");
        LocalDateTime starts = LocalDate.now().atStartOfDay();
        LocalDateTime finishes = starts.plusHours(1);
        Long id = 2L;

        a1 = new Appointment();
        a1.setId(id);
        a1.setDoctor(d1);
        a1.setPatient(p1);
        a1.setRoom(r1);
        a1.setStartsAt(starts);
        a1.setFinishesAt(finishes);

        assertThat(a1.getId()).isEqualTo(id);
        assertThat(a1.getDoctor()).isEqualTo(d1);
        assertThat(a1.getPatient()).isEqualTo(p1);
        assertThat(a1.getRoom()).isEqualTo(r1);
        assertThat(a1.getStartsAt()).isEqualTo(starts);
        assertThat(a1.getFinishesAt()).isEqualTo(finishes);
    }

    @Test
    void shouldOverlapsAppointment1_byStartTime() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");
        r1 = new Room("Oncology");
        LocalDateTime startsAt = LocalDate.now().atStartOfDay();
        LocalDateTime finishesAt = startsAt.plusHours(1);

        a1 = new Appointment(p1,d1,r1,startsAt,finishesAt);

        a2 = new Appointment(p1,d1,r1,startsAt,finishesAt);
        a2.setFinishesAt(startsAt.plusMinutes(30));

        assertThat(a1.overlaps(a2));
    }

    @Test
    void shouldOverlapsAppointment2_byFinishTime() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");
        r1 = new Room("Oncology");
        LocalDateTime startsAt = LocalDate.now().atStartOfDay();
        LocalDateTime finishesAt = startsAt.plusHours(1);

        a1 = new Appointment(p1,d1,r1,startsAt,finishesAt);

        a2 = new Appointment(p1,d1,r1,startsAt,finishesAt);
        a2.setStartsAt(startsAt.plusMinutes(30));

        assertThat(a1.overlaps(a2));
    }

    @Test
    void shouldOverlapsAppointment3_AStartsBeforeBFinishes_and_BFinishesBeforeAFinishes() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");
        r1 = new Room("Oncology");
        LocalDateTime startsAt = LocalDate.now().atStartOfDay();
        LocalDateTime finishesAt = startsAt.plusHours(1);

        a1 = new Appointment(p1,d1,r1,startsAt,finishesAt);

        a2 = new Appointment(p1,d1,r1,startsAt,finishesAt);
        a2.setStartsAt(startsAt.plusMinutes(20));
        a2.setFinishesAt(finishesAt.minusMinutes(10));

        assertThat(a1.overlaps(a2));
    }

    @Test
    void shouldOverlapsAppointment4_BStartsBeforeAStarts_and_AFinishesBeforeBFinishes() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");
        r1 = new Room("Oncology");
        LocalDateTime startsAt = LocalDate.now().atStartOfDay();
        LocalDateTime finishesAt = startsAt.plusHours(1);

        a1 = new Appointment(p1,d1,r1,startsAt,finishesAt);

        a2 = new Appointment(p1,d1,r1,startsAt,finishesAt);
        a2.setStartsAt(startsAt.plusMinutes(10));
        a2.setFinishesAt(finishesAt.plusMinutes(10));

        assertThat(a1.overlaps(a2));
    }

    @Test
    void shouldNotOverlapsAppointment() {
        d1 = new Doctor("Juan", "Garcia", 38, "juan@mail.com");
        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");
        r1 = new Room("Oncology");
        LocalDateTime startsAt = LocalDate.now().atStartOfDay();
        LocalDateTime finishesAt = startsAt.plusHours(1);

        a1 = new Appointment(p1,d1,r1,startsAt,finishesAt);

        a2 = new Appointment(p1,d1,r1,startsAt,finishesAt);
        a2.setStartsAt(startsAt.plusHours(2));
        a2.setFinishesAt(finishesAt.plusHours(2));

        assertThat(a1.overlaps(a2)).isFalse();
    }






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
