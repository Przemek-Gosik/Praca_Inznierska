CREATE TABLE IF NOT EXISTS TestFastWriting(
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
