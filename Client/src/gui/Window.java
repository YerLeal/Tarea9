/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import client.MyClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Yerlin Leal
 */
public class Window extends JFrame implements ActionListener {

    private JMenuBar jMenuBar;
    private JMenuItem jmiCreate, jmiRetrive, jmiUpdate, jmiDelete, jmiFinalize;
    private JInternalFrame internalFrame;

    public Window() {
        super();
        this.setLayout(null);
        init();
        this.setSize(800, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    } // constructor

    private void init() {
        this.jMenuBar = new JMenuBar();
        this.jmiCreate = new JMenuItem("Create");
        this.jmiRetrive = new JMenuItem("Retrive");
        this.jmiUpdate = new JMenuItem("Update");
        this.jmiDelete = new JMenuItem("Delete");
        this.jmiFinalize = new JMenuItem("Finalize");

        this.jmiCreate.setMnemonic('C');
        this.jmiRetrive.setMnemonic('R');
        this.jmiUpdate.setMnemonic('U');
        this.jmiDelete.setMnemonic('D');
        this.jmiFinalize.setMnemonic('F');

        this.jmiCreate.addActionListener(this);
        this.jmiRetrive.addActionListener(this);
        this.jmiUpdate.addActionListener(this);
        this.jmiDelete.addActionListener(this);
        this.jmiFinalize.addActionListener(this);

        this.jMenuBar.add(this.jmiCreate);
        this.jMenuBar.add(this.jmiRetrive);
        this.jMenuBar.add(this.jmiUpdate);
        this.jMenuBar.add(this.jmiDelete);
        this.jMenuBar.add(this.jmiFinalize);
        this.setJMenuBar(this.jMenuBar);

        this.internalFrame = new JInternalFrame();
        this.add(this.internalFrame);
    } // init

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.jmiCreate) {
            if (!this.internalFrame.isShowing()) {
                this.internalFrame = new JIFCreate();
                this.add(this.internalFrame);
                this.internalFrame.setVisible(true);
            }
        } else if (e.getSource() == this.jmiRetrive) {
            if (!this.internalFrame.isShowing()) {
                this.internalFrame = new JIFRetrive();
                this.add(this.internalFrame);
                this.internalFrame.setVisible(true);
            }
        } else if (e.getSource() == this.jmiUpdate) {
            if (!this.internalFrame.isShowing()) {
                this.internalFrame = new JIFUpdate();
                this.add(this.internalFrame);
                this.internalFrame.setVisible(true);
            }
        } else if (e.getSource() == this.jmiDelete) {
            if (!this.internalFrame.isShowing()) {
                this.internalFrame = new JIFDelete();
                this.add(this.internalFrame);
                this.internalFrame.setVisible(true);
            }
        } else if (e.getSource() == this.jmiFinalize) {
            MyClient client = new MyClient();
            client.setAction("Finalize");
            client.start();
        }
    } // actionPerformed

} // end class
