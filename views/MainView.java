package views;

import controllers.SimulatorController;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() throws HeadlessException {
        SimulatorController simulatorController = new SimulatorController();

        Container contentPane = getContentPane();
        contentPane.add(simulatorController.getCarParkView(), BorderLayout.CENTER);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        simulatorController.updateView();
    }
}
