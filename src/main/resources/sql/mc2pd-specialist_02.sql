CREATE DATABASE  IF NOT EXISTS `mc2pd-specialist` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `mc2pd-specialist`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: mc2pd-specialist
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(100) DEFAULT NULL,
  `role_description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Analista de Sistemas','Analista de Sistemas'),(2,'Arquiteto de Software','Arquiteto de Software'),(3,'Gerente de Projeto','Gerente de Projeto');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_login`
--

DROP TABLE IF EXISTS `user_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_login` (
  `login_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `password_hash` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`login_id`),
  KEY `fk_user_login_idx` (`user_id`),
  CONSTRAINT `fk_user_login` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_login`
--

LOCK TABLES `user_login` WRITE;
/*!40000 ALTER TABLE `user_login` DISABLE KEYS */;
INSERT INTO `user_login` VALUES (1,1,'adminadmin'),(2,2,'adminadmin'),(3,3,'adminadmin'),(4,4,'adminadmin'),(5,5,'adminadmin'),(6,6,'adminadmin'),(7,7,'adminadmin'),(8,8,'adminadmin'),(9,9,'adminadmin'),(10,10,'adminadmin'),(11,11,'adminadmin'),(12,12,'adminadmin');
/*!40000 ALTER TABLE `user_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_microservices_weight`
--

DROP TABLE IF EXISTS `user_microservices_weight`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_microservices_weight` (
  `id` int(11) NOT NULL,
  `microservice` varchar(200) DEFAULT NULL,
  `weight` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_microservices_weight`
--

LOCK TABLES `user_microservices_weight` WRITE;
/*!40000 ALTER TABLE `user_microservices_weight` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_microservices_weight` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_roles` (
  `id_user` int(11) NOT NULL,
  `id_role` int(11) NOT NULL,
  PRIMARY KEY (`id_user`,`id_role`),
  KEY `fk_id_role_tb_roles_idx` (`id_role`),
  CONSTRAINT `fk_id_role_tb_roles` FOREIGN KEY (`id_role`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `fk_id_user_tb_user_login` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(2,1),(7,1),(8,1),(3,2),(4,2),(9,2),(10,2),(5,3),(6,3),(11,3),(12,3);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_visions`
--

DROP TABLE IF EXISTS `user_visions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_visions` (
  `user_id` int(11) NOT NULL,
  `vision_id` int(11) NOT NULL,
  `preference_order` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`vision_id`),
  KEY `fk_id_user_tb_user_vision_pref_idx` (`user_id`),
  KEY `fk_vision_id_tb_visions_idx` (`vision_id`),
  CONSTRAINT `fk_id_user_tb_user_vision_pref` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `fk_vision_id_tb_visions` FOREIGN KEY (`vision_id`) REFERENCES `visions` (`vision_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_visions`
--

LOCK TABLES `user_visions` WRITE;
/*!40000 ALTER TABLE `user_visions` DISABLE KEYS */;
INSERT INTO `user_visions` VALUES (1,1,1),(1,2,2),(1,3,3),(1,4,4),(1,5,5),(1,6,6),(1,7,7),(2,1,7),(2,2,6),(2,3,5),(2,4,4),(2,5,3),(2,6,2),(2,7,1),(3,1,2),(3,2,1),(3,3,4),(3,4,3),(3,5,6),(3,6,5),(3,7,7),(4,1,6),(4,2,7),(4,3,4),(4,4,5),(4,5,2),(4,6,3),(4,7,1),(5,1,7),(5,2,6),(5,3,5),(5,4,4),(5,5,3),(5,6,2),(5,7,1),(6,1,2),(6,2,1),(6,3,4),(6,4,3),(6,5,6),(6,6,5),(6,7,7),(7,1,6),(7,2,7),(7,3,4),(7,4,5),(7,5,2),(7,6,3),(7,7,1),(8,1,2),(8,2,1),(8,3,4),(8,4,3),(8,5,6),(8,6,5),(8,7,7),(9,1,6),(9,2,7),(9,3,4),(9,4,5),(9,5,2),(9,6,3),(9,7,1),(10,1,1),(10,2,2),(10,3,3),(10,4,4),(10,5,5),(10,6,6),(10,7,7),(11,1,7),(11,2,6),(11,3,5),(11,4,4),(11,5,3),(11,6,2),(11,7,1),(12,1,6),(12,2,7),(12,3,4),(12,4,5),(12,5,2),(12,6,3),(12,7,1);
/*!40000 ALTER TABLE `user_visions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `dt_register` datetime DEFAULT NULL,
  `company` varchar(45) DEFAULT NULL,
  `job_title` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Exemplo_Analista','01_E01','2019-08-27 17:55:23','Empresa_01','Analista de Sistemas'),(2,'Exemplo_Analista','02_E01','2019-08-27 17:55:23','Empresa_01','Analista de Sistemas'),(3,'Exemplo_Arquiteto','01_E01','2019-08-27 17:55:23','Empresa_01','Arquiteto de Software'),(4,'Exemplo_Arquiteto','02_E01','2019-08-27 17:55:23','Empresa_01','Arquiteto de Software'),(5,'Exemplo_Gerente','01_E01','2019-08-27 17:55:23','Empresa_01','Gerente de Projeto'),(6,'Exemplo_Gerente','02_E01','2019-08-27 17:55:23','Empresa_01','Gerente de Projeto'),(7,'Exemplo_Analista','01_E02','2019-08-27 17:55:23','Empresa_02','Analista de Sistemas'),(8,'Exemplo_Analista','02_E02','2019-08-27 17:55:23','Empresa_02','Analista de Sistemas'),(9,'Exemplo_Arquiteto','01_E02','2019-08-27 17:55:23','Empresa_02','Arquiteto de Software'),(10,'Exemplo_Arquiteto','02_E02','2019-08-27 17:55:23','Empresa_02','Arquiteto de Software'),(11,'Exemplo_Gerente','01_E02','2019-08-27 17:55:23','Empresa_02','Gerente de Projeto'),(12,'Exemplo_Gerente','02_E02','2019-08-27 17:55:23','Empresa_02','Gerente de Projeto');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visions`
--

DROP TABLE IF EXISTS `visions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `visions` (
  `vision_id` int(11) NOT NULL,
  `vision_name` varchar(100) DEFAULT NULL,
  `vision_desc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`vision_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visions`
--

LOCK TABLES `visions` WRITE;
/*!40000 ALTER TABLE `visions` DISABLE KEYS */;
INSERT INTO `visions` VALUES (1,'Relevância para o Negócio','Relevância para o Negócio'),(2,'Número de integrações com outros microserviços','Número de integrações com outros microserviços'),(3,'Custo de Manutenção','Custo de Manutenção'),(4,'Opinião de Especialistas','Opinião de Especialistas'),(5,'Tempo de Resposta','Tempo de Resposta'),(6,'Utilização de Recursos Computacionais','Utilização de Recursos Computacionais'),(7,'Cobertura de Testes Unitários','Cobertura de Testes Unitários');
/*!40000 ALTER TABLE `visions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'mc2pd-specialist'
--

--
-- Dumping routines for database 'mc2pd-specialist'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-27 15:15:35
