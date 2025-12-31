--
-- Database: brainutrain (PostgreSQL version)
--

-- Table: memorizings
CREATE TABLE memorizings (
    id_memorizing BIGSERIAL PRIMARY KEY,
    level VARCHAR(255) NOT NULL,
    score BIGINT NOT NULL,
    start_time TIMESTAMP NOT NULL,
    type VARCHAR(255) NOT NULL,
    user_id_user BIGINT DEFAULT NULL
);

-- Table: reading_questions
CREATE TABLE reading_questions (
    id_reading_question BIGSERIAL PRIMARY KEY,
    answer BOOLEAN NOT NULL,
    question TEXT NOT NULL,
    reading_text_id_reading_text BIGINT NOT NULL
);

-- Table: reading_results
CREATE TABLE reading_results (
    id_reading_result BIGSERIAL PRIMARY KEY,
    level VARCHAR(255) NOT NULL,
    score DOUBLE PRECISION NOT NULL,
    start_time TIMESTAMP NOT NULL,
    time REAL NOT NULL,
    type VARCHAR(255) NOT NULL,
    text_id_reading_text BIGINT DEFAULT NULL,
    user_id_user BIGINT DEFAULT NULL
);

-- Table: reading_texts
CREATE TABLE reading_texts (
    id_reading_text BIGSERIAL PRIMARY KEY,
    level VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    title VARCHAR(255) NOT NULL
);

-- Table: reports
CREATE TABLE reports (
    id_report BIGSERIAL PRIMARY KEY,
    active BOOLEAN NOT NULL,
    date TIMESTAMP NOT NULL,
    email VARCHAR(45) DEFAULT NULL,
    text TEXT DEFAULT NULL,
    title VARCHAR(45) DEFAULT NULL,
    user_id_user BIGINT DEFAULT NULL
);

-- Table: roles
CREATE TABLE roles (
    id_role BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL
);

-- Table: settings
CREATE TABLE settings (
    id_setting BIGSERIAL PRIMARY KEY,
    font_size VARCHAR(255) NOT NULL,
    theme VARCHAR(255) NOT NULL,
    user_id_user BIGINT NOT NULL
);

-- Table: users
CREATE TABLE users (
    id_user BIGSERIAL PRIMARY KEY,
    email VARCHAR(45) DEFAULT NULL,
    is_active BOOLEAN DEFAULT NULL,
    is_email_confirmed BOOLEAN DEFAULT NULL,
    login VARCHAR(45) DEFAULT NULL,
    password VARCHAR(120) DEFAULT NULL,
    CONSTRAINT uk_users_email UNIQUE (email),
    CONSTRAINT uk_users_login UNIQUE (login)
);

-- Table: user_roles
CREATE TABLE user_roles (
    user_id_user BIGINT NOT NULL,
    role_id_role BIGINT NOT NULL,
    PRIMARY KEY (user_id_user, role_id_role)
);

-- Table: validation_codes
CREATE TABLE validation_codes (
    id_validation_code BIGSERIAL PRIMARY KEY,
    code VARCHAR(255) DEFAULT NULL,
    purpose VARCHAR(255) NOT NULL,
    was_used BOOLEAN NOT NULL,
    user_id_user BIGINT NOT NULL
);

-- Table: writing_lessons
CREATE TABLE writing_lessons (
    id_writing_lesson BIGSERIAL PRIMARY KEY,
    generated_characters VARCHAR(10) NOT NULL,
    name VARCHAR(255) NOT NULL,
    number INTEGER NOT NULL,
    module_id_writing_module BIGINT DEFAULT NULL,
    CONSTRAINT uk_writing_lessons_characters UNIQUE (generated_characters),
    CONSTRAINT uk_writing_lessons_name UNIQUE (name),
    CONSTRAINT uk_writing_lessons_number UNIQUE (number)
);

-- Table: writing_lesson_results
CREATE TABLE writing_lesson_results (
    id_writing_lesson_result BIGSERIAL PRIMARY KEY,
    number_of_attempts INTEGER NOT NULL,
    number_of_typed_letters INTEGER NOT NULL,
    score DOUBLE PRECISION NOT NULL,
    start_time TIMESTAMP NOT NULL,
    time REAL NOT NULL,
    user_id_user BIGINT DEFAULT NULL,
    writing_lesson_id_writing_lesson BIGINT DEFAULT NULL
);

-- Table: writing_modules
CREATE TABLE writing_modules (
    id_writing_module BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    number INTEGER NOT NULL
);

-- Table: writing_texts
CREATE TABLE writing_texts (
    id_writing_text BIGSERIAL PRIMARY KEY,
    level VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    title VARCHAR(255) NOT NULL
);

-- Table: writing_text_results
CREATE TABLE writing_text_results (
    id_writing_text_result BIGSERIAL PRIMARY KEY,
    score DOUBLE PRECISION NOT NULL,
    start_time TIMESTAMP NOT NULL,
    time REAL NOT NULL,
    typed_text TEXT NOT NULL,
    text_id_writing_text BIGINT DEFAULT NULL,
    user_id_user BIGINT DEFAULT NULL
);

--
-- Indexes
--
CREATE INDEX idx_memorizings_user ON memorizings(user_id_user);
CREATE INDEX idx_reading_questions_text ON reading_questions(reading_text_id_reading_text);
CREATE INDEX idx_reading_results_text ON reading_results(text_id_reading_text);
CREATE INDEX idx_reading_results_user ON reading_results(user_id_user);
CREATE INDEX idx_reports_user ON reports(user_id_user);
CREATE INDEX idx_settings_user ON settings(user_id_user);
CREATE INDEX idx_user_roles_role ON user_roles(role_id_role);
CREATE INDEX idx_validation_codes_user ON validation_codes(user_id_user);
CREATE INDEX idx_writing_lessons_module ON writing_lessons(module_id_writing_module);
CREATE INDEX idx_writing_lesson_results_user ON writing_lesson_results(user_id_user);
CREATE INDEX idx_writing_lesson_results_lesson ON writing_lesson_results(writing_lesson_id_writing_lesson);
CREATE INDEX idx_writing_text_results_text ON writing_text_results(text_id_writing_text);
CREATE INDEX idx_writing_text_results_user ON writing_text_results(user_id_user);

--
-- Foreign Keys
--
ALTER TABLE memorizings
    ADD CONSTRAINT fk_memorizings_user FOREIGN KEY (user_id_user) REFERENCES users (id_user) ON DELETE CASCADE;

ALTER TABLE reading_questions
    ADD CONSTRAINT fk_reading_questions_text FOREIGN KEY (reading_text_id_reading_text) REFERENCES reading_texts (id_reading_text) ON DELETE CASCADE;

ALTER TABLE reading_results
    ADD CONSTRAINT fk_reading_results_text FOREIGN KEY (text_id_reading_text) REFERENCES reading_texts (id_reading_text);
ALTER TABLE reading_results
    ADD CONSTRAINT fk_reading_results_user FOREIGN KEY (user_id_user) REFERENCES users (id_user) ON DELETE CASCADE;

ALTER TABLE reports
    ADD CONSTRAINT fk_reports_user FOREIGN KEY (user_id_user) REFERENCES users (id_user) ON DELETE CASCADE;

ALTER TABLE settings
    ADD CONSTRAINT fk_settings_user FOREIGN KEY (user_id_user) REFERENCES users (id_user) ON DELETE CASCADE;

ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id_role) REFERENCES roles (id_role);
ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id_user) REFERENCES users (id_user);

ALTER TABLE validation_codes
    ADD CONSTRAINT fk_validation_codes_user FOREIGN KEY (user_id_user) REFERENCES users (id_user) ON DELETE CASCADE;

ALTER TABLE writing_lessons
    ADD CONSTRAINT fk_writing_lessons_module FOREIGN KEY (module_id_writing_module) REFERENCES writing_modules (id_writing_module) ON DELETE CASCADE;

ALTER TABLE writing_lesson_results
    ADD CONSTRAINT fk_writing_lesson_results_lesson FOREIGN KEY (writing_lesson_id_writing_lesson) REFERENCES writing_lessons (id_writing_lesson) ON DELETE CASCADE;
ALTER TABLE writing_lesson_results
    ADD CONSTRAINT fk_writing_lesson_results_user FOREIGN KEY (user_id_user) REFERENCES users (id_user) ON DELETE CASCADE;

ALTER TABLE writing_text_results
    ADD CONSTRAINT fk_writing_text_results_text FOREIGN KEY (text_id_writing_text) REFERENCES writing_texts (id_writing_text) ON DELETE CASCADE;
ALTER TABLE writing_text_results
    ADD CONSTRAINT fk_writing_text_results_user FOREIGN KEY (user_id_user) REFERENCES users (id_user) ON DELETE CASCADE;
