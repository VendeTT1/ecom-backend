-- This file allows us to load static data into the test database before tests are run.

-- Passwords are in the format: Password<UserLetter>123. Unless specified otherwise.
-- Encrypted using https://www.javainuse.com/onlineBcrypt
INSERT INTO user (email, firstname, lastname, password, username, email_verified)
    VALUES ('UserA@junit.com', 'UserA-FirstName', 'UserA-LastName', '$2a$10$e.UjbsYP28ih/E1wVolbbuGM8.4Lq2IUNRwC/egPQ3rZgPg89ltp6', 'UserA', true)
    , ('UserB@junit.com', 'UserB-FirstName', 'UserB-LastName', '$2a$10$OTZUooL8Fkqq3hVw1tqvIeCNbWnyg9hsubyX5qmuP2ULtbncx.DD.', 'UserB', false);