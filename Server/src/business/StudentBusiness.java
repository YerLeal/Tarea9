/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import data.StudentData;
import domain.Student;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom.JDOMException;

/**
 *
 * @author Yerlin Leal
 */
public class StudentBusiness {

    private StudentData data;

    public StudentBusiness() throws JDOMException, IOException {
        this.data = new StudentData(Utilities.Constants.pathFile);
    } // constructor

    public void createStudent(Student student) throws IOException {
        this.data.createStudent(student);
    } // createStudet

    public ArrayList<Student> retriveStudent(String identification, String name) throws IOException {
        return this.data.retriveStudent(identification, name);
    } // retriveStudent

    public void updateStudent(Student student) throws IOException {
        this.data.updateStudent(student);
    } // updateStudent

    public boolean deleteStudent(String identification) throws IOException {
        return this.data.deleteStudent(identification);
    } // deleteStudent

    public boolean idExist(String identification) throws IOException {
        return this.data.idExist(identification);
    } // idExist

    public Student retriveAnStudent(String identification) throws IOException {
        return this.data.retriveAnStudent(identification);
    } // retriveAnStudent

} // end class
