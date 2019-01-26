package controllers;

import java.util.ArrayList;

public abstract class AbstractController {

    private static ArrayList<AbstractController> controllers = new ArrayList<>();

    protected void addController(AbstractController controller) {
        controllers.add(controller);
    }

    public abstract void tick();

    public abstract void updateController();

    public static ArrayList<AbstractController> getControllers() {
        return controllers;
    }

}
