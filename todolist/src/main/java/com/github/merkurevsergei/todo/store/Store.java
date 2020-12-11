package com.github.merkurevsergei.todo.store;

import com.github.merkurevsergei.todo.model.Category;
import com.github.merkurevsergei.todo.model.Item;
import com.github.merkurevsergei.todo.model.User;

import java.util.List;
import java.util.Optional;

/**
 * The {@code Store} interface allows to make queries to the database.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public interface Store {

    /**
     * @param id item
     * @return {@code Item}
     */
    Optional<Item> findItemById(int id);

    /**
     * @return all {@code Item} from store
     */
    List<Item> findAllItems();

    /**
     * @return incomplete {@code Item}
     */
    List<Item> findIncompleteItems();

    /**
     * @param user {@code User}
     * @return {@code List<Item>} by {@code User}
     */
    List<Item> findItems(User user);

    /**
     * @param user {@code User}
     * @param done flag
     * @return {@code List<Item>} by {@code User} and {@code done} flag
     */
    List<Item> findItems(User user, Boolean done);

    /**
     * @param name user
     * @return {@code User}
     */
    Optional<User> findUserByName(String name);

    /**
     * @return {@code List<Category>} all categories
     * from database.
     */
    List<Category> findAllCategories();

    /**
     * @param entity saved
     */
    void save(Object entity);

    /**
     * @param item          saved
     * @param categoriesIds is categories id for item
     */
    void saveItem(Item item, String[] categoriesIds);

    /**
     * @param entity updated
     * @param <T>    result
     */
    <T> void update(T entity);
}
