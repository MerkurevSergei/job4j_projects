package ru.job4j.dream.store;

import org.jetbrains.annotations.NotNull;
import ru.job4j.dream.model.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {

    private static final MemStore INST = new MemStore();

    private static final AtomicInteger POST_ID = new AtomicInteger(4);
    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);
    private static final AtomicInteger PICTURE_ID = new AtomicInteger(4);
    private static final AtomicInteger USER_ID = new AtomicInteger(4);

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private final Map<Integer, Picture> pictures = new ConcurrentHashMap<>();

    private final Map<Integer, User> users = new ConcurrentHashMap<>();

    private final Map<Integer, City> cities = new ConcurrentHashMap<>();

    private MemStore() {
        posts.put(1,
                new Post(1, "Junior Java Job", "15 year xp", LocalDateTime.of(2016, 1, 1, 9, 5)));
        posts.put(2,
                new Post(2, "Middle Java Job", "5 year xp", LocalDateTime.of(2018, 3, 5, 5, 15)));
        posts.put(3,
                new Post(3, "Senior Java Job", "2 year xp", LocalDateTime.of(2017, 7, 7, 7, 7)));
//        candidates.put(1, new Candidate(1, "Junior Java"));
//        candidates.put(2, new Candidate(2, "Middle Java"));
//        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public static MemStore instOf() {
        return INST;
    }

    @Override
    public @NotNull Collection<Post> findAllPosts() {
        return posts.values();
    }

    @Override
    public @NotNull Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    @Override
    public @NotNull Collection<User> findAllUsers() {
        return users.values();
    }

    @Override
    public @NotNull Collection<City> findAllCities() {
        return cities.values();
    }

    @Override
    public @NotNull Post findPostById(int id) {
        return posts.get(id);
    }

    @Override
    public @NotNull Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    @Override
    public @NotNull User findUserByEmail(String email) {
        User user = new User();
        email = Optional.ofNullable(email).orElse("");
        for (User u: users.values()) {
            if (email.equals(user.getEmail())) {
                user = u;
                break;
            }
        }
        return user;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public void save(Picture picture) {
        if (picture.getId() == 0) {
            picture.setId(PICTURE_ID.incrementAndGet());
        }
        pictures.put(picture.getId(), picture);
    }

    @Override
    public void save(User user) {
        if (user.getId() == 0) {
            user.setId(PICTURE_ID.incrementAndGet());
        }
        users.put(user.getId(), user);
    }

    @Override
    public void deleteCandidate(int id) {
        candidates.remove(id);
    }
}
