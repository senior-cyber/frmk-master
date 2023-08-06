package com.senior.cyber.frmk.common.simulator;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.SerializationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class SimulateFunction {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimulateFunction.class);

    public static Object simulateHttpEntity(Map<String, SimulateEnum> configuration, SimulateEnum type, Serializer serializer, Deserializer deserializer, ProceedingJoinPoint proceeding) throws Throwable {
        SimulateEnum newType = type;
        if (newType == SimulateEnum.Configuration) {
            String className = proceeding.getTarget().getClass().getName();
            String methodName = proceeding.getSignature().getName();
            String key = className + "." + methodName;
            if (configuration.get(key) != null) {
                newType = configuration.get(key);
            } else {
                newType = SimulateEnum.None;
            }
        }

        if (newType == SimulateEnum.Serialize || newType == SimulateEnum.Replay) {
            var requestDate = new Date();
            var args = new Object[proceeding.getArgs().length];
            for (int i = 0; i < args.length; i++) {
                Object arg = proceeding.getArgs()[i];
                if (arg instanceof RequestEntity<?> request) {
                    InternalRequestEntity newArg = new InternalRequestEntity();
                    newArg.setHeaders(request.getHeaders());
                    newArg.setUrl(request.getUrl());
                    newArg.setMethod(request.getMethod());
                    newArg.setType(request.getType());
                    newArg.setBody(request.getBody());
                    args[i] = newArg;
                } else if (arg instanceof Serializable) {
                    args[i] = arg;
                } else {
                    if (arg != null) {
                        throw new IllegalArgumentException(arg.getClass().getName() + " is not serializable");
                    }
                }
            }
            var requestData = SerializationUtils.serialize(args);
            String className = proceeding.getTarget().getClass().getName();
            String methodName = proceeding.getSignature().getName();
            var sha1 = Hashing.sha1().hashBytes(requestData).toString();
            Date responseDate = null;

            boolean canRead;
            byte[] responseData = null;
            if (newType == SimulateEnum.Replay) {
                try {
                    responseData = deserializer.deserialize(sha1, className, methodName);
                    responseDate = new Date();
                    canRead = true;
                } catch (NotFoundException e) {
                    canRead = false;
                }
            } else {
                canRead = false;
            }

            if (newType == SimulateEnum.Serialize || !canRead) {
                Object returnObject = null;
                boolean error = false;
                try {
                    returnObject = proceeding.proceed(proceeding.getArgs());
                    responseDate = new Date();
                    return returnObject;
                } catch (Throwable e) {
                    returnObject = e;
                    error = true;
                    throw e;
                } finally {
                    if (returnObject instanceof ResponseEntity<?> response) {
                        InternalResponseEntity r = new InternalResponseEntity();
                        r.setStatusCode(response.getStatusCode());
                        r.setBody(response.getBody());
                        r.setHeaders(response.getHeaders());
                        responseData = SerializationUtils.serialize(r);
                        serializer.serialize(sha1, className, methodName, requestDate, requestData, responseDate, responseData, error);
                    } else if (returnObject instanceof Serializable s) {
                        responseData = SerializationUtils.serialize(s);
                        serializer.serialize(sha1, className, methodName, requestDate, requestData, responseDate, responseData, error);
                    } else {
                        if (returnObject != null) {
                            throw new IllegalArgumentException(returnObject.getClass().getName() + " is not serializable");
                        } else {
                            responseData = SerializationUtils.serialize((Serializable) returnObject);
                            serializer.serialize(sha1, className, methodName, requestDate, requestData, responseDate, responseData, error);
                        }
                    }
                }
            } else {
                var returnObject = SerializationUtils.deserialize(responseData);
                if (returnObject instanceof InternalResponseEntity e) {
                    return ResponseEntity.status(e.getStatusCode()).headers(e.getHeaders()).body(e.getBody());
                } else if (returnObject instanceof Throwable e) {
                    throw e;
                } else {
                    return returnObject;
                }
            }
        } else {
            return proceeding.proceed();
        }
    }

    @FunctionalInterface
    public interface Serializer {
        void serialize(String sha1, String className, String methodName, Date requestDate, byte[] requestData, Date responseDate, byte[] responseData, boolean isError);
    }

    @FunctionalInterface
    public interface Deserializer {
        byte[] deserialize(String sha1, String className, String methodName) throws NotFoundException;
    }

}
