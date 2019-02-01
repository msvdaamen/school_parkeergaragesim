package controllers;

import models.Time;
import views.TimerView;

public class TimerController extends AbstractController {

    private TimerView timerView;

    public TimerController() {
        addController(this);
        timerView = new TimerView(this);
    }

    public TimerView getTimerView() {
        return this.timerView;
    }

    public String getTime() {
        return Time.getDate();
    }

    @Override
    public void tick() {

    }

    @Override
    public void updateController() {

    }
}
