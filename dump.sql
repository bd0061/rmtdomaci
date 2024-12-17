/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 10.4.27-MariaDB : Database - rmtdomaci
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rmtdomaci` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `rmtdomaci`;

/*Table structure for table `drzave` */

DROP TABLE IF EXISTS `drzave`;

CREATE TABLE `drzave` (
  `skracenoIme` varchar(10) NOT NULL,
  `punoIme` varchar(255) NOT NULL,
  PRIMARY KEY (`skracenoIme`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `drzave` */

insert  into `drzave`(`skracenoIme`,`punoIme`) values 
('AT','Austrija'),
('BE','Belgija'),
('BG','Bugarska'),
('CY','Kipar'),
('CZ','Češka'),
('DE','Nemačka'),
('DK','Danska'),
('EE','Estonija'),
('ES','Španija'),
('FI','Finska'),
('FR','Francuska'),
('GR','Grčka'),
('HR','Hrvatska'),
('HU','Mađarska'),
('IE','Irska'),
('IT','Italija'),
('LT','Litvanija'),
('LU','Luksemburg'),
('LV','Letonija'),
('MT','Malta'),
('NL','Holandija'),
('PL','Poljska'),
('PT','Portugal'),
('RO','Rumunija'),
('SE','Švedska'),
('SI','Slovenija'),
('SK','Slovačka');

/*Table structure for table `korisnici` */

DROP TABLE IF EXISTS `korisnici`;

CREATE TABLE `korisnici` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stanovnik_id` int(11) DEFAULT NULL,
  `korisnicko_ime` varchar(50) NOT NULL,
  `sifra` varchar(255) NOT NULL,
  `email` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `korisnicko_ime` (`korisnicko_ime`),
  KEY `stanovnik_id` (`stanovnik_id`),
  CONSTRAINT `korisnici_ibfk_1` FOREIGN KEY (`stanovnik_id`) REFERENCES `stanovnici` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `korisnici` */

/*Table structure for table `prijave` */

DROP TABLE IF EXISTS `prijave`;

CREATE TABLE `prijave` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stanovnik_id` int(11) NOT NULL,
  `datum_ulaska` date NOT NULL,
  `datum_izlaska` date NOT NULL,
  `nacin_prevoza` varchar(50) NOT NULL,
  `besplatna` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `stanovnik_id` (`stanovnik_id`),
  CONSTRAINT `prijave_ibfk_1` FOREIGN KEY (`stanovnik_id`) REFERENCES `stanovnici` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `prijave` */

/*Table structure for table `prijave_drzave` */

DROP TABLE IF EXISTS `prijave_drzave`;

CREATE TABLE `prijave_drzave` (
  `prijava_id` int(11) NOT NULL,
  `drzava_skraceno_ime` varchar(10) NOT NULL,
  PRIMARY KEY (`prijava_id`,`drzava_skraceno_ime`),
  KEY `drzava_skraceno_ime` (`drzava_skraceno_ime`),
  CONSTRAINT `prijave_drzave_ibfk_1` FOREIGN KEY (`prijava_id`) REFERENCES `prijave` (`id`) ON DELETE CASCADE,
  CONSTRAINT `prijave_drzave_ibfk_2` FOREIGN KEY (`drzava_skraceno_ime`) REFERENCES `drzave` (`skracenoIme`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `prijave_drzave` */

/*Table structure for table `stanovnici` */

DROP TABLE IF EXISTS `stanovnici`;

CREATE TABLE `stanovnici` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(100) NOT NULL,
  `prezime` varchar(100) NOT NULL,
  `JMBG` char(13) NOT NULL,
  `brojPasosa` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `JMBG` (`JMBG`),
  UNIQUE KEY `brojPasosa` (`brojPasosa`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `stanovnici` */

insert  into `stanovnici`(`id`,`ime`,`prezime`,`JMBG`,`brojPasosa`) values 
(1,'anon','anon','-','-'),
(2,'Aleksa','Djordjevic','2410968203704','363064219'),
(3,'Lazar','Aleksic','2211989103014','145001682'),
(4,'Miroslav','Savic','0112951100458','768314977'),
(5,'Jelena','Jovanovic','0808973108015','933222964'),
(6,'Dragoslav','Stojakovic','0412988000759','738210304'),
(7,'Predrag','Stojakovic','0602986101403','484194554'),
(8,'Aleksandra','Radovanovic','1305960206540','809858226'),
(9,'Milos','Antic','2211975404798','276701009'),
(10,'Nemanja','Randjelovic','2810980503738','637369068'),
(11,'Ana','Stankovic','1305999607003','440943361'),
(12,'Anja','Radovanovic','0505983607556','242210998'),
(13,'Lazar','Mitrovic','3008989000448','921420111'),
(14,'Jovana','Antic','2401982707724','744294847'),
(15,'Tamara','Stefanovic','2207974808456','117508835'),
(16,'Aleksa','Stefanovic','0410976204089','534861616'),
(17,'Dragoslav','Mitrovic','0406999303672','922874354'),
(18,'Milan','Stojakovic','2502955603861','743371689'),
(19,'Milan','Markovic','2107999202167','726358786'),
(20,'Jana','Zdravkovic','2008998008717','270991820'),
(21,'Marija','Nikolic','2005971509340','652494618'),
(22,'Stefan','Jovanovic','2309950500458','582481426'),
(23,'Tamara','Markovic','3107956705088','110708932'),
(24,'Sara','Antic','2002957606202','968290902'),
(25,'Vladimir','Aleksic','0812000403606','461145477'),
(26,'Ivana','Mitrovic','0206981407404','231702174'),
(27,'Milos','Savic','2406957802048','649193210'),
(28,'Kristina','Popovic','0208955409299','574519301'),
(29,'Nikola','Nikolic','2207986704052','168914956'),
(30,'Darko','Stefanovic','2710988500644','191212682'),
(31,'Predrag','Radovanovic','1601000703998','842868259'),
(32,'Marko','Lazarevic','1401955303375','980206151'),
(33,'Ivan','Markovic','0108963302431','901095105'),
(34,'Vladimir','Antic','2212981300763','995223576'),
(35,'Jana','Ivanovic','2310978407788','570914995'),
(36,'Miroslav','Nikolic','1002989002992','887156020'),
(37,'Ivan','Popovic','2708952901958','858860834'),
(38,'Ivana','Lazarevic','2601955405103','710855282'),
(39,'Jovan','Petrovic','0405953001217','349118900'),
(40,'Anja','Petrovic','2510963205479','766864625'),
(41,'Natalija','Milosevic','0608004907203','314723726'),
(42,'Natalija','Petrovic','1706977509185','776706145'),
(43,'Jovan','Nikolic','0612965003172','287872812'),
(44,'Milos','Savic','0203955700986','408055754'),
(45,'Milos','Popovic','0805983803146','173065887'),
(46,'Stefan','Popovic','1808963204788','831407892'),
(47,'Nikola','Jovanovic','0408997501887','683540499'),
(48,'Darko','Petrovic','1402004503796','846395004'),
(49,'Miroslav','Ivanovic','1210981501088','372012460'),
(50,'Nemanja','Antic','2101968403328','450537743'),
(51,'Tijana','Savic','2911977207061','587233219');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
