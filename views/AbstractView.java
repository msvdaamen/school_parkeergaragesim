package views;

import javax.swing.*;
import java.util.ArrayList;

public abstract class AbstractView extends JPanel {

    private static ArrayList<AbstractView> views = new ArrayList<>();

    public AbstractView() {
    }

    protected void addView(AbstractView view) {
        views.add(view);
    }

    public static ArrayList<AbstractView> getViews() {
        return views;
    }

    public abstract void tick();

    public abstract void updateView();
}
