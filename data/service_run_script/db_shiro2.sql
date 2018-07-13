--
-- Table structure for table `hzdy_equipment2`
--

DROP TABLE IF EXISTS `hzdy_equipment2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hzdy_equipment2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `equipmentName` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ip` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL,
  `macAddress` varchar(15) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `network` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT '1',
  `managementAgreement` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `warnNum` int(11) NOT NULL DEFAULT '0',
  `onlineStatus` int(11) NOT NULL DEFAULT '1',
  `department` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
