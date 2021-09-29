package com.senior.cyber.frmk.common.provider;

import com.senior.cyber.frmk.common.model.ContentHeader;

import java.io.Serializable;

public interface IContentHeaderProvider extends Serializable {

    ContentHeader fetchContentHeader();

}
