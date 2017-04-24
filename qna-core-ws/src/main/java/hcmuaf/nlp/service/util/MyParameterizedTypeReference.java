/*
 * ParameterizedTypeReference.java
 * 
 * Created on Jan 15, 2016
 * 
 * Copyright (C) 2016 Toennies, All rights reserved.
 */

package hcmuaf.nlp.service.util;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;

/**
 * This is our custom ParameterizedTypeReference to support Generic.
 * See "http://stackoverflow.com/questions/21987295 for more detail"
 *
 * @param <T> the generic type
 */
public class MyParameterizedTypeReference<T> extends ParameterizedTypeReference<T> {

    /** The my type. */
    private Type myType;

    /**
     * Instantiates a new my parameterized type reference.
     *
     * @param t the t
     */
    public MyParameterizedTypeReference(Type t) {
        super();
        this.myType = t;
    }

    @Override
    public Type getType() {
        // see http://stackoverflow.com/questions/21987295
        Type[] responseWrapperActualTypes = {myType};
        ParameterizedType responseWrapperType =
                new ParameterizedTypeImpl(PagedResources.class, responseWrapperActualTypes, null);
        return responseWrapperType;
    }

}
