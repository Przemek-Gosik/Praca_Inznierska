CREATE TABLE IF NOT EXISTS Setting (
                                                `idSetting` INT NOT NULL,
                                                `fontSize` ENUM('small', 'medium', 'big') NOT NULL,
    `theme` ENUM('day', 'night', 'contrast') NOT NULL,
    PRIMARY KEY (`idSetting`));
