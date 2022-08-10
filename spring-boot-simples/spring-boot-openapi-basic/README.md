
# spring-boot-openapi-basic

- Ref:

https://github.com/purnima-jain/openapi-integration

## Swagger Url

http://localhost:8080/swagger-ui.html


http://localhost:8080/api/v1/customers

## next topic

```
<plugin>
                <groupId>org.openapitools</groupId>
                <artifactId>openapi-generator-maven-plugin</artifactId>
                <version>${openapi-generator.version}</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                        <configuration>
                            <inputSpec>
                                ${project.basedir}/src/main/resources/petstore.yml
                            </inputSpec>
                            <generatorName>spring</generatorName>
                            <apiPackage>com.oopsmails.openapi.api</apiPackage>
                            <modelPackage>com.oopsmails.openapi.model</modelPackage>
                            <supportingFilesToGenerate>
                                ApiUtil.java
                            </supportingFilesToGenerate>
                            <configOptions>
                                <delegatePattern>true</delegatePattern>
                            </configOptions>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

```

https://stackoverflow.com/questions/71528720/unable-to-inherit-in-openapi-3-using-allof-in-java


```
Foo:
  allOf:
    - $ref: "#/components/schemas/Bar"
  properties:
    ancestors:
      items:
        $ref: "#/components/schemas/Bar"
      type: array
    description:
      type: object
      additionalProperties:
        type: string
    id:
      description: id
      type: string
  type: object
Bar:
  properties:
    accessAllowed:
      items:
        type: string
      type: array
    catalog:
      type: boolean
    children:
      items:
        $ref: "#/components/schemas/Bar"
      type: array

```