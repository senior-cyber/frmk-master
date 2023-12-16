package com.senior.cyber.frmk.transport;

import java.util.List;

public interface MessageFactory<T> {

    T one();

    T[] array(int length);

    List<T> list(int size);

}
