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
        a2 = new Appointment(p1, d1, r1, startsAt, finishesAt);
        a3 = new Appointment(p1, d1, r1, startsAt, finishesAt);
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
            entityManager.persistAndFlush(a1);

            assertThat(a1).isNotNull();
            assertThat(a1).hasNoNullFieldsOrProperties();
        }

        @Test
        void shouldCreateAppointmentWithNoArguments() {
            a1 = new Appointment();
            entityManager.persistAndFlush(a1);

            assertThat(a1)
                    .hasAllNullFieldsOrPropertiesExcept("id");
        }

        @Test
        void shouldSetAppointmentValues() {
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
        void shouldGetAppointmentProperties() {
            Long id = a1.getId();
            Doctor doctor = a1.getDoctor();
            Patient patient= a1.getPatient();
            Room room = a1.getRoom();
            LocalDateTime startTime = a1.getStartsAt();
            LocalDateTime finishTime = a1.getFinishesAt();

            assertThat(id).isNotNull();
            assertThat(doctor)
                    .hasFieldOrPropertyWithValue("firstName", DOCTOR_FIRST_NAME);
            assertThat(patient)
                    .hasFieldOrPropertyWithValue("firstName", PATIENT_FIRST_NAME);
            assertThat(room)
                    .hasFieldOrPropertyWithValue("roomName", ROOM_NAME);
            assertThat(startTime).isNotNull();
            assertThat(finishTime).isNotNull();
        }

        @Test
        void shouldGetSavedAppointment() {
            Appointment savedAppointment = entityManager.persistAndFlush(a1);
            Appointment retrieveAppointment = entityManager.find(Appointment.class, savedAppointment.getId());

            assertThat(p1).isEqualTo(savedAppointment.getPatient());
            assertThat(d1).isEqualTo(savedAppointment.getDoctor());
            assertThat(r1).isEqualTo(savedAppointment.getRoom());
            assertThat(a1.getStartsAt()).isEqualTo(savedAppointment.getStartsAt());
            assertThat(a1.getFinishesAt()).isEqualTo(savedAppointment.getFinishesAt());
            assertThat(retrieveAppointment).isEqualTo(savedAppointment);

        }

        @Test
        void overlapCasesTest() {
            LocalDateTime someTime = LocalDate.now().atStartOfDay();
            // Case 1: A.starts == B.starts
            a1.setStartsAt(someTime);
            a2.setStartsAt(someTime);
            assertThat(a1.overlaps(a2)).isTrue();

            // Case 2: A.finishes == B.finishes
            a2.setFinishesAt(someTime);
            a3.setFinishesAt(someTime);
            assertThat(a2.overlaps(a3)).isTrue();

            // Case 3: A.starts < B.finishes && B.finishes < A.finishes
            a1.setStartsAt(someTime);
            a2.setFinishesAt(someTime.plusMinutes(30));
            a3.setStartsAt(someTime.plusMinutes(15));
            assertThat(a1.overlaps(a2)).isTrue();
            assertThat(a2.overlaps(a3)).isTrue();

            // Case 4: B.starts < A.starts && A.finishes < B.finishes
            a1.setStartsAt(someTime);
            a1.setFinishesAt(someTime.plusMinutes(30));
            a2.setStartsAt(someTime.minusMinutes(15));
            a3.setFinishesAt(someTime.plusMinutes(15));
            assertThat(a1.overlaps(a2)).isTrue();
            assertThat(a1.overlaps(a3)).isTrue();
        }
    }

}
