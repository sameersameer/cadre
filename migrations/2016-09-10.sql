-- Create syntax for TABLE 'campaign'
CREATE TABLE `campaign` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `advertiser_id` varchar(32) NOT NULL DEFAULT '',
  `campaign_name` varchar(200) NOT NULL DEFAULT '',
  `advertiser_name` varchar(200) NOT NULL DEFAULT '',
  `campaign_desc` text NOT NULL,
  `start_date` timestamp NULL DEFAULT NULL,
  `end_date` timestamp NULL DEFAULT NULL,
  `distance_cap` int(11) NOT NULL DEFAULT '-1',
  `num_of_drivers` int(11) NOT NULL DEFAULT '-1',
  `status` enum('DRAFT','READY','RUNNING','COMPLETE') NOT NULL DEFAULT 'READY',
  `total_budget` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12390 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'creative'
CREATE TABLE `creative` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `campaign_id` int(11) NOT NULL,
  `creative_url` text NOT NULL,
  `decal_type` enum('FULL','HALF','PANEL') DEFAULT NULL,
  `is_primary` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'device_campaign_mapping'
CREATE TABLE `device_campaign_mapping` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` varchar(32) NOT NULL DEFAULT '',
  `campaign_id` int(11) NOT NULL,
  `creative_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'driver'
CREATE TABLE `driver` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` varchar(32) NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL,
  `driver_name` varchar(100) NOT NULL DEFAULT 'John Doe',
  `age` int(11) DEFAULT NULL,
  `experience` int(11) DEFAULT NULL,
  `contact_number` varchar(30) NOT NULL DEFAULT '',
  `contact_mail` varchar(30) NOT NULL DEFAULT '',
  `vehicle_id` int(11) NOT NULL,
  `driver_status` enum('REGISTERED','ONBOARDED','ACTIVE','INACTIVE') NOT NULL DEFAULT 'INACTIVE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'location_info'
CREATE TABLE `location_info` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` varchar(32) NOT NULL DEFAULT '',
  `latitude` decimal(9,6) NOT NULL,
  `longitude` decimal(9,6) NOT NULL,
  `timestamp` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (
  `id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'severity'
CREATE TABLE `severity` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` varchar(32) NOT NULL DEFAULT '',
  `lat1` decimal(9,6) NOT NULL,
  `long1` decimal(9,6) NOT NULL,
  `lat2` decimal(9,6) NOT NULL,
  `long2` decimal(9,6) NOT NULL,
  `time1` bigint(20) NOT NULL,
  `time2` bigint(20) NOT NULL,
  `severity` tinyint(4) NOT NULL,
  `distance` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 'stats'
CREATE TABLE `stats` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `device_id` varchar(32) NOT NULL DEFAULT '',
  `mileage_d` bigint(20) NOT NULL,
  `mileage_w` bigint(20) NOT NULL,
  `mileage_t` bigint(20) NOT NULL,
  `earnings_d` bigint(20) NOT NULL,
  `earnings_w` bigint(20) NOT NULL,
  `earnings_t` bigint(20) NOT NULL,
  `last_updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
