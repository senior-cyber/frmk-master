package com.senior.cyber.frmk.common.wicket;

import com.senior.cyber.frmk.common.exception.UnauthorizedInstantiationException;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;

public class Permission {

    public static boolean hasAccess(AbstractAuthenticatedWebSession session, String role) {
        return session.getRoles().hasRole(role);
    }

    public static boolean hasAccess(AbstractAuthenticatedWebSession session, String... roles) {
        if (roles != null) {
            for (String role : roles) {
                if (session.getRoles().hasRole(role)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public static void tryAccess(AbstractAuthenticatedWebSession session, String role) throws UnauthorizedInstantiationException {
        if (!session.getRoles().hasRole(role)) {
            StackTraceElement[] traces = Thread.currentThread().getStackTrace();
            String callerClass = null;
            for (int i = 0; i < traces.length; i++) {
                if (Permission.class.getName().equals(traces[i].getClassName())) {
                    callerClass = traces[i + 1].getClassName();
                }
            }
            throw new UnauthorizedInstantiationException(callerClass, role);
        }
    }

    public static void tryAccess(AbstractAuthenticatedWebSession session, String... roles) throws UnauthorizedInstantiationException {
        boolean hasAccess = false;
        if (roles != null) {
            for (String role : roles) {
                if (session.getRoles().hasRole(role)) {
                    hasAccess = true;
                    break;
                }
            }
        }
        if (!hasAccess) {
            StackTraceElement[] traces = Thread.currentThread().getStackTrace();
            String callerClass = null;
            for (int i = 0; i < traces.length; i++) {
                if (Permission.class.getName().equals(traces[i].getClassName())) {
                    callerClass = traces[i + 1].getClassName();
                }
            }
            throw new UnauthorizedInstantiationException(callerClass, roles);
        }
    }

}
