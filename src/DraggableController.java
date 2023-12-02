package src;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class DraggableController {

    @FXML
    private AnchorPane draggablePane;

    private double xOffset, yOffset;

    @FXML
    public void initialize() {
        // Set event handlers for mouse pressed, dragged, and released
        draggablePane.setOnMousePressed(this::handleMousePressed);
        draggablePane.setOnMouseDragged(this::handleMouseDragged);
        draggablePane.setOnMouseReleased(this::handleMouseReleased);
    }

    private void handleMousePressed(MouseEvent event) {
        // Record the initial mouse cursor position
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        // Calculate the delta distance of mouse movement
        double xDelta = event.getSceneX() - xOffset;
        double yDelta = event.getSceneY() - yOffset;

        // Update the layout position of the draggable pane
        draggablePane.setLayoutX(draggablePane.getLayoutX() + xDelta);
        draggablePane.setLayoutY(draggablePane.getLayoutY() + yDelta);

        // Update the initial mouse cursor position for the next iteration
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void handleMouseReleased(MouseEvent event) {
        // Perform any cleanup or additional actions upon mouse release if needed
    }
}