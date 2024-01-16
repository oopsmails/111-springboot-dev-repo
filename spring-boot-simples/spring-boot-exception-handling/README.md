
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


## 20240115: fixing Unit Test


found multiple declarations of @BootstrapWith for test class

https://stackoverflow.com/questions/66485049/configuration-error-found-multiple-declarations-of-bootstrapwith-for-test-clas

```
/*
We cannot use @WebMvcTest(BookingController.class) as we need the injection of the component used by the BookingController (BookingService)
@AutoConfigureMockMvc is necessary for the MockMvc instantiation.
*/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ContextConfiguration(classes = BackendApplicationTest.class)
/*
We cannot use the @DataJpaTest annotation
Otherwise we will have the error "Configuration error: found multiple declarations of @BootstrapWith" (present in @SpringBootTest and @WebMvcTest)
But we need the @AutoConfigureDataJpa, @AutoConfigureTestDatabase and @AutoConfigureTestEntityManager annotations to autowire the BookingRepository
*/
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Sql({"/sql/insert-rooms.sql", "/sql/insert-accounts.sql", "/sql/insert-five-reservations.sql"})
// Reset the h2 database state after each test, otherwise the insert will be done twice and will fail
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookingControllerTest {

```

## Spring JPA

## solve the problem of Infinite recursion

Ref: 
- https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
- https://github.com/WeBankFinTech/Qualitis/blob/85041c4b963d3ef41b0e1094fc4568ab4aad1c45/core/project/src/main/java/com/webank/wedatasphere/qualitis/rule/entity/Template.java

### Using @JsonManagedReference and @JsonBackReference

- @JsonManagedReference is the forward part of reference, the one that gets serialized normally.
- @JsonBackReference is the back part of reference; it'll be omitted from serialization.
- The serialized Item object doesn't contain a reference to the User object.

```
    @OneToMany(targetEntity = Branch.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "institution_id", nullable = false)
    @JsonManagedReference
//    @JsonBackReference
//        @JsonIgnore // can also use this for stackOverflow error
    private List<Branch> branchList;
    

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "institution_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
//    @JsonIgnore
    private Institution institution;

```


Also note that we can't switch around the annotations. The following will work for the serialization:

```
@JsonBackReference
public List<Item> userItems;

@JsonManagedReference
public User owner;
```

But when we attempt to deserialize the object, it'll throw an exception, **as @JsonBackReference can't be used on a collection**.

If we want to have the serialized Item object contain a reference to the User, we need to use **@JsonIdentityInfo**. 

The @JsonIgnore is an alternative  for the @JsonBackReference. So you can used @JsonIgnore in place of @JsonBackReference

### Using @JsonIdentityInfo

```
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")

```
As of 20230630: Using this along, can deal with Institution Entity well, but Branch Entity is NOT dealt well

### Working Version

- Using @JsonManagedReference and @JsonBackReference
- Because in Branch, it is ManyToOne, so use EAGER `@ManyToOne(fetch = FetchType.EAGER)`
- See retrieveBranchByIdV2() in controller, the Institution is actually there, it's just cannot be displayed.


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



