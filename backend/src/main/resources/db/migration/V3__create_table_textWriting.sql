CREATE TABLE IF NOT EXISTS TextWriting (
                                                    `idTextWriting` INT NOT NULL,
                                                    `text` LONGTEXT NOT NULL,
                                                    `module` VARCHAR(45) NOT NULL,
    `level` ENUM('easy', 'medium', 'advanced') NOT NULL,
    PRIMARY KEY (`idTextWriting`));