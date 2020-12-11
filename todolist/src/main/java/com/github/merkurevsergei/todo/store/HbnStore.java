package com.github.merkurevsergei.todo.store;

import com.github.merkurevsergei.todo.model.Category;
import com.github.merkurevsergei.todo.model.Item;
import com.github.merkurevsergei.todo.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * The {@code HbnStore} implements {@code Store} interface and
 * allows to make queries to the database.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
@ApplicationScoped
public class HbnStore implements Store {
    @PersistenceUnit(unitName = "default")
    private final EntityManagerFactory emf;

    /**
     * Required constructor.
     */
    public HbnStore() {
        emf = Persistence.createEntityManagerFactory("default");
    }

    /**
     * @param id item
     * @return {@code Item}
     */
    @Override
    public Optional<Item> findItemById(int id) {
        return this.tx(entityManager -> entityManager
                .createQuery("from Item where id = :id", Item.class)
                .setParameter("id", id)
                .getResultStream().findFirst()
        );
    }

    /**
     * @return all {@code Item} from store
     */
    @Override
    public List<Item> findAllItems() {
        return this.tx(entityManager -> entityManager
                .createQuery(
                        "select distinct i "
                                + "from Item i "
                                + "left join fetch i.categories "
                                + "order by i.created", Item.class)
                .getResultList()
        );
    }

    /**
     * @return incomplete {@code Item}
     */
    @Override
    public List<Item> findIncompleteItems() {
        return this.tx(
                entityManager -> entityManager
                        .createQuery(
                                "select distinct i "
                                        + "from Item i "
                                        + "left join fetch i.categories "
                                        + "where i.done = false "
                                        + "order by i.created", Item.class)
                        .getResultList()
        );
    }

    /**
     * @param user {@code User}
     * @return {@code List<Item>} by {@code User}
     */
    @Override
    public List<Item> findItems(User user) {
        return this.tx(entityManager -> entityManager
                .createQuery(
                        "select distinct i "
                                + "from Item i "
                                + "left join fetch i.categories "
                                + "where i.user = :user "
                                + "order by i.created", Item.class)
                .setParameter("user", user)
                .getResultList()
        );
    }

    /**
     * @param user {@code User}
     * @param done flag
     * @return {@code List<Item>} by {@code User} and {@code done} flag
     */
    @Override
    public List<Item> findItems(User user, Boolean done) {
        return this.tx(entityManager -> entityManager
                .createQuery(
                        "select distinct i "
                                + "from Item i "
                                + "left join fetch i.categories "
                                + "where i.user = :user and i.done = :done "
                                + "order by i.created", Item.class)
                .setParameter("user", user)
                .setParameter("done", done)
                .getResultList()
        );
    }

    /**
     * @param name user
     * @return {@code User}
     */
    @Override
    public Optional<User> findUserByName(String name) {
        return this.tx(
                entityManager -> entityManager
                        .createQuery("from User where lower(name) = lower(:name)", User.class)
                        .setParameter("name", name)
                        .getResultStream().findFirst()
        );
    }

    /**
     * @return {@code List<Category>} all categories
     * from database.
     */
    @Override
    public List<Category> findAllCategories() {
        return this.tx(entityManager -> entityManager
                .createQuery("from Category c order by c.name", Category.class)
                .getResultList()
        );
    }

    /**
     * @param entity saved
     */
    @Override
    public void save(Object entity) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(entity);
        tx.commit();
        em.close();
    }

    /**
     * @param item          saved
     * @param categoriesIds is categories id for item
     */
    @Override
    public void saveItem(Item item, String[] categoriesIds) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();
        tx.begin();
        for (String categoryId : categoriesIds) {
            Category category = em.find(Category.class, Integer.parseInt(categoryId));
            item.addCategory(category);
        }
        em.persist(item);
        tx.commit();
        em.close();
    }

    /**
     * @param entity updated
     * @param <T>    result
     */
    @Override
    public <T> void update(T entity) {
        this.tx(entityManager -> entityManager.merge(entity));
    }

    private <T> T tx(final Function<EntityManager, T> command) {
        final EntityManager em = emf.createEntityManager();
        final EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            T rsl = command.apply(em);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
