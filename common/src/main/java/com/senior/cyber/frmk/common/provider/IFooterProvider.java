package com.senior.cyber.frmk.common.provider;

import com.senior.cyber.frmk.common.model.Footer;

import java.io.Serializable;

public interface IFooterProvider extends Serializable {

    Footer fetchFooter();

}
