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

## Other Notes

### Two-way SSL Java Example

#### ref: https://www.snaplogic.com/glossary/two-way-ssl-java-example

Secure Sockets Layer (SSL) is a standard security technology for establishing an encrypted link between a server and a client. It is widely applied during transactions involving sensitive or personal information such as credit card numbers, login credentials, and Social Security numbers. The encryption can be applied in one direction or both — one-way or two-way. In one-way SSL, the client confirms the identity of the server while the identity of the client remains anonymous. In two-way SSL, AKA mutual SSL, the client confirms the identity of the server and the server confirms the identity of the client.

Two-way SSL begins with a “hello” from the client to the server. The server replies with a “hello” paired with its public certificate. The client verifies the received certificate using certificates stored in the client’s TrustStores. If the server certificate validation is successful, the client will present certificate stores in their KeyStores. The server validates the received certificate using the server’s TrustStores. The server decrypts session keys using the server’s private key to establish a secure connection.

Java employs Java Keystore (JKS), a password-protected database for certificates and keys. Each entry must be identified by a unique alias. Keystore provides credentials.

Java also uses Truststore which is located in $JAVA_HOME/lb/security/cacerts. It stores trusted Certificate Authority (CA) entries and self-signed certificates from trusted third parties. Truststore verifies server identities.

Java also provides keytool, a command-line tool to maintain the Keystore and the Truststore.

You can run TrustStore using the following code. Replace $CERT_ALIAS and $CERT_PASSWORD with your alias and password, respectively.

* If we do not have the server certificate, we use openssl to retrieve it

```
echo -n | openssl s_client -connect SERVERDOMAIN:PORT -servername SERVERDOMAIN

-key myclient.key -cert myclient.cert.pem

| sed -ne ‘/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p’

| tee “server.crt”

```

* Create the Truststore from the server certificate

```
keytool -import -alias $CERT_ALIAS -file server.crt -keystore truststore.jks -deststorepass $CERT_PASSWORD
```

* Next, generate the IdentityStore. Replace $CERT_ALIAS and $CERT_PASSWORD with your alias and password, respectively.
```
cat intermediate.cert.pem myclient.cert.pem myclient.key > full-chain.keycert.pem
```

* Generate the PKCS12 keystore with the alias of the server url
```
openssl pkcs12 -export -in full-chain.keycert.pem

-out full-chain.keycert.p12

-password env:$CERT_PASSWORD

-name $CERT_ALIAS

-noiter -nomaciter  
```
* Convert .p12 to .jks
```
keytool -importkeystore -srckeystore full-chain.keycert.p12

-srcstoretype pkcs12 -srcalias $CERT_ALIAS -srcstorepass $CERT_PASSWORD

-destkeystore identity.jks -deststoretype jks

-deststorepass $CERT_PASSWORD -destalias $CERT_ALIAS
```
This Java code loads Keystore and the Truststore into a custom SSL context, creates a SSLConnectionSocketFactory, and then binds it to a HttpClient.

spring-boot-java-main/src/main/java/com/oopsmails/ssl/SSLMutualAuthMain.java
