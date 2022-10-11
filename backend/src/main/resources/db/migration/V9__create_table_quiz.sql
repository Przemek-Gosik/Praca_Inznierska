CREATE TABLE IF NOT EXISTS `Quiz` (
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