
CREATE TABLE Reservations (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    responsiblePerson VARCHAR(100) NOT NULL,
    account VARCHAR(20) NOT NULL,
    dateOfCheckIn DATE,
    duration INTEGER,
    nOBed INTEGER,
    idRoom INTEGER
 )CHARACTER SET utf8 COLLATE utf8_general_ci;