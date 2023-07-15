package com.senior.cyber.frmk.common.spring;

import com.google.gson.Gson;
import com.senior.cyber.frmk.common.simulator.SimulateEnum;
import com.senior.cyber.frmk.common.simulator.SimulateHttpEntity;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GsonHttpMessageConverter extends org.springframework.http.converter.json.GsonHttpMessageConverter {

    public GsonHttpMessageConverter() {
    }

    public GsonHttpMessageConverter(Gson gson) {
        super(gson);
    }

    @Override
    protected void writeInternal(Object object, Type type, Writer writer) throws Exception {
        if (type instanceof ParameterizedType p && p.getRawType() == ResponseEntity.class) {
            super.writeInternal(object, p.getActualTypeArguments()[0], writer);
        } else {
            super.writeInternal(object, type, writer);
        }
    }

}
