package it.unibo.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private static final String PATH_VIEW = "it.unibo.mvc.view.";
    private static final int N_VIEWS = 3;

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) throws ClassNotFoundException, NoSuchMethodException, 
        InstantiationException, IllegalAccessException, InvocationTargetException {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);

        final List<String> views = List.of("DrawNumberSwingView", "DrawNumberStandardOutputView");
        for (final var view : views) {
            final Class<?> c = Class.forName(PATH_VIEW + view);
            if (DrawNumberView.class.isAssignableFrom(c)) {
                final Constructor<?> con = c.getConstructor();
                for (int i = 0; i < N_VIEWS; i++) {
                    app.addView((DrawNumberView) con.newInstance());
                }
            }
        } 
    }
}
