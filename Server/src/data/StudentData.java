/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import domain.Student;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Yerlin Leal
 */
public class StudentData {

    private Document document;
    private Element root;
    private String pathFile;

    public StudentData(String path) throws JDOMException, IOException {
        this.pathFile = path;
        File archivo = new File(this.pathFile);
        if (archivo.exists()) {
            SAXBuilder saxBuilder = new SAXBuilder();
            saxBuilder.setIgnoringElementContentWhitespace(true);
            this.document = saxBuilder.build(archivo);
            this.root = this.document.getRootElement();
        } else {
            this.root = new Element("Students");
            this.document = new Document(this.root);
            storeXML();
        } // if-else
    } // constructor

    private void storeXML() throws IOException {
        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.output(this.document, new PrintWriter(this.pathFile));
    } // almacena en disco duro nuestro documento xml en la ruta especificada

    public void createStudent(Student student) throws IOException {
        // creamos los elementos
        Element eStudent = new Element("student");
        Element eName = new Element("name");
        Element eLastname = new Element("lastname");
        Element eAge = new Element("age");
        Element eTown = new Element("town");
        Element eAdmissionGrade = new Element("admissionGrade");

        eStudent.setAttribute("id", student.getId()); // agregamos un atributo
        // agregamos los elementos
        eName.addContent(student.getName());
        eLastname.addContent(student.getLastname());
        eAge.addContent(String.valueOf(student.getAge()));
        eTown.addContent(student.getTown());
        eAdmissionGrade.addContent(String.valueOf(student.getAdmissionGrade()));

        // agregamos los elementos a student
        eStudent.addContent(eName);
        eStudent.addContent(eLastname);
        eStudent.addContent(eAge);
        eStudent.addContent(eTown);
        eStudent.addContent(eAdmissionGrade);

        this.root.addContent(eStudent); // agregar a root
        storeXML(); // guardamos todo
    } // createStudent

    public ArrayList<Student> retriveStudent(String identification, String name) throws IOException {
        List listElementos = this.root.getChildren();
        ArrayList<Student> students = new ArrayList<Student>();
        for (Object objectActual : listElementos) {
            Element currentElement = (Element) objectActual;
            if (currentElement.getAttributeValue("id").equals(identification) || currentElement.getChild("name").getValue().equals(name)) {
                Student student = new Student();
                student.setId(currentElement.getAttributeValue("id"));
                student.setName(currentElement.getChild("name").getValue());
                student.setLastname(currentElement.getChild("lastname").getValue());
                student.setAge(Integer.parseInt(currentElement.getChild("age").getValue()));
                student.setTown(currentElement.getChild("town").getValue());
                student.setAdmissionGrade(Float.parseFloat(currentElement.getChild("admissionGrade").getValue()));
                students.add(student);
            }
        }
        return students;
    } // retriveStudent

    public void updateStudent(Student student) throws IOException {
        deleteStudent(student.getId());
        createStudent(student);
    } // updateStudent

    public boolean deleteStudent(String identification) throws IOException {
        List listElementos = this.root.getChildren();
        int cont = 0;
        for (Object objectActual : listElementos) {
            Element elementoActual = (Element) objectActual;
            if (elementoActual.getAttributeValue("id").equals(identification)) {
                this.root.removeContent(cont);
                storeXML();
                return true;
            }
            cont++;
        }
        return false;
    } // deleteStudent

    public boolean idExist(String identification) throws IOException {
        List listElementos = this.root.getChildren();
        for (Object objectActual : listElementos) {
            Element currentElement = (Element) objectActual;
            if (currentElement.getAttributeValue("id").equals(identification)) {
                return true;
            }
        }
        return false;
    } // idExist

    public Student retriveAnStudent(String identification) throws IOException {
        List listElementos = this.root.getChildren();
        for (Object objectActual : listElementos) {
            Element currentElement = (Element) objectActual;
            if (currentElement.getAttributeValue("id").equals(identification)) {
                Student student = new Student();
                student.setId(currentElement.getAttributeValue("id"));
                student.setName(currentElement.getChild("name").getValue());
                student.setLastname(currentElement.getChild("lastname").getValue());
                student.setAge(Integer.parseInt(currentElement.getChild("age").getValue()));
                student.setTown(currentElement.getChild("town").getValue());
                student.setAdmissionGrade(Float.parseFloat(currentElement.getChild("admissionGrade").getValue()));
                return student;
            }
        } // for
        return new Student();
    } // retriveStudent

} // end class
