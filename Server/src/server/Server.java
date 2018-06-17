/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Yerlin Leal
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MyServer myServer = new MyServer(Utilities.Constants.socketPortNumber);
        myServer.start();
    } // main

} // end class
