CREATE TABLE IF NOT EXISTS 'FastReading' (
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