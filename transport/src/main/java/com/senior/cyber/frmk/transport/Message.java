package com.senior.cyber.frmk.transport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface Message {

    void writeTo(Transport transport, DataOutputStream to) throws IOException;

    void readFrom(Transport transport, DataInputStream from) throws IOException;

    default Boolean delegateDeserialize(HandlerBoolean handler, DataInputStream from) throws IOException {
        return handler.deserialize(from);
    }

    default boolean[] delegateDeserializeArray(HandlerBoolean handler, DataInputStream from) throws IOException {
        return handler.deserializeArray(from);
    }

    default List<Boolean> delegateDeserializeList(HandlerBoolean handler, DataInputStream from) throws IOException {
        return handler.deserializeList(from);
    }

    default Byte delegateDeserialize(HandlerByte handler, DataInputStream from) throws IOException {
        return handler.deserialize(from);
    }

    default byte[] delegateDeserializeArray(HandlerByte handler, DataInputStream from) throws IOException {
        return handler.deserializeArray(from);
    }

    default List<Byte> delegateDeserializeList(HandlerByte handler, DataInputStream from) throws IOException {
        return handler.deserializeList(from);
    }

    default Character delegateDeserialize(HandlerCharacter handler, DataInputStream from) throws IOException {
        return handler.deserialize(from);
    }

    default char[] delegateDeserializeArray(HandlerCharacter handler, DataInputStream from) throws IOException {
        return handler.deserializeArray(from);
    }

    default List<Character> delegateDeserializeList(HandlerCharacter handler, DataInputStream from) throws IOException {
        return handler.deserializeList(from);
    }

    default Date delegateDeserialize(HandlerDate handler, DataInputStream from) throws IOException {
        return handler.deserialize(from);
    }

    default Date[] delegateDeserializeArray(HandlerDate handler, DataInputStream from) throws IOException {
        return handler.deserializeArray(from);
    }

    default List<Date> delegateDeserializeList(HandlerDate handler, DataInputStream from) throws IOException {
        return handler.deserializeList(from);
    }

    default Double delegateDeserialize(HandlerDouble handler, DataInputStream from) throws IOException {
        return handler.deserialize(from);
    }

    default double[] delegateDeserializeArray(HandlerDouble handler, DataInputStream from) throws IOException {
        return handler.deserializeArray(from);
    }

    default List<Double> delegateDeserializeList(HandlerDouble handler, DataInputStream from) throws IOException {
        return handler.deserializeList(from);
    }

    default Float delegateDeserialize(HandlerFloat handler, DataInputStream from) throws IOException {
        return handler.deserialize(from);
    }

    default float[] delegateDeserializeArray(HandlerFloat handler, DataInputStream from) throws IOException {
        return handler.deserializeArray(from);
    }

    default List<Float> delegateDeserializeList(HandlerFloat handler, DataInputStream from) throws IOException {
        return handler.deserializeList(from);
    }

    default Integer delegateDeserialize(HandlerInteger handler, DataInputStream from) throws IOException {
        return handler.deserialize(from);
    }

    default int[] delegateDeserializeArray(HandlerInteger handler, DataInputStream from) throws IOException {
        return handler.deserializeArray(from);
    }

    default List<Integer> delegateDeserializeList(HandlerInteger handler, DataInputStream from) throws IOException {
        return handler.deserializeList(from);
    }

    default Long delegateDeserialize(HandlerLong handler, DataInputStream from) throws IOException {
        return handler.deserialize(from);
    }

    default long[] delegateDeserializeArray(HandlerLong handler, DataInputStream from) throws IOException {
        return handler.deserializeArray(from);
    }

    default List<Long> delegateDeserializeList(HandlerLong handler, DataInputStream from) throws IOException {
        return handler.deserializeList(from);
    }

    default Short delegateDeserialize(HandlerShort handler, DataInputStream from) throws IOException {
        return handler.deserialize(from);
    }

    default short[] delegateDeserializeArray(HandlerShort handler, DataInputStream from) throws IOException {
        return handler.deserializeArray(from);
    }

    default List<Short> delegateDeserializeList(HandlerShort handler, DataInputStream from) throws IOException {
        return handler.deserializeList(from);
    }

    default <T extends Message> T delegateDeserialize(HandlerMessage handler, DataInputStream from, Class<T> clazz) throws IOException {
        return handler.deserialize(from, clazz);
    }

    default <T extends Message> T[] delegateDeserializeArray(HandlerMessage handler, DataInputStream from, Class<T> clazz) throws IOException {
        return handler.deserializeArray(from, clazz);
    }

    default <T extends Message> List<T> delegateDeserializeList(HandlerMessage handler, DataInputStream from, Class<T> clazz) throws IOException {
        return handler.deserializeList(from, clazz);
    }

    default String delegateDeserialize(HandlerString handler, DataInputStream from) throws IOException {
        return handler.deserialize(from);
    }

    default String[] delegateDeserializeArray(HandlerString handle, DataInputStream from) throws IOException {
        return handle.deserializeArray(from);
    }

    default List<String> delegateDeserializeList(HandlerString handle, DataInputStream from) throws IOException {
        return handle.deserializeList(from);
    }

    default void delegateSkipOne(Transport transport, DataInputStream from) throws IOException {
        transport.skipOne(from);
    }

}