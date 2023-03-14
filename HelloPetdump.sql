-- --------------------------------------------------------
-- 호스트:                          13.125.199.85
-- 서버 버전:                        5.5.68-MariaDB - MariaDB Server
-- 서버 OS:                        Linux
-- HeidiSQL 버전:                  12.1.0.6537
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- hellopet 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `hellopet` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `hellopet`;

-- 테이블 hellopet.api_hospital 구조 내보내기
CREATE TABLE IF NOT EXISTS `api_hospital` (
  `hosNo` int(11) NOT NULL AUTO_INCREMENT,
  `hosName` varchar(100) NOT NULL,
  `hosAddr` varchar(255) NOT NULL,
  `lon` double NOT NULL,
  `lat` double NOT NULL,
  `tel` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`hosNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 hellopet.api_hospital:~0 rows (대략적) 내보내기

-- 테이블 hellopet.api_pharmacy 구조 내보내기
CREATE TABLE IF NOT EXISTS `api_pharmacy` (
  `pharNo` int(11) NOT NULL AUTO_INCREMENT,
  `pharName` varchar(100) NOT NULL,
  `pharAddr` varchar(255) NOT NULL,
  `lon` double NOT NULL,
  `lat` double NOT NULL,
  `tel` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`pharNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 hellopet.api_pharmacy:~0 rows (대략적) 내보내기

-- 테이블 hellopet.community_article 구조 내보내기
CREATE TABLE IF NOT EXISTS `community_article` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(20) NOT NULL,
  `parent` int(11) DEFAULT '0',
  `group` varchar(20) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text NOT NULL,
  `img1` varchar(255) DEFAULT NULL,
  `img2` varchar(255) DEFAULT NULL,
  `img3` varchar(255) DEFAULT NULL,
  `hit` int(11) DEFAULT '0',
  `heart` int(11) DEFAULT '0',
  `regip` varchar(100) NOT NULL,
  `rdate` datetime NOT NULL,
  PRIMARY KEY (`no`),
  KEY `fk_community_article_pet_owner_idx` (`uid`),
  CONSTRAINT `fk_community_article_pet_owner` FOREIGN KEY (`uid`) REFERENCES `pet_owner` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 hellopet.community_article:~0 rows (대략적) 내보내기

-- 테이블 hellopet.cs_article 구조 내보내기
CREATE TABLE IF NOT EXISTS `cs_article` (
  `no` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(20) NOT NULL,
  `parent` int(11) DEFAULT '0',
  `reply` tinyint(4) DEFAULT '0',
  `group` varchar(20) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text NOT NULL,
  `file` tinyint(4) DEFAULT '0',
  `hit` int(11) DEFAULT '0',
  `regip` varchar(100) NOT NULL,
  `rdate` datetime NOT NULL,
  PRIMARY KEY (`no`),
  KEY `fk_cs_article_pet_owner1_idx` (`uid`),
  CONSTRAINT `fk_cs_article_pet_owner1` FOREIGN KEY (`uid`) REFERENCES `pet_owner` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 hellopet.cs_article:~0 rows (대략적) 내보내기

-- 테이블 hellopet.disease 구조 내보내기
CREATE TABLE IF NOT EXISTS `disease` (
  `disNo` int(11) NOT NULL AUTO_INCREMENT,
  `group` varchar(20) NOT NULL,
  `cate1` int(11) NOT NULL,
  `cate2` int(11) NOT NULL,
  `disName` varchar(45) NOT NULL,
  `description` text NOT NULL,
  `department` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`disNo`),
  KEY `fk_disease_disease_cate21_idx` (`cate1`),
  CONSTRAINT `fk_disease_disease_cate21` FOREIGN KEY (`cate1`) REFERENCES `disease_cate2` (`cate1`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 hellopet.disease:~0 rows (대략적) 내보내기

-- 테이블 hellopet.disease_cate1 구조 내보내기
CREATE TABLE IF NOT EXISTS `disease_cate1` (
  `cate1` int(11) NOT NULL,
  `c1Name` varchar(45) NOT NULL,
  PRIMARY KEY (`cate1`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 hellopet.disease_cate1:~0 rows (대략적) 내보내기

-- 테이블 hellopet.disease_cate2 구조 내보내기
CREATE TABLE IF NOT EXISTS `disease_cate2` (
  `cate1` int(11) NOT NULL,
  `cate2` int(11) NOT NULL,
  `c2Name` text NOT NULL,
  PRIMARY KEY (`cate1`),
  KEY `fk_disease_cate2_disease_cate11_idx` (`cate1`),
  CONSTRAINT `fk_disease_cate2_disease_cate11` FOREIGN KEY (`cate1`) REFERENCES `disease_cate1` (`cate1`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 hellopet.disease_cate2:~0 rows (대략적) 내보내기

-- 테이블 hellopet.member_terms 구조 내보내기
CREATE TABLE IF NOT EXISTS `member_terms` (
  `terms` text NOT NULL,
  `privacy` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 hellopet.member_terms:~0 rows (대략적) 내보내기

-- 테이블 hellopet.pet_hospital_pharmacy 구조 내보내기
CREATE TABLE IF NOT EXISTS `pet_hospital_pharmacy` (
  `medNo` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(20) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `name` varchar(20) NOT NULL,
  `hp` char(13) NOT NULL,
  `medicalName` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '2' COMMENT '1:펫오너, 2:병원/약국',
  `level` tinyint(4) DEFAULT NULL COMMENT '0:탈퇴 1:일반 2:병원 3:약국 7:웹관리자',
  `zip` varchar(10) DEFAULT NULL,
  `addr1` varchar(255) DEFAULT NULL,
  `addr2` varchar(255) DEFAULT NULL,
  `ceo` varchar(20) NOT NULL,
  `ceoHp` char(13) NOT NULL,
  `reserveOK` tinyint(4) NOT NULL COMMENT '1:동의 2:미동의',
  `reserve` int(11) DEFAULT '0',
  `hit` int(11) DEFAULT '0',
  `wdate` datetime DEFAULT NULL,
  `rdate` datetime NOT NULL,
  `regip` varchar(100) NOT NULL,
  PRIMARY KEY (`medNo`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `medicalName_UNIQUE` (`medicalName`),
  UNIQUE KEY `uid_UNIQUE` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 hellopet.pet_hospital_pharmacy:~0 rows (대략적) 내보내기

-- 테이블 hellopet.pet_owner 구조 내보내기
CREATE TABLE IF NOT EXISTS `pet_owner` (
  `uid` varchar(20) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `name` varchar(20) NOT NULL,
  `hp` char(13) NOT NULL COMMENT '1:남, 2:여',
  `nick` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1:펫오너, 2:병원/약국',
  `level` tinyint(4) DEFAULT '1' COMMENT '0:탈퇴 1:일반 2:병원 3:약국 7:웹관리자',
  `zip` varchar(10) DEFAULT NULL,
  `addr1` varchar(255) DEFAULT NULL,
  `addr2` varchar(255) DEFAULT NULL,
  `regip` varchar(100) NOT NULL,
  `wdate` datetime DEFAULT NULL,
  `rdate` datetime NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `nick_UNIQUE` (`nick`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 hellopet.pet_owner:~0 rows (대략적) 내보내기

-- 테이블 hellopet.reserve_list 구조 내보내기
CREATE TABLE IF NOT EXISTS `reserve_list` (
  `revNo` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(20) NOT NULL,
  `medNo` int(11) NOT NULL,
  `petType1` varchar(20) NOT NULL,
  `petType2` varchar(20) DEFAULT NULL,
  `petName1` varchar(100) NOT NULL,
  `petName2` varchar(100) DEFAULT NULL,
  `petAge1` int(11) NOT NULL,
  `petAge2` int(11) DEFAULT NULL,
  `department` varchar(100) NOT NULL,
  `memo` varchar(100) DEFAULT NULL,
  `revDate` date NOT NULL,
  `revTime` time NOT NULL,
  `rdate` datetime NOT NULL,
  `status` tinyint(4) DEFAULT '0' COMMENT '0:예약대기중 1:예약확정 2:예약거절',
  PRIMARY KEY (`revNo`),
  KEY `fk_reserve_list_pet_owner1_idx` (`uid`),
  KEY `fk_reserve_list_pet_hospital_pharmacy1_idx` (`medNo`),
  CONSTRAINT `fk_reserve_list_pet_hospital_pharmacy1` FOREIGN KEY (`medNo`) REFERENCES `pet_hospital_pharmacy` (`medNo`),
  CONSTRAINT `fk_reserve_list_pet_owner1` FOREIGN KEY (`uid`) REFERENCES `pet_owner` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 테이블 데이터 hellopet.reserve_list:~0 rows (대략적) 내보내기

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
