SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Setting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Setting` (
                                                `idSetting` INT NOT NULL,
                                                `fontSize` ENUM('small', 'medium', 'big') NOT NULL,
    `theme` ENUM('day', 'night', 'contrast') NOT NULL,
    PRIMARY KEY (`idSetting`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`User` (
                                             `idUser` INT NOT NULL,
                                             `login` VARCHAR(45) NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    `Setting_idSetting` INT NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idUser`, `Setting_idSetting`),
    UNIQUE INDEX `userName_UNIQUE` (`login` ASC),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC),
    INDEX `fk_User_Setting1_idx` (`Setting_idSetting` ASC),
    CONSTRAINT `fk_User_Setting1`
    FOREIGN KEY (`Setting_idSetting`)
    REFERENCES `mydb`.`Setting` (`idSetting`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TextWriting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TextWriting` (
                                                    `idTextWriting` INT NOT NULL,
                                                    `text` LONGTEXT NOT NULL,
                                                    `module` VARCHAR(45) NOT NULL,
    `level` ENUM('easy', 'medium', 'advanced') NOT NULL,
    PRIMARY KEY (`idTextWriting`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TestFastWriting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TestFastWriting` (
                                                        `idtTest` INT NOT NULL,
                                                        `name` VARCHAR(45) NOT NULL,
    `module` VARCHAR(45) NOT NULL,
    `number` INT NOT NULL,
    `typedText` LONGTEXT NOT NULL,
    `score` DOUBLE NOT NULL,
    `startTime` TIMESTAMP NOT NULL,
    `time` FLOAT NOT NULL,
    `TextWriting_idTextWriting` INT NOT NULL,
    `User_idUser` INT NOT NULL,
    `User_Setting_idSetting` INT NOT NULL,
    PRIMARY KEY (`idtTest`),
    INDEX `fk_TestFastWriting_Text1_idx` (`TextWriting_idTextWriting` ASC),
    INDEX `fk_TestFastWriting_User1_idx` (`User_idUser` ASC, `User_Setting_idSetting` ASC),
    CONSTRAINT `fk_TestFastWriting_Text1`
    FOREIGN KEY (`TextWriting_idTextWriting`)
    REFERENCES `mydb`.`TextWriting` (`idTextWriting`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_TestFastWriting_User1`
    FOREIGN KEY (`User_idUser` , `User_Setting_idSetting`)
    REFERENCES `mydb`.`User` (`idUser` , `Setting_idSetting`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`TextReading`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`TextReading` (
                                                    `idTextReading` INT NOT NULL,
                                                    `text` LONGTEXT NOT NULL,
                                                    `level` ENUM('easy', 'medium', 'advanced') NOT NULL,
    PRIMARY KEY (`idTextReading`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`FastReading`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`FastReading` (
                                                    `idFastReading` INT NOT NULL,
                                                    `type` ENUM('schubert', 'finding_numbers', 'reading_with_quiz') NOT NULL,
    `score` DOUBLE NOT NULL,
    `startTime` TIMESTAMP NOT NULL,
    `time` FLOAT NOT NULL,
    `User_idUser` INT NOT NULL,
    `User_Setting_idSetting` INT NOT NULL,
    `TextReading_idTextReading` INT NOT NULL,
    PRIMARY KEY (`idFastReading`),
    INDEX `fk_OtherTest_User1_idx` (`User_idUser` ASC, `User_Setting_idSetting` ASC),
    INDEX `fk_FastReading_TextReading1_idx` (`TextReading_idTextReading` ASC),
    CONSTRAINT `fk_OtherTest_User1`
    FOREIGN KEY (`User_idUser` , `User_Setting_idSetting`)
    REFERENCES `mydb`.`User` (`idUser` , `Setting_idSetting`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_FastReading_TextReading1`
    FOREIGN KEY (`TextReading_idTextReading`)
    REFERENCES `mydb`.`TextReading` (`idTextReading`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Admin` (
                                              `idAdmin` INT NOT NULL,
                                              `login` VARCHAR(45) NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idAdmin`))
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Report`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Report` (
                                               `idReport` INT NOT NULL,
                                               `title` VARCHAR(45) NOT NULL,
    `text` LONGTEXT NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    `User_idUser` INT NOT NULL,
    `User_Setting_idSetting` INT NOT NULL,
    `Admin_idAdmin` INT NOT NULL,
    PRIMARY KEY (`idReport`),
    INDEX `fk_Report_User1_idx` (`User_idUser` ASC, `User_Setting_idSetting` ASC),
    INDEX `fk_Report_Admin1_idx` (`Admin_idAdmin` ASC),
    CONSTRAINT `fk_Report_User1`
    FOREIGN KEY (`User_idUser` , `User_Setting_idSetting`)
    REFERENCES `mydb`.`User` (`idUser` , `Setting_idSetting`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_Report_Admin1`
    FOREIGN KEY (`Admin_idAdmin`)
    REFERENCES `mydb`.`Admin` (`idAdmin`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Quiz`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Quiz` (
                                             `idQuiz` INT NOT NULL,
                                             `test` JSON NOT NULL,
                                             `TextReading_idTextReading` INT NOT NULL,
                                             PRIMARY KEY (`idQuiz`, `TextReading_idTextReading`),
    INDEX `fk_Quiz_TextReading1_idx` (`TextReading_idTextReading` ASC),
    CONSTRAINT `fk_Quiz_TextReading1`
    FOREIGN KEY (`TextReading_idTextReading`)
    REFERENCES `mydb`.`TextReading` (`idTextReading`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Memorizing`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Memorizing` (
                                                   `idMemorizing` INT NOT NULL,
                                                   `type` ENUM('memory', 'mnemonics') NOT NULL,
    `level` ENUM('easy', 'medium', 'advanced') NOT NULL,
    `score` DOUBLE NOT NULL,
    `startTime` TIMESTAMP NOT NULL,
    `User_idUser` INT NOT NULL,
    `User_Setting_idSetting` INT NOT NULL,
    PRIMARY KEY (`idMemorizing`),
    INDEX `fk_Memorizing_User1_idx` (`User_idUser` ASC, `User_Setting_idSetting` ASC),
    CONSTRAINT `fk_Memorizing_User1`
    FOREIGN KEY (`User_idUser` , `User_Setting_idSetting`)
    REFERENCES `mydb`.`User` (`idUser` , `Setting_idSetting`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;