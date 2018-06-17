/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import domain.Student;
import gui.Window;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Yerlin Leal
 */
public class MyClient extends Thread {

    private int socketPortNumber;
    private String action;
    private Student student;
    private String message;
    private String aux;
    private Student[] studens;
    private boolean finished;

    public MyClient() {
        this.socketPortNumber = Utilities.Constants.socketPortNumber;
        this.action = "Finalize";
        this.message = "";
        this.aux = "";
        this.student = new Student();
        this.finished = false;
    } // constructor

    @Override
    public void run() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            Socket socket = new Socket(address, this.socketPortNumber);

            PrintStream send = new PrintStream(socket.getOutputStream());
            BufferedReader receive = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            send.println(this.action); // Envio la accion

            switch (this.action) {
                case "C":
                    send.println(create());
                    this.message = receive.readLine(); // este es el mensaje
                    break;
                case "R":
                    send.println(this.message); // envio id
                    send.println(this.aux); // envio name
                    int tamanno = Integer.parseInt(receive.readLine());
                    this.studens = new Student[tamanno];
                    if (tamanno != 0) {
                        for (int i = 0; i < this.studens.length; i++) {
                            this.studens[i] = fromElementToStudent(fromStringToElement(receive.readLine()));
                        }
                    }
                    this.finished = true;
                    break;
                case "U":
                    send.println(fromStudentToString(this.student));
                    this.message = receive.readLine();
                    break;
                case "D":
                    send.println(this.message); // envio id
                    this.message = receive.readLine(); // recibo si el msj fue eliminado o no
                    break;
                case "R1":
                    send.println(this.message); // envio id
                    retrive(receive.readLine()); // recibo al estudiante o un mensaje
                    break;
                case "Finalize":
                    send.println("Finalize");
                    JOptionPane.showMessageDialog(null, "The client ended the connection to the server.");
                    System.exit(0);
                    break;
            }
            socket.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // run

    public void setAction(String action) {
        this.action = action;
    } // setAction

    public void setAux(String aux) {
        this.aux = aux;
    } // setAux

    public void setStudent(Student student) {
        this.student = student;
    } // setStudent

    public Student getStudent() {
        return this.student;
    } // getStudent

    public Student[] getStudents() {
        return this.studens;
    } // getStudents

    public boolean getFinished() {
        return this.finished;
    } // getFinished

    public String getMessage() {
        return this.message;
    } // getMessage

    public void setMessage(String id) {
        this.message = id;
    } // setMessage

    private Student fromElementToStudent(Element eStudent) {
        Student student1 = new Student();
        student1.setId(eStudent.getAttributeValue("id"));
        student1.setName(eStudent.getChildText("name"));
        student1.setLastname(eStudent.getChildText("lastname"));
        student1.setAge(Integer.parseInt(eStudent.getChildText("age")));
        student1.setTown(eStudent.getChildText("town"));
        student1.setAdmissionGrade(Float.parseFloat(eStudent.getChildText("admissionGrade")));
        return student1;
    } // fromElementToStudent

    private Element fromStringToElement(String studentString) {
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            StringReader stringReader = new StringReader(studentString);
            Document doc = saxBuilder.build(stringReader);
            Element eStudent = doc.getRootElement();
            return eStudent;
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } // fromStringToElement

    private String fromStudentToString(Student student) {
        Element eStudent = new Element("student");
        Element eName = new Element("name");
        Element eLastname = new Element("lastname");
        Element eAge = new Element("age");
        Element eTown = new Element("town");
        Element eAdmissionGrade = new Element("admissionGrade");
        eStudent.setAttribute("id", student.getId());
        eName.addContent(student.getName());
        eLastname.addContent(student.getLastname());
        eAge.addContent(String.valueOf(student.getAge()));
        eTown.addContent(student.getTown());
        eAdmissionGrade.addContent(String.valueOf(student.getAdmissionGrade()));
        eStudent.addContent(eName);
        eStudent.addContent(eLastname);
        eStudent.addContent(eAge);
        eStudent.addContent(eTown);
        eStudent.addContent(eAdmissionGrade);

        XMLOutputter output = new XMLOutputter(Format.getCompactFormat());
        String xmlStringElementEStudent = output.outputString(eStudent);
        xmlStringElementEStudent = xmlStringElementEStudent.replace("\n", "");
        return xmlStringElementEStudent;
    } // fromStudentToString

    private String create() {
        Element eStudent = new Element("student");
        Element eName = new Element("name");
        Element eLastname = new Element("lastname");
        Element eAge = new Element("age");
        Element eTown = new Element("town");
        Element eAdmissionGrade = new Element("admissionGrade");
        eStudent.setAttribute("id", this.student.getId());
        eName.addContent(this.student.getName());
        eLastname.addContent(this.student.getLastname());
        eAge.addContent(String.valueOf(this.student.getAge()));
        eTown.addContent(this.student.getTown());
        eAdmissionGrade.addContent(String.valueOf(this.student.getAdmissionGrade()));
        eStudent.addContent(eName);
        eStudent.addContent(eLastname);
        eStudent.addContent(eAge);
        eStudent.addContent(eTown);
        eStudent.addContent(eAdmissionGrade);

        XMLOutputter output = new XMLOutputter(Format.getCompactFormat());
        String xmlStringElementEStudent = output.outputString(eStudent);
        xmlStringElementEStudent = xmlStringElementEStudent.replace("\n", "");
        return xmlStringElementEStudent;
    } // create

    private void retrive(String studentString) {
        if (studentString.equals("The id does not exist")) {
            this.message = "The id does not exist";
        } else {
            try {
                SAXBuilder saxBuilder = new SAXBuilder();
                StringReader stringReader = new StringReader(studentString);
                Document doc = saxBuilder.build(stringReader);
                Element eStudent = doc.getRootElement();
                this.student = fromElementToStudent(eStudent);
                this.message = "";
            } catch (JDOMException | IOException ex) {
                Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } // retrive

} // fin clase
