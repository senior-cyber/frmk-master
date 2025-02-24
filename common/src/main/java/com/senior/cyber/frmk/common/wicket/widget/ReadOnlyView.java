package com.senior.cyber.frmk.common.wicket.widget;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.io.Serial;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

public class ReadOnlyView extends Label {

    @Serial
    private static final long serialVersionUID = 1L;

    protected String format;

    public static Map<String, NumberFormat> FORMATS;

    static {
        FORMATS = new HashMap<>();
        FORMATS.put("#,###.00", new DecimalFormat("#,###.00"));
        FORMATS.put("#.00", new DecimalFormat("#.00"));
        FORMATS.put("#,###,##0.00", new DecimalFormat("#,###,##0.00"));
    }

    public static NumberFormat getFormat(String format) {
        FORMATS.put(format, new DecimalFormat(format));
        return FORMATS.get(format);
    }

    public ReadOnlyView(String id, IModel<?> value) {
        super(id, value);
    }

    public ReadOnlyView(String id, IModel<?> value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id) {
        this(id, "");
    }

    public ReadOnlyView(String id, String value) {
        super(id, value);
    }

    public ReadOnlyView(String id, String[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Boolean value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Boolean[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Byte value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Byte value[]) {
        super(id, value);
    }

    public ReadOnlyView(String id, Byte value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Byte[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, byte value) {
        super(id, value);
    }

    public ReadOnlyView(String id, byte[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, byte value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, byte[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Short value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Short value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, short value) {
        super(id, value);
    }

    public ReadOnlyView(String id, short value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Short[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Short[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, short[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, short[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Integer value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Integer value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, int value) {
        super(id, value);
    }

    public ReadOnlyView(String id, int value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Integer[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Integer[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, int[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, int[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Long value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Long value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, long value) {
        super(id, value);
    }

    public ReadOnlyView(String id, long value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Long[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Long[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, long[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, long[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Float value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Float value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, float value) {
        super(id, value);
    }

    public ReadOnlyView(String id, float value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Float[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Float[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, float[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, float[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Double value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Double value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, double value) {
        super(id, value);
    }

    public ReadOnlyView(String id, double value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Double[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Double[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, double[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, double[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, BigInteger value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, BigInteger value) {
        super(id, value);
    }

    public ReadOnlyView(String id, BigInteger value[], String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, BigInteger value[]) {
        super(id, value);
    }

    public ReadOnlyView(String id, BigDecimal value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, BigDecimal value) {
        super(id, value);
    }

    public ReadOnlyView(String id, BigDecimal value[], String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, BigDecimal value[]) {
        super(id, value);
    }

    public ReadOnlyView(String id, char value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Character value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Date value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, char[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Character[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Date[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, List<?> value) {
        super(id, Model.ofList(value));
    }

    public ReadOnlyView(String id, List<?> value, String format) {
        super(id, Model.ofList(value));
        this.format = format;
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("readonly", "readonly");
        IModel<?> model = getDefaultModel();
        Object object = model.getObject();
        boolean multipleLine = false;
        if (object instanceof List) {
            multipleLine = ((List) object).size() > 1;
        } else {
            if (object instanceof Object[]) {
                multipleLine = ((Object[]) object).length > 1;
            }
        }
        if (multipleLine) {
            String style = tag.getAttribute("style");
            if (style == null || "".equals(style)) {
                style = "height: auto";
            } else {
                style = "height: auto !important; " + style;
            }
            tag.put("style", style);
        }
    }

    @Override
    public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
        IModel<?> model = getDefaultModel();
        if (model == null) {
            replaceComponentTagBody(markupStream, openTag, "");
        } else {
            Object object = model.getObject();
            if (object == null) {
                replaceComponentTagBody(markupStream, openTag, "");
            } else {
                List<String> objects = new ArrayList<>();
                Class<?> objectClass = object.getClass();
                if (object instanceof List) {
                    for (Object v : (List<Object>) object) {
                        if (v != null) {
                            Class<?> vClass = v.getClass();
                            parceObject(objects, vClass, v);
                        }
                    }
                } else if (objectClass == Boolean[].class) {
                    for (Boolean v : (Boolean[]) object) {
                        if (v != null) {
                            objects.add(v ? "Yes" : "No");
                        }
                    }
                } else if (objectClass == boolean[].class) {
                    for (boolean v : (boolean[]) object) {
                        objects.add(v ? "Yes" : "No");
                    }
                } else if (objectClass == Byte[].class) {
                    for (Byte v : (Byte[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                    : getFormat(this.format).format(v));
                        }
                    }
                } else if (objectClass == byte[].class) {
                    for (byte v : (byte[]) object) {
                        objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                : getFormat(this.format).format(v));
                    }
                } else if (objectClass == Short[].class) {
                    for (Short v : (Short[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                    : getFormat(this.format).format(v));
                        }
                    }
                } else if (objectClass == short[].class) {
                    for (short v : (short[]) object) {
                        objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                : getFormat(this.format).format(v));
                    }
                } else if (objectClass == Integer[].class) {
                    for (Integer v : (Integer[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                    : getFormat(this.format).format(v));
                        }
                    }
                } else if (objectClass == int[].class) {
                    for (int v : (int[]) object) {
                        objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                : getFormat(this.format).format(v));
                    }
                } else if (objectClass == Long[].class) {
                    for (Long v : (Long[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                    : getFormat(this.format).format(v));
                        }
                    }
                } else if (objectClass == long[].class) {
                    for (long v : (long[]) object) {
                        objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                : getFormat(this.format).format(v));
                    }
                } else if (objectClass == Float[].class) {
                    for (Float v : (Float[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                    : getFormat(this.format).format(v));
                        }
                    }
                } else if (objectClass == float[].class) {
                    for (float v : (float[]) object) {
                        objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                : getFormat(this.format).format(v));
                    }
                } else if (objectClass == Double[].class) {
                    for (Double v : (Double[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                    : getFormat(this.format).format(v));
                        }
                    }
                } else if (objectClass == double[].class) {
                    for (double v : (double[]) object) {
                        objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                : getFormat(this.format).format(v));
                    }
                } else if (objectClass == BigInteger[].class) {
                    for (BigInteger v : (BigInteger[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                    : getFormat(this.format).format(v.longValue()));
                        }
                    }
                } else if (objectClass == BigDecimal[].class) {
                    for (BigDecimal v : (BigDecimal[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v)
                                    : getFormat(this.format).format(v.doubleValue()));
                        }
                    }
                } else if (objectClass == Date[].class) {
                    for (Date v : (Date[]) object) {
                        if (v != null) {
                            objects.add(DateFormatUtils.format(v, this.format));
                        }
                    }
                } else if (objectClass == java.sql.Date[].class) {
                    for (Date v : (java.sql.Date[]) object) {
                        if (v != null) {
                            objects.add(DateFormatUtils.format(v, this.format));
                        }
                    }
                } else if (objectClass == Timestamp[].class) {
                    for (Date v : (Timestamp[]) object) {
                        if (v != null) {
                            objects.add(DateFormatUtils.format(v, this.format));
                        }
                    }
                } else if (objectClass == Time[].class) {
                    for (Date v : (Time[]) object) {
                        if (v != null) {
                            objects.add(DateFormatUtils.format(v, this.format));
                        }
                    }
                } else if (objectClass == Character[].class) {
                    for (Character v : (Character[]) object) {
                        if (v != null) {
                            objects.add(String.valueOf(v));
                        }
                    }
                } else if (objectClass == char[].class) {
                    for (char v : (char[]) object) {
                        objects.add(String.valueOf(v));
                    }
                } else if (objectClass == String[].class) {
                    for (String v : (String[]) object) {
                        if (v != null && !"".equals(v)) {
                            objects.add(v);
                        }
                    }
                } else {
                    parceObject(objects, objectClass, object);
                }
                replaceComponentTagBody(markupStream, openTag, StringUtils.join(objects, ", "));
            }
        }
    }

    protected void parceObject(List<String> objects, Class<?> clazz, Object value) {
        if (clazz == Boolean.class || clazz == boolean.class) {
            objects.add((boolean) value ? "Yes" : "No");
        } else if (clazz == Byte.class || clazz == byte.class) {
            objects.add(this.format == null || "".equals(this.format) ? String.valueOf((byte) value)
                    : getFormat(this.format).format((byte) value));
        } else if (clazz == Short.class || clazz == short.class) {
            objects.add(this.format == null || "".equals(this.format) ? String.valueOf((short) value)
                    : getFormat(this.format).format((short) value));
        } else if (clazz == Integer.class || clazz == int.class) {
            objects.add(this.format == null || "".equals(this.format) ? String.valueOf((int) value)
                    : getFormat(this.format).format((int) value));
        } else if (clazz == Long.class || clazz == long.class) {
            objects.add(this.format == null || "".equals(this.format) ? String.valueOf((long) value)
                    : getFormat(this.format).format((long) value));
        } else if (clazz == Float.class || clazz == float.class) {
            objects.add(this.format == null || "".equals(this.format) ? String.valueOf((float) value)
                    : getFormat(this.format).format((float) value));
        } else if (clazz == Double.class || clazz == double.class) {
            objects.add(this.format == null || "".equals(this.format) ? String.valueOf((double) value)
                    : getFormat(this.format).format((double) value));
        } else if (clazz == Character.class || clazz == char.class) {
            objects.add(String.valueOf((Character) value));
        } else if (clazz == BigInteger.class) {
            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(((BigInteger) value).longValue())
                    : getFormat(this.format).format(((BigInteger) value).longValue()));
        } else if (clazz == BigDecimal.class) {
            objects.add(
                    this.format == null || "".equals(this.format) ? String.valueOf(((BigDecimal) value).doubleValue())
                            : getFormat(this.format).format(((BigDecimal) value).doubleValue()));
        } else if (clazz == Date.class || clazz == Time.class || clazz == java.sql.Date.class
                || clazz == Timestamp.class) {
            objects.add(DateFormatUtils.format((Date) value, this.format));
        } else if (clazz == String.class) {
            if (!"".equals(value)) {
                objects.add((String) value);
            }
        } else {
            throw new WicketRuntimeException("Unknown " + clazz.getSimpleName());
        }
    }

}
