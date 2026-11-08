PRAGMA foreign_keys = ON;

CREATE TABLE ACCOUNT (
    account_id INTEGER PRIMARY KEY AUTOINCREMENT,
    role VARCHAR(20) NOT NULL
        CHECK (role IN ('ADMIN', 'PARTICIPANT'))
);

CREATE TABLE PROFILE (
    profile_id INTEGER PRIMARY KEY AUTOINCREMENT,
    account_id INTEGER NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL CHECK (age >= 0),

    FOREIGN KEY (account_id)
        REFERENCES ACCOUNT(account_id)
        ON DELETE CASCADE
);

CREATE TABLE USER_CREDENTIALS (
    account_id INTEGER PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,

    FOREIGN KEY (account_id)
        REFERENCES ACCOUNT(account_id)
        ON DELETE CASCADE
);

CREATE TABLE CONFERENCE (
    conference_id INTEGER PRIMARY KEY AUTOINCREMENT,
    title VARCHAR(255) NOT NULL,
    capacity INTEGER NOT NULL CHECK (capacity > 0),
    start_datetime DATETIME NOT NULL,
    end_datetime DATETIME NOT NULL,
    venue VARCHAR(255),

    CHECK (
        julianday(end_datetime) > julianday(start_datetime)
    )
);

CREATE TABLE REGISTRATION (
    registration_id INTEGER PRIMARY KEY AUTOINCREMENT,
    account_id INTEGER NOT NULL,
    conference_id INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL
        CHECK (status IN ('CONFIRMED', 'WAITLISTED')),
    registration_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    waitlist_position INTEGER,

    FOREIGN KEY (account_id)
        REFERENCES ACCOUNT(account_id),

    FOREIGN KEY (conference_id)
        REFERENCES CONFERENCE(conference_id),

    UNIQUE (account_id, conference_id),

    UNIQUE (conference_id, waitlist_position),

    CHECK (
        (status = 'WAITLISTED' AND waitlist_position > 0)
        OR
        (status = 'CONFIRMED' AND waitlist_position IS NULL)
    )
);

CREATE INDEX idx_registration_conference
ON REGISTRATION(conference_id);

CREATE INDEX idx_registration_account
ON REGISTRATION(account_id);