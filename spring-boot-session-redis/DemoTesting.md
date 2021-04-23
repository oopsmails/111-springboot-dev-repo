
# Demo Testing


## Testing using APPs

- Run Redis server, using docker

springboot-session-redis/docker-compose.yml

`docker-compose up `

- Start the UI application now and let’s login with curl.

curl -X POST -F "username=user" -F "password=password" -v http://localhost:8080/login

```
albert@eosvm:~/Documents/github/general-info$ curl -X POST -F "username=user" -F "password=password" -v http://localhost:8080/login
Note: Unnecessary use of -X or --request, POST is already inferred.
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> POST /login HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.58.0
> Accept: */*
> Content-Length: 252
> Content-Type: multipart/form-data; boundary=------------------------317d62e9cd2b17dc
> 
< HTTP/1.1 302 
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Set-Cookie: SESSION=22727b5c-b69d-4319-bc06-11f247a306cf; Path=/; HttpOnly; SameSite=Lax
< Location: http://localhost:8080/
< Content-Length: 0
< Date: Fri, 23 Apr 2021 13:46:25 GMT
* HTTP error before end of send, stop sending


```

- This step was omitted by following because of only demoing .... 

`cookieSerializer.setUseBase64Encoding(false);`

As mentioned before, the session id is base64 encoded in the cookie, and you can not directly use the value received by the login and input it here. You can either decode it on the command line (on *nix and Macs):

`echo 22727b5c-b69d-4319-bc06-11f247a306cf | base64 -D`

- Try the SESSION is working by calling API by curl, result, seeing response "user" ...

`curl http://localhost:8081/name -v -H "X-Auth-Token: 22727b5c-b69d-4319-bc06-11f247a306cf"`

```
albert@eosvm:~/Documents/github/general-info$ curl http://localhost:8081/name -v -H "X-Auth-Token: 22727b5c-b69d-4319-bc06-11f247a306cf"
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8081 (#0)
> GET /name HTTP/1.1
> Host: localhost:8081
> User-Agent: curl/7.58.0
> Accept: */*
> X-Auth-Token: 22727b5c-b69d-4319-bc06-11f247a306cf
> 
< HTTP/1.1 200 
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Type: text/plain;charset=UTF-8
< Content-Length: 4
< Date: Fri, 23 Apr 2021 14:04:05 GMT
< 
* Connection #0 to host localhost left intact
user

```

## Demo for connecting Redis server running with docker-compose

- To find container ID, 

`docker ps -a`

```
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
3ca0c981a45f        redis:alpine        "docker-entrypoint.s…"   32 seconds ago      Up 30 seconds       0.0.0.0:6379->6379/tcp   springboot-session-redis_redis_1
```

- To connect to Redis docker container to use bash

```
docker exec -it 3ca0c981a45f sh

redis-cli -h localhost -p 6379 -a redis_password

keys *

```

```
albert@eosvm:~/$ docker exec -it 1eb5c2b81899 sh
/data # ls
/data # redis-cli -h localhost -p 6379 -a redis_password
Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
localhost:6379> keys *
1) "spring:session:index:org.springframework.session.FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME:user"
2) "spring:session:sessions:302220eb-49aa-42ad-abe6-25e0f0017f38"
3) "spring:session:sessions:expires:302220eb-49aa-42ad-abe6-25e0f0017f38"
4) "spring:session:expirations:1619189220000"
localhost:6379>

```


rdcli -h 192.168.99.100
rdcli -h localhost


redis-cli -h host.domain.com -p port -a yourpassword

redis-cli -h localhost -p 6379 -a sOmE_sEcUrE_pAsS


