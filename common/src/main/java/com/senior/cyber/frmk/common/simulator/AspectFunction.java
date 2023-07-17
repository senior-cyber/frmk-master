package com.senior.cyber.frmk.common.simulator;

import com.google.common.hash.Hashing;
import org.apache.commons.lang3.SerializationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class AspectFunction {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectFunction.class);

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
            var requestData = SerializationUtils.serialize(proceeding.getArgs());
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
                    responseData = SerializationUtils.serialize((Serializable) returnObject);
                    serializer.serialize(sha1, className, methodName, requestDate, requestData, responseDate, responseData, error);
                }
            } else {
                var returnObject = SerializationUtils.deserialize(responseData);
                if (returnObject instanceof Throwable e) {
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
