package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;
/**
 * Implementation of DeathNote interface.
 */

public class DeathNoteImpl implements DeathNote {

    private static final int TIME_SETCAUSE = 40;
    private static final int TIME_SETDETAILS = 6040;

    private final Map<String, Death> humans;
    private String lastNameWritten;

    /**
     * Constructor of DeathNoteImpl class.
     */
    public DeathNoteImpl() {
        this.humans = new HashMap<>();
        this.lastNameWritten = null;
    }

    /**
     * Returns the rule with the given number.
     *
     * @param ruleNumber the number of the rule to return. The first rule has number one
     * @return the rule with the given number
     * @throws IllegalArgumentException if the given rule number is smaller than 1 or larger
     *     than the number of rules
     */
    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("The rule number is lower than 1 or greater than " + RULES.size());
        } else {
            return RULES.get(ruleNumber);
        }
    }

    /**
     * Writes a human's name into the DeathNote.
     * 
     * @param name the human's name
     * @throws NullPointerException if name is null
     */
    @Override
    public void writeName(final String name) {
        if (Objects.isNull(name)) {
            throw new NullPointerException("the param passed is null"); //NOPMD
        }

        this.humans.put(name, new Death(System.currentTimeMillis()));
        this.lastNameWritten = name;
    }

    /**
     * If the cause of death is written within the next 40 milliseconds of writing the person's
     * name, it will happen.
     *
     * @param cause the cause of the human's death
     * @return true if the cause was written within 40 milliseconds, false otherwise
     * @throws IllegalStateException if there is no name written in this DeathNote,
     *     or the cause is null
     */
    @Override
    public boolean writeDeathCause(final String cause) {
        if (Objects.isNull(this.lastNameWritten) || Objects.isNull(cause)) {
            throw new IllegalStateException("The name or the cause is null, not valid");
        }
        return this.humans.get(this.lastNameWritten).changeCause(cause);
    }

    /**
     * After writing the cause of death, details of the death should be written in the next
     * 6 seconds and 40 milliseconds.
     *
     * @param details the details of the human's death
     * @return true if the details were written within 6 seconds and 40 milliseconds, false otherwise
     * @throws IllegalStateException if there is no name written in this DeathNote,
     *     or the details are null
     */
    @Override
    public boolean writeDetails(final String details) {
        if (Objects.isNull(this.lastNameWritten) || Objects.isNull(details)) {
            throw new IllegalStateException("The name or the details is null, not valid");
        }
        return this.humans.get(this.lastNameWritten).changeDetails(details);
    }

    /**
     * Provides the cause of death of the person with the given name.
     *
     * @param name the name of the person whose death cause to return
     * @return the death cause of the person with the given name.
     *     If the cause of death is not specified, the method will return "heart attack".
     * @throws IllegalArgumentException if the provider name is not written in this DeathNote
     */
    @Override
    public String getDeathCause(final String name) {
        if (!this.humans.containsKey(name)) {
            throw new IllegalArgumentException("the name is not written in the deathnote yet or the param is null");
        }
        return this.humans.get(name).getCause();
    }

    /**
     * Provides the details of the death of the person with the given name.
     *
     * @param name the name of the person whose death cause to return
     * @return the death details of the person with the given name,
     *     or an empty string if no details have been provided.
     * @throws IllegalArgumentException if the provider name is not written in this DeathNote.
     */
    @Override
    public String getDeathDetails(final String name) {
        if (!this.humans.containsKey(name)) {
            throw new IllegalArgumentException("the name is not written in the deathnote yet or the param is null");
        }
        return this.humans.get(name).getDetails();
    }

    /**
     * Checks if the given name is written in this DeathNote.
     *
     * @param name the name of the person
     * @return true if the given name is written in this DeathNote, false otherwise
     */
    @Override
    public boolean isNameWritten(final String name) {
        return this.humans.containsKey(name);
    }

    private static class Death {
        private String cause;
        private String details;
        private final long timeNameWritten;
        private long timeCauseWritten;

        Death(final long timeNameWritten) {
            this.cause = "heart attack";
            this.details = "";
            this.timeNameWritten = timeNameWritten;
            this.timeCauseWritten = 0;
        }

        public String getCause() { 
            return this.cause; 
        }

        public String getDetails() { 
            return this.details; 
        }

        public boolean changeCause(final String newCause) { 
            final long now = System.currentTimeMillis();
            if (now - this.timeNameWritten <= TIME_SETCAUSE) {
                this.cause = newCause;
                this.timeCauseWritten = now;
                return true;
            }

            return false;
        }

        public boolean changeDetails(final String newDetails) { 
            final long now = System.currentTimeMillis();
            final long tmpTime;
            if (this.timeCauseWritten > 0) {
                tmpTime = this.timeCauseWritten;
            } else {
                tmpTime = this.timeNameWritten;
            }
            if (now - tmpTime <= TIME_SETDETAILS) {
                this.details = newDetails;
                return true;
            }
            return false;
        }
    }
}
