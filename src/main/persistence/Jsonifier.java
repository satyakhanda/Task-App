package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;


import java.util.Calendar;
import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {


        JSONObject tagJson = new JSONObject();
        tagJson.put("name", tag.getName());
        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();

        priorityJson.put("important", priority.isImportant());
        priorityJson.put("urgent", priority.isUrgent());

        return priorityJson;
    }

    // EFFECTS: returns JSON respresentation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject dueDateJson = new JSONObject();

        if (dueDate == null) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(dueDate.getDate());

        dueDateJson.put("year", cal.get(Calendar.YEAR));
        dueDateJson.put("month", cal.get(Calendar.MONTH));
        dueDateJson.put("day", cal.get(Calendar.DAY_OF_MONTH));
        dueDateJson.put("hour", cal.get(Calendar.HOUR_OF_DAY));
        dueDateJson.put("minute", cal.get(Calendar.MINUTE));


        return dueDateJson;
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskJson = new JSONObject();

        taskJson.put("description", task.getDescription());

        JSONArray jsonArrayTags = new JSONArray();
        for (Tag tag : task.getTags()) {
            jsonArrayTags.put(tagToJson(tag));
        }
        taskJson.put("tags", jsonArrayTags);

        JSONObject dueDate = dueDateToJson(task.getDueDate());
        if (dueDate == null) {
            taskJson.put("due-date", JSONObject.NULL);
        } else {
            taskJson.put("due-date", dueDate);
        }

        JSONObject priority = priorityToJson(task.getPriority());
        taskJson.put("priority", priority);

        taskJson.put("status", task.getStatus());

        return taskJson;

    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {

        JSONArray jsonArrayTasks = new JSONArray();
        for (Task task : tasks) {
            jsonArrayTasks.put(taskToJson(task));
        }

        return jsonArrayTasks;

    }

}
