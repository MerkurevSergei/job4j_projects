package data;

import logic.model.*;

import java.util.*;

/**
 * The Data provider.
 *
 * @author Merkurev Sergei (merkurevsergei@yandex.ru)
 * @version 0.1
 * @since 0.1
 */
public final class DataMapper implements TroopFactory {

    /**
     * The improved group.
     */
    private final Set<Unit> improvedGroup;

    /**
     * The degraded group.
     */
    private final Set<Unit> degradedGroup;

    /**
     * Constructor.
     */
    public DataMapper() {
        this.improvedGroup = new HashSet<>();
        this.degradedGroup = new HashSet<>();
    }

    /**
     * @return improved group.
     */
    @Override
    public final Set<Unit> getImproved() {
        return improvedGroup;
    }

    /**
     * @return degraded group.
     */
    @Override
    public final Set<Unit> getDegraded() {
        return degradedGroup;
    }

    /**
     * @param type side type
     * @return Troop
     */
    @Override
    public Troop createTroop(WarSide type) {
        Troop res = null;
        int flag = new Random().nextInt(2);
        if (type == WarSide.GOOD && flag == 0) {
            res = createElvesTroop();
        } else if (type == WarSide.GOOD) {
            res = createMenTroop();
        } else if (type == WarSide.EVIL && flag == 0) {
            res = createOrcsTroop();
        } else if (type == WarSide.EVIL) {
            res = createUndeadTroop();
        }
        return res;
    }

    /**
     * @return elves troop
     */
    private Troop createElvesTroop() {
        Action actWizImprove = new ActionImprove("applied improve to", improvedGroup, degradedGroup);
        Action actWizDamage = new ActionDamage("attacks with magic", 10, improvedGroup, degradedGroup);
        Unit wizard = new UnitImpl(UnitRace.ELVES, UnitType.WIZARD, WarSide.GOOD, Arrays.asList(actWizImprove, actWizDamage));

        Action actArchShoot = new ActionDamage("shoot arrows at", 7, improvedGroup, degradedGroup);
        Action actArchDamage = new ActionDamage("attacks", 3, improvedGroup, degradedGroup);
        Unit archer1 = new UnitImpl(UnitRace.ELVES, UnitType.ARCHER, WarSide.GOOD, Arrays.asList(actArchShoot, actArchDamage));
        Unit archer2 = new UnitImpl(UnitRace.ELVES, UnitType.ARCHER, WarSide.GOOD, Arrays.asList(actArchShoot, actArchDamage));
        Unit archer3 = new UnitImpl(UnitRace.ELVES, UnitType.ARCHER, WarSide.GOOD, Arrays.asList(actArchShoot, actArchDamage));

        Action actWarrior = new ActionDamage("attacks with a sword", 15, improvedGroup, degradedGroup);
        Unit warrior1 = new UnitImpl(UnitRace.ELVES, UnitType.WARRIOR, WarSide.GOOD, Collections.singletonList(actWarrior));
        Unit warrior2 = new UnitImpl(UnitRace.ELVES, UnitType.WARRIOR, WarSide.GOOD, Collections.singletonList(actWarrior));
        Unit warrior3 = new UnitImpl(UnitRace.ELVES, UnitType.WARRIOR, WarSide.GOOD, Collections.singletonList(actWarrior));
        Unit warrior4 = new UnitImpl(UnitRace.ELVES, UnitType.WARRIOR, WarSide.GOOD, Collections.singletonList(actWarrior));

        return new TroopImpl(
                new ArrayList<>(Arrays.asList(wizard, archer1, archer2, archer3, warrior1, warrior2, warrior3, warrior4))
        );
    }


    /**
     * @return men troop
     */
    private Troop createMenTroop() {
        Action actWizImprove = new ActionImprove("applied improve to", improvedGroup, degradedGroup);
        Action actWizDamage = new ActionDamage("attacks with magic", 4, improvedGroup, degradedGroup);
        Unit wizard = new UnitImpl(UnitRace.MEN, UnitType.WIZARD, WarSide.GOOD, Arrays.asList(actWizImprove, actWizDamage));

        Action actBowShoot = new ActionDamage("shoot a сrossbow at", 5, improvedGroup, degradedGroup);
        Action actBowDamage = new ActionDamage("attacks", 3, improvedGroup, degradedGroup);
        Unit bowMan1 = new UnitImpl(UnitRace.MEN, UnitType.ARCHER, WarSide.GOOD, Arrays.asList(actBowShoot, actBowDamage));
        Unit bowMan2 = new UnitImpl(UnitRace.MEN, UnitType.ARCHER, WarSide.GOOD, Arrays.asList(actBowShoot, actBowDamage));
        Unit bowMan3 = new UnitImpl(UnitRace.MEN, UnitType.ARCHER, WarSide.GOOD, Arrays.asList(actBowShoot, actBowDamage));

        Action actWarrior = new ActionDamage("attacks with a sword", 18, improvedGroup, degradedGroup);
        Unit warrior1 = new UnitImpl(UnitRace.MEN, UnitType.WARRIOR, WarSide.GOOD, Collections.singletonList(actWarrior));
        Unit warrior2 = new UnitImpl(UnitRace.MEN, UnitType.WARRIOR, WarSide.GOOD, Collections.singletonList(actWarrior));
        Unit warrior3 = new UnitImpl(UnitRace.MEN, UnitType.WARRIOR, WarSide.GOOD, Collections.singletonList(actWarrior));
        Unit warrior4 = new UnitImpl(UnitRace.MEN, UnitType.WARRIOR, WarSide.GOOD, Collections.singletonList(actWarrior));

        return new TroopImpl(
                new ArrayList<>(Arrays.asList(wizard, bowMan1, bowMan2, bowMan3, warrior1, warrior2, warrior3, warrior4))
        );
    }

    /**
     * @return orcs troop
     */
    private Troop createOrcsTroop() {
        Action actShamImprove = new ActionImprove("applied improve to", improvedGroup, degradedGroup);
        Action actShamDegrade = new ActionDegrade("applied curses to", improvedGroup, degradedGroup);
        Unit shaman = new UnitImpl(UnitRace.ORCS, UnitType.SHAMAN, WarSide.EVIL, Arrays.asList(actShamImprove, actShamDegrade));

        Action actArchShoot = new ActionDamage("shoot arrows at", 3, improvedGroup, degradedGroup);
        Action actArchDamage = new ActionDamage("blade strike on", 2, improvedGroup, degradedGroup);
        Unit archer1 = new UnitImpl(UnitRace.ORCS, UnitType.ARCHER, WarSide.EVIL, Arrays.asList(actArchShoot, actArchDamage));
        Unit archer2 = new UnitImpl(UnitRace.ORCS, UnitType.ARCHER, WarSide.EVIL, Arrays.asList(actArchShoot, actArchDamage));
        Unit archer3 = new UnitImpl(UnitRace.ORCS, UnitType.ARCHER, WarSide.EVIL, Arrays.asList(actArchShoot, actArchDamage));

        Action actWarrior = new ActionDamage("attacks with a club", 20, improvedGroup, degradedGroup);
        Unit warrior1 = new UnitImpl(UnitRace.ORCS, UnitType.GOBLIN, WarSide.EVIL, Collections.singletonList(actWarrior));
        Unit warrior2 = new UnitImpl(UnitRace.ORCS, UnitType.GOBLIN, WarSide.EVIL, Collections.singletonList(actWarrior));
        Unit warrior3 = new UnitImpl(UnitRace.ORCS, UnitType.GOBLIN, WarSide.EVIL, Collections.singletonList(actWarrior));
        Unit warrior4 = new UnitImpl(UnitRace.ORCS, UnitType.GOBLIN, WarSide.EVIL, Collections.singletonList(actWarrior));

        return new TroopImpl(
                new ArrayList<>(Arrays.asList(shaman, archer1, archer2, archer3, warrior1, warrior2, warrior3, warrior4))
        );
    }

    /**
     * @return undead troop
     */
    private Troop createUndeadTroop() {
        Action actNecDamage = new ActionDamage("attacks with magic", 5, improvedGroup, degradedGroup);
        Action actNecDegrade = new ActionDegrade("applied curses to", improvedGroup, degradedGroup);
        Unit necromancer = new UnitImpl(UnitRace.UNDEAD, UnitType.NECROMANCER, WarSide.EVIL, Arrays.asList(actNecDamage, actNecDegrade));

        Action actArchShoot = new ActionDamage("shoot arrows at", 4, improvedGroup, degradedGroup);
        Action actArchDamage = new ActionDamage("attacks", 2, improvedGroup, degradedGroup);
        Unit hunter1 = new UnitImpl(UnitRace.UNDEAD, UnitType.HUNTER, WarSide.EVIL, Arrays.asList(actArchShoot, actArchDamage));
        Unit hunter2 = new UnitImpl(UnitRace.UNDEAD, UnitType.HUNTER, WarSide.EVIL, Arrays.asList(actArchShoot, actArchDamage));
        Unit hunter3 = new UnitImpl(UnitRace.UNDEAD, UnitType.HUNTER, WarSide.EVIL, Arrays.asList(actArchShoot, actArchDamage));

        Action actWarrior = new ActionDamage("attacks with a spear", 18, improvedGroup, degradedGroup);
        Unit zombies1 = new UnitImpl(UnitRace.UNDEAD, UnitType.ZOMBIES, WarSide.EVIL, Collections.singletonList(actWarrior));
        Unit zombies2 = new UnitImpl(UnitRace.UNDEAD, UnitType.ZOMBIES, WarSide.EVIL, Collections.singletonList(actWarrior));
        Unit zombies3 = new UnitImpl(UnitRace.UNDEAD, UnitType.ZOMBIES, WarSide.EVIL, Collections.singletonList(actWarrior));
        Unit zombies4 = new UnitImpl(UnitRace.UNDEAD, UnitType.ZOMBIES, WarSide.EVIL, Collections.singletonList(actWarrior));

        return new TroopImpl(
                new ArrayList<>(Arrays.asList(necromancer, hunter1, hunter2, hunter3, zombies1, zombies2, zombies3, zombies4))
        );
    }
}
