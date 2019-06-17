package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.ListView;
import ui.PomoTodoApp;
import utility.JsonFileIO;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";

    private File todoOptionsPopUpFxmlFile = new File(todoOptionsPopUpFXML);
    private File todoActionsPopUpFxmlFile = new File(todoActionsPopUpFXML);


    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    // not given
    private JFXPopup actionPopUp;
    private JFXPopup viewPopUp;

    private Task task;

    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadViewOptionsPopUp();
        loadViewOptionsPopUpActionListener();
        loadActionPopUp();
        loadActionPopUpActionListener();
    }

    private void loadViewOptionsPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new ViewOptionsPopUpController());

            viewPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void loadActionPopUp() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsPopUpFxmlFile.toURI().toURL());
            fxmlLoader.setController(new ActionPopUpController());

            actionPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    private void loadViewOptionsPopUpActionListener() {

        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                viewPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        12, // what does this do
                        15);
            }
        });
    }

    private void loadActionPopUpActionListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                actionPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12,
                        15);
            }
        });
    }


    // inner class for to-do
    class ActionPopUpController {
        @FXML
        private JFXListView<?> actionPopUpList;

        @FXML
        private void submit() {
            submitHelper();
            actionPopUp.hide();
        }

        private void submitHelper() {
            int selectedIndex = actionPopUpList.getSelectionModel().getSelectedIndex();
            submitHelper2(selectedIndex);
        }

        private void submitHelper2(int selectedIndex) {
            switch (selectedIndex) {
                case 0: Logger.log("ActionsPopUpController", "To Do is not supported in this version");
                    break;
                case 1: Logger.log("ActionsPopUpController", "Up Next is not supported in this version");
                    break;
                case 2: Logger.log("ActionsPopUpController", "In Progress is not supported in this version");
                    break;
                case 3: Logger.log("ActionsPopUpController", "Done is not supported in this version");
                    break;
                case 4: Logger.log("ActionsPopUpController", "Pomodoro is not supported in this version");
                    break;
                default: Logger.log("ActionsPopUpController", "No action is implemented for the selected option");
            }
        }

    }


    // inner class for options (exit/delete)
    class ViewOptionsPopUpController {

        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private JFXListView<?> saveButton;

        @FXML
        private JFXListView<?> cancleButton;

        @FXML
        private void submit() {
            int selectedIndex = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (selectedIndex) {
                case 0:
                    Logger.log("TodobarOptionsPopUpController", "Edit Task");
                    editTask(task);
                    break;
                case 1:
                    Logger.log("TodobarOptionsPopUpController", "Delete task");
                    deleteTask(task);
                    break;
                default:
                    Logger.log("TodobarOptionsPopUpController", "No action is implemented for the selected option");
            }
            viewPopUp.hide();
        }

        public void editTask(Task task) {
            EditTask editedTask = new EditTask(task);
            PomoTodoApp.setScene(editedTask);

//            try {
//
//                List<Task> tasks = JsonFileIO.read();
//                for (int i = 0; i < tasks.size(); i++) {
//                    if (tasks.get(i) == task) {
//                        tasks.remove(i);
//                        tasks.add(task);
//                    }
//                }
//                JsonFileIO.write(tasks);
//                Logger.log("EditTaskController", "Save task:\n" + task);
//            } catch (IOException e) {
//                System.out.println("something went wrong");
//            }
        }


        public void deleteTask(Task task) {
            try {
                List<Task> tasks = JsonFileIO.read();
                tasks.remove(task);
                JsonFileIO.write(tasks);
                PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));

            } catch (IOException e) {
                System.out.println("Wack");
            }
        }

    }
}
