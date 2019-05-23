package org.sadtech.vkbot.core.distribution;

@FunctionalInterface
public interface Convert<T, C> {

    C converting(T target);

}
