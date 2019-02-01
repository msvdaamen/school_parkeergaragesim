package views;

import controllers.*;
import models.SimulatorModel;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() throws HeadlessException {
        SimulatorModel simulatorModel = new SimulatorModel(3, 6, 30, 2, 1);
        SimulatorController simulatorController = new SimulatorController(simulatorModel);
        LegendaController legendaController = new LegendaController();
        MenuController menuController = new MenuController();
        TimerController timerController = new TimerController();
        GrafiekController grafiekController = new GrafiekController(simulatorModel);

        Container contentPane = getContentPane();
        contentPane.add(simulatorController.getCarParkView(), BorderLayout.CENTER);
        contentPane.add(legendaController.getLegendaView(), BorderLayout.WEST);
        contentPane.add(menuController.getMenuView(), BorderLayout.SOUTH);
        contentPane.add(timerController.getTimerView(), BorderLayout.NORTH);
        contentPane.add(grafiekController.getGrafiekView(), BorderLayout.EAST);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        simulatorController.updateView();
    }
}
