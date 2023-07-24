INSERT INTO CUSTOMER_INFO(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AGE) VALUES (9029, 'Tamara', 'Cormier', 36);
INSERT INTO CUSTOMER_INFO(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AGE) VALUES (8881, 'Eden', 'Erdman', 41); 
INSERT INTO CUSTOMER_INFO(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AGE) VALUES (1408, 'Krystal', 'Reichert', 38); 
INSERT INTO CUSTOMER_INFO(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AGE) VALUES (2209, 'Freda', 'Mann', 12); 
INSERT INTO CUSTOMER_INFO(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AGE) VALUES (3307, 'Norval', 'Orn', 19); 
INSERT INTO CUSTOMER_INFO(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AGE) VALUES (4921, 'Brando', 'Funk', 19); 
INSERT INTO CUSTOMER_INFO(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AGE) VALUES (7546, 'Vergie', 'Boyle', 26); 
INSERT INTO CUSTOMER_INFO(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AGE) VALUES (7075, 'Jaycee', 'Kunze', 35); 
INSERT INTO CUSTOMER_INFO(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AGE) VALUES (8475, 'Gerardo', 'Dietrich', 20); 
INSERT INTO CUSTOMER_INFO(CUSTOMER_ID, FIRST_NAME, LAST_NAME, AGE) VALUES (3836, 'Hans', 'Lang', 36);


INSERT INTO INSTITUTION (institution_name, description, created_by, created_date) VALUES ('inst1', 'inst1 desc', 'system-init', CURRENT_TIMESTAMP());
INSERT INTO INSTITUTION (institution_name, description, created_by, created_date) VALUES ('inst2', 'inst2 desc', 'system-init', CURRENT_TIMESTAMP());
INSERT INTO INSTITUTION (institution_name, description, created_by, created_date) VALUES ('inst3', 'inst3 desc', 'system-init', CURRENT_TIMESTAMP());


INSERT INTO branch (institution_id, branch_name, description, created_by, created_date) VALUES (1, 'branch11', 'branch11 desc', 'system-init', CURRENT_TIMESTAMP());

INSERT INTO branch (institution_id, branch_name, description, created_by, created_date) VALUES (2, 'branch21', 'branch21 desc', 'system-init', CURRENT_TIMESTAMP());
INSERT INTO branch (institution_id, branch_name, description, created_by, created_date) VALUES (2, 'branch22', 'branch22 desc', 'system-init', CURRENT_TIMESTAMP());

INSERT INTO branch (institution_id, branch_name, description, created_by, created_date) VALUES (3, 'branch31', 'branch31 desc', 'system-init', CURRENT_TIMESTAMP());
INSERT INTO branch (institution_id, branch_name, description, created_by, created_date) VALUES (3, 'branch32', 'branch32 desc', 'system-init', CURRENT_TIMESTAMP());
INSERT INTO branch (institution_id, branch_name, description, created_by, created_date) VALUES (3, 'branch33', 'branch33 desc', 'system-init', CURRENT_TIMESTAMP());

