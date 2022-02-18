package com.github.merkurevsergei.repository;

import com.github.merkurevsergei.model.Accident;
import com.github.merkurevsergei.model.AccidentRule;
import com.github.merkurevsergei.model.AccidentType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * The {@code AccidentJdbcTemplate} is repository for {@code Accident} and
 * related entity as {@code AccidentRule} and {@code AccidentType}.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
@Repository
public class AccidentJdbcTemplate {
    private final static String FIND_ALL_ACCIDENTS =
            "select acc.id as id, acc.name as name, acc.text as text, acc.address as address, "
                    + "t.id as type_id, t.name as type_name, "
                    + "mtmr.rule_id as rule_id, r.name as rule_name "
                    + "from accidents acc "
                    + "left join accident_types t on acc.type_id = t.id "
                    + "left join accidents_accident_rules mtmr on acc.id = mtmr.accident_id "
                    + "left join accident_rules r on mtmr.rule_id = r.id ";
    private final JdbcTemplate jdbc;

    /**
     * @param jdbc injected is entity for work with database
     */
    public AccidentJdbcTemplate(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Accident> findAllAccidents() {
        return jdbc.query(
                FIND_ALL_ACCIDENTS,
                new AccidentExtractor()
        );
    }

    /**
     * Find {@code Accident} by id, or return empty Optional.
     *
     * @param id of {@code Accident}
     * @return {@code Optional<Accident>}
     */
    public Optional<Accident> findAccidentById(int id) {
        Optional<Accident> accident = Optional.empty();
        final List<Accident> accidents = jdbc.query(
                FIND_ALL_ACCIDENTS
                        + "where acc.id = ?",
                new Object[]{id},
                new AccidentExtractor()
        );
        if (accidents != null) {
            accident = accidents.stream().findFirst();
        }
        return accident;
    }

    /**
     * Create {@code Accident}.
     *
     * @param accident for created
     * @return created {@code Optional<Accident>}
     */
    public Optional<Accident> create(Accident accident) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbc.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into accidents (name, text, address, type_id) "
                                    + "values (?, ?, ?, ?)",
                            new String[]{"id"}
                    );
                    ps.setString(1, accident.getName());
                    ps.setString(2, accident.getText());
                    ps.setString(3, accident.getAddress());
                    ps.setInt(4, accident.getType().getId());
                    return ps;
                },
                key);
        List<Object[]> batch = new ArrayList<>();
        for (AccidentRule rule : accident.getRules()) {
            Object[] values = new Object[]{
                    key.getKey(), rule.getId()};
            batch.add(values);
        }
        jdbc.batchUpdate(
                "insert into accidents_accident_rules (accident_id, rule_id) values (?, ?)",
                batch);
        final Number keyNum = Optional.ofNullable(key.getKey()).orElse(0);
        return findAccidentById(keyNum.intValue());
    }

    /**
     * Update {@code Accident}.
     *
     * @param accident for updated
     * @return updated {@code Optional<Accident>}
     */
    public Optional<Accident> update(Accident accident) {
        jdbc.update("update accidents set name = ?, text = ?, address = ?, type_id = ? where id =?",
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getType().getId(),
                accident.getId());
        jdbc.update("delete from accidents_accident_rules where accident_id = ?", accident.getId());
        List<Object[]> batch = new ArrayList<>();
        for (AccidentRule rule : accident.getRules()) {
            Object[] values = new Object[]{
                    accident.getId(), rule.getId()};
            batch.add(values);
        }
        jdbc.batchUpdate(
                "insert into accidents_accident_rules (accident_id, rule_id) values (?, ?)",
                batch);
        return findAccidentById(accident.getId());
    }

    /**
     * Find all {@code AccidentType} from database.
     *
     * @return {@code Collection<AccidentType>}
     */
    public Collection<AccidentType> findAllTypes() {
        return jdbc.query(
                "select id, name from accident_types",
                (rs, rowNum) -> AccidentType.of(rs.getInt("id"), rs.getString("name"))
        );
    }

    /**
     * Find all {@code AccidentRule} from database.
     *
     * @return {@code Collection<AccidentRule>}
     */
    public Collection<AccidentRule> findAllRules() {
        return jdbc.query(
                "select id, name from accident_rules",
                (rs, rowNum) -> AccidentRule.of(rs.getInt("id"), rs.getString("name"))
        );
    }

    /**
     * Extract {@code Accident} from ResultSet from Query.
     */
    private static class AccidentExtractor implements ResultSetExtractor<ArrayList<Accident>> {
        @Override
        public ArrayList<Accident> extractData(ResultSet rs)
                throws SQLException, DataAccessException {
            final LinkedHashMap<Integer, Accident> accs = new LinkedHashMap<>();
            while (rs.next()) {
                final int id = rs.getInt("id");
                accs.putIfAbsent(id,
                        new Accident(
                                id,
                                rs.getString("name"),
                                rs.getString("text"),
                                rs.getString("address"),
                                AccidentType.of(
                                        rs.getInt("type_id"),
                                        rs.getString("type_name")
                                ))
                );
                final int rule_id = rs.getInt("rule_id");
                if (rule_id != 0) {
                    accs.get(id).addRule(
                            AccidentRule.of(rs.getInt("rule_id"), rs.getString("rule_name"))
                    );
                }
            }
            return new ArrayList<>(accs.values());
        }
    }
}