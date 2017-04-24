/*
 * ParameterizedTypeImpl.java
 * 
 * Created on Jan 15, 2016
 * 
 * Copyright (C) 2016 Toennies, All rights reserved.
 */

package hcmuaf.nlp.service.util;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * The Class ParameterizedTypeImpl.
 */
public class ParameterizedTypeImpl implements ParameterizedType {

    /** The raw type. */
    private final Type rawType;

    /** The actual type arguments. */
    private final Type[] actualTypeArguments;

    /** The owner. */
    private final Type owner;

    /**
     * Instantiates a new parameterized type impl.
     *
     * @param rawType the raw type
     * @param actualTypeArguments the actual type arguments
     * @param owner the owner
     */
    public ParameterizedTypeImpl(Type rawType, Type[] actualTypeArguments, Type owner) {
        this.rawType = rawType;
        if (actualTypeArguments != null) {
            this.actualTypeArguments = actualTypeArguments.clone();
        } else {
            this.actualTypeArguments = null;
        }
        this.owner = owner;
    }

    @Override
    public Type getRawType() {
        return rawType;
    }

    @Override
    public Type[] getActualTypeArguments() {
        if (actualTypeArguments == null) {
            return null;
        } else {
            return actualTypeArguments.clone();
        }
    }

    @Override
    public Type getOwnerType() {
        return owner;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ParameterizedType)) {
            return false;
        } else {
            ParameterizedType that = (ParameterizedType) object;
            if (this == that) {
                return true;
            }
            Type thatOwner = that.getOwnerType();
            Type thatRawType = that.getRawType();
            // to be equals, it must equal on owner, rawType and actualTypeArguments
            // avoid inline condition
            boolean isOwnerEqual = isEqual(owner, thatOwner);
            boolean isRawTypeEqual = isEqual(rawType, thatRawType);
            boolean isActualTypeArgumetsEqual = Arrays.equals(actualTypeArguments, that.getActualTypeArguments());
            return isOwnerEqual && isRawTypeEqual && isActualTypeArgumetsEqual;
        }
    }

    /**
     * Checks if two types are equal.
     *
     * @param thisType the this type
     * @param thatType the that type
     * @return true, if is equal
     */
    private boolean isEqual(Type thisType, Type thatType) {
        if (thisType == null) {
            return thatType == null;
        }
        return thisType.equals(thatType);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(actualTypeArguments) ^ getHashCode(owner) ^ getHashCode(rawType);
    }

    /**
     * Gets the hash code.
     *
     * @param type the type
     * @return the hash code
     */
    private int getHashCode(Type type) {
        if (type == null) {
            return 0;
        }
        return type.hashCode();
    }

}
