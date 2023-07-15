package com.senior.cyber.frmk.common.simulator;

import com.google.common.hash.Hashing;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.Serializable;

public class AspectFunction {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectFunction.class);

    public static Object simulateHttpEntity(File folder, SimulateEnum type, ProceedingJoinPoint proceeding) throws Throwable {
        if (type == SimulateEnum.Serialize || type == SimulateEnum.Replay) {
            var argsData = SerializationUtils.serialize(proceeding.getArgs());
            var argsSha1 = Hashing.sha1().hashBytes(argsData).toString();
            String className = proceeding.getTarget().getClass().getName();
            String methodName = proceeding.getSignature().getName();
            File requestOutputFile = new File(folder, className + "." + methodName + "." + argsSha1 + ".request");
            FileUtils.writeByteArrayToFile(requestOutputFile, argsData);
            LOGGER.debug("Request Output File [{}]", requestOutputFile.getAbsolutePath());
            File responseIoFile = new File(folder, className + "." + methodName + "." + argsSha1 + ".response");
            if (type == SimulateEnum.Serialize || !responseIoFile.canRead()) {
                Object returnObject = null;
                try {
                    returnObject = proceeding.proceed(proceeding.getArgs());
                    return returnObject;
                } catch (Throwable e) {
                    returnObject = e;
                    throw e;
                } finally {
                    byte[] returnData = SerializationUtils.serialize((Serializable) returnObject);
                    FileUtils.writeByteArrayToFile(responseIoFile, returnData);
                    LOGGER.debug("Response Output File [{}]", responseIoFile.getAbsolutePath());
                }
            } else {
                LOGGER.debug("Response Input File [{}]", responseIoFile.getAbsolutePath());
                var returnData = FileUtils.readFileToByteArray(responseIoFile);
                var returnObject = SerializationUtils.deserialize(returnData);
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

}
