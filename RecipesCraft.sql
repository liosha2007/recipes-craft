
CREATE TABLE IF NOT EXISTS Categories (
    _Id INTEGER PRIMARY KEY UNIQUE,
    Name VARCHAR(64),
    Icon VARCHAR(128)
);
CREATE TABLE IF NOT EXISTS Favorites (
    _Id INTEGER PRIMARY KEY UNIQUE
);
CREATE TABLE IF NOT EXISTS Items (
    _Id INTEGER PRIMARY KEY UNIQUE,
    Name VARCHAR(64),
    Icon VARCHAR(128),
    Description VARCHAR(512),
    Note VARCHAR(256)
);
CREATE TABLE IF NOT EXISTS Mods (
    _Id INTEGER PRIMARY KEY UNIQUE,
    Name VARCHAR(64),
    Icon VARCHAR(128)
);
CREATE TABLE IF NOT EXISTS Recipes (
    _Id INTEGER PRIMARY KEY UNIQUE,
    Type INTEGER
);

ALTER TABLE Favorites
    ADD COLUMN Item INTEGER REFERENCES Items(_Id);

ALTER TABLE Items
    ADD COLUMN Category INTEGER REFERENCES Categories(_Id);
ALTER TABLE Items
    ADD COLUMN Mod INTEGER REFERENCES Mods(_Id);

ALTER TABLE Recipes ADD COLUMN P1x1 INTEGER REFERENCES Items(_Id);
ALTER TABLE Recipes ADD COLUMN P1x2 INTEGER REFERENCES Items(_Id);
ALTER TABLE Recipes ADD COLUMN P1x3 INTEGER REFERENCES Items(_Id);
ALTER TABLE Recipes ADD COLUMN P2x1 INTEGER REFERENCES Items(_Id);
ALTER TABLE Recipes ADD COLUMN P2x2 INTEGER REFERENCES Items(_Id);
ALTER TABLE Recipes ADD COLUMN P2x3 INTEGER REFERENCES Items(_Id);
ALTER TABLE Recipes ADD COLUMN P3x1 INTEGER REFERENCES Items(_Id);
ALTER TABLE Recipes ADD COLUMN P3x2 INTEGER REFERENCES Items(_Id);
ALTER TABLE Recipes ADD COLUMN P3x3 INTEGER REFERENCES Items(_Id);
ALTER TABLE Recipes ADD COLUMN Result INTEGER REFERENCES Items(_Id);

-- Android meta table
CREATE TABLE "android_metadata" ("locale" TEXT DEFAULT 'en_US');
INSERT INTO "android_metadata" VALUES ('en_US')
