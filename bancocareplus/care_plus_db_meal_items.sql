-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: care_plus_db
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `meal_items`
--

DROP TABLE IF EXISTS `meal_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `meal_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `calories` int NOT NULL,
  `carbs` double NOT NULL,
  `fats` double NOT NULL,
  `fiber` double NOT NULL,
  `proteins` double NOT NULL,
  `quantity` double NOT NULL,
  `sodium` double NOT NULL,
  `sugar` double NOT NULL,
  `food_id` bigint NOT NULL,
  `meal_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKchoxjvcjim16ipnv0se5w3uyu` (`food_id`),
  KEY `FKbkra8m7kb523wvlyg55di7ecx` (`meal_id`),
  CONSTRAINT `FKbkra8m7kb523wvlyg55di7ecx` FOREIGN KEY (`meal_id`) REFERENCES `meals` (`id`),
  CONSTRAINT `FKchoxjvcjim16ipnv0se5w3uyu` FOREIGN KEY (`food_id`) REFERENCES `foods` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `meal_items`
--

LOCK TABLES `meal_items` WRITE;
/*!40000 ALTER TABLE `meal_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `meal_items` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-21 13:53:03
