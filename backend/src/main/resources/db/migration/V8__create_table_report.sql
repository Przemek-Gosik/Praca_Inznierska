CREATE TABLE IF NOT EXISTS `Report` (
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
