drop table if exists `service_request_template`;
CREATE TABLE `service_request_template` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`service_pipeline_id` INT(11) NOT NULL,
	`name` VARCHAR(255) NOT NULL,
	`process_definition_key`  VARCHAR(255) NOT NULL,
	`status` VARCHAR(32) NULL DEFAULT NULL,
	PRIMARY KEY (`id`),
	UNIQUE INDEX `service_channel_and_name` (`service_pipeline_id`, `name`)
);

drop table if exists `service_request`;
CREATE TABLE `service_request` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`template_id` INT(11) NOT NULL ,
	`name` VARCHAR(255) NOT NULL,
	`reporter_role_id` INT(11) NULL DEFAULT NULL,
	`reporter` VARCHAR(64) NULL DEFAULT NULL,
	`report_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`emergency` VARCHAR(32) NULL,
	`description` VARCHAR(255) NOT NULL,
	`attach_file_name` VARCHAR(255) NULL,
	`attach_file` MEDIUMTEXT NULL,
	`result` VARCHAR(32) NULL,
	`process_instance_id` VARCHAR(255) NULL DEFAULT NULL,
	`status` VARCHAR(32) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
);

drop table if exists `task`;
CREATE TABLE `task` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`service_request_id` INT(11) NOT NULL ,
	`process_instance_id` VARCHAR(255) NULL DEFAULT NULL,
	`callback_url` VARCHAR(255) NULL DEFAULT NULL,
	`name` VARCHAR(64) NOT NULL,
	`process_definition_key` VARCHAR(255) NOT NULL,
	`reporter` VARCHAR(64) NULL DEFAULT NULL,
	`report_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`operator_role_id` INT(11) NULL DEFAULT NULL,
	`operator` VARCHAR(64) NULL DEFAULT NULL,
	`operate_time` TIMESTAMP NULL,
	`input_parameters` TEXT NULL DEFAULT NULL,
	`description` VARCHAR(128) NULL DEFAULT NULL,
	`result` VARCHAR(32) NULL DEFAULT NULL,
	`result_message` VARCHAR(255) NULL DEFAULT NULL,
	`status` VARCHAR(32) NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
);
