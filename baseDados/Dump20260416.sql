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
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
INSERT INTO `administrador` VALUES (2,30),(3,31);
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,32,209211210,'919320943','mdmedmdk  dkwdmdd','3','3');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `equipamento`
--

LOCK TABLES `equipamento` WRITE;
/*!40000 ALTER TABLE `equipamento` DISABLE KEYS */;
INSERT INTO `equipamento` VALUES (1,'asus','asus','9903','990','2026-04-04',NULL,32),(2,'samsung','samsung','88383','883','2026-04-04',NULL,32);
/*!40000 ALTER TABLE `equipamento` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `funcionario`
--

LOCK TABLES `funcionario` WRITE;
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
INSERT INTO `funcionario` VALUES (2,2,123456789,123456789,'jefifowofwi ',3,'2026-03-21'),(3,2,209211210,919320943,'oowowowddwo dwowdodw',3,'2026-03-21'),(4,2,209211210,919320943,'odwoowdodwodwo ',4,'2026-03-21'),(6,20,200200200,919919919,'kkkkkkk',9,'2026-03-24');
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `notificacao`
--

LOCK TABLES `notificacao` WRITE;
/*!40000 ALTER TABLE `notificacao` DISABLE KEYS */;
/*!40000 ALTER TABLE `notificacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `peca`
--

DROP TABLE IF EXISTS `peca`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `peca` (
  `idPeca` int NOT NULL AUTO_INCREMENT,
  `designacao` varchar(150) DEFAULT NULL,
  `fabricante` varchar(100) DEFAULT NULL,
  `quantidade` int DEFAULT NULL,
  `preco` float DEFAULT NULL,
  PRIMARY KEY (`idPeca`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peca`
--

LOCK TABLES `peca` WRITE;
/*!40000 ALTER TABLE `peca` DISABLE KEYS */;
/*!40000 ALTER TABLE `peca` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `peca_usada`
--

DROP TABLE IF EXISTS `peca_usada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `peca_usada` (
  `idTransacao` int NOT NULL AUTO_INCREMENT,
  `idPeca` int DEFAULT NULL,
  `idReparacao` int DEFAULT NULL,
  PRIMARY KEY (`idTransacao`),
  KEY `fk_peca_idx` (`idPeca`),
  KEY `fk_reparacao_idx` (`idReparacao`),
  CONSTRAINT `fk_peca` FOREIGN KEY (`idPeca`) REFERENCES `peca` (`idPeca`),
  CONSTRAINT `fk_reparacao` FOREIGN KEY (`idReparacao`) REFERENCES `reparacao` (`idR`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peca_usada`
--

LOCK TABLES `peca_usada` WRITE;
/*!40000 ALTER TABLE `peca_usada` DISABLE KEYS */;
/*!40000 ALTER TABLE `peca_usada` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trg_decrementa_stock_peca` AFTER INSERT ON `peca_usada` FOR EACH ROW BEGIN
    UPDATE `peca`
    SET `quantidade` = `quantidade` - 1
    WHERE `idPeca` = NEW.idPeca;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

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

--
-- Dumping data for table `reparacao`
--

LOCK TABLES `reparacao` WRITE;
/*!40000 ALTER TABLE `reparacao` DISABLE KEYS */;
INSERT INTO `reparacao` VALUES (11,1,4,'2026-04-15','2026-04-15',0,' Nao funciona ',2),(12,2,3,'2026-04-15','2026-04-15',999,' n funcimina',2);
/*!40000 ALTER TABLE `reparacao` ENABLE KEYS */;
UNLOCK TABLES;
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
-- Dumping data for table `testes`
--

LOCK TABLES `testes` WRITE;
/*!40000 ALTER TABLE `testes` DISABLE KEYS */;
INSERT INTO `testes` VALUES (1,12,'teste tetudo','testar o teste','2026-04-15',999);
/*!40000 ALTER TABLE `testes` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `utilizador`
--

LOCK TABLES `utilizador` WRITE;
/*!40000 ALTER TABLE `utilizador` DISABLE KEYS */;
INSERT INTO `utilizador` VALUES (1,'miguel','miguel','miguel',1,'miguel'),(2,'ola','ola','ola49',1,'mii@gmail.com'),(4,'ola2','ola2','ola2',1,'ola2'),(9,'ola3','ola3','ola3',1,'ola3444@gmail.com'),(12,'oow','oo','oo',0,'ooo@gmail.com'),(13,'miguel','miguel3403','miguel3403',0,'miguel@gmail.com'),(15,'ola','ola223','ola223',0,'ola2333@gmail.com'),(17,'adeus','adeus','adeus',0,'adeus@gmail.com'),(18,'q','q','q',0,'q@gmail.com'),(19,'oooo','oooo','oooo',0,'oooo@gmail.com'),(20,'kkk','kkk','kkk',0,'kkk@gmail.com'),(22,'Miguel','miguel3030','miguel3030',0,'miguelkekek@gmail.com'),(23,'Mimi','mimi','mimi',0,'mimi@gmail.com'),(24,'mimi2','mimi2','mimi',0,'mimi2@gmail.com'),(25,'mimi3','mimi3','mimi3',0,'mimi3@gmail.com'),(26,'mimi4','mimi4','mimi4',0,'mimi4@gmail.com'),(27,'mimi5','mimi5','mimi5',0,'mimi5@gmail.com'),(29,'mimi6','mimi6','mimi6',0,'mimi6@gmail.com'),(30,'mimi7','mimi7','mimi7',1,'mimi7@gmail.com'),(31,'mimi8','mimi8','mimi8',1,'mimi8@gmail.com'),(32,'ola50','ola50','ola50',1,'ola50@gmail.com');
/*!40000 ALTER TABLE `utilizador` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-16 15:02:14
