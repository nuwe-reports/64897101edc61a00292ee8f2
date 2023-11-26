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

    private final String DOCTOR_FIRST_NAME = "Juan";
    private final String DOCTOR_LAST_NAME = "Garcia";
    private final int DOCTOR_AGE = 38;
    private final String DOCTOR_EMAIL = "juan@mail.com";

    private final String PATIENT_FIRST_NAME = "Marta";
    private final String PATIENT_LAST_NAME = "Sanchez";
    private final int PATIENT_AGE = 27;
    private final String PATIENT_EMAIL = "marta@mail.com";

    private final String ROOM_NAME = "Oncology";

    @Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    @BeforeEach
    public void setup() {

        d1 = new Doctor(DOCTOR_FIRST_NAME, DOCTOR_LAST_NAME, DOCTOR_AGE, DOCTOR_EMAIL);

        p1 = new Patient(PATIENT_FIRST_NAME, PATIENT_LAST_NAME, PATIENT_AGE, PATIENT_EMAIL);

        r1 = new Room(ROOM_NAME);

        LocalDateTime startsAt = LocalDate.now().atStartOfDay();
        LocalDateTime finishesAt = startsAt.plusHours(1);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
    }

    @Nested
    class DoctorEntityTest {
        @Test
        void shouldCreateDoctorWithArguments() {
            entityManager.persistAndFlush(d1);

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
            entityManager.persistAndFlush(d1);

            assertThat(d1).isNotNull();
            assertThat(d1.getId()).isPositive();
        }

        @Test
        void shouldSetDoctorValuesTest() {
            d1.setFirstName("Kike");
            d1.setLastName("Perez");
            d1.setEmail("perezkike@mail.com");
            d1.setAge(77);
            entityManager.persistAndFlush(d1);

            assertThat(d1.getId()).isPositive();
            assertThat(d1.getFirstName()).isEqualTo("Kike");
            assertThat(d1.getLastName()).isEqualTo("Perez");
            assertThat(d1.getEmail()).isEqualTo("perezkike@mail.com");
            assertThat(d1.getAge()).isEqualTo(77);
        }

        @Test
        void shouldGetSavedDoctor() {
            Doctor savedDoctor = entityManager.persistAndFlush(d1);
            Doctor retrievedDoctor = entityManager.find(Doctor.class, savedDoctor.getId());

            assertThat(savedDoctor.getFirstName()).isEqualTo(DOCTOR_FIRST_NAME);
            assertThat(savedDoctor.getLastName()).isEqualTo(DOCTOR_LAST_NAME);
            assertThat(savedDoctor.getAge()).isEqualTo(DOCTOR_AGE);
            assertThat(savedDoctor.getEmail()).isEqualTo(DOCTOR_EMAIL);
            assertThat(retrievedDoctor).isEqualTo(savedDoctor);
        }
    }

    @Nested
    class PatientEntityTest {
        @Test
        void shouldCreatePatientWithArguments() {
            entityManager.persistAndFlush(p1);

            assertThat(p1.getId()).isPositive();
            assertThat(p1)
                    .hasFieldOrPropertyWithValue("firstName", PATIENT_FIRST_NAME)
                    .hasFieldOrPropertyWithValue("lastName", PATIENT_LAST_NAME)
                    .hasFieldOrPropertyWithValue("age", PATIENT_AGE)
                    .hasFieldOrPropertyWithValue("email", PATIENT_EMAIL);
        }

        @Test
        void shouldCreatePatientWithNoArguments() {
            p1 = new Patient();
            entityManager.persistAndFlush(p1);

            assertThat(p1).isNotNull();
            assertThat(p1.getId()).isPositive();
        }

        @Test
        void shouldSetPatientValuesTest() {
            p1.setFirstName("Maria");
            p1.setLastName("Rodriguez");
            p1.setEmail("sanchezMarta@mail.com");
            p1.setAge(22);
            entityManager.persistAndFlush(p1);

            assertThat(p1.getId()).isPositive();
            assertThat(p1.getFirstName()).isEqualTo("Maria");
            assertThat(p1.getLastName()).isEqualTo("Rodriguez");
            assertThat(p1.getEmail()).isEqualTo("sanchezMarta@mail.com");
            assertThat(p1.getAge()).isEqualTo(22);
        }

        @Test
        void shouldGetSavedPatient() {
            Patient savedPatient = entityManager.persistAndFlush(p1);
            Patient retrievedPatient = entityManager.find(Patient.class, savedPatient.getId());

            assertThat(savedPatient.getFirstName()).isEqualTo(PATIENT_FIRST_NAME);
            assertThat(savedPatient.getLastName()).isEqualTo(PATIENT_LAST_NAME);
            assertThat(savedPatient.getAge()).isEqualTo(PATIENT_AGE);
            assertThat(savedPatient.getEmail()).isEqualTo(PATIENT_EMAIL);
            assertThat(retrievedPatient).isEqualTo(savedPatient);
        }
    }

    @Nested
    class RoomEntityTest {
        @Test
        void shouldCreateRoomWithArguments() {
            entityManager.persistAndFlush(r1);

            assertThat(r1)
                    .hasFieldOrPropertyWithValue("roomName", "Oncology");
        }

        @Test
        void shouldSaveRoom() {
            Room savedRoom = entityManager.persistAndFlush(r1);
            Room retrievedRoom = entityManager.find(Room.class, r1.getRoomName());

            assertThat(savedRoom.getRoomName()).isEqualTo(ROOM_NAME);
            assertThat(retrievedRoom).isEqualTo(savedRoom);
        }

    }

    @Nested
    class AppointmentEntityTest {
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


}
