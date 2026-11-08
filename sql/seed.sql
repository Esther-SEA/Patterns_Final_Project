PRAGMA foreign_keys = ON;

INSERT INTO ACCOUNT (account_id, role)
VALUES
    (1, 'ADMIN'),
    (2, 'PARTICIPANT'),
    (3, 'PARTICIPANT'),
    (4, 'PARTICIPANT');

INSERT INTO PROFILE (profile_id, account_id, full_name, age)
VALUES
    (1, 1, 'System Administrator', 35),
    (2, 2, 'Alex Martin', 22),
    (3, 3, 'Sam Taylor', 27),
    (4, 4, 'Jordan Smith', 24);

INSERT INTO USER_CREDENTIALS
    (account_id, email, password_hash)
VALUES
    (1, 'admin@example.com',
    'pbkdf2_sha256$120000$00112233445566778899aabbccddeeff$096ce3d769ae8c9e7b0cb059050d419d6555d94d8a83911f8d25eb3f0aa97155'),

    (2, 'alex@example.com',
    'pbkdf2_sha256$120000$102132435465768798a9bacbdcedfe0f$950eab538a510f66b6a160c0b8e1d79fe273ae2e3436db0f08a88e182eedf78c'),

    (3, 'sam@example.com',
    'pbkdf2_sha256$120000$ffeeddccbbaa99887766554433221100$ea230ccca591a73545613cfb0243bc465462384b7b44aba8651b87bd91f9add6'),

    (4, 'jordan@example.com',
    'pbkdf2_sha256$120000$102132435465768798a9bacbdcedfe0f$950eab538a510f66b6a160c0b8e1d79fe273ae2e3436db0f08a88e182eedf78c');

INSERT INTO CONFERENCE
    (conference_id, title, capacity,
     start_datetime, end_datetime, venue)
VALUES
    (1, 'Technology and Innovation Summit',
     100, '2027-03-10 09:00:00',
     '2027-03-10 17:00:00',
     'Montreal Convention Centre'),

    (2, 'Cybersecurity and Data Protection',
     60, '2027-04-15 10:00:00',
     '2027-04-15 16:00:00',
     'Science Campus'),

    (3, 'Software Development Workshop',
     30, '2027-05-20 09:30:00',
     '2027-05-20 12:30:00',
     'Room 204');

INSERT INTO REGISTRATION
    (registration_id, account_id, conference_id,
     status, registration_date, waitlist_position)
VALUES
    (1, 2, 1, 'CONFIRMED',
     '2027-01-05 10:00:00', NULL),