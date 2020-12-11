package ru.job4j.dream.store;

import org.jetbrains.annotations.NotNull;
import ru.job4j.dream.model.*;

import java.util.Collection;

public interface Store {

    @NotNull Post findPostById(int id);

    @NotNull Candidate findCandidateById(int id);

    @NotNull User findUserByEmail(String email);

    @NotNull Collection<Post> findAllPosts();

    @NotNull Collection<Candidate> findAllCandidates();

    @NotNull Collection<User> findAllUsers();

    @NotNull Collection<City> findAllCities();

    void save(Post post);

    void save(Candidate candidate);

    void save(Picture picture);

    void save(User user);

    void deleteCandidate(int id);
}
