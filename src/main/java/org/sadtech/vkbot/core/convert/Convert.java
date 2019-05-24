package org.sadtech.vkbot.core.convert;

public interface Convert<T, C> {

    C converting(T target);


}
