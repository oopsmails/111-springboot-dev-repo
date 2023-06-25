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

CREATE TABLE employee
(
	employee_id VARCHAR(100) NOT NULL,
	first_name VARCHAR(100) NOT NULL,
	last_name VARCHAR(100),
	email_id VARCHAR(100),
	organization_id VARCHAR(100),
	department_id VARCHAR(100),
	created_by VARCHAR(100),
	created_date datetime,

    PRIMARY KEY ( employee_id )
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


CREATE TABLE institution
(
	institution_id BIGINT GENERATED ALWAYS AS IDENTITY,
	institution_name VARCHAR(100) NOT NULL,
	description VARCHAR(300),
	created_by VARCHAR(100),
	created_date datetime,

    PRIMARY KEY ( institution_id )
);


CREATE TABLE branch
(
--	branch_id VARCHAR(100) NOT NULL,
--	branch_id VARCHAR(100) GENERATED ALWAYS AS IDENTITY,
    branch_id BIGINT GENERATED ALWAYS AS IDENTITY,
	institution_id BIGINT,
	branch_name VARCHAR(100) NOT NULL,
	description VARCHAR(300),
	created_by VARCHAR(100),
	created_date datetime,

    PRIMARY KEY ( branch_id ),
    CONSTRAINT fk_branch_institution
    FOREIGN KEY (institution_id)
    REFERENCES institution (institution_id)
);


--ALTER TABLE branch
--ADD COLUMN institution_id VARCHAR(100),
--ADD CONSTRAINT fk_branch_institution
--FOREIGN KEY (institution_id)
--REFERENCES institution (institution_id);

