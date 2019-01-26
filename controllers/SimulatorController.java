package controllers;

import models.*;
import views.CarParkView;

import java.awt.*;
import java.util.Random;

public class SimulatorController extends AbstractController {
    private CarParkView carParkView;
    private SimulatorModel simulatorModel;

    public SimulatorController() {
        addController(this);
        this.carParkView = new CarParkView(this);
        this.simulatorModel = new SimulatorModel(2, 6, 30);

    }

    public CarParkView getCarParkView() {
        return this.carParkView;
    }

    public void updateView() {
        this.carParkView.updateView();
    }

    @Override
    public void tick() {
        this.simulatorModel.handleExit();
    }


    public Car getCarAt(Location location) {
       return this.simulatorModel.getCarAt(location);
    }

    @Override
    public void updateController() {
        this.simulatorModel.handleEntrance();
    }

    public int getNumberOfFloors() {
        return this.simulatorModel.getNumberOfFloors();
    }

    public int getNumberOfRows() {
        return this.simulatorModel.getNumberOfRows();
    }

    public int getNumberOfPlaces() {
        return this.simulatorModel.getNumberOfPlaces();
    }

    public Car[][][] getCars() {
        return this.simulatorModel.getCars();
    }
}
