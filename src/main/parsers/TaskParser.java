package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// Represents Task parser
public class TaskParser {

    private List<Task> tasks = new ArrayList<>();
    private DueDate nullDate = null;
    private boolean urgent;
    private boolean important;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;



    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {

        JSONArray tasksString = new JSONArray(input);

        for (int i = 0; i < tasksString.length(); i++) {
            Task task = new Task("Initial Task");
            JSONObject jsonObject = tasksString.getJSONObject(i);
            try {
                parseDescription(task, jsonObject);
                tagsParser(task, jsonObject);
                dueDateParser(task, jsonObject);
                priorityParser(task, jsonObject);
                statusParser(task, jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
            tasks.add(task);
        }

        return tasks;
    }


    private void parseDescription(Task task, JSONObject jsonObject) {
        if (!jsonObject.keySet().contains("description")) {
            throw new JSONException("The description key is either missing or misspelled.");
        } else {
            task.setDescription(jsonObject.getString("description"));
        }
    }
//
//    private void statusParser(String status, Task task) {
//        if (status.equals("DONE")) {
//            task.setStatus(Status.DONE);
//        } else if (status.equals("IN_PROGRESS")) {
//            task.setStatus(Status.IN_PROGRESS);
//        } else if (status.equals("UP_NEXT")) {
//            task.setStatus(Status.UP_NEXT);
//        } else {
//            task.setStatus(Status.TODO);
//        }
//    }

    private void statusParser(Task task, JSONObject jsonObject) {
        if (!jsonObject.keySet().contains("status")) {
            throw new JSONException("The status key is either missing or misspelled.");
        } else {
            String status = jsonObject.getString("status");
            if (status.equals("DONE")) {
                task.setStatus(Status.DONE);
            } else if (status.equals("IN_PROGRESS")) {
                task.setStatus(Status.IN_PROGRESS);
            } else if (status.equals("UP_NEXT")) {
                task.setStatus(Status.UP_NEXT);
            } else {
                task.setStatus(Status.TODO);
            }
        }
    }


//    private void priorityParser(JSONObject priority, Task task) {
//        Boolean important = priority.getBoolean("important");
//        Boolean urgent = priority.getBoolean("urgent");
//
//        Priority priority1 = new Priority();
//        priority1.setImportant(important);
//        priority1.setUrgent(urgent);
//
//        task.setPriority(priority1);
//
//    }

    private void priorityParser(Task task, JSONObject jsonObject) {
        Priority priority1 = new Priority();

        if (!jsonObject.keySet().contains("priority")) {
            throw new JSONException("The priority key is either missing or misspelled");
        } else {
            JSONObject priority = jsonObject.getJSONObject("priority");
            if (!priority.keySet().contains("important")) {
                throw new JSONException("The important key is either missing or misspelled");
            } else {
                important = priority.getBoolean("important");
            }
            if (!priority.keySet().contains("urgent")) {
                throw new JSONException("The urgent key is either missing or misspelled");
            }
            urgent = priority.getBoolean("urgent");
            priority1.setImportant(important);
            priority1.setUrgent(urgent);

            task.setPriority(priority1);

        }
    }

//    private void tagsParser(JSONArray tags, Task task) {
//        for (int i = 0; i < tags.length(); i++) {
//            JSONObject tagObject = tags.getJSONObject(i);
//            String tag = tagObject.getString("name");
//            task.addTag(tag);
//        }
//    }

    private void tagsParser(Task task, JSONObject jsonObject) {
        //System.out.println(jsonObject);
        if (!jsonObject.keySet().contains("tags")) {
            throw new JSONException("The tags key is either missing or misspelled");
        } else {
            JSONArray tags = jsonObject.getJSONArray("tags");
            for (int i = 0; i < tags.length(); i++) {
                JSONObject tagObject = tags.getJSONObject(i);
                if (!tagObject.keySet().contains("name")) {
                    System.out.println("The name key is either missing or misspelled");
                    continue;
                } else {
                    String tag = tagObject.getString("name");
                    task.addTag(tag);
                }
            }
        }
    }


//    private void dueDateParser(JSONObject dueDate, Task task) {
//        int year = dueDate.getInt("year");
//        int month = dueDate.getInt("month");
//        int day = dueDate.getInt("day");
//        int hour = dueDate.getInt("hour");
//        int minute = dueDate.getInt("minute");
//
//        DueDate dueDate1 = new DueDate();
//        Calendar cal = Calendar.getInstance();
//        cal.set(year, month, day, hour, minute);
//        dueDate1.setDueDate(cal.getTime());
//        task.setDueDate(dueDate1);
//    }

    private void dueDateParser(Task task, JSONObject jsonObject) {

        if (!jsonObject.keySet().contains("due-date")) {
            throw new JSONException("The due-date key is either missing or misspelled");
        } else if (jsonObject.isNull("due-date")) {
            task.setDueDate(nullDate);
        } else {
            JSONObject dueDate = jsonObject.getJSONObject("due-date");
            dueDateParser2(dueDate, task);


        }
    }

    private void dueDateParser2(JSONObject dueDate, Task task) {

        checkContains("year", dueDate);
        year = dueDate.getInt("year");

        checkContains("month", dueDate);
        month = dueDate.getInt("month");

        checkContains("day", dueDate);
        day = dueDate.getInt("day");

        checkContains("hour", dueDate);
        hour = dueDate.getInt("hour");

        checkContains("minute", dueDate);
        minute = dueDate.getInt("minute");
//        /*if (!dueDate.keySet().contains("month")) {
//            throw new JSONException("The month key is either missing or misspelled");
//        } else {
//            month = dueDate.getInt("month");
//        }
//        if (!dueDate.keySet().contains("day")) {
//            throw new JSONException("The day key is either missing or misspelled");
//        } else {
//            day = dueDate.getInt("day");
//        }
//        if (!dueDate.keySet().contains("hour")) {
//            throw new JSONException("The hour key is either missing or misspelled");
//        } else {
//            hour = dueDate.getInt("hour");
//        }
//        if (!dueDate.keySet().contains("minute")) {
//            throw new JSONException("The minute key is either missing or misspelled");
//        } else {
//            minute = dueDate.getInt("minute");
//        }*/
        dueDateParser3(year, month, day, hour, minute, task);

    }

    private void checkContains(String field, JSONObject dueDate) {
        if (!dueDate.keySet().contains(field)) {
            throw new JSONException("The " + field + "key is either missing or misspelled");
        }
    }

    private void dueDateParser3(int year, int month, int day, int hour, int minute, Task task) {
        DueDate dueDate1 = new DueDate();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, minute);
        dueDate1.setDueDate(cal.getTime());
        task.setDueDate(dueDate1);
    }
}

