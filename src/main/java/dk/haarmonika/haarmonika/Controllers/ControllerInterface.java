package dk.haarmonika.haarmonika.Controllers;

public interface ControllerInterface {

    /**
     * To make sure when switching scenes that the correct info is being shown in the GUI, such as lists etc.
     */
    default void refresh() {
        //Does nothing if not implemented
    }
}
