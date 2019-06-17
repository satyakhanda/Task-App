package ui;

import controller.EditTaskController;
import controller.ToolbarController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.StackPane;
import model.Task;
import org.json.JSONObject;
import utility.JsonFileIO;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Add task view
public class AddTask extends StackPane {
    private static final String FXML = "resources/fxml/AddTask.fxml";
    private File fxmlFile = new File(FXML);
    private Task task;

    public AddTask() {
        load();
    }

    private void load() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile.toURI().toURL());
            fxmlLoader.setRoot(this);
            fxmlLoader.load();


        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void addTask(Task task) {
        try {
            List<Task> newTasks = PomoTodoApp.getTasks();
            newTasks.add(task);

            JsonFileIO.write(newTasks);
        } catch (IOException ioe) {
            Logger.log("AddTaskController", "Failed to create a new task from description IOEXCEPTION"
                    + task.getDescription());
        }


    }

}

