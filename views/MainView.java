package views;

import controllers.SimulatorController;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public SimulatorController simulatorController;
    private boolean running;



    public MainView() throws HeadlessException {
        SimulatorController simulatorController = new SimulatorController();
        System.out.println("mainview" + running);

        Container contentPane = getContentPane();
        contentPane.add(simulatorController.getCarParkView(), BorderLayout.CENTER);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setRunning(simulatorController.getRunning());

        simulatorController.updateView();
    }



    public boolean getRunning(){
        return running;
    }

    public void setRunning(boolean b){
        running = b;
    }






}
