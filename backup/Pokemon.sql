-- MySQL dump 10.13  Distrib 8.0.27, for Linux (x86_64)
--
-- Host: 172.17.0.1    Database: Pokemon
-- ------------------------------------------------------
-- Server version	5.5.5-10.7.1-MariaDB-1:10.7.1+maria~focal

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Attacks`
--

DROP TABLE IF EXISTS `Attacks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Attacks` (
  `name` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `damage` int(11) DEFAULT NULL,
  `ap` int(11) NOT NULL,
  `accuracy` int(11) NOT NULL,
  `ko` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Attacks`
--

LOCK TABLES `Attacks` WRITE;
/*!40000 ALTER TABLE `Attacks` DISABLE KEYS */;
INSERT INTO `Attacks` VALUES ('Blast Burn','Fire',60,15,100,0),('Blaze Kick','Fire',110,15,80,0),('Earthquake','Ground',60,15,100,0),('Fire Blast','Fire',75,15,85,0),('Firestorm','Fire',120,15,80,0),('Flamethrower','Fire',75,15,85,0),('Heatwave','Fire',80,30,95,0),('Hyper Beam','Normal',150,5,90,0),('Thunder','Electric',80,30,95,0);
/*!40000 ALTER TABLE `Attacks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Fights`
--

DROP TABLE IF EXISTS `Fights`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Fights` (
  `fight_id` int(11) NOT NULL AUTO_INCREMENT,
  `rounds` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `winner` int(11) NOT NULL,
  `loser` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`fight_id`),
  KEY `winner` (`winner`),
  KEY `loser` (`loser`),
  CONSTRAINT `Fights_ibfk_1` FOREIGN KEY (`winner`) REFERENCES `Trainers` (`trainer_id`),
  CONSTRAINT `Fights_ibfk_2` FOREIGN KEY (`loser`) REFERENCES `Trainers` (`trainer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Fights`
--

LOCK TABLES `Fights` WRITE;
/*!40000 ALTER TABLE `Fights` DISABLE KEYS */;
INSERT INTO `Fights` VALUES (1,5,250,2,1,'2021-12-02 00:19:01'),(2,5,250,2,1,'2021-12-02 00:19:29'),(3,9,250,2,1,'2021-12-02 00:19:37'),(4,5,250,2,1,'2021-12-02 00:22:30'),(5,1,250,1,2,'2021-12-02 19:37:24'),(6,3,250,1,2,'2021-12-02 19:46:32'),(7,1,250,1,2,'2021-12-02 19:51:28'),(8,5,250,1,2,'2021-12-02 19:58:24'),(9,3,250,1,2,'2021-12-02 19:59:44'),(10,3,250,1,2,'2021-12-02 20:47:18'),(11,3,250,1,2,'2021-12-02 20:51:36'),(12,9,250,1,2,'2021-12-02 21:06:33'),(13,3,250,1,2,'2021-12-02 21:07:09'),(14,5,250,1,2,'2021-12-02 21:07:32'),(15,3,250,1,2,'2021-12-02 21:07:43'),(16,3,250,1,2,'2021-12-02 21:08:42'),(17,5,250,1,2,'2021-12-02 21:08:52');
/*!40000 ALTER TABLE `Fights` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pokemons`
--

DROP TABLE IF EXISTS `Pokemons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Pokemons` (
  `pokemon_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `lv` int(11) NOT NULL,
  `hp` int(11) NOT NULL,
  `speed` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `weakness` varchar(255) NOT NULL,
  `attack_1` varchar(255) NOT NULL,
  `attack_2` varchar(255) NOT NULL,
  `attack_3` varchar(255) NOT NULL,
  `attack_4` varchar(255) NOT NULL,
  PRIMARY KEY (`pokemon_id`),
  KEY `attack_1` (`attack_1`),
  KEY `attack_2` (`attack_2`),
  KEY `attack_3` (`attack_3`),
  KEY `attack_4` (`attack_4`),
  CONSTRAINT `Pokemons_ibfk_1` FOREIGN KEY (`attack_1`) REFERENCES `Attacks` (`name`),
  CONSTRAINT `Pokemons_ibfk_2` FOREIGN KEY (`attack_2`) REFERENCES `Attacks` (`name`),
  CONSTRAINT `Pokemons_ibfk_3` FOREIGN KEY (`attack_3`) REFERENCES `Attacks` (`name`),
  CONSTRAINT `Pokemons_ibfk_4` FOREIGN KEY (`attack_4`) REFERENCES `Attacks` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pokemons`
--

LOCK TABLES `Pokemons` WRITE;
/*!40000 ALTER TABLE `Pokemons` DISABLE KEYS */;
INSERT INTO `Pokemons` VALUES (1,'Glumanda',70,250,180,'Fire,Earth','Water','Fire Blast','Heatwave','Hyper Beam','Blast Burn'),(2,'Groudon',90,380,200,'Earth','Water','Hyper Beam','Flamethrower','Thunder','Earthquake'),(3,'Glurak',80,360,125,'Fire,Earth','Water','Blast Burn','Blaze Kick','Earthquake','Fire Blast');
/*!40000 ALTER TABLE `Pokemons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Trainers`
--

DROP TABLE IF EXISTS `Trainers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Trainers` (
  `trainer_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `balance` int(11) DEFAULT NULL,
  PRIMARY KEY (`trainer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Trainers`
--

LOCK TABLES `Trainers` WRITE;
/*!40000 ALTER TABLE `Trainers` DISABLE KEYS */;
INSERT INTO `Trainers` VALUES (1,'Ash',6000),(2,'Barry',4000);
/*!40000 ALTER TABLE `Trainers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-02 22:09:49
