# Spring Boot, Security, MongoDB, Angular 8: Build Authentication

This source code is part of [Spring Boot, Security, MongoDB, Angular 8: Build Authentication](https://www.djamware.com/post/5d3332980707cc65eac46c7b/spring-boot-security-mongodb-angular-8-build-authentication) tutorial


## MongoDB:

### Spring properties file configured to connect to local docker mongodb.

```
spring.data.mongodb.database=springmongodb
#spring.data.mongodb.host=localhost
spring.data.mongodb.host=192.168.99.100
spring.data.mongodb.port=27017
```

****Use nosqlbooster4mongo-5.1.12.exe as mongo db GUI**

nosqlbooster4mongo-6.2.8.AppImage

### Sample data:

`sudo docker exec -it mongodb bash`

```
use springmongodb

db.createCollection("product")
db.createCollection("roles")
db.createCollection("users")

db.roles.save({ role: "ADMIN" })
db.roles.save({ role: "USER" })
```

```
db.product.save([
    {
        "prodName": "Lego Ninja",
        "prodDesc": "Toy abc",
        "prodPrice": 56.66,
        "prodImage": "abc.png"
    },
    {
        "prodName": "Lego Mario",
        "prodDesc": "Toy cde",
        "prodPrice": 88.88,
        "prodImage": "bcd.png"
    }
]
)
```


#### About @DBRef

https://stackoverflow.com/questions/44259583/spring-mongo-db-dbref

https://www.baeldung.com/cascading-with-dbref-and-lifecycle-events-in-spring-data-mongodb

- Need to run following updates to link roles for the user

- Copy the roles to be used in update

```
{
"_id": "ObjectId(\"600595a460c6d5d5e37401a0\")",
"role": "USER"
},
{
"_id": "ObjectId(\"600595a160c6d5d5e374019f\")",
"role": "ADMIN"
}

```

```

db.users.update({ _id: ObjectId("5d40a41593cd1d5864705b1e") }, {
    $set: {
        "email": "test1@abc.com",
        "password": "$2a$10$mtspWGa1HYagmh7lGiVv.u8WdJCwz7MJ4vSwzfImsuvcF9bDoEwWG",
        "enabled": true,
        "roles": [
            { "$ref":"roles",
               "$id" :ObjectId("600595a460c6d5d5e37401a0")
             },
             { "$ref":"roles",
               "$id":ObjectId("600595a160c6d5d5e374019f")
             }
        ],
        "_class": "com.oopsmails.springangularauth.models.User"
    }
})


db.users.update({ _id: ObjectId("5d40a41593cd1d5864705b1e") }, {
    $set: {
        "email": "test1@abc.com",
        "password": "$2a$10$VzIm8k0v0wOuD.TdEtwvZeIa.JS9D4bIN8qekgo7sRTxbBpUEVGnq",
        "enabled": true,
        "roles": [
            DBRef("roles", ObjectId("600595a460c6d5d5e37401a0")),
            DBRef("roles", ObjectId("600595a160c6d5d5e374019f")),
        ],
        "_class": "com.oopsmails.springangularauth.models.User"
    }
})

```


## Trouble Shooting

### Spring Boot: 

- Spring Security: Only adding "@PreAuthorize" not working, i.e, not seeing 403 error when using ROLE_VIEWER vs ADMIN(or USER)

```
@PreAuthorize("hasRole('ROLE_VIEWER') or hasRole('ROLE_EDITOR')")
    @RequestMapping(method = RequestMethod.GET, value = "/product")
    public Iterable<Products> product() {
        return productRepository.findAll();
    }

```

Need to add following in configuration class,

@EnableWebSecurity 
@EnableGlobalMethodSecurity(prePostEnabled = true)

```
@SpringBootApplication
@EnableWebSecurity // <---------- here 
@EnableGlobalMethodSecurity(prePostEnabled = true)// <---------- here 
public class SpringAngularAuthApplication {

```

- spring boot rolevoter rolePrefix, by default adding role prefix "ROLE_"

https://stackoverflow.com/questions/38134121/how-do-i-remove-the-role-prefix-from-spring-security-with-javaconfig

```
@Bean
	public GrantedAuthorityDefaults grantedAuthorityDefaults() {
		return  new GrantedAuthorityDefaults("");
	}
```

### JDK8 to JDK11

- javax.xml ...

``````
status: 500
statusText: "OK"
url: "http://localhost:8080/api/auth/login"
ok: false
name: "HttpErrorResponse"
message: "Http failure response for http://localhost:8080/api/auth/login: 500 OK"
```

Solution: build.gradle

```
	implementation 'jakarta.xml.bind:jakarta.xml.bind-api:2.3.3'
	implementation 'org.glassfish.jaxb:jaxb-runtime:2.3.3'

	annotationProcessor 'jakarta.xml.bind:jakarta.xml.bind-api:2.3.3'
	annotationProcessor 'org.glassfish.jaxb:jaxb-runtime:2.3.3'
	annotationProcessor 'javax.annotation:javax.annotation-api:1.3.2'
```


