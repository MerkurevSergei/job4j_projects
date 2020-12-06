package ru.job4j.tracker.store;

import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {
    private Connection cn;

    public SqlTracker() {
    }

    public SqlTracker(Connection cn) {
        this.cn = cn;
    }

    @Override
    public void init() {
        try (final InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    @Override
    public Item add(Item item) {
        try (final PreparedStatement ps =
                     cn.prepareStatement("INSERT INTO item (name) VALUES (?);", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            final int id = ps.executeUpdate();
            item.setId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        boolean res = false;
        try (final PreparedStatement ps = cn.prepareStatement("UPDATE item SET name = ? where id = ?;")) {
            ps.setString(1, item.getName());
            ps.setInt(2, id);
            res = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public boolean delete(Integer id) {
        boolean res = false;
        try (final PreparedStatement ps = cn.prepareStatement("DELETE FROM item where id = ?;")) {
            ps.setInt(1, id);
            res = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Item> findAll() {
        List<Item> res = new ArrayList<>();
        try (final PreparedStatement ps = cn.prepareStatement("SELECT id, name FROM item;");
             final ResultSet resultSet = ps.executeQuery()) {

            while (resultSet.next()) {
                Item item = new Item(resultSet.getString("name"));
                item.setId(resultSet.getInt("id"));
                res.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> res = new ArrayList<>();
        try (final PreparedStatement ps = cn.prepareStatement("SELECT id FROM item WHERE name = ?;")) {
            ps.setString(1, key);
            try (final ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Item item = new Item(key);
                    item.setId(resultSet.getInt("id"));
                    res.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Item findById(Integer id) {
        Item item = null;
        try (final PreparedStatement ps = cn.prepareStatement("SELECT name FROM item WHERE id = ?;")) {
            ps.setInt(1, id);
            try (final ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    item = new Item(resultSet.getString("name"));
                    item.setId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}
