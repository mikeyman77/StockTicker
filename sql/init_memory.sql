-- @author Stuart Connall
-- @version 1.0 02/25/2014

--
-- Table structure for table stocks
--
DROP TABLE IF EXISTS stock;
CREATE MEMORY TABLE IF NOT EXISTS stock (
  stockId    int NOT NULL AUTO_INCREMENT,
  symbol       varchar(5) DEFAULT NULL,
  description  varchar(30) DEFAULT NULL,
  PRIMARY KEY (stockId)
) AUTO_INCREMENT=0 ;

DROP INDEX IF EXISTS idx_symbol;
CREATE UNIQUE INDEX IF NOT EXISTS idx_symbol ON stock(symbol);
--
-- Table structure for table tracked_stocks
--
DROP TABLE IF EXISTS tracked_stock;
CREATE MEMORY TABLE IF NOT EXISTS tracked_stock (
  trackId    int NOT NULL AUTO_INCREMENT,
  userId     int NOT NULL,
  stockId    int NOT NULL,
  PRIMARY KEY (trackId, userId, stockId)
) AUTO_INCREMENT=0 ;

DROP INDEX IF EXISTS idx_tracked_stock;
CREATE UNIQUE INDEX IF NOT EXISTS idx_tracked_stock ON tracked_stock(trackId);

--
-- Table structure for table userinfo
--
DROP TABLE IF EXISTS userinfo;
CREATE MEMORY TABLE IF NOT EXISTS userinfo (
  userInfoId   int NOT NULL AUTO_INCREMENT,
  firstName   varchar(30) DEFAULT NULL,
  lastName    varchar(30) DEFAULT NULL,
  FK_trackId  int DEFAULT NULL,
  PRIMARY KEY (userInfoId),
  FOREIGN KEY (FK_trackId)
    REFERENCES tracked_stock(trackId)
) AUTO_INCREMENT=0 ;

--
-- Table structure for table user
--
DROP TABLE IF EXISTS user;
CREATE MEMORY TABLE IF NOT EXISTS user (
  userId     int NOT NULL AUTO_INCREMENT,
  FK_userInfoId int DEFAULT NOT NULL,
  username     varchar(20) DEFAULT NULL,
  password     varchar(12) DEFAULT NULL,
  joinedDate datetime DEFAULT NULL,
  isLoggedIn  boolean,
  PRIMARY KEY (userId),
  FOREIGN KEY (FK_userInfoId)
    REFERENCES userinfo(userInfoId)
    ON DELETE CASCADE
) AUTO_INCREMENT=0 ;

DROP INDEX IF EXISTS idx_user;
CREATE UNIQUE INDEX IF NOT EXISTS idx_user ON user(userId);
