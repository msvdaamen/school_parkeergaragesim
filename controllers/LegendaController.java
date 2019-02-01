package controllers;

import views.LegendaView;

public class LegendaController extends AbstractController {

    private LegendaView view;

    public LegendaController() {
        addController(this);
        this.view = new LegendaView();
    }

    public LegendaView getLegendaView() {
        return this.view;
    }

    @Override
    public void tick() {

    }

    @Override
    public void updateController() {

    }
}
