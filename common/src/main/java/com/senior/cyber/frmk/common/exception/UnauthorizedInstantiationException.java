package com.senior.cyber.frmk.common.exception;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.authorization.AuthorizationException;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.request.component.IRequestableComponent;

import java.util.Arrays;
import java.util.List;

public class UnauthorizedInstantiationException extends AuthorizationException {

    private String className;

    private List<String> roles;

    public <T extends IRequestableComponent> UnauthorizedInstantiationException(final Class<T> componentClass) {
        if (componentClass != null) {
            String[] temps = StringUtils.split(componentClass.getName(), '.');
            for (int i = 0; i < temps.length - 1; i++) {
                temps[i] = temps[i].substring(0, 1);
            }
            this.className = StringUtils.join(temps, ".");
        }
        AuthorizeInstantiation classAnnotation = componentClass.getAnnotation(AuthorizeInstantiation.class);
        if (classAnnotation != null && classAnnotation.value() != null && classAnnotation.value().length != 0) {
            this.roles = Arrays.asList(classAnnotation.value());
        }
    }

    public <T extends IRequestableComponent> UnauthorizedInstantiationException(final Class<T> componentClass, String... roles) {
        if (componentClass != null) {
            String[] temps = StringUtils.split(componentClass.getName(), '.');
            for (int i = 0; i < temps.length - 1; i++) {
                temps[i] = temps[i].substring(0, 1);
            }
            this.className = StringUtils.join(temps, ".");
        }
        if (roles != null && roles.length != 0) {
            this.roles = Arrays.asList(roles);
        }
    }

    public UnauthorizedInstantiationException(final String className, String... roles) {
        if (className != null && !"".equals(className)) {
            String[] temps = StringUtils.split(className, '.');
            for (int i = 0; i < temps.length - 1; i++) {
                temps[i] = temps[i].substring(0, 1);
            }
            this.className = StringUtils.join(temps, ".");
        }
        if (roles != null && roles.length != 0) {
            this.roles = Arrays.asList(roles);
        }
    }

    public String getClassName() {
        return className;
    }

    public List<String> getRoles() {
        return roles;
    }

}
