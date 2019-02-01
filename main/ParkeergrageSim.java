package main;

import controllers.AbstractController;
import models.*;
import views.AbstractView;
import views.MainView;

public class ParkeergrageSim {

    private static int speed = 1;
    private static boolean start = true;

    public void run() {
        while(true) {
            if(start) {
                tick();
            }
            System.out.println();
        }
    }

    private void tick() {
        Time.advanceTime();
        tickController();
//        handleExit();
        updateViews();
        // Pause.
        try {
            Thread.sleep(100 / speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        handleEntrance();
        updateController();
    }

    private void updateViews() {
        for(AbstractView view: AbstractView.getViews()) {
            view.tick();
            view.updateView();
        }
    }

    private void tickController() {
        for(AbstractController controller: AbstractController.getControllers()) {
            controller.tick();
        }
    }

    private void updateController() {
        for(AbstractController controller: AbstractController.getControllers()) {
            controller.updateController();
        }
    }

    public static void start() {
        start = true;
    }

    public static void stop() {
        start = false;
    }

    public static boolean isRunning() {
        return start;
    }

    public static void setSpeed(int speeds) {
        speed = speeds;
    }

    public static void main(String args[]) {
        ParkeergrageSim parkeergrageSim = new ParkeergrageSim();
        MainView mainView = new MainView();
        parkeergrageSim.run();
    }
}
