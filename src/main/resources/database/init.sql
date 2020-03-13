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

drop table if exists `task`;
CREATE TABLE `task` (
    `id` VARCHAR(32) NOT NULL ,
    `service_request_id` INT(11) NULL ,
    `callback_url` VARCHAR(255) NULL DEFAULT NULL,
    `name` VARCHAR(64) NOT NULL,
    `reporter` VARCHAR(64) NULL DEFAULT NULL,
    `report_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `operator_role` VARCHAR(64) NULL DEFAULT NULL,
    `operator` VARCHAR(64) NULL DEFAULT NULL,
    `operate_time` TIMESTAMP NULL,
    `input_parameters` TEXT NULL DEFAULT NULL,
    `description` VARCHAR(128) NULL DEFAULT NULL,
    `result` VARCHAR(32) NULL DEFAULT NULL,
    `result_message` VARCHAR(255) NULL DEFAULT NULL,
    `status` VARCHAR(32) NULL DEFAULT NULL,
    `request_id` VARCHAR(255) NULL ,
    `callback_parameter` VARCHAR(255) NULL ,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

drop table if exists `service_form`;
CREATE TABLE `service_form` (
    `id` VARCHAR(32) NOT NULL ,
    `service_pipeline_id` VARCHAR(32) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NULL,
    `process_definition_key`  VARCHAR(255) NOT NULL,
    `status` VARCHAR(32) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `service_pipeline_and_name` (`service_pipeline_id`, `name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

drop table if exists `form_custom_item`;
CREATE TABLE `form_custom_item` (
    `id` VARCHAR(32) NOT NULL,
    `service_form_id` VARCHAR(32) NOT NULL,
    `name` VARCHAR(50) NULL DEFAULT NULL,    
    `display_name` VARCHAR(50) NULL DEFAULT NULL,   
    `data_type` VARCHAR(50) NULL DEFAULT NULL,  /* string , int */
    `length` VARCHAR(50) NULL DEFAULT NULL,

    PRIMARY KEY (`id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists `custom_item__process_node`;
CREATE TABLE `custom_item__process_node` (
    `id` VARCHAR(32) NOT NULL,
    `custom_item_id` VARCHAR(32) NOT NULL,
    `process_node_id` VARCHAR(255) NULL DEFAULT NULL,    
    `input_type` VARCHAR(50) NULL DEFAULT NULL, /* input , output */   

    PRIMARY KEY (`id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;


drop table if exists `service_ticket`;
CREATE TABLE `service_ticket` (
    `id` VARCHAR(32) NOT NULL ,
    `service_form_id` VARCHAR(32) NOT NULL ,
    `title` VARCHAR(32) NOT NULL,
    `emergency` VARCHAR(32) NULL,
    `description` VARCHAR(255) NOT NULL,
    `attach_file_id` VARCHAR(32) NULL DEFAULT NULL,
    `result` VARCHAR(1024) NULL,
    `status` VARCHAR(32) NULL DEFAULT NULL,
    `env_type` VARCHAR(32) NULL DEFAULT 'TEST',
    `request_no` BIGINT NOT NULL, 
    `report_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `process_instance_id`  VARCHAR(255) NOT NULL,
    `status` VARCHAR(32) NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `idx_service_form_id` (`service_form_id`),
    CONSTRAINT `fk_service_form_service_ticket` FOREIGN KEY (`service_form_id`) REFERENCES `service_form` (`id`),
    CONSTRAINT `fk_attach_file_service_ticket` FOREIGN KEY (`attach_file_id`) REFERENCES `attach_file` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

drop table if exists `ticket_custom_item`;
CREATE TABLE `ticket_custom_item` (
    `id` VARCHAR(32) NOT NULL,
    `service_ticket_id` VARCHAR(32) NOT NULL,
    `form_custom_item_id` VARCHAR(32) NOT NULL,    
    `value` VARCHAR(1024) NULL DEFAULT NULL,   

    PRIMARY KEY (`id`)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;