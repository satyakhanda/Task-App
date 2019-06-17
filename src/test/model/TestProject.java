package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class TestProject {
    private Project project;
    private String description = "test";
    private HashSet<Todo> tasks;
    private Task testTask;


    @BeforeEach
    public void runBefore() {
        project = new Project(description);
        tasks = new HashSet<>();
    }

    @Test
    public void testProjectExceptionEmptyString() {
        try {
            Project project = new Project("");
            fail("Failed to catch EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Caught the correct exception!");
        }
    }

    @Test
    public void testProjectExceptionNull() {
        try {
            Project project = new Project(null);
            fail("Failed to catch EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Caught correct exception!");
        }
    }

    @Test
    public void testProject() {
        try {
            Project project = new Project("testProject");
            assertEquals("testProject", project.getDescription());
            assertEquals(0, project.getNumberOfTasks());
            System.out.println("Correct!");
        } catch (EmptyStringException e) {
            System.out.println("Failed");
        }
    }

    @Test
    public void testAddTrue() {
        try {
            Project project = new Project("testProject");
            Task task = new Task("description ## today");
            project.add(task);
            assertTrue(project.contains(task));
            System.out.println("Correct, no exception needed!");
        } catch (NullArgumentException e) {
            fail("Caught NullArgumentException when not needed");
        }

    }

    @Test
    public void testAddFalse() {
        try {
            Project project = new Project("testProject");
            Task task = new Task("description");
            project.add(task);
            project.add(task);
            assertEquals(1, project.getNumberOfTasks());
        } catch (NullArgumentException e) {
            fail("Caught NullArgumentException when not needed");
        }
    }

    @Test
    public void testProjectAddingItsSelf() {
        project.add(project);
        assertFalse(project.contains(project));
    }


    @Test
    public void testAddException() {
        try {
            Project project = new Project("testProject");
            project.add(testTask);
            fail("Didn't catch an exception when you needed to");
        } catch (NullArgumentException e) {
            System.out.println("Caught correct exception!");
        }
    }

    @Test
    public void testRemoveTrue() {
        try {
            Project project = new Project("testProject");
            Task task = new Task("description");
            project.add(task);
            project.remove(task);
            assertFalse(project.contains(task));
            assertEquals(0, project.getNumberOfTasks());
            System.out.println("Correct, no exception needed!");
        } catch (NullArgumentException e) {
            fail("Caught NullArgumentException when not needed");
        }
    }

    @Test
    public void testRemoveException() {
        try {
            Project project = new Project("testProject");
            Task task = new Task("testTask");
            project.add(task);
            project.remove(testTask);
            assertFalse(project.contains(testTask));
            fail("False");
        } catch (NullArgumentException e) {
            System.out.println("Correct!");
        }
    }

    @Test
    public void testRemoveFalse() {
        try {
            Project project = new Project("testProject");
            Task task = new Task("task");
            Task task1 = new Task("task1");
            project.add(task);
            project.remove(task1);
            assertEquals(1, project.getNumberOfTasks());
            System.out.println("Correct no exception needed!");
        } catch (NullArgumentException e) {
            fail();
        }

    }

    @Test
    public void testGetDescription() {
        Project project = new Project("testProject");
        String description = project.getDescription();
        assertEquals(description, "testProject");
    }

    @Test
    public void testGetEstimatedTimeToComplete() {
        Project project = new Project("testProject");
        Project project1 = new Project("testProject1");
        Task task = new Task("task");
        Task task1 = new Task("task1");
        Task task2 = new Task("task2");
        task.setEstimatedTimeToComplete(40);
        task1.setEstimatedTimeToComplete(10);
        task2.setEstimatedTimeToComplete(90);
        project1.add(task2);
        project1.add(task1);
        project.add(project1);
        project.add(task);
        assertEquals(140, project.getEstimatedTimeToComplete());
    }

    @Test
    public void testGetTasks() {
        try {
            Project project = new Project("testProject");
            Task task = new Task("testTask");
            project.add(task);
            project.getTasks();
            fail();
        } catch (UnsupportedOperationException e) {
            System.out.println("Correct!");
        }
    }

    @Test
    public void testGetProgressNoTask() {
        Project project = new Project("testProject");
        assertEquals(0, project.getProgress());
    }


    @Test
    public void testGetProgressTree() {
        Project project = new Project("testProject");
        Project project1 = new Project("testProject1");
        Task task = new Task("task");
        Task task1 = new Task("task1");
        Task task2 = new Task("task2");
        task.setProgress(40);
        task1.setProgress(10);
        task2.setProgress(90);
        project1.add(task2);
        project1.add(task1);
        project.add(project1);
        project.add(task);
        assertEquals(45, project.getProgress());
    }

    @Test
    public void testNumberOfTasksOne() {
        Project project = new Project("testProject");
        Task task = new Task("description");
        project.add(task);
        assertEquals(1, project.getNumberOfTasks());
    }

    @Test
    public void testNumberofTasksZero() {
        Project project = new Project("testProject");
        assertEquals(0, project.getNumberOfTasks());
    }

    @Test
    public void testNumberofTasksMultiple() {
        Project project = new Project("testProject");
        Task task = new Task("description");
        Task task1 = new Task("description1");
        project.add(task);
        project.add(task1);
        assertEquals(2, project.getNumberOfTasks());
    }

    @Test
    public void testIsCompletedEmpty() {
        Project project = new Project("testProject");
        assertFalse(project.isCompleted());
    }

    @Test
    public void testIsCompletedTrue() {
        Project project = new Project("testProject");
        Project project1 = new Project("testProject1");
        Task task = new Task("task");
        Task task1 = new Task("task1");
        Task task2 = new Task("task2");
        task.setProgress(100);
        task1.setProgress(100);
        task2.setProgress(100);
        project1.add(task2);
        project1.add(task1);
        project.add(project1);
        project.add(task);
        assertTrue(project.isCompleted());
    }

    @Test
    public void testIsCompletedFalse() {
        Project project = new Project("testProject");
        Project project1 = new Project("testProject1");
        Task task = new Task("task");
        Task task1 = new Task("task1");
        Task task2 = new Task("task2");
        task.setProgress(100);
        task1.setProgress(100);
        task2.setProgress(80);
        project1.add(task2);
        project1.add(task1);
        project.add(project1);
        project.add(task);
        assertFalse(project.isCompleted());
    }


    @Test
    public void testContainsTrue() {
        try {
            Project project = new Project("testProject");
            Task task = new Task("description");
            project.add(task);
            assertTrue(project.contains(task));
            System.out.println("Correct, no exception needed!");
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    public void testContainsFalse() {
        try {
            Project project = new Project("testProject");
            Task task = new Task("description");
            assertFalse(project.contains(task));
            System.out.println("Correct, no exception needed!");
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    public void testContainsNull() {
        try {
            Project project = new Project("testProject");
            assertFalse(project.contains(testTask));
            fail();
        } catch (NullArgumentException e) {
            System.out.println("Correct!");
        }
    }

    @Test
    public void testEquals1() {
        Project project = new Project("testProject");
        Project project1 = new Project("testProject1");
        assertFalse(project.equals(project1));
    }

    @Test
    public void testEquals2() {
        Project project = new Project("testProject");
        assertTrue(project.equals(project));
    }

    @Test
    public void testEquals3() {
        Project project = new Project("testProject");
        Project project1 = new Project("testProject");
        assertTrue(project.equals(project1));
    }

    @Test
    public void testEquals4() {
        Project project = new Project("testProject");
        String project1 = "testProject";
        assertFalse(project.equals(project1));
    }

    @Test
    public void testHashCode() {
        Project project = new Project("testProject");
        Project project1 = new Project("testProject");
        assertEquals(project.hashCode(), project1.hashCode());
    }



    @Test
    public void testIteratorEmpty() {
        Project project = new Project("projectIterator");
        Iterator<Todo> itr = project.iterator();
        assertFalse(itr.hasNext());
    }

    @Test
    public void testIteratorOneSubProject() {
        Project project = new Project("projectIterator");
        Iterator<Todo> itr = project.iterator();
        Project project1 = new Project("p1");
        project1.setPriority(new Priority(1));
        project.add(project1);
        assertTrue(itr.hasNext());
        Todo next = itr.next();
        assertEquals(project1, next);
        assertFalse(itr.hasNext());
    }

    @Test
    public void testIteratorOneMultipleProject() {
        Project project = new Project("projectIterator");
        Iterator<Todo> itr = project.iterator();
        Project project1 = new Project("p1");
        Project project2 = new Project("p2");
        Project project3 = new Project("p3");
        project1.setPriority(new Priority(3));
        project2.setPriority(new Priority(1));
        project3.setPriority(new Priority(2));
        project.add(project1);
        project.add(project2);
        project.add(project3);
        assertTrue(itr.hasNext());
        Todo next = itr.next();
        assertEquals(project2, next);
        assertTrue(itr.hasNext());
        next = itr.next();
        assertEquals(project3, next);
        assertTrue(itr.hasNext());
        next = itr.next();
        assertEquals(project1, next);
        assertFalse(itr.hasNext());
    }

    @Test
    public void testIteratorOneTask() {
        Project project = new Project("projectIterator");
        Iterator<Todo> itr = project.iterator();
        Task task1 = new Task("task1");
        task1.setPriority(new Priority(3));
        project.add(task1);
        assertTrue(itr.hasNext());
        Todo next = itr.next();
        assertEquals(task1, next);
        assertFalse(itr.hasNext());

    }

    @Test
    public void testIteratorMultipleTasks() {
        Project project = new Project("projectIterator");
        Iterator<Todo> itr = project.iterator();
        Task task1 = new Task("task1");
        Task task2 = new Task("task2");
        Task task3 = new Task("task3");
        Task task4 = new Task("task4");
        task1.setPriority(new Priority(4));
        task2.setPriority(new Priority(2));
        task3.setPriority(new Priority(3));
        task4.setPriority(new Priority(4));
        project.add(task1);
        project.add(task2);
        project.add(task3);
        project.add(task4);
        for (Todo t : project) {
            System.out.println(t.getDescription());
        }
        assertTrue(itr.hasNext());
        Todo next = itr.next();
        assertEquals(task2, next);
        assertTrue(itr.hasNext());
        next = itr.next();
        assertEquals(task3, next);
        assertTrue(itr.hasNext());
        next = itr.next();
        assertEquals(task1, next);
        assertTrue(itr.hasNext());
        next = itr.next();
        assertEquals(task4, next);
        assertFalse(itr.hasNext());

    }

    @Test
    public void testIteratorMultipleTasksAndProjects() {
        Project project = new Project("testProject");
        Project project1 = new Project("testProject1");
        Project project2 = new Project("testProject2");
        project.setPriority(new Priority(2));
        project1.setPriority(new Priority(2));
        project2.setPriority(new Priority(1));
        Task task = new Task("task");
        Task task1 = new Task("task1");
        Task task2 = new Task("task2");
        Task task3 = new Task("task3");
        Task task4 = new Task("task4");
        Task task5 = new Task("task5");
        task.setPriority(new Priority(4));
        task1.setPriority(new Priority(3));
        task2.setPriority(new Priority(1));
        task3.setPriority(new Priority(2));
        task4.setPriority(new Priority(1));
        task5.setPriority(new Priority(4));
        project.add(project1);
        project.add(task2);
        project.add(task3);
        project.add(task5);
        project.add(task4);
        project.add(project2);
        project.add(task1);
        project.add(task);
        for (Todo t : project) {
            System.out.println(t.description);
        }
    }

    @Test
    public void testMultipleProjectsWithChildren() {

        Project project = new Project("projectIterator");
        Iterator<Todo> itr = project.iterator();
        Project project1 = new Project("project1");
        Project project2 = new Project("project2");
        Task task1 = new Task("task1");
        Task task2 = new Task("task2");
        Task task3 = new Task("task3");
        Task task4 = new Task("task4");
        task1.setPriority(new Priority(4));
        task2.setPriority(new Priority(2));
        task3.setPriority(new Priority(3));
        task4.setPriority(new Priority(4));
        project.add(project1);
        project1.add(task1);
        project.add(task2);
        project1.add(task3);
        project.add(task4);
        project1.add(project2);
        for (Todo t : project) {
            System.out.println(t.getDescription());
        }
        assertTrue(itr.hasNext());
        Todo next = itr.next();
        assertEquals(task2, next);
        assertTrue(itr.hasNext());
        next = itr.next();
        assertEquals(project1, next);
        assertTrue(itr.hasNext());
        next = itr.next();
        assertEquals(task4, next);
        assertFalse(itr.hasNext());

    }

    @Test
    public void testOneImportantAndOneUrgent() {
        Project project = new Project("projectIterator");
        Iterator<Todo> itr = project.iterator();
        Project project1 = new Project("project1");
        Project project2 = new Project("project2");
        project1.setPriority(new Priority(2));
        project2.setPriority(new Priority(3));
        project.add(project2);
        project.add(project1);
        assertTrue(itr.hasNext());
        Todo next = itr.next();
        assertEquals(project1, next);
        assertTrue(itr.hasNext());
        next = itr.next();
        assertEquals(project2, next);
        assertFalse(itr.hasNext());
        for (Todo t : project) {
            System.out.println(t.getDescription());
        }
    }

    @Test
    public void testMultipleImportantAndMultipleUrgent() {
        Project project = new Project("projectIterator");
        Iterator<Todo> itr = project.iterator();
        Project project1 = new Project("project1");
        Project project2 = new Project("project2");
        project1.setPriority(new Priority(2));
        project2.setPriority(new Priority(3));
        project.add(project2);
        project.add(project1);
        assertTrue(itr.hasNext());
        Todo next = itr.next();
        assertEquals(project1, next);
        assertTrue(itr.hasNext());
        next = itr.next();
        assertEquals(project2, next);
        assertFalse(itr.hasNext());
        for (Todo t : project) {
            System.out.println(t.getDescription());
        }
    }

    @Test
    public void testNoSuchElementException() {
        try {
            Project project = new Project("projectIterator");
            Iterator<Todo> itr = project.iterator();
            itr.next();
            fail();
        } catch (NoSuchElementException e) {
            System.out.println("Correct!");
        }

    }

}