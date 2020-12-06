package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.MemTracker;

import static org.junit.Assert.*;

public class MemTrackerTest {

    @Test
    public void whenAddNewItemThenTrackerHasSameItem() {
        MemTracker tracker = new MemTracker();
        Item item = new Item("test1");
        tracker.add(item);
        Item result = tracker.findById(item.getId());
        assertEquals(result.getName(), item.getName());
    }

    @Test
    public void findAll() {
        MemTracker tracker = new MemTracker();
        Item item1 = new Item("test1");
        Item item2 = new Item("test1");
        Item item3 = new Item("test3");
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        Item[] expected = {item1, item2, item3};
        Item[] result = tracker.findAll();

        assertArrayEquals(expected, result);
    }

    @Test
    public void findByName() {
        MemTracker tracker = new MemTracker();
        Item item1 = new Item("test1");
        Item item2 = new Item("test1");
        Item item3 = new Item("test3");
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        Item[] expected = {item1, item2};
        Item[] result = tracker.findByName("test1");

        assertArrayEquals(expected, result);
    }

    @Test
    public void findById() {
        MemTracker tracker = new MemTracker();
        Item item1 = new Item("test1");
        Item item2 = new Item("test1");
        Item item3 = new Item("test3");
        tracker.add(item1);
        tracker.add(item2);
        tracker.add(item3);

        Item result = tracker.findById(item1.getId());

        assertEquals(item1, result);
    }

    @Test
    public void whenReplace() {
        MemTracker tracker = new MemTracker();
        Item bug = new Item("Bug");
        tracker.add(bug);
        Integer id = bug.getId();
        Item bugWithDesc = new Item("Bug with description");
        tracker.replace(id, bugWithDesc);
        assertEquals(tracker.findById(id).getName(), "Bug with description");
    }

    @Test
    public void whenDelete() {
        MemTracker tracker = new MemTracker();
        Item bug = new Item("Bug");
        tracker.add(new Item("Bug1"));
        tracker.add(new Item("Bug2"));
        tracker.add(bug);
        tracker.add(new Item("Bug3"));
        tracker.add(new Item("Bug4"));
        tracker.add(new Item("Bug5"));
        Integer id = bug.getId();
        tracker.delete(id);
        assertNull(tracker.findById(id));
    }
}