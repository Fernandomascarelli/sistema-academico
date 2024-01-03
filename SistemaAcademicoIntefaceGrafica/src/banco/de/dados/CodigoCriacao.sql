/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Administrador
 * Created: 3 de jan. de 2024
 */

CREATE DATABASE  IF NOT EXISTS `trabfinal` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `trabfinal`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: trabfinal
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `aluno`
--

DROP TABLE IF EXISTS `aluno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `aluno` (
  `idaluno` int NOT NULL AUTO_INCREMENT,
  `nomeA` varchar(45) DEFAULT NULL,
  `cpfA` varchar(45) DEFAULT NULL,
  `emailA` varchar(45) DEFAULT NULL,
  `generoA` varchar(45) DEFAULT NULL,
  `dataNascimentoA` date DEFAULT NULL,
  `ra` varchar(45) DEFAULT NULL,
  `dataMatricula` date DEFAULT NULL,
  `situacao` varchar(45) DEFAULT NULL,
  `idendA` int DEFAULT NULL,
  `idcursoA` int DEFAULT NULL,
  PRIMARY KEY (`idaluno`),
  KEY `idendA_idx` (`idendA`),
  KEY `idcursoA_idx` (`idcursoA`),
  CONSTRAINT `idcursoA` FOREIGN KEY (`idcursoA`) REFERENCES `curso` (`idcurso`),
  CONSTRAINT `idendA` FOREIGN KEY (`idendA`) REFERENCES `endereco` (`idendereco`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `aluno`
--

LOCK TABLES `aluno` WRITE;
/*!40000 ALTER TABLE `aluno` DISABLE KEYS */;
/*!40000 ALTER TABLE `aluno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `curso` (
  `idcurso` int NOT NULL AUTO_INCREMENT,
  `nomeC` varchar(45) DEFAULT NULL,
  `cargaHoraria` int DEFAULT NULL,
  `qtdSemestre` int DEFAULT NULL,
  `iddocenteC` int DEFAULT NULL,
  PRIMARY KEY (`idcurso`),
  KEY `iddocenteD_idx` (`iddocenteC`),
  CONSTRAINT `iddocenteD` FOREIGN KEY (`iddocenteC`) REFERENCES `docente` (`iddocente`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `disciplina`
--

DROP TABLE IF EXISTS `disciplina`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `disciplina` (
  `iddisciplina` int NOT NULL AUTO_INCREMENT,
  `nomeD` varchar(45) DEFAULT NULL,
  `semestre` int DEFAULT NULL,
  `crgHoraria` int DEFAULT NULL,
  `idcursoDis` int DEFAULT NULL,
  PRIMARY KEY (`iddisciplina`),
  KEY `idcursoDis_idx` (`idcursoDis`),
  CONSTRAINT `idcursoDis` FOREIGN KEY (`idcursoDis`) REFERENCES `curso` (`idcurso`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `disciplina`
--

LOCK TABLES `disciplina` WRITE;
/*!40000 ALTER TABLE `disciplina` DISABLE KEYS */;
/*!40000 ALTER TABLE `disciplina` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docente`
--

DROP TABLE IF EXISTS `docente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docente` (
  `iddocente` int NOT NULL AUTO_INCREMENT,
  `nomeD` varchar(45) DEFAULT NULL,
  `cpfD` varchar(45) DEFAULT NULL,
  `emailD` varchar(45) DEFAULT NULL,
  `generoD` varchar(45) DEFAULT NULL,
  `dataNascimentoD` date DEFAULT NULL,
  `ctpsD` varchar(45) DEFAULT NULL,
  `salarioD` double DEFAULT NULL,
  `formacao` varchar(45) DEFAULT NULL,
  `idendD` int DEFAULT NULL,
  PRIMARY KEY (`iddocente`),
  KEY `idendD_idx` (`idendD`),
  CONSTRAINT `idendD` FOREIGN KEY (`idendD`) REFERENCES `endereco` (`idendereco`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docente`
--

LOCK TABLES `docente` WRITE;
/*!40000 ALTER TABLE `docente` DISABLE KEYS */;
/*!40000 ALTER TABLE `docente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `endereco`
--

DROP TABLE IF EXISTS `endereco`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `endereco` (
  `idendereco` int NOT NULL AUTO_INCREMENT,
  `cidade` varchar(45) DEFAULT NULL,
  `rua` varchar(45) DEFAULT NULL,
  `numero` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idendereco`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `endereco`
--

LOCK TABLES `endereco` WRITE;
/*!40000 ALTER TABLE `endereco` DISABLE KEYS */;
/*!40000 ALTER TABLE `endereco` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `funcionario` (
  `idfuncionario` int NOT NULL AUTO_INCREMENT,
  `nomeF` varchar(45) DEFAULT NULL,
  `cpfF` varchar(45) DEFAULT NULL,
  `emailF` varchar(45) DEFAULT NULL,
  `generoF` varchar(45) DEFAULT NULL,
  `dataNascimentoF` date DEFAULT NULL,
  `ctpsF` varchar(45) DEFAULT NULL,
  `salarioF` double DEFAULT NULL,
  `idendF` int DEFAULT NULL,
  PRIMARY KEY (`idfuncionario`),
  KEY `idendF_idx` (`idendF`),
  CONSTRAINT `idendF` FOREIGN KEY (`idendF`) REFERENCES `endereco` (`idendereco`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionario`
--

LOCK TABLES `funcionario` WRITE;
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-27 19:42:53

