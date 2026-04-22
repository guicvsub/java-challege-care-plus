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
-- Table structure for table `food_logs`
--

DROP TABLE IF EXISTS `food_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_logs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `consumed_at` datetime(6) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `is_planned` bit(1) NOT NULL,
  `quantity` double NOT NULL,
  `total_calories` int NOT NULL,
  `total_carbs` double NOT NULL,
  `total_fats` double NOT NULL,
  `total_fiber` double NOT NULL,
  `total_proteins` double NOT NULL,
  `total_sodium` double NOT NULL,
  `total_sugar` double NOT NULL,
  `food_id` bigint NOT NULL,
  `meal_id` bigint NOT NULL,
  `patient_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9pu0unl100iuxlh1wnj2iiju9` (`food_id`),
  KEY `FKc82qiqlpfrpgj8u671ym8283a` (`meal_id`),
  KEY `FK5ny7e1p5tmbulvses86g9t1mk` (`patient_id`),
  CONSTRAINT `FK5ny7e1p5tmbulvses86g9t1mk` FOREIGN KEY (`patient_id`) REFERENCES `pacientes` (`id`),
  CONSTRAINT `FK9pu0unl100iuxlh1wnj2iiju9` FOREIGN KEY (`food_id`) REFERENCES `foods` (`id`),
  CONSTRAINT `FKc82qiqlpfrpgj8u671ym8283a` FOREIGN KEY (`meal_id`) REFERENCES `meals` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_logs`
--

LOCK TABLES `food_logs` WRITE;
/*!40000 ALTER TABLE `food_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `food_logs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-21 13:53:04
