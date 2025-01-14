package duke;

import java.io.IOException;

import java.util.Collections;

import duke.tools.Ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * A control using FXML to represent a dialog box consisting of an ImageView to represent the
 * speaker's face and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Returns a Node of Duke's welcome message contained in a dialog box.
     * @param dukeImage A image file of duke.
     * @return A node of Duke's welcome message.
     */
    public static Node getDukeGreeting(Image dukeImage) {
        String greeting = Ui.getWelcomeMessage();
        DialogBox db = new DialogBox(greeting, dukeImage);
        db.flip();
        return db;
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Returns a new DialogBox that contains the user's image and user input.
     * @param text Is a string of user input.
     * @param img Is an image of the user.
     * @return A new Dialog box containing the user image and input.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Returns a new DialogBox that contains the Duke's image and user input.
     * @param text Is a string of Duke's response.
     * @param img Is an image of Duke.
     * @return A new Dialog box containing Duke's image and response.
     */
    public static DialogBox getDukeDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
