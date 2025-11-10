package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private static final int ZERO = 0;
    private static final int NEGATIVE_NUMBER = -1;
    private static final String NAME = "Mattia"; 
    private static final String RANDOM = "Alessio";
    private static final String EMPTY_STRING = "";
    private static final String HEART_ATTACK = "heart attack";
    private static final String KARTING_ACCIDENT = "karting accident";
    private static final String POISONED = "poisoned";
    private static final String DETAIL = "run for too long";
    private static final int MILLIS_100 = 100;
    private static final int MILLIS_6100 = 6100;

    private DeathNote deathNote;

    @BeforeEach
    void set() {
        this.deathNote = new DeathNoteImpl();
    }

    @Test
    void testNegativeOrZeroRule() {
        try {
            this.deathNote.getRule(ZERO);
        } catch (final IllegalArgumentException e) {
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
            assertTrue(Objects.nonNull(e.getMessage()));
        }

        try {
            this.deathNote.getRule(NEGATIVE_NUMBER);
        } catch (final IllegalArgumentException e) {
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
            assertTrue(Objects.nonNull(e.getMessage()));
        }
    }

    @Test
    void testNoAnyNullOrEmptyRule() {
        for (final String rule: DeathNote.RULES) {
            assertFalse(rule.isBlank());
            assertFalse(Objects.isNull(rule));
        }
    }

    @Test
    void testHumanWrittenDie() {
        assertFalse(this.deathNote.isNameWritten(NAME));
        this.deathNote.writeName(NAME);
        assertTrue(this.deathNote.isNameWritten(NAME));
        assertFalse(this.deathNote.isNameWritten(RANDOM));
        assertFalse(this.deathNote.isNameWritten(EMPTY_STRING));
    }

    @Test
    void testCauseOfDeath() {
        try {
            this.deathNote.writeDeathCause(HEART_ATTACK);
        } catch (final IllegalStateException e) {
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
            assertTrue(Objects.nonNull(e.getMessage()));

            this.deathNote.writeName(NAME);
            assertEquals(HEART_ATTACK, this.deathNote.getDeathCause(NAME));
            this.deathNote.writeName(RANDOM);
            assertTrue(this.deathNote.writeDeathCause(KARTING_ACCIDENT));
            assertEquals(KARTING_ACCIDENT, this.deathNote.getDeathCause(RANDOM));

            try {
                Thread.sleep(MILLIS_100);
            } catch (final InterruptedException t) {
                assertTrue(Objects.nonNull(t.getMessage()));
            }

            this.deathNote.writeDeathCause(POISONED);
            assertEquals(KARTING_ACCIDENT, this.deathNote.getDeathCause(RANDOM));
        }
    }

    @Test
    void testWritingDeathDetails() {
        try {
            this.deathNote.writeDetails(DETAIL);
        } catch (final IllegalStateException e) {
            this.deathNote.writeName(NAME);
            assertTrue(this.deathNote.getDeathDetails(NAME).isEmpty());
            assertTrue(this.deathNote.writeDetails(DETAIL));
            assertEquals(DETAIL, this.deathNote.getDeathDetails(NAME));
            this.deathNote.writeName(RANDOM);

            try {
                Thread.sleep(MILLIS_6100);
            } catch (final InterruptedException t) {
                assertTrue(Objects.nonNull(t.getMessage()));
            }

            this.deathNote.writeDetails(DETAIL);
            assertTrue(this.deathNote.getDeathDetails(RANDOM).isEmpty());
        }
    }
}
