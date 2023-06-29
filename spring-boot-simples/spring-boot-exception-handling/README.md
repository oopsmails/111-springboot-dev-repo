
# spring-boot-exception-handling
- Ref:

https://github.com/purnima-jain/spring-boot-exception-handling

## H2 Console

```
2023-06-24T12:25:00:092 [] [main] [INFO ] o.s.b.a.h.H2ConsoleAutoConfiguration - H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:mockdb'
```

http://localhost:8080/h2-console


## Testing

Postman:  

spring-boot-simples/spring-boot-exception-handling/spring-boot-exception-handling.postman_collection.json

## MS SQL

### Find next value of identity

```
-- Assuming "BRANCH" is the name of your table
DECLARE @nextBranchId BIGINT;

-- Get the last identity value generated for the table
SET @nextBranchId = IDENT_CURRENT('BRANCH');

-- Increment by 1 to get the next value
SET @nextBranchId = @nextBranchId + 1;

-- Print the next branch_id value
PRINT 'Next branch_id value: ' + CAST(@nextBranchId AS VARCHAR);

-- Assuming "BRANCH" is the name of your table
-- Increase the current identity value by 2
DBCC CHECKIDENT ('BRANCH', RESEED, IDENT_CURRENT('BRANCH') + 2);

```

### increasing identity by 2

```
-- Assuming "BRANCH" is the name of your table
-- Disable the identity column temporarily
SET IDENTITY_INSERT BRANCH ON;

-- Update the current identity value by incrementing it by 2
UPDATE BRANCH SET branch_id = branch_id + 2;

-- Enable the identity column again
SET IDENTITY_INSERT BRANCH OFF;
```

### Reset identity to 0, i.e, start inserting from 1

```
-- Assuming "BRANCH" is the name of your table
-- Delete all data from the table
TRUNCATE TABLE BRANCH;

-- Reset the identity value to 0
DBCC CHECKIDENT ('BRANCH', RESEED, 0);

```

### cannot truncate table because there is a foreign key .... what to do?

```
-- 1. if there is a foreign key, then cannot TRANCATE, need to delete and RESEED

delete OTHER_TABLE_FK_TO_BRANCH
DBCC CHECKIDENT ('BRANCH', RESEED, 0);

-- 2. Disable the foreign key constraint temporarily

-- Assuming "BRANCH" is the name of your table
-- Disable the foreign key constraint
ALTER TABLE BRANCH NOCHECK CONSTRAINT fk_branch_institution;

-- Delete all data from the table
TRUNCATE TABLE BRANCH;

-- Reset the identity value to 1
DBCC CHECKIDENT ('BRANCH', RESEED, 1);

-- Enable the foreign key constraint
ALTER TABLE BRANCH CHECK CONSTRAINT fk_branch_institution;

```

### Using variable to insert ...

```
-- Declare the variable
DECLARE @branchId bigint;

-- Set the variable using a SELECT statement
SELECT @branchId = branch_id
FROM branch
WHERE ...; -- Add your condition here

-- Use the variable as needed
SELECT @branchId;

-- Convert and print the variable value
PRINT 'Branch ID: ' + CONVERT(VARCHAR(100), @branchId);
-- or
PRINT 'Branch ID: ' + CAST(@branchId AS VARCHAR(100));

INSERT INTO BRANCH_SUB_TABLE (BRANCH_ID, ....)
VALUES (@branchId, ....)

```



