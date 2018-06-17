/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Yerlin Leal
 */
public class Student {

    private String id;
    private String name;
    private String lastname;
    private int age;
    private String town;
    private float admissionGrade;

    public Student() {
        this.id = "";
        this.name = "";
        this.lastname = "";
        this.age = -1;
        this.town = "";
        this.admissionGrade = -1;
    } // constructor default

    public Student(String id, String name, String lastname, int age, String town, float admissionGrade) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.town = town;
        this.admissionGrade = admissionGrade;
    } // constructor sobrecargado

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public float getAdmissionGrade() {
        return admissionGrade;
    }

    public void setAdmissionGrade(float admissionGrade) {
        this.admissionGrade = admissionGrade;
    }

    @Override
    public String toString() {
        return "Student{" + "name=" + name + ", lastname=" + lastname + ", age=" + age + ", town=" + town + ", admissionGrade=" + admissionGrade + '}';
    }

} // end class
