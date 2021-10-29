package com.oopsmails.common.annotation.crypto;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Slf4j
@Aspect
@Component
public class CryptoCheckAspect {
    @Autowired
    private HmacConverter hmacConverter;

    @Autowired
    private Environment environment;

    @Value("${mockbackend.employee.crypto.enabled:true}")
    private boolean isCryptoEnabled;

    @Around("@annotation(com.oopsmails.common.annotation.crypto.CryptoCheck)")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("@CryptoCheck annotation starting ...");

        Object[] args = proceedingJoinPoint.getArgs();

        if (isCryptoEnabled) {
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = methodSignature.getMethod();

            CryptoCheck cryptoCheck = method.getAnnotation(CryptoCheck.class);
            String mandatoryProperty = cryptoCheck.mandatoryProperty();
            boolean isMandatory = !StringUtils.isEmpty(mandatoryProperty) && "true".equals(environment.getProperty(mandatoryProperty));
            String cryptoSecretProperty = cryptoCheck.cryptoSecretProperty();
            String secret = StringUtils.isEmpty(cryptoSecretProperty) ? null : environment.getProperty(cryptoSecretProperty);

            if (isMandatory && StringUtils.isEmpty(secret)) {
                log.error("Crypto <secret> should not be empty!");
            }

            Parameter[] parameters = method.getParameters();

            String cryptoCheckPayload = null;
            String cryptoCheckSignature = null;

            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].getAnnotation(CryptoCheckPayload.class) != null) {
                    cryptoCheckPayload = args[i].toString();
                }

                if (parameters[i].getAnnotation(CryptoCheckSignature.class) != null) {
                    cryptoCheckSignature = args[i].toString();
                }
            }

            if (isMandatory && cryptoCheckPayload == null) {
                log.error("Crypto <payload> invalid!");
                throw new IllegalAccessException("Invalid Crypto <payload>");
            }

            if (isMandatory && cryptoCheckSignature == null) {
                log.error("Crypto <signatureHeader> invalid!");
                throw new IllegalAccessException("Invalid Crypto <signatureHeader>");
            }

            if (cryptoCheckPayload != null && cryptoCheckSignature != null && !StringUtils.isEmpty(secret)) {
                String cryptoResult = hmacConverter.digest(cryptoCheckPayload, secret);
                if (!cryptoCheckSignature.equals(cryptoResult)) {
                    log.error("HMAC validation failed! signatureHeader: [{}], payload: [{}], cryptoResult: [{}]", cryptoCheckSignature, cryptoCheckPayload, cryptoResult);
                    throw new IllegalAccessException("Invalid Access!!");
                }
            }
        } else {
            log.info("CryptoCheck check is not enabled.");
        }

        log.info("@CryptoCheck annotation ending ...");
        return proceedingJoinPoint.proceed(args);
    }
}
