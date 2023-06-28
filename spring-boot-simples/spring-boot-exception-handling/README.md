
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

