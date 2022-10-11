CREATE TABLE IF NOT EXISTS User (
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
    REFERENCES Setting (`idSetting`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);