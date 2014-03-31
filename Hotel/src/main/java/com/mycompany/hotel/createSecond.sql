
CREATE TABLE Reservations (
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    responsiblePerson VARCHAR(100) NOT NULL,
    account VARCHAR(15) NOT NULL,
    dateOfCheckIn DATE ,
    duration INTEGER,
    nOBed INTEGER,
    idRoom INTEGER

 );