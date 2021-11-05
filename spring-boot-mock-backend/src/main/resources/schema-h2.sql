-- Creating the table: customer_info
CREATE TABLE customer_info
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