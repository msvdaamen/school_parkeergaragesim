package controllers;

import models.*;
import views.CarParkView;

public class SimulatorController extends AbstractController {
    private CarParkView carParkView;
    private SimulatorModel simulatorModel;

    public SimulatorController() {
        addController(this);
        this.carParkView = new CarParkView(this);
        this.simulatorModel = new SimulatorModel(2, 6, 30, 2, 1);

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

    //Antonie: Whether or not a spot is VIP
    public boolean locationIsVip(Location location) {
        int row = location.getRow();
        int floor = location.getFloor();
        if(row < simulatorModel.getNumberOfVipRows() && floor < simulatorModel.getNumberOfVipFloors()) {
            return true;
        }
        return false;
    }
 }