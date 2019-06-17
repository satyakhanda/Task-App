package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestTask {
    private Task testTask;
    private Tag testTag;
    private Priority testPriority;
    private Set<Tag> tags;

    @BeforeEach
    public void runBefore() {
        testTask = new Task("Test Task");
        tags = testTask.getTags();

    }

    @Test
    public void testTaskNull() {
        try {
            Task task = new Task(null);
            fail();
        } catch (EmptyStringException e) {
            System.out.println("Correct");
        }
    }

    @Test
    public void testTaskEmpty() {
        try {
            Task task = new Task("");
            fail();
        } catch (EmptyStringException e) {
            System.out.println("Correct");
        }
    }

    @Test
    public void testTaskRegular() {
        try {
            Task task = new Task("description");
            assertEquals(0, task.getProgress());
            assertEquals(0, task.getEstimatedTimeToComplete());
            System.out.println("Correct");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    public void testAddTagStringNull() {
        try {
            String nullString = null;
            testTask.addTag(nullString);
            fail("Failed to catch EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Correct behavior!");
        }
    }

    @Test
    public void testAddTagStringEmpty() {
        try {
            testTask.addTag("");
            fail("Failed to catch EmptyStringException");
        } catch (EmptyStringException e) {
            System.out.println("Correct behavior!");
        }
    }

    @Test
    public void testAddTagStringTrue() {
        try {
            testTask.addTag("Tag1");
            assertEquals(1, tags.size());

            testTag = new Tag("Tag1");
            assertTrue(tags.contains(testTag));

            System.out.println("Correct behavior!");
        } catch (EmptyStringException e) {
            fail("No exception needed");
        }
    }

    @Test
    public void testAddTagStringFalse() {
        try {
            testTask.addTag("Tag1");
            testTask.addTag("Tag1");
            testTag = new Tag("Tag1");

            assertTrue(tags.contains(testTag));
            assertEquals(1, tags.size());

            System.out.println("Correct behavior!");
        } catch (EmptyStringException e) {
            fail("No exception needed");
        }
    }

    @Test
    public void testAddTagNullTag() {
        try {
            testTask.addTag(testTag);
            fail("Failed to catch NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Correct behavior!");
        }
    }


    @Test
    public void testAddTagTrue() {
        try {
            testTag = new Tag("Tag1");

            testTask.addTag(testTag);
            assertTrue(tags.contains(testTag));
            assertTrue(testTag.containsTask(testTask));

            System.out.println("Correct behavior!");
        } catch (NullArgumentException e) {
            fail("No exception needed");
        }
    }


    @Test
    public void testAddTagFalse() {
        try {
            testTag = new Tag("Tag1");
            testTask.addTag(testTag);
            assertTrue(tags.contains(testTag));
            assertTrue(testTag.containsTask(testTask));

            Tag testTag2 = new Tag("Tag1");
            testTask.addTag(testTag2);
            assertEquals(1, tags.size());
            assertFalse(testTag2.containsTask(testTask));

            System.out.println("Correct behavior!");
        } catch (NullArgumentException e) {
            fail("No exception needed");
        }
    }

    @Test
    public void testRemoveTagStringEmpty() {
        try {
            testTask.removeTag("");
            fail();
        } catch (EmptyStringException e) {
            System.out.println("Correct!");
        }
    }

    @Test
    public void testRemoveTagStringNull() {
        try {
            String nullString = null;
            testTask.removeTag(nullString);
            fail();
        } catch (EmptyStringException e) {
            System.out.println("Correct!");
        }
    }

    @Test
    public void testRemoveTagStringTrue() {
        try {
            testTask.addTag("Tag1");
            testTask.removeTag("Tag1");
            assertEquals(0, testTask.getTags().size());
            System.out.println("Correct!");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    public void testRemoveTagStringFalse() {
        try {
            testTask.addTag("Tag1");
            testTask.removeTag("Tag2");
            assertEquals(1, testTask.getTags().size());
            System.out.println("Correct!");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    public void testRemoveTagNullTag() {
        try {
            testTask.removeTag(testTag);
            fail("Failed to catch NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Correct behavior!");
        }
    }


    @Test
    public void testRemoveTagTrueSingle() {
        try {
            testTag = new Tag("Tag1");
            testTask.addTag(testTag);
            assertTrue(tags.contains(testTag));
            assertTrue(testTag.containsTask(testTask));

            Tag testTag2 = new Tag("Tag2");
            testTask.addTag(testTag2);
            assertTrue(tags.contains(testTag2));
            assertTrue(testTag2.containsTask(testTask));

            testTask.removeTag(testTag);
            assertEquals(1, tags.size());
            assertFalse(testTag.containsTask(testTask));
            assertFalse(tags.contains(testTag));

            System.out.println("Correct behavior!");
        } catch (NullArgumentException e) {
            fail("No exception needed");
        }
    }

    @Test
    public void testRemoveTagTrueAll() {
        try {
            testTag = new Tag("Tag1");
            testTask.addTag(testTag);
            assertTrue(tags.contains(testTag));
            assertTrue(testTag.containsTask(testTask));

            Tag testTag2 = new Tag("Tag2");
            testTask.addTag(testTag2);
            assertTrue(tags.contains(testTag2));
            assertTrue(testTag2.containsTask(testTask));

            testTask.removeTag(testTag);
            assertEquals(1, tags.size());
            assertFalse(tags.contains(testTag));
            assertFalse(testTag.containsTask(testTask));

            testTask.removeTag(testTag2);
            assertEquals(0, tags.size());
            assertFalse(tags.contains(testTag2));
            assertFalse(testTag2.containsTask(testTask));

            System.out.println("Correct behavior!");
        } catch (NullArgumentException e) {
            fail("No exception needed");
        }
    }


    @Test
    public void testRemoveTagFalse() {
        try {
            testTag = new Tag("Tag1");
            testTask.addTag(testTag);
            assertTrue(tags.contains(testTag));
            assertTrue(testTag.containsTask(testTask));

            Tag testTag2 = new Tag("Tag2");

            testTask.removeTag(testTag2);
            assertEquals(1, tags.size());
            assertFalse(tags.contains(testTag2));

            System.out.println("Correct behavior!");
        } catch (NullArgumentException e) {
            fail("No exception needed");
        }
    }

    @Test
    public void testGetTags() {
        Task task = new Task("testTask");
        task.addTag("Tag1");
        task.addTag("Tag2");
        assertEquals(2, task.getTags().size());
    }

    @Test
    public void testGetPriority() {
        testTask = new Task("description ## urgent");
        Priority priority = new Priority(3);
        assertEquals(priority, testTask.getPriority());
    }

    @Test
    public void testSetPriorityNull() {
        try {
            testTask.setPriority(testPriority);
            fail();
        } catch (NullArgumentException e) {
            System.out.println("Correct!");
        }
    }

    @Test
    public void testSetPriority() {
        try {
            testPriority = new Priority(2);
            testTask.setPriority(testPriority);
            assertEquals(testPriority, testTask.getPriority());
            System.out.println("Correct!");
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    public void testGetStatus() {
        testTask = new Task("description ## todo; urgent");
        Status status = Status.TODO;
        assertEquals(status, testTask.getStatus());
    }

    @Test
    public void testSetStatusNull() {
        try {
            testTask.setStatus(null);
            fail();
        } catch (NullArgumentException e) {
            System.out.println("Correct");
        }
    }

    @Test
    public void testSetStatus() {
        try {
            testTask.setStatus(Status.UP_NEXT);
            assertEquals(Status.UP_NEXT, testTask.getStatus());
            System.out.println("Correct!");
        } catch (NullArgumentException e) {
            fail();
        }
    }


    @Test
    public void testGetDescription() {
        testTask.setDescription("testDescription");
        assertEquals("testDescription", testTask.getDescription());
    }

    @Test
    public void testSetDescriptionNull() {
        try {
            testTask.setDescription(null);
            fail();
        } catch (EmptyStringException e) {
            System.out.println("Correct");
        }
    }

    @Test
    public void testSetDescriptionEmptyString() {
        try {
            testTask.setDescription("");
            fail();
        } catch (EmptyStringException e) {
            System.out.println("Correct");
        }
    }

    @Test
    public void testSetDescription() {
        Task task = new Task("test");
        task.setDescription("testDescription ## tomorrow; Tag1");
        assertEquals("testDescription ", task.getDescription());
        assertTrue(task.containsTag("Tag1"));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH) + 1;
        cal.set(year, month, day);
        DueDate testDate = new DueDate(cal.getTime());
        assertEquals(testDate, task.getDueDate());
    }

    @Test
    public void testGetEstimatedTimeToComplete() {
        Task task = new Task("description");
        task.setEstimatedTimeToComplete(10);
        assertEquals(10, task.getEstimatedTimeToComplete());
    }

    @Test
    public void testGetProgress() {
        Task task = new Task("description");
        task.setProgress(19);
        assertEquals(19, task.getProgress());
    }

    @Test
    public void testGetDueDate() {
        Task task = new Task("name ## today");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(year, month, day);
        DueDate testDate = new DueDate(cal.getTime());
        assertEquals(testDate, task.getDueDate());
    }

    @Test
    public void testSetDueDate() {
        Task task = new Task("name ## today");
        Calendar cal = Calendar.getInstance();
        DueDate testDate = new DueDate(cal.getTime());
        task.setDueDate(testDate);
        assertEquals(testDate, task.getDueDate());
    }


    @Test
    public void testSetDueDateNoException() {
        try {
            Date newDate = new Date();
            DueDate newDueDate = new DueDate(newDate);
            testTask.setDueDate(newDueDate);
            assertEquals(newDueDate, testTask.getDueDate());
            System.out.println("Correct, no exception needed");
        } catch (NullArgumentException e) {
            fail("Caught NullArgumentException when not needed");
        }
    }

    @Test
    public void testSetDueDateNullException() {
        try {
            Date newDate = new Date();
            DueDate newDueDate = new DueDate(null);
            testTask.setDueDate(newDueDate);
            assertEquals(newDueDate, testTask.getDueDate());
            fail("Failed to catch NullArgumentException");
        } catch (NullArgumentException e) {
            System.out.println("Caught correct exception");
        }
    }

    @Test
    public void testContainsTagStringTrue() {
        try {
            testTask = new Task("description ## tag1; tag2");
            assertTrue(testTask.containsTag("tag1"));
            System.out.println("Correct!");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    public void testContainsTagStringFalse() {
        try {
            testTask = new Task("description ## tag1");
            assertFalse(testTask.containsTag("tag2"));
            System.out.println("Correct!");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    public void testContainsTagStringNull() {
        try {
            testTask = new Task("description ## tag1");
            String nullString = null;
            assertFalse(testTask.containsTag(nullString));
            fail();
        } catch (EmptyStringException e) {
            System.out.println("Correct");
        }
    }

    @Test
    public void testContainsTagStringEmpty() {
        try {
            testTask = new Task("description ## tag1");
            assertFalse(testTask.containsTag(""));
            fail();
        } catch (EmptyStringException e) {
            System.out.println("Correct");
        }
    }

    @Test
    public void testContainsTagTrue() {
        try {
            testTask = new Task("description");
            Tag tag = new Tag("tag");
            testTask.addTag(tag);
            assertTrue(testTask.containsTag(tag));
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    public void testContainsTagFalse() {
        try {
            testTask = new Task("description");
            Tag tag = new Tag("tag");
            assertFalse(testTask.containsTag(tag));
        } catch (NullArgumentException e) {
            fail();
        }
    }

    @Test
    public void testContainsTagNull() {
        try {
            testTask = new Task("description");
            assertFalse(testTask.containsTag(testTag));
            fail();
        } catch (NullArgumentException e) {
            System.out.println("Correct");
        }
    }




    @Test
    public void testContainsTagEmpty() {
        try {
            testTask = new Task("description ## tag1");
            assertFalse(testTask.containsTag(""));
            fail();
        } catch (EmptyStringException e) {
            System.out.println("Correct!");

        }
    }

    @Test
    public void testToString() {
        Task task = new Task("description ## today; tag1");
        assertEquals( "\n" +
                "{\n" +
                "\tDescription: description \n" +
                "\tDue date: Thu Mar 28 2019 11:59 PM\n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags: #tag1\n" +
                "}",task.toString());
    }

    @Test
    public void testToString2() {
        Task task = new Task("description ## today");
        assertEquals( "\n" +
                "{\n" +
                "\tDescription: description \n" +
                "\tDue date: Thu Mar 28 2019 11:59 PM\n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags:  \n" +
                "}",task.toString());
    }

    @Test
    public void testToString3() {
        Task task = new Task("description ## today; tag1; tag2; tag3");
        assertEquals( "\n" +
                "{\n" +
                "\tDescription: description \n" +
                "\tDue date: Thu Mar 28 2019 11:59 PM\n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags: #tag1, #tag2, #tag3\n" +
                "}",task.toString());
    }

    @Test
    public void testToString4() {
        Task task = new Task("description ## tag1; urgent; tag2; tag3");
        assertEquals( "\n" +
                "{\n" +
                "\tDescription: description \n" +
                "\tDue date: \n" +
                "\tStatus: TODO\n" +
                "\tPriority: URGENT\n" +
                "\tTags: #tag1, #tag2, #tag3\n" +
                "}",task.toString());
    }

    @Test
    public void testEquals() {
        testTask = new Task("description ## today; urgent; important; up next; tag1");
        Task task2 = new Task("description ## today; urgent; important; up next; tag1");
        assertTrue(testTask.equals(task2));
    }

    @Test
    public void testEquals2() {
        testTask = new Task("description ## today; urgent; up next; tag1");
        Task task2 = new Task("f ## tomorrow; important");
        assertFalse(testTask.equals(task2));
    }

    @Test
    public void testEquals3() {
        testTask = new Task("description ## today; urgent; up next; tag1");
        String task2 = "f";
        assertFalse(testTask.equals(task2));
    }

    @Test
    public void testEquals4() {
        testTask = new Task("description ## today; urgent; up next; tag1");
        assertTrue(testTask.equals(testTask));
    }

    @Test
    public void testEquals5() {
        testTask = new Task("description ## today");
        Task task2 = new Task("description ## tomorrow");
        assertFalse(testTask.equals(task2));
    }

    @Test
    public void testEquals6() {
        testTask = new Task("description ## important");
        Task task2 = new Task("description ## urgent; important");
        assertFalse(testTask.equals(task2));
    }

    @Test
    public void testEquals7() {
        testTask = new Task("description ## urgent");
        Task task2 = new Task("description ## urgent");
        assertTrue(testTask.equals(task2));
    }

    @Test
    public void testEquals8() {
        testTask = new Task("description ## important");
        Task task2 = new Task("description ## urgent");
        assertFalse(testTask.equals(task2));
    }

    @Test
    public void testEquals9() {
        testTask = new Task("description ## today; done; urgent; important");
        Task task2 = new Task("description ## today; done; urgent");
        System.out.println(testTask.toString());
        System.out.println(task2.toString());
        assertFalse(testTask.equals(task2));
    }

    @Test
    public void testEquals10() {
        testTask = new Task("descrip ## today, done, urgent, important");
        Task task2 = new Task("description ## tomorrow, up next, urgent");
        assertFalse(testTask.equals(task2));
    }

    @Test
    public void testEquals11() {
        testTask = new Task("description ## today, done, urgent, important, urgent");
        Task task2 = new Task("description ## tomorrow; up next; urgent");
        assertFalse(testTask.equals(task2));
    }


    @Test
    public void testHashCode() {
        testTask = new Task("description");
        Task testTask2 = new Task("description");
        assertEquals(testTask.hashCode(), testTask2.hashCode());
    }

    @Test
    public void testSetProgressLowerException() {
        try {
            testTask.setProgress(-100);
            fail();
        } catch (InvalidProgressException e) {
            System.out.println("Correct");
        }
    }

    @Test
    public void testSetProgressUpperException() {
        try {
            testTask.setProgress(110);
            fail();
        } catch (InvalidProgressException e) {
            System.out.println("Correct");
        }
    }

    @Test
    public void testSetProgress() {
        try {
            testTask.setProgress(70);
            assertEquals(70, testTask.getProgress());
        } catch (InvalidProgressException e) {
            fail();
        }
    }

    @Test
    public void testSetEstimatedTimeToCompleteException() {
        try {
            testTask.setEstimatedTimeToComplete(-100);
            fail();
        } catch (NegativeInputException e) {
            System.out.println("Correct");
        }
    }

    @Test
    public void testSetEstimatedTimeToComplete() {
        try {
            testTask.setEstimatedTimeToComplete(19);
            assertEquals(19, testTask.getEstimatedTimeToComplete());
        } catch (NegativeInputException e) {
            System.out.println("Correct");
        }
    }

    @Test
    public void testExceptionInvalidProgressException() {
        InvalidProgressException invalidProgressException = new InvalidProgressException();
    }

    @Test
    public void testExceptionNegativeInputException() {
        NegativeInputException negativeInputException = new NegativeInputException();

    }
}
