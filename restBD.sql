
-- Table structure for table `armadora`

DROP TABLE IF EXISTS `armadora`;
CREATE TABLE `armadora` (
  `clave` varchar(255) COLLATE latin1_spanish_ci NOT NULL,
  `nPlantas` int(11) NOT NULL,
  `nombre` varchar(255) COLLATE latin1_spanish_ci DEFAULT NULL,
  `paisOrigen` varchar(255) COLLATE latin1_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`clave`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

-- Dumping data for table `armadora`

LOCK TABLES `armadora` WRITE;
INSERT INTO `armadora` VALUES ('BMW',0,'BMW European cars Corporation','Alemania'),
('Ferrari',0,'Ferrari International','Italia'),
('Fiat',1,'Fabbrica Italiana Automobili Torino','Italia'),
('Ford',1,'Ford Motor Company','EUA'),
('GM',2,'General Motors Company','EUA'),
('KIA',0,'KIA International Inc.','Corea del Sur'),
('Lamborghini',0,'Lamborghini International','Italia'),
('MB',2,'Daimler Mercedes Benz','Alemania'),
('Peugeot',1,'Peugeot S.A.','Francia'),
('Renault',1,'Groupe Renault','Francia'),
('Seat',1,'Sociedad Española de Autos de Turismo','España'),
('Tesla',0,'Tesla Motors','EUA'),('Volvo',0,'Volvo cars','Suecia'),
('VW',1,' Volkswagen Nutzfahrzeuge','Alemania');
UNLOCK TABLES;

-- Table structure for table `modeloauto`

DROP TABLE IF EXISTS `modeloauto`;
CREATE TABLE `modeloauto` (
  `claveModelo` varchar(255) COLLATE latin1_spanish_ci NOT NULL,
  `importado` bit(1) NOT NULL,
  `nombre` varchar(255) COLLATE latin1_spanish_ci DEFAULT NULL,
  `version` varchar(255) COLLATE latin1_spanish_ci DEFAULT NULL,
  `claveArmadora` varchar(255) COLLATE latin1_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`claveModelo`),
  KEY `FKfjwyalt39yfbmxxgp8n2a2af6` (`claveArmadora`),
  CONSTRAINT `FKfjwyalt39yfbmxxgp8n2a2af6` FOREIGN KEY (`claveArmadora`) REFERENCES `armadora` (`clave`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;

-- Dumping data for table `modeloauto`

LOCK TABLES `modeloauto` WRITE;
INSERT INTO `modeloauto` VALUES 
('500',1,'Fiat 500 Diabolo','200 Caballos Turbo','Fiat'),
('GolfGTI',1,'Golf GTI DSG','Turbo GTI Stronic','VW'),
('Jetta','\0','Jetta A4 Trendline','Automatic','VW'),
('LoboHD',1,'Lobo Harley Davidson','Harley Davidson','Ford'),
('Mustang',1,'Mustang GT 500','500 Caballos','Ford'),
('Panda','\0','Fiat Panda','Estándar','Fiat'),
('Spark','\0','Spark Std Aus','Estándar austero','GM'),
('X3',1,'Serie 3 SUV','Luxe','BMW');
UNLOCK TABLES;

-- Dump completed on 2021-12-22 22:38:35