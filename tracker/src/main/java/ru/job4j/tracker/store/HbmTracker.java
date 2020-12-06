package ru.job4j.tracker.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.job4j.tracker.model.Item;

import java.util.List;

@Component
@Scope("prototype")
public class HbmTracker implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public void init() {
    }

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        boolean result = false;
        Session session = sf.openSession();
        session.beginTransaction();
        Item itemOld = session.get(Item.class, id);
        if (itemOld.getId().equals(id)) {
            session.detach(itemOld);
            item.setId(id);
            session.update(item);
            result = true;
        }
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public boolean delete(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        if (item != null) {
            session.delete(item);
        }
        session.getTransaction().commit();
        session.close();
        return item != null;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Item> findAll() {
        Session session = sf.openSession();
        Query query = session.createQuery("from Item");
        List list = query.list();
        session.close();
        return list;
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        Query query = session.createQuery("from Item where name = :key");
        query.setParameter("key", key);
        List list = query.list();
        session.close();
        return list;
    }

    @Override
    public Item findById(Integer id) {
        Session session = sf.openSession();
        Item item = session.get(Item.class, id);
        session.close();
        return item;
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
