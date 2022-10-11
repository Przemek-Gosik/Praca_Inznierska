CREATE TABLE IF NOT EXISTS TextReading(
                                                    `idTextReading` INT NOT NULL,
                                                    `text` LONGTEXT NOT NULL,
                                                    `level` ENUM('easy', 'medium', 'advanced') NOT NULL,
    PRIMARY KEY (`idTextReading`))
    ENGINE = InnoDB;
