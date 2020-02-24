DROP TABLE IF EXISTS User_Answer;
DROP TABLE IF EXISTS Correct_Answer;
DROP TABLE IF EXISTS Option;
DROP TABLE IF EXISTS Question;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS User;
DROP SEQUENCE IF EXISTS GLOBAL_SEQ;
CREATE SEQUENCE GLOBAL_SEQ START WITH 100000;

CREATE TABLE IF NOT EXISTS User
(
    id       INTEGER   DEFAULT nextval('GLOBAL_SEQ') PRIMARY KEY,
    date     TIMESTAMP DEFAULT now(),
    username VARCHAR(100)           NOT NULL,
    email    VARCHAR(50)            NOT NULL,
    password VARCHAR(100)           NOT NULL,
    name     VARCHAR(255)           NOT NULL,
    enabled  BOOLEAN   DEFAULT TRUE NOT NULL
);
CREATE UNIQUE INDEX user_unique_email_idx ON User (username, email);

CREATE TABLE IF NOT EXISTS Role
(
    user_id INTEGER     NOT NULL,
    role    VARCHAR(50) NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES User (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Question
(
    id            INTEGER   DEFAULT nextval('GLOBAL_SEQ') PRIMARY KEY,
    date          TIMESTAMP DEFAULT now(),
    question_item VARCHAR(1000) NOT NULL
);

CREATE TABLE IF NOT EXISTS Option
(
    id          INTEGER   DEFAULT nextval('GLOBAL_SEQ') PRIMARY KEY,
    date        TIMESTAMP DEFAULT now(),
    question_id INTEGER      NOT NULL,
    option_item VARCHAR(255) NOT NULL,
    FOREIGN KEY (question_id) REFERENCES Question (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX question_unique_option_idx ON Option (question_id, option_item);

CREATE TABLE IF NOT EXISTS Correct_Answer
(
    id          INTEGER   DEFAULT nextval('GLOBAL_SEQ') PRIMARY KEY,
    date        TIMESTAMP DEFAULT now(),
    question_id INTEGER      NOT NULL,
    answer      VARCHAR(255) NOT NULL,
    FOREIGN KEY (question_id) REFERENCES Question (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX correct_unique_answer_idx ON Correct_Answer (question_id, answer);

CREATE TABLE IF NOT EXISTS User_Answer
(
    id          INTEGER   DEFAULT nextval('GLOBAL_SEQ') PRIMARY KEY,
    date        TIMESTAMP DEFAULT now(),
    user_id     INTEGER NOT NULL,
    question_id INTEGER NOT NULL,
    answer      VARCHAR(255),
    correct     BOOLEAN NOT NULL,
    FOREIGN KEY (user_id) REFERENCES User (id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES Question (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX user_unique_question_idx ON User_Answer (user_id, question_id);