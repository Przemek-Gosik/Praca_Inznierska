CREATE TABLE IF NOT EXISTS `Admin` (
                                              `idAdmin` INT NOT NULL,
                                              `login` VARCHAR(45) NOT NULL,
    `email` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`idAdmin`))
    ENGINE = InnoDB;