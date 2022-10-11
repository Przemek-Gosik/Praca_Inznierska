CREATE TABLE IF NOT EXISTS `Memorizing` (
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