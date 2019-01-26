import controllers.AbstractController;
import models.*;
import views.AbstractView;
import views.MainView;

public class ParkeergrageSim {

    private int tickPause = 100;

    public void run() {
        for (int i = 0; i < 10000; i++) {
            tick();
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
    }
}
