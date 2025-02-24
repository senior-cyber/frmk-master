package com.senior.cyber.frmk.common.wicket.resource;

import com.senior.cyber.frmk.common.base.AdminLTEResourceReference;
import com.senior.cyber.frmk.common.base.LTEAdminProperties;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.resource.FileSystemResourceReference;

import java.io.File;

public class SummerNoteBs4CSS extends FileSystemResourceReference {

    public static final SummerNoteBs4CSS INSTANCE = new SummerNoteBs4CSS();

    public SummerNoteBs4CSS() {
        super(new File(((LTEAdminProperties) WebApplication.get()).getWebUiProperties().getAdminLte(), AdminLTEResourceReference.CSS_SUMMER_NOTE).getPath());
    }

}
