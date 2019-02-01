package controllers;

import main.ParkeergrageSim;
import views.MenuView;

import javax.swing.*;

public class MenuController extends AbstractController {

    private MenuView menuView;

    public MenuController() {
        addController(this);
        this.menuView = new MenuView(this);
        this.addStartAction();
        this.addStopAction();
        this.addSliderAction();

    }

    public void addStartAction() {
        this.menuView.start.addActionListener((e) -> {
            ParkeergrageSim.start();
        });
    }

    public void addStopAction() {
        this.menuView.stop.addActionListener((e) -> {
            ParkeergrageSim.stop();
        });
    }

    public void addSliderAction() {
        this.menuView.versnellen.addChangeListener((e) -> {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
                int speed = (int)source.getValue();
                if (speed == 0) {
                    if(ParkeergrageSim.isRunning()) {
                        ParkeergrageSim.stop();
                    }
                } else {
                    if(!ParkeergrageSim.isRunning()) {
                        ParkeergrageSim.start();
                    }
                    ParkeergrageSim.setSpeed(speed);
                }
            }
        });
    }

    @Override
    public void tick() {

    }

    @Override
    public void updateController() {

    }

    public MenuView getMenuView() {
        return this.menuView;
    }
}
