package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import java.io.Serializable;

public enum Operator implements Serializable {
    NotEqual, Equal, Like, NotLike, LessThan, LessThanOrEqual, GreaterThan, GreaterThanOrEqual, Between, NotBetween;
}