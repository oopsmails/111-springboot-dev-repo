-- Creating the table: customer_info
CREATE TABLE customer_info_mock
(
	customer_id VARCHAR(10) NOT NULL,
	first_name VARCHAR(100),
	last_name VARCHAR(100),
	age INT,
    PRIMARY KEY ( customer_id )
);

CREATE TABLE audit_event
(
	event_id VARCHAR(100) NOT NULL,
	event_source VARCHAR(100),
	event_json VARCHAR(max),
	created_by VARCHAR(100),
	created_date datetime,

    PRIMARY KEY ( event_id )
);

CREATE TABLE mutex
(
	RESOURCE_CD VARCHAR(100) NOT NULL,
	NODE_ID VARCHAR(100) NOT NULL,
	LOCK_UNTIL datetime NOT NULL,

	CREATED_BY VARCHAR(100),
	CREATED_DATE datetime,
	LAST_UPDATED_BY VARCHAR(100),
	LAST_UPDATED_DATE datetime,

	VERSION int,

    PRIMARY KEY ( RESOURCE_CD )
);

