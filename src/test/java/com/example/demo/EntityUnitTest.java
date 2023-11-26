package com.example.demo;

import com.example.demo.entities.Appointment;
import com.example.demo.entities.Doctor;
import com.example.demo.entities.Patient;
import com.example.demo.entities.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

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

    private final String DOCTOR_FIRST_NAME = "Juan";
    private final String DOCTOR_LAST_NAME = "Garcia";
    private final int DOCTOR_AGE = 38;
    private final String DOCTOR_EMAIL = "juan@mail.com";


    @BeforeEach
    public void setup() {

        d1 = new Doctor(DOCTOR_FIRST_NAME, DOCTOR_LAST_NAME, DOCTOR_AGE, DOCTOR_EMAIL);

        p1 = new Patient("Marta", "Sanchez", 27, "marta@mail.com");

        r1 = new Room("Oncology");

        LocalDateTime startsAt = LocalDate.now().atStartOfDay();
        LocalDateTime finishesAt = startsAt.plusHours(1);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
    }

    @Nested
    class DoctorEntityTest {
        @Test
        void shouldCreateDoctorWithArguments() {
            assertThat(d1.getId()).isPositive();
            assertThat(d1)
                    .hasFieldOrPropertyWithValue("firstName", DOCTOR_FIRST_NAME)
                    .hasFieldOrPropertyWithValue("lastName", DOCTOR_LAST_NAME)
                    .hasFieldOrPropertyWithValue("age", DOCTOR_AGE)
                    .hasFieldOrPropertyWithValue("email", DOCTOR_EMAIL);
        }

        @Test
        void shouldCreateDoctorWithNoArguments() {
            d1 = new Doctor();

            assertThat(d1).isNotNull();
            assertThat(d1.getId()).isPositive();
        }

        @Test
        void shouldSetValuesInDoctorTest() {
            d1.setFirstName("Kike");
            d1.setLastName("Perez");
            d1.setEmail("new@mail.com");
            d1.setAge(77);
            entityManager.persistAndFlush(d1);

            assertThat(d1.getId()).isPositive();
            assertThat(d1.getFirstName()).isEqualTo("Kike");
            assertThat(d1.getLastName()).isEqualTo("Perez");
            assertThat(d1.getEmail()).isEqualTo("new@mail.com");
            assertThat(d1.getAge()).isEqualTo(77);
        }

        @Test
        void shouldChangeDoctorFirstName() {
            d1.setFirstName("Carlos");

            assertThat(d1.getFirstName()).isEqualTo("Carlos");
        }

        @Test
        void shouldChangeDoctorLastName() {
            d1.setLastName("Rodriguez");

            assertThat(d1.getLastName()).isEqualTo("Rodriguez");
        }

        @Test
        void shouldChangeDoctorAge() {
            d1.setAge(22);

            assertThat(d1.getAge()).isEqualTo(22);
        }

        @Test
        void shouldChangeDoctorEmail() {
            d1.setEmail("garciaJuan@mail.com");

            assertThat(d1.getEmail()).isEqualTo("garciaJuan@mail.com");
        }

        @Test
        void shouldChangeDoctorId() {
            d1.setId(2);

            assertThat(d1.getId()).isEqualTo(2);
        }

        @Test
        void shouldSaveDoctor() {
            Doctor savedDoctor = entityManager.persistAndFlush(d1);

            assertThat(savedDoctor.getFirstName()).isEqualTo(DOCTOR_FIRST_NAME);
            assertThat(savedDoctor.getLastName()).isEqualTo(DOCTOR_LAST_NAME);
            assertThat(savedDoctor.getAge()).isEqualTo(DOCTOR_AGE);
            assertThat(savedDoctor.getEmail()).isEqualTo(DOCTOR_EMAIL);
        }

        @Test
        void shouldGetDoctorFromDatabaseTest() {
            Doctor savedDoctor = entityManager.persistAndFlush(d1);

            Doctor retrievedDoctor = entityManager.find(Doctor.class, savedDoctor.getId());
            assertThat(retrievedDoctor).isEqualTo(savedDoctor);
        }


    //-- Patient Class --
    @Test
    void shouldCreatePatientWithArguments() {
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
        p1.setFirstName("Maria");

        assertThat(p1.getFirstName()).isEqualTo("Maria");
    }

    @Test
    void shouldChangePatientLastName() {
        p1.setLastName("Rodriguez");

        assertThat(p1.getLastName()).isEqualTo("Rodriguez");
    }

    @Test
    void shouldChangePatientAge() {
        p1.setAge(22);

        assertThat(p1.getAge()).isEqualTo(22);
    }

    @Test
    void shouldChangePatientEmail() {
        p1.setEmail("sanchezMarta@mail.com");

        assertThat(p1.getEmail()).isEqualTo("sanchezMarta@mail.com");
    }

    @Test
    void shouldChangePatientId() {
        p1.setId(2);

        assertThat(p1.getId()).isEqualTo(2);
    }

    @Test
    void shouldSavePatient() {
        Patient savedPatient = entityManager.persistAndFlush(p1);

        assertThat(p1.getFirstName()).isEqualTo(savedPatient.getFirstName());
        assertThat(p1.getLastName()).isEqualTo(savedPatient.getLastName());
        assertThat(p1.getAge()).isEqualTo(savedPatient.getAge());
        assertThat(p1.getEmail()).isEqualTo(savedPatient.getEmail());
    }


    //-- Room Class --
    @Test
    void shouldCreateRoomWithArguments() {
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
        String roomName = r1.getRoomName();

        assertThat(roomName).isEqualTo("Oncology");
    }

    @Test
    void shouldSaveRoom() {
        Room savedRoom = entityManager.persistAndFlush(r1);

        assertThat(r1.getRoomName()).isEqualTo(savedRoom.getRoomName());
    }


    //-- Appointment Class --
    @Test
    void shouldCreateAppointmentWithArguments() {
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
        LocalDateTime starts = LocalDate.now().atStartOfDay().plusHours(1);
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
        Doctor d2 = new Doctor("Hugo", "Lopez", 46, "hugo@mail.com");
        Patient p2 = new Patient("Rosana", "Fuentes", 23, "rosana@mail.com");

        LocalDateTime startsAt2 = a1.getStartsAt();
        LocalDateTime finishesAt2 = a1.getFinishesAt();

        a2 = new Appointment(p1,d1,r1,startsAt2,finishesAt2);
        a2.setFinishesAt(finishesAt2.plusMinutes(30));

        assertThat(a1.overlaps(a2));
    }

    @Test
    void shouldOverlapsAppointment2_byFinishTime() {
        Doctor d2 = new Doctor("Hugo", "Lopez", 46, "hugo@mail.com");
        Patient p2 = new Patient("Rosana", "Fuentes", 23, "rosana@mail.com");

        LocalDateTime startsAt2 = a1.getStartsAt();
        LocalDateTime finishesAt2 = a1.getFinishesAt();

        a2 = new Appointment(p1,d1,r1,startsAt2,finishesAt2);
        a2.setStartsAt(startsAt2.plusMinutes(30));

        assertThat(a1.overlaps(a2));
    }

    @Test
    void shouldOverlapsAppointment3_AStartsBeforeBFinishes_and_BFinishesBeforeAFinishes() {
        Doctor d2 = new Doctor("Hugo", "Lopez", 46, "hugo@mail.com");
        Patient p2 = new Patient("Rosana", "Fuentes", 23, "rosana@mail.com");

        LocalDateTime startsAt2 = a1.getStartsAt();
        LocalDateTime finishesAt2 = a1.getFinishesAt();

        a2 = new Appointment(p1,d1,r1,startsAt2,finishesAt2);
        a2.setStartsAt(startsAt2.plusMinutes(20));
        a2.setFinishesAt(finishesAt2.minusMinutes(10));

        assertThat(a1.overlaps(a2));
    }

    @Test
    void shouldOverlapsAppointment4_BStartsBeforeAStarts_and_AFinishesBeforeBFinishes() {
        Doctor d2 = new Doctor("Hugo", "Lopez", 46, "hugo@mail.com");
        Patient p2 = new Patient("Rosana", "Fuentes", 23, "rosana@mail.com");

        LocalDateTime startsAt2 = a1.getStartsAt();
        LocalDateTime finishesAt2 = a1.getFinishesAt();


        a2 = new Appointment(p1,d1,r1,startsAt2,finishesAt2);
        a2.setStartsAt(startsAt2.plusMinutes(10));
        a2.setFinishesAt(finishesAt2.plusMinutes(10));

        assertThat(a1.overlaps(a2));
    }

    @Test
    void shouldNotOverlapsAppointment() {
        Doctor d2 = new Doctor("Hugo", "Lopez", 46, "hugo@mail.com");
        Patient p2 = new Patient("Rosana", "Fuentes", 23, "rosana@mail.com");

        LocalDateTime startsAt2 = a1.getStartsAt();
        LocalDateTime finishesAt2 = a1.getFinishesAt();

        a2 = new Appointment(p1,d1,r1,startsAt2,finishesAt2);
        a2.setStartsAt(startsAt2.plusHours(2));
        a2.setFinishesAt(finishesAt2.plusHours(2));

        assertThat(a1.overlaps(a2)).isFalse();
    }

    @Test
    void testSaveAppointment() {
        Appointment savedAppointment = entityManager.persistAndFlush(a1);

        assertThat(p1).isEqualTo(savedAppointment.getPatient());
        assertThat(d1).isEqualTo(savedAppointment.getDoctor());
        assertThat(r1).isEqualTo(savedAppointment.getRoom());
        assertThat(a1.getStartsAt()).isEqualTo(savedAppointment.getStartsAt());
        assertThat(a1.getFinishesAt()).isEqualTo(savedAppointment.getFinishesAt());

    }

}
