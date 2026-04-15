CREATE DATABASE  IF NOT EXISTS `reparacoesdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `reparacoesdb`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: reparacoesdb
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrador` (
  `idA` int NOT NULL AUTO_INCREMENT,
  `id` int DEFAULT NULL,
  PRIMARY KEY (`idA`),
  KEY `id` (`id`),
  CONSTRAINT `administrador_ibfk_1` FOREIGN KEY (`id`) REFERENCES `utilizador` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `idC` int NOT NULL AUTO_INCREMENT,
  `id` int DEFAULT NULL,
  `NIF` int DEFAULT NULL,
  `Telemovel` varchar(20) DEFAULT NULL,
  `Morada` varchar(200) DEFAULT NULL,
  `SectorA` varchar(100) DEFAULT NULL,
  `Escalao` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idC`),
  KEY `id` (`id`),
  CONSTRAINT `clientes_ibfk_1` FOREIGN KEY (`id`) REFERENCES `utilizador` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `equipamento`
--

DROP TABLE IF EXISTS `equipamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `equipamento` (
  `idEquip` int NOT NULL AUTO_INCREMENT,
  `Marca` varchar(100) DEFAULT NULL,
  `Modelo` varchar(100) DEFAULT NULL,
  `SKU` varchar(100) DEFAULT NULL,
  `Lote` varchar(100) DEFAULT NULL,
  `dataSubmissao` date DEFAULT NULL,
  `dataReparacao` date DEFAULT NULL,
  `id` int DEFAULT NULL,
  PRIMARY KEY (`idEquip`),
  KEY `id_idx` (`id`),
  CONSTRAINT `id` FOREIGN KEY (`id`) REFERENCES `utilizador` (`id`),
  CONSTRAINT `equipamento_chk_1` CHECK ((`SKU` between 1 and 1000000))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionario` (
  `idC` int NOT NULL AUTO_INCREMENT,
  `id` int DEFAULT NULL,
  `NIF` int DEFAULT NULL,
  `Telemovel` int DEFAULT NULL,
  `Morada` varchar(200) DEFAULT NULL,
  `NivelE` int DEFAULT NULL,
  `DataI` date DEFAULT NULL,
  PRIMARY KEY (`idC`),
  KEY `id` (`id`),
  CONSTRAINT `funcionario_ibfk_1` FOREIGN KEY (`id`) REFERENCES `utilizador` (`id`),
  CONSTRAINT `funcionario_chk_1` CHECK ((`Telemovel` between 100000000 and 999999999)),
  CONSTRAINT `funcionario_chk_2` CHECK ((`nif` between 100000000 and 999999999))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `notificacao`
--

DROP TABLE IF EXISTS `notificacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notificacao` (
  `idN` int NOT NULL AUTO_INCREMENT,
  `id` int DEFAULT NULL,
  `mensagem` text,
  `dataEnvio` date DEFAULT NULL,
  `lido` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idN`),
  KEY `id` (`id`),
  CONSTRAINT `notificacao_ibfk_1` FOREIGN KEY (`id`) REFERENCES `utilizador` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `peca`
--

DROP TABLE IF EXISTS `peca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `peca` (
  `idPeca` int NOT NULL AUTO_INCREMENT,
  `idEquip` int DEFAULT NULL,
  `codigo` int DEFAULT NULL,
  `designacao` varchar(150) DEFAULT NULL,
  `fabricante` varchar(100) DEFAULT NULL,
  `quantidade` int DEFAULT NULL,
  PRIMARY KEY (`idPeca`),
  KEY `idEquip` (`idEquip`),
  CONSTRAINT `peca_ibfk_1` FOREIGN KEY (`idEquip`) REFERENCES `equipamento` (`idEquip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reparacao`
--

DROP TABLE IF EXISTS `reparacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reparacao` (
  `idR` int NOT NULL AUTO_INCREMENT,
  `idEquip` int DEFAULT NULL,
  `Estado` int DEFAULT NULL,
  `DataInicio` date DEFAULT NULL,
  `DataFim` date DEFAULT NULL,
  `Custo` float DEFAULT NULL,
  `Observacao` text,
  `FuncionarioA` int DEFAULT NULL,
  PRIMARY KEY (`idR`),
  KEY `idEquip` (`idEquip`),
  KEY `FuncionarioA_idx` (`FuncionarioA`),
  CONSTRAINT `FuncionarioA` FOREIGN KEY (`FuncionarioA`) REFERENCES `funcionario` (`id`),
  CONSTRAINT `reparacao_ibfk_1` FOREIGN KEY (`idEquip`) REFERENCES `equipamento` (`idEquip`),
  CONSTRAINT `reparacao_chk_1` CHECK ((`Estado` between 1 and 5))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `AtualizarCustoReparacao` BEFORE UPDATE ON `reparacao` FOR EACH ROW BEGIN
    IF NEW.Estado = 4 AND OLD.Estado != 4 THEN
        SET NEW.Custo = (
            SELECT COALESCE(SUM(preco), 0) 
            FROM testes 
            WHERE idReparacao = NEW.idR
        );
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `testes`
--

DROP TABLE IF EXISTS `testes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `testes` (
  `idTeste` int NOT NULL AUTO_INCREMENT,
  `idReparacao` int DEFAULT NULL,
  `designacao` varchar(100) DEFAULT NULL,
  `descricao` text,
  `dataTeste` date DEFAULT NULL,
  `preco` float DEFAULT '0',
  PRIMARY KEY (`idTeste`),
  KEY `idReparacao` (`idReparacao`),
  CONSTRAINT `testes_ibfk_1` FOREIGN KEY (`idReparacao`) REFERENCES `reparacao` (`idR`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utilizador`
--

DROP TABLE IF EXISTS `utilizador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilizador` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `estado` tinyint(1) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'reparacoesdb'
--

--
-- Dumping routines for database 'reparacoesdb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-15 21:49:00
