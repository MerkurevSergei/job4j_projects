package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.City;
import ru.job4j.dream.model.Post;

import java.time.LocalDateTime;

public class PsqlMain {
    public static void main(String[] args) {

        Store store = PsqlStore.instOf();

        // POSTS
        store.save(new Post(0, "Java Job", null, null));
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }

        Post postById = store.findPostById(6);
        System.out.println(postById.getName() + " | " + postById.getDescription());

        store.save(new Post(6, "Java Job2", "Test", LocalDateTime.now()));
        postById = store.findPostById(6);
        System.out.println(postById.getName() + " | " + postById.getDescription());

        // CANDIDATES
        store.save(new Candidate(0, "Java DEV", "1", new City(0, "")));
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }

        Candidate candidateById = store.findCandidateById(1);
        System.out.println(candidateById.getName());

        store.save(new Candidate(1, "Java DEV3", "1", new City(0, "")));
        candidateById = store.findCandidateById(1);
        System.out.println(candidateById.getName());
    }
}
