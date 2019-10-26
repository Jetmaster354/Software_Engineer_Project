package Tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.Patient;
import project.PatientManager;
import project.Person;

public class PatientManagerTester{
    @Test
    @DisplayName("addPatient return correct when adding patients")
    void addPatientTest(){
        PatientManager pm = new PatientManager();
        Person p = new Person("Andy", "Russel");
        int SSN = 999292;
        Patient pat = new Patient(p, SSN);
        //assertTrue(pm.addPatient(pat));
    }
    @Test
    @DisplayName("addPatient return correct when adding a duplicate")
    void addPatientTestforDup(){
        PatientManager pm = new PatientManager();
        Person p = new Person("Sean", "Northcutt");
        int SSN = 999292;
        Patient pat = new Patient(p, SSN);

        Person p2 = new Person("Carson", "Davis");
        int SSN2 = 95499292;
        Patient pat2 = new Patient(p2, SSN2);

        //assertFalse(pm.addPatient(pat2));
    }
}