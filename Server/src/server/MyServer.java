/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import business.StudentBusiness;
import domain.Student;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MyServer extends Thread {

    private StudentBusiness business;
    private int socketPortNumber;
    private String message;

    public MyServer(int socketPortNumber) {
        super("Server Thread");
        this.socketPortNumber = socketPortNumber;;
        try {
            this.business = new StudentBusiness();
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // constructor

    @Override
    public void run() {
        try {
            System.out.println("Server active");
            ServerSocket serverSocket = new ServerSocket(this.socketPortNumber);
            do {
                Socket socket = serverSocket.accept();
                PrintStream send = new PrintStream(socket.getOutputStream());
                BufferedReader receive = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                this.message = receive.readLine();
                systemMessage(this.message);
                if (this.message.equals("Finalize")) {
                    socket.close();
                    System.out.println("Closed server");
                    break;
                }
                switch (this.message) {
                    case "C":
                        System.out.println("Server asks for data");
                        String answer = create(receive.readLine());
                        System.out.println("Server: " + answer);
                        send.println(answer);
                        break;
                    case "R":
                        System.out.println("Server wait for id");
                        String idRetrive = receive.readLine();
                        System.out.println("Client: " + idRetrive);
                        System.out.println("Server wait for name");
                        String nameRetrive = receive.readLine();
                        System.out.println("Client: " + nameRetrive);
                        ArrayList<Student> students = business.retriveStudent(idRetrive, nameRetrive);
                        System.out.println("Server sends the number of records, "+ String.valueOf(students.size()));
                        send.println(String.valueOf(students.size())); // envio el tamaño del array
                        if (!students.isEmpty()) {
                            System.out.println("Server sends the records");
                            for (int i = 0; i < students.size(); i++) {
                                send.println(fromStudentToString(students.get(i))); // envio estudiante uno por uno
                            }
                        }
                        break;
                    case "U":
                        System.out.println("Server wait for data");
                        String dataUpdate = receive.readLine();
                        String messageUpdate = update(dataUpdate);
                        System.out.println("Server: "+ messageUpdate);
                        send.println(messageUpdate);
                        break;
                    case "D":
                        System.out.println("Server wait for id");
                        String idEliminar = receive.readLine();
                        System.out.println("Client: " + idEliminar);
                        String messageDelete = delete(idEliminar);
                        System.out.println("Server: "+ messageDelete);
                        send.println(messageDelete);
                        break;
                    case "R1":
                        System.out.println("Server wait for id");
                        String idSearch = receive.readLine(); // recibo id a buscar
                        System.out.println("Client: " + idSearch);
                        String data = retrive(idSearch);
                        System.out.println("Server send data for update");
                        send.println(data);
                        break;
                    default:
                        break;
                } //swich
            } while (true);
        } // run
        catch (IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // run

    private void systemMessage(String m) {
        switch (m) {
            case "C":
                System.out.println("Client: I want to Create");
                break;
            case "R":
                System.out.println("Client: I want to Retrive");
                break;
            case "R1":
                System.out.println("Client: I want to Retrive");
                break;
            case "U":
                System.out.println("Client: I want to Update");
                break;
            case "D":
                System.out.println("Client: I want to Delete");
                break;
                case "Finalize":
                System.out.println("The client ended the connection to the server.");
                break;
            default:
                break;
        }
    } // systemMessage

    private Student fromElementToStudent(Element eStudent) {
        Student student = new Student();
        student.setId(eStudent.getAttributeValue("id"));
        student.setName(eStudent.getChildText("name"));
        student.setLastname(eStudent.getChildText("lastname"));
        student.setAge(Integer.parseInt(eStudent.getChildText("age")));
        student.setTown(eStudent.getChildText("town"));
        student.setAdmissionGrade(Float.parseFloat(eStudent.getChildText("admissionGrade")));
        return student;
    } // fromElementToStudent

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

    private String create(String studentString) {
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            StringReader stringReader = new StringReader(studentString);
            Document doc = saxBuilder.build(stringReader);
            Element eStudent = doc.getRootElement();
            if (!this.business.idExist(eStudent.getAttributeValue("id"))) {
                this.business.createStudent(fromElementToStudent(eStudent));
                return "Success";
            } else {
                return "The id is already registered";
            }
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Fatal error";
    } // createt

    private String retrive(String id) {
        try {
            Student s1 = this.business.retriveAnStudent(id);
            if (!s1.getId().equals("")) {
                return fromStudentToString(s1);
            }
        } catch (IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "The id does not exist";
    } // retrive

    private String update(String studentString) {
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            StringReader stringReader = new StringReader(studentString);
            Document doc = saxBuilder.build(stringReader);
            Element eStudent = doc.getRootElement();
            business.updateStudent(fromElementToStudent(eStudent));
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Success";
    } // update

    private String delete(String id) {
        boolean state = false;
        try {
            state = this.business.deleteStudent(id);
        } catch (IOException ex) {
            Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (state) {
            return "Student removed";
        }
        return "The id is not registered";
    } // delete

} // end class
