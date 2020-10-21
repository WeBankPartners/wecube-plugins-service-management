SET FOREIGN_KEY_CHECKS = 0;

drop table if exists `service_catalogue`;
CREATE TABLE `service_catalogue` (
    `id` VARCHAR(32) NOT NULL ,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    `status` VARCHAR(32) NULL default null,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

drop table if exists `attach_file`;
CREATE TABLE `attach_file` (
    `id` VARCHAR(32) NOT NULL,
    `attach_file_name` VARCHAR(255) NULL,
    `s3_url` VARCHAR(2048) NULL,
    `s3_bucket_name` VARCHAR(64) NULL,
    `s3_key_name` VARCHAR(256) NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

drop table if exists `service_pipeline`;
CREATE TABLE `service_pipeline` (
    `id` VARCHAR(32) NOT NULL ,
    `service_catalogue_id` VARCHAR(32) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    `owner_role` VARCHAR(64) NULL,
    `status` VARCHAR(32) NULL DEFAULT null,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_service_catalogue_service_pipeline` FOREIGN KEY (`service_catalogue_id`) REFERENCES `service_catalogue` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

drop table if exists `service_request_template`;
CREATE TABLE `service_request_template` (
    `id` VARCHAR(32) NOT NULL ,
    `service_pipeline_id` VARCHAR(32) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    `process_definition_key`  VARCHAR(255) NOT NULL,
    `status` VARCHAR(32) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `service_pipeline_and_name` (`service_pipeline_id`, `name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

drop table if exists `service_request`;
CREATE TABLE `service_request` (
    `id` VARCHAR(32) NOT NULL ,
    `template_id` VARCHAR(32) NOT NULL ,
    `name` VARCHAR(255) NOT NULL,
    `reporter` VARCHAR(64) NULL DEFAULT NULL,
    `report_role` VARCHAR(64) NULL DEFAULT NULL,
    `report_time` datetime DEFAULT NULL,
    `emergency` VARCHAR(32) NULL,
    `description` VARCHAR(255) NOT NULL,
    `attach_file_id` VARCHAR(32) NULL DEFAULT NULL,
    `result` VARCHAR(1024) NULL,
    `status` VARCHAR(32) NULL DEFAULT NULL,
    `env_type` VARCHAR(32) NULL DEFAULT 'TEST',
    `request_no` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_template_id` (`template_id`),
    CONSTRAINT `fk_service_request_template_service_request` FOREIGN KEY (`template_id`) REFERENCES `service_request_template` (`id`),
    CONSTRAINT `fk_attach_file_service_request` FOREIGN KEY (`attach_file_id`) REFERENCES `attach_file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

drop table if exists `task`;
CREATE TABLE `task` (
    `id` VARCHAR(32) NOT NULL ,
    `service_request_id` INT(11) NULL ,
    `callback_url` VARCHAR(255) NULL DEFAULT NULL,
    `name` VARCHAR(255) NOT NULL,
    `reporter` VARCHAR(64) NULL DEFAULT NULL,
    `report_time` datetime DEFAULT NULL,
    `operator_role` VARCHAR(64) NULL DEFAULT NULL,
    `operator` VARCHAR(64) NULL DEFAULT NULL,
    `operate_time` datetime DEFAULT NULL,
    `input_parameters` TEXT NULL DEFAULT NULL,
    `description` VARCHAR(2048) NULL DEFAULT NULL,
    `result` VARCHAR(32) NULL DEFAULT NULL,
    `result_message` VARCHAR(255) NULL DEFAULT NULL,
    `status` VARCHAR(32) NULL DEFAULT NULL,
    `request_id` VARCHAR(255) NULL ,
    `callback_parameter` VARCHAR(255) NULL ,
    `allowed_options` text NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

SET FOREIGN_KEY_CHECKS = 1;


#@v0.5.1.11-begin@;
ALTER TABLE `task` ADD COLUMN `over_time` DATETIME NULL DEFAULT NULL;
ALTER TABLE `task` ADD COLUMN `due_date` VARCHAR(32) NULL DEFAULT NULL ;
#@v0.5.1.11-end@;

#@v0.6.3.0-begin@;
ALTER TABLE `service_request` ADD COLUMN `root_data_id` VARCHAR(255) NULL DEFAULT NULL;
ALTER TABLE `service_request` ADD COLUMN `proc_inst_id` VARCHAR(255) NULL DEFAULT NULL;
ALTER TABLE `service_request_template` ADD COLUMN `proc_def_name` VARCHAR(255) NULL DEFAULT NULL;
ALTER TABLE `task` modify service_request_id varchar(32);
#@v0.6.3.0-end@;