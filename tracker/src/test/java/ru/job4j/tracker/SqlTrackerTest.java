package ru.job4j.tracker;

import org.junit.Test;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.store.SqlTracker;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class SqlTrackerTest {

    public Connection init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void whenAdd() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name"));
            assertFalse(tracker.findByName("name").isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenReplace() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name1"));
            final Integer id = tracker.findByName("name1").get(0).getId();
            tracker.replace(id, new Item("name2"));
            assertFalse(tracker.findByName("name2").isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenDelete() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name1"));
            final Integer id = tracker.findByName("name1").get(0).getId();
            tracker.delete(id);
            assertTrue(tracker.findByName("name1").isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindAll() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name1"));
            tracker.add(new Item("name2"));
            assertFalse(tracker.findAll().isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindByName() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name1"));
            assertFalse(tracker.findByName("name1").isEmpty());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindById() {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name1"));
            final Integer id = tracker.findByName("name1").get(0).getId();
            assertEquals(tracker.findById(id).getName(), "name1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}