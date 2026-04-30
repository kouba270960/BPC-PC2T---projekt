-- SQL databáza pre zálohu zamestnancov
-- Spustiť v phpMyAdmin (XAMPP)

CREATE DATABASE IF NOT EXISTS zaloha_sql;
USE zaloha_sql;

CREATE TABLE IF NOT EXISTS zamestnanci (
    id INT PRIMARY KEY,
    jmeno VARCHAR(100) NOT NULL,
    prijmeni VARCHAR(100) NOT NULL,
    rok_narozeni INT NOT NULL,
    typ VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS spoluprace (
    id_zamestnance INT NOT NULL,
    id_kolegy INT NOT NULL,
    kvalita VARCHAR(20) NOT NULL,
    PRIMARY KEY (id_zamestnance, id_kolegy)
);