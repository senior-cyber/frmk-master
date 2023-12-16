package com.senior.cyber.frmk.transport;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Transport {

    public static final byte TYPE_BOOLEAN = 1;
    public static final byte TYPE_BYTE = 3;
    public static final byte TYPE_SHORT = 5;
    public static final byte TYPE_INTEGER = 7;
    public static final byte TYPE_LONG = 9;
    public static final byte TYPE_FLOAT = 11;
    public static final byte TYPE_DOUBLE = 13;
    public static final byte TYPE_CHARACTER = 15;
    public static final byte TYPE_STRING = 17;
    public static final byte TYPE_MESSAGE = 19;
    public static final byte TYPE_DATE = 21;

    private final HandlerBoolean handlerBoolean = new HandlerBoolean();
    private final HandlerCharacter handlerCharacter = new HandlerCharacter();
    private final HandlerString handlerString = new HandlerString();
    private final HandlerMessage handlerMessage = new HandlerMessage(this);

    private final HandlerByte handlerByte = new HandlerByte();
    private final HandlerShort handlerShort = new HandlerShort();
    private final HandlerInteger handlerInteger = new HandlerInteger();
    private final HandlerLong handlerLong = new HandlerLong();

    private final HandlerFloat handlerFloat = new HandlerFloat();
    private final HandlerDouble handlerDouble = new HandlerDouble();
    private final HandlerDate handlerDate = new HandlerDate();

    private final Map<String, MessageFactory> factory = new HashMap<>();

    public <T extends Message> void register(Class<T> clazz, MessageFactory<T> factory) {
        this.factory.put(clazz.getName(), factory);
    }

    public <T extends Message> MessageFactory<T> getFactory(Class<T> clazz) {
        return this.factory.get(clazz.getName());
    }

    public HandlerBoolean booleanHandler() {
        return handlerBoolean;
    }

    public HandlerCharacter characterHandler() {
        return handlerCharacter;
    }

    public HandlerString stringHandler() {
        return handlerString;
    }

    public HandlerMessage messageHandler() {
        return handlerMessage;
    }

    public HandlerByte byteHandler() {
        return handlerByte;
    }

    public HandlerShort shortHandler() {
        return handlerShort;
    }

    public HandlerInteger integerHandler() {
        return handlerInteger;
    }

    public HandlerLong longHandler() {
        return handlerLong;
    }

    public HandlerFloat floatHandler() {
        return handlerFloat;
    }

    public HandlerDouble doubleHandler() {
        return handlerDouble;
    }

    public HandlerDate dateHandler() {
        return handlerDate;
    }

    protected void skipOne(DataInputStream stream) throws IOException {
        int dataType = stream.readByte();
        boolean notNull = stream.readBoolean();
        if (notNull) {
            int dataSize = stream.readInt();
            stream.skip(dataSize);
        }
    }

}