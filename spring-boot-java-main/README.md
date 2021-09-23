# spring-boot-java-main

## Contents

- httpclient testing
- javamain
    - awt, mousemove
    - date, DateCalculator, formatter
    - ConvertJson2Pojo, test
    - EmployeePredicateRepository, with params
    - ThreadLocal, SimpleDateFormat
    - QuickProducerConsumerMain, consuming by sequence, *static BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);*
    - JsonUtils
    
- JJWTDemo, UUID
- javers, object compare
- TestFizzBuzz

## Migrating from *github/spring-main*

### To get rid of big switch or lots of if-else's

- FunctionObjectTest, get rid of big switch
  
see FunctionObjectTest.java, function pointer to be used according to passed in class.

- https://www.fatalerrors.org/a/0ttz1A.html

  - Spring way
```
@Component("dataSourceProcessor#mysql")
public class MysqlProcesser extends AbstractDataSourceProcesser<MysqlQueryInput>{

@Component("dataSourceProcessor#tidb")
public class TidbProcesser extends AbstractDataSourceProcesser<TidbQueryInput>{
```

I can use Spring to help us load all beans inherited from abstractdatasourceprocessor at one time, such as map < string, abstractdatasourceprocessor >. Key is the name of the Bean, and Value is the corresponding Bean:

```
@Service
public class QueryDataServiceImpl implements QueryDataService {
  @Resource
  public Map<String, AbstractDataSourceProcesser> dataSourceProcesserMap;
  public static String beanPrefix = "dataSourceProcessor#";
  
  @Override
  public List<HashMap> queryData(QueryInputDomain domain) {
    AbstractDataSourceProcesser dataSourceProcesser = dataSourceProcesserMap.get(beanPrefix + domain.getDataSource());
    //Omit query code
  }
}

```

  - non-Spring, java reflection

It should be noted that this method obtains the instance of the class through the className, and the front-end pass parameter will not pass the className. Therefore, enumeration classes can be used to define the class names of different data sources:


```

public static String classPrefix = "com.lyl.java.advance.service.";

AbstractDataSourceProcesser sourceGenerator = 
(AbstractDataSourceProcesser) Class.forName
(classPrefix+DataSourceEnum.getClasszByCode(domain.getDataSource()))
.newInstance();

public enum DataSourceEnum {
    mysql("mysql", "MysqlProcesser"),
    tidb("tidb", "TidbProcesser");
    private String code;
    private String classz;

```

### Design Patterns



