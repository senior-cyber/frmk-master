package com.senior.cyber.frmk.common.aspect;

import com.google.common.hash.Hashing;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import java.io.File;
import java.io.Serializable;

public class AspectFunction {

    public static Object serializeHttpEntity(ProceedingJoinPoint proceeding) throws Throwable {
        var argsData = SerializationUtils.serialize(proceeding.getArgs());
        var argsSha1 = Hashing.sha1().hashBytes(argsData).toString();
        String className = proceeding.getTarget().getClass().getName();
        String methodName = proceeding.getSignature().getName();
        File requestOutputFile = new File(FileUtils.getTempDirectory(), className + "." + methodName + "." + argsSha1 + ".request");
        FileUtils.writeByteArrayToFile(requestOutputFile, argsData);
        Object returnObject = null;
        try {
            returnObject = proceeding.proceed(proceeding.getArgs());
            return returnObject;
        } catch (Throwable e) {
            returnObject = e;
            throw e;
        } finally {
            byte[] returnData = SerializationUtils.serialize((Serializable) returnObject);
            File responseOutputFile = new File(FileUtils.getTempDirectory(), className + "." + methodName + "." + argsSha1 + ".response");
            FileUtils.writeByteArrayToFile(responseOutputFile, returnData);
        }
    }

    public static Object simulateHttpEntity(ProceedingJoinPoint proceeding) throws Throwable {
        var argsData = SerializationUtils.serialize(proceeding.getArgs());
        var argsSha1 = Hashing.sha1().hashBytes(argsData).toString();
        String className = proceeding.getTarget().getClass().getName();
        String methodName = proceeding.getSignature().getName();
        File responseInputFile = new File(FileUtils.getTempDirectory(), className + "." + methodName + "." + argsSha1 + ".response");
        var returnData = FileUtils.readFileToByteArray(responseInputFile);
        var returnObject = SerializationUtils.deserialize(returnData);
        if (returnObject instanceof Throwable e) {
            throw e;
        } else {
            return returnObject;
        }
    }

}
