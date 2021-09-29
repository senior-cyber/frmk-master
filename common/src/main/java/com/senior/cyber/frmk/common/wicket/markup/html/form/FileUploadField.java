package com.senior.cyber.frmk.common.wicket.markup.html.form;

import com.senior.cyber.frmk.common.wicket.resource.FileUploadFieldOnDomReady;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.IModel;

import java.util.List;

public class FileUploadField extends org.apache.wicket.markup.html.form.upload.FileUploadField {

    public FileUploadField(String id) {
        super(id);
        setOutputMarkupId(true);
    }

    public FileUploadField(String id, IModel<? extends List<FileUpload>> model) {
        super(id, model);
        setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(new FileUploadFieldOnDomReady("bsCustomFileInput.init()"));
    }

}
