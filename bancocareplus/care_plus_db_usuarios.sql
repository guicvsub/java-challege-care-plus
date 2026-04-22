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
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `senha` varchar(60) NOT NULL,
  `sessao_ativa` bit(1) DEFAULT NULL,
  `token_expiracao` datetime(6) DEFAULT NULL,
  `ultimo_acesso` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKkfsp0s1tflm1cwlj8idhqsad0` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (2,NULL,'usuario@careplus.com','$2b$12$DJOYCtsdDQpz1BQrcYoQNeKwA4.dLkyqmwWf1G5ewkrp8WgHTIvqS',_binary '\0',NULL,NULL,NULL),(3,NULL,'testsenha@careplus.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',_binary '\0',NULL,NULL,NULL),(4,NULL,'sessao@careplus.com','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',_binary '',NULL,'2026-04-20 13:24:34.000000',NULL),(5,NULL,'expirado@careplus.com','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',_binary '',NULL,'2026-04-20 13:20:34.000000',NULL),(6,NULL,'sqltest@careplus.com','$2a$10$x2ECJtmpMhTj0Z4G7sTfA.1JkK8jGz6bG3W1vY0sP8nQ2fR7zX9e',_binary '\0',NULL,NULL,NULL),(7,NULL,'token@careplus.com','$2a$10$y3FDKunqN0oL7sT5gUfZb.2KlL9hH0aC4X2wZ1tQ9oR3sS8yY0f',_binary '\0',NULL,NULL,NULL),(8,NULL,'admin@careplus.com','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',_binary '\0',NULL,NULL,NULL),(10,'2026-04-20 17:00:47.000000','usuario1@careplus.com','$2b$12$DJOYCtsdDQpz1BQrcYoQNeKwA4.dLkyqmwWf1G5ewkrp8WgHTIvqS',_binary '\0',NULL,NULL,NULL),(11,'2026-04-21 13:03:21.944308','teste@careplus.com','$2a$10$M9z/CUiWSJ1uxDnBOiDsUehMqFsuez2330uJu1gUHR6j669KB8NA.',_binary '\0',NULL,NULL,'2026-04-21 13:03:21.944308'),(12,'2026-04-21 13:09:45.121605','novo123@careplus.com','$2a$10$OpyyXLHtNaC9WM7aCbTy6OrsOxwsuRVkLJEqdp7BB78yJASlm4hX2',_binary '\0',NULL,NULL,'2026-04-21 13:09:45.121605'),(13,'2026-04-21 13:12:10.304441','atualizado@careplus.com','$2a$10$K2jFv5zrGlFKuHuTjay0yemEnXPK.g31r9F97Qiqkzqut2.ejdA0O',_binary '\0',NULL,NULL,'2026-04-21 13:34:04.038468'),(14,'2026-04-21 13:15:34.060847','posttest2@careplus.com','$2a$10$y/pERQMRGbVYmupMrdSZc.lLo9oCk.wZQkM4F4gqfKRqWHo85yR8u',NULL,NULL,NULL,'2026-04-21 13:15:34.060847'),(15,'2026-04-21 13:18:43.016253','test_403_fix@careplus.com','$2a$10$ibsL38gCvlRX9xBH5LMcR.oDhrOCyjE3V43dKJkEuKkmG1ZxvJp96',NULL,NULL,NULL,'2026-04-21 13:18:43.016253'),(16,'2026-04-21 13:33:54.846318','novo@careplus.com','$2a$10$7e8GHsz42xTJ7c7mBYJAl.OYSd/P9n/h8/P/.IEDMs6/XDtXYr9r2',_binary '\0',NULL,NULL,'2026-04-21 13:33:54.846318');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
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
