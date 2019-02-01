package controllers;

import models.SimulatorModel;
import views.GrafiekView;

public class GrafiekController extends AbstractController {

    private GrafiekView grafiekView;
    private SimulatorModel simulatorModel;

    public GrafiekController(SimulatorModel simulatorModel) {
        addController(this);
        this.grafiekView = new GrafiekView(this);
        this.simulatorModel = simulatorModel;
    }

    public GrafiekView getGrafiekView() {
        return this.grafiekView;
    }

    public int[] getRevenueWeek() {
        return this.simulatorModel.getRevenueWeek();
    }

    public int getQueNormal() {
        return this.simulatorModel.getEntranceCarQueue().carsInQueue();
    }

    public int getQuePass() {
        return this.simulatorModel.getEntrancePassQueue().carsInQueue();
    }

    public int getResQue() {
        return this.simulatorModel.getEntranceResQueue().carsInQueue();
    }

    public int getExitQue() {
        return this.simulatorModel.getExitCarQueue().carsInQueue();
    }

    @Override
    public void tick() {

    }

    @Override
    public void updateController() {

    }
}
