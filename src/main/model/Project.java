package model;

import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    //private String description;
    private List<Todo> tasks;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (task != this && !contains(task)) {
            tasks.add(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (contains(task)) {
            tasks.remove(task);
        }
    }

    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    @Override
    public int getEstimatedTimeToComplete() {
        return etcHours;
//        int total = 0;
//        for (Todo task : tasks) {
//            total += task.getEstimatedTimeToComplete();
//        }
//        return total;
    }


    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
//     the percentage of completion (rounded down to the nearest integer).
//     the value returned is the average of the percentage of completion of
//     all the tasks and sub-projects in this project.
    public int getProgress() {
        int total = 0;
        int taskNum = 0;
        int average;

        for (Todo t : tasks) {

            total += t.getProgress();
            taskNum++;
        }
        if (taskNum == 0) {
            average = 0;
        } else {
            average = total / taskNum;
        }
        return average;
    }


    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
//     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }

    private class ProjectIterator implements Iterator<Todo> {

        private int priorityLevel = 1;
        private int index = 0;

        @Override
        public boolean hasNext() {
            int maxPriority = 1;
            for (Todo t : tasks) {
                if (t.getPriorityInt() > maxPriority) {
                    maxPriority = t.getPriorityInt();
                }
            }
            if (priorityLevel == maxPriority && index == tasks.size()) {
                return false;
            } else if (priorityLevel < maxPriority) {
                return true;
            }
            for (int i = index; i < tasks.size(); i++) {
                if (tasks.get(i).getPriorityInt() == maxPriority) {
                    return true;
                }
            }
            return false;
        }



        @Override
        public Todo next() {

            Todo returnTodo = nextHelper(1);
            if (returnTodo != null) {
                return returnTodo;
            }

            returnTodo = nextHelper(2);
            if (returnTodo != null) {
                return returnTodo;
            }

            returnTodo = nextHelper(3);
            if (returnTodo != null) {
                return returnTodo;
            }

            returnTodo = nextHelper(4);
            if (returnTodo != null) {
                return returnTodo;
            }

            throw new NoSuchElementException();
        }

        private Todo nextHelper(int priorityCheck) {
            while (priorityLevel == priorityCheck) {
                if (index < tasks.size() && tasks.get(index).priority.equals(new Priority(priorityCheck))) {
                    index++;
                    return tasks.get(index - 1);
                } else if (index == tasks.size()) {
                    index = 0;
                    priorityLevel++;
                    break;
                }
                index++;
            }
            return null;
        }
    }
}