package views;

import controllers.SimulatorController;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {



    public MainView() throws HeadlessException {
        SimulatorController simulatorController = new SimulatorController();

        Container contentPane = getContentPane();
//        contentPane.setLayout(null);
        contentPane.add(simulatorController.getCarParkView(), BorderLayout.CENTER);
//        setSize(1000, 1000);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        simulatorController.updateView();
    }


}
