import controllers.AbstractController;
import controllers.SimulatorController;
import models.*;
import views.AbstractView;

import views.CarParkView;
import views.MainView;

public class ParkeergrageSim {


private boolean running;
    private MainView mainView;
    private int tickPause = 100;






    public void run() {
        System.out.println(running);

         {
            for (int i = 0; i < 10000; i++) {
                tick();

            }
        }


    }

    private void tick() {


        Time.advanceTime();
        tickController();
//        handleExit();
        updateViews();
        // Pause.
        try {
            Thread.sleep(tickPause);
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


    public static void main(String args[]) {
        ParkeergrageSim parkeergrageSim = new ParkeergrageSim();
        MainView mainView = new MainView();
        parkeergrageSim.run();
        parkeergrageSim.setRunning(mainView.getRunning());

    }

    public void setRunning(boolean b){
        running = b;
    }










}
