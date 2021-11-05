# Talk-ScalablePersistenceLayersSpringDataJPA

The code samples used in my talk "Building Fast and Scalable Persistence Layers with Spring Data JPA".

- You can download the slides at 

https://thorben-janssen.com/talkscalable-persistence-layers-spring-data-jpa/

- video:

https://www.youtube.com/watch?v=smyFi4OCHDE

- git:

https://github.com/thjanssen/Talk-ScalablePersistenceLayersSpringDataJPA

Thanks!!!!

## Learning

### TestDemo

Notes in TestDemo.java

ChessPlayer class, don't put @JsonIgnore for testing, though this is circular referencing for Jackson.

### Using Lombok

For Entity classes, better to use @Getter and @Setter instead of @Data

@Data includes @HashCode and @Equals, could be causing following exception when having recursive references, e.g, @ManyToMany ...

```
HHH000100: Fail-safe cleanup (collections) : org.hibernate.engine.loading.internal.CollectionLoadContext
```

In my case it was because of entities calling each other's hashcode recursively, if you use lombok remove it and make it yourself.Put breakpoint of debugger on the methods of two hashcodes. You'll sea that they are calling each other. Remove for example from the first entity's hashcode method second entity's link.

So, only using @Getter and @Setter is safe.

### Circular Reference

```
java.lang.StackOverflowError: null

at com.fasterxml.jackson.databind.ser.std.CollectionSerializer.serialize(CollectionSerializer.java:107)
```

- Ref:

https://stackoverflow.com/questions/41407921/eliminate-circular-json-in-java-spring-many-to-many-relationship



Four options:

- Option 1: Using @JsonIgnore along with @ManyToMany, for example

This could be used combined with DTOs

```
@ManyToMany(mappedBy = "players") //, fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
    @JsonIgnore
    private Set<ChessTournament> tournaments;
    
```

- Option 2: Using the @JsonIgnoreProperties annotation is another alternative:

```
see http://springquay.blogspot.com/2016/01/new-approach-to-solve-json-recursive.html for more details

@Entity
public class Student extends AbstractUser {
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Group.class)
    @JoinTable(name = "GROUPS_STUDENTS",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") })
    @JsonIgnoreProperties("students")
    private List<Group> groups = new ArrayList<Group>();
}

@Entity
public class Group implements Item, Serializable {
    @ManyToMany(mappedBy = "groups", targetEntity = Student.class)
    @JsonIgnoreProperties("groups")
    private List<Student> students;
}

```


- Option 3: To solve jackson infinite recursion you can use @JsonManagedReference, @JsonBackReference.

@JsonManagedReference is the forward part of reference – the one that gets serialized normally.
@JsonBackReference is the back part of reference – it will be omitted from serialization.

```
public class Student extends AbstractUser {
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Group.class)
    @JoinTable(name = "GROUPS_STUDENTS",
            joinColumns = { @JoinColumn(name = "student_id") },
            inverseJoinColumns = { @JoinColumn(name = "group_id") })
    @JsonManagedReference
    private List<Group> groups = new ArrayList<Group>();
}

public class Group implements Item, Serializable {
    @ManyToMany(mappedBy = "groups", targetEntity = Student.class)
    @JsonBackReference
    private List<Student> students;
}
```

- Option 4: Using custom serializer. This is similar to Option 1, flexible.

```
public class CustomStudentSerializer extends StdSerializer<List<Student>> {

    public CustomStudentSerializer() {
        this(null);
    }

    public CustomStudentSerializer(Class<List<Student>> t) {
        super(t);
    }

    @Override
    public void serialize(
            List<Student> students,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        List<Student> studs = new ArrayList<>();
        for (Student s : students) {
            s.setGroups(null);
            studs.add(s);
        }
        generator.writeObject(studs);
    }
}
```
