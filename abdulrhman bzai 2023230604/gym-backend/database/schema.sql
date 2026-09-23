-- Gym database schema (MySQL / MariaDB)
-- Spring Boot (Hibernate) creates these tables automatically on startup

CREATE DATABASE IF NOT EXISTS gym_db;
USE gym_db;

-- Price list
CREATE TABLE IF NOT EXISTS membership_plans (
    plan_id          INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    duration_months  INT          NOT NULL,
    price            INT          NOT NULL,   -- RSD, for the whole plan
    created_at       DATETIME(6)  NOT NULL,
    updated_at       DATETIME(6)
);

CREATE TABLE IF NOT EXISTS trainers (
    trainer_id   INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    specialty    VARCHAR(255) NOT NULL,
    phone        VARCHAR(255),
    monthly_fee  INT          NOT NULL,       -- RSD per month, added to the plan price
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6)
);

CREATE TABLE IF NOT EXISTS members (
    member_id    INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL UNIQUE,
    plan_id      INT          NOT NULL,       -- every member has a plan
    trainer_id   INT          NULL,           -- NULL = trains alone
    start_date   DATE         NOT NULL,
    end_date     DATE         NOT NULL,       -- start_date + plan duration
    total_price  INT          NOT NULL,       -- plan price + trainer fee x months
    created_at   DATETIME(6)  NOT NULL,
    updated_at   DATETIME(6),
    CONSTRAINT fk_members_plan
        FOREIGN KEY (plan_id) REFERENCES membership_plans (plan_id),
    CONSTRAINT fk_members_trainer
        FOREIGN KEY (trainer_id) REFERENCES trainers (trainer_id)
);
