package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * Implementation of DrawNumberView.
 */
public class DrawNumberStandardOutputView implements DrawNumberView {
    /**
     * Sets the controller controlled by this view (if works as input).
     *
     * @param observer the controller to attach
     */
    @Override
    public void setController(final DrawNumberController observer) { }

    /**
     * This method is called before the UI is used. It should finalize its status (if needed).
     */
    @Override
    public void start() { }

    /**
     * Tells the UI to display the result of the draw.
     *
     * @param res the result of the last draw
     */
    @Override
    public void result(final DrawResult res) {
        System.out.println(res.getDescription()); //NOPMD
    }
}
