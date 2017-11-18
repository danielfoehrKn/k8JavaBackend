package com.curious.daniel.entities;

import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JSessionLog extends AbstractSessionLog {
    private static final Logger log = LoggerFactory.getLogger("org.eclipse.persistence.Eclipselink");

    public SLF4JSessionLog() {
    }

    public boolean shouldLog(int level) {
        return true;
    }

    public void log(SessionLogEntry sessionLogEntry) {
        int level = sessionLogEntry.getLevel();
        switch(level) {
            case 1:
                log.trace(this.formatMessage(sessionLogEntry), sessionLogEntry.getException());
                break;
            case 2:
            case 3:
                log.debug(this.formatMessage(sessionLogEntry), sessionLogEntry.getException());
                break;
            case 4:
            case 5:
                log.info(this.formatMessage(sessionLogEntry), sessionLogEntry.getException());
                break;
            case 6:
                log.warn(this.formatMessage(sessionLogEntry), sessionLogEntry.getException());
                break;
            case 7:
                log.error(this.formatMessage(sessionLogEntry), sessionLogEntry.getException());
        }

    }
}

