package org.sadtech.vkbot.core.convert;

@FunctionalInterface
public interface Convert<T, C> {

    C converting(T target);


}
