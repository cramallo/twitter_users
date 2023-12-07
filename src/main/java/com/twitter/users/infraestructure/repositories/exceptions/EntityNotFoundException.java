package com.twitter.users.infraestructure.repositories.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static java.lang.String.format;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {

    private String entityName;
    private String className;
    private String methodName;

    public EntityNotFoundException(final String entityName,
                                   final String className,
                                   final String methodName) {
        super();
        this.entityName = entityName;
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public String getMessage() {
        return format("Entity %s not found in class %s and method %s", entityName, className, methodName);
    }

}
