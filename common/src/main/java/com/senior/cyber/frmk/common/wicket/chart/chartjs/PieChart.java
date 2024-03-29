package com.senior.cyber.frmk.common.wicket.chart.chartjs;

import com.senior.cyber.frmk.common.Pkg;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.parser.XmlTag.TagType;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PieChart extends WebComponent {

    @Serial
    private static final long serialVersionUID = 1L;

    private IModel<PieData> dataset;

    private String option = "{'maintainAspectRatio':false,'responsive':true}";

    public PieChart(String id, IModel<PieData> model) {
        super(id);
        setOutputMarkupId(true);
        this.dataset = model;
    }

    @Override
    protected void onComponentTag(final ComponentTag tag) {
        checkComponentTag(tag, "canvas");
        super.onComponentTag(tag);
        if (tag.isOpenClose()) {
            tag.setType(TagType.OPEN);
        }
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(new PackageResourceReference(Pkg.class, "chart.js")));
        String json = "{'type':'pie','data':" + this.dataset.getObject().toString() + ",'options':" + this.option + "}";
        String markupId = getMarkupId();
        String chart = String.format("new Chart(document.getElementById('%s'), %s)", markupId, json);
        response.render(OnDomReadyHeaderItem.forScript(chart));
    }

    public static class PieItem implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        private List<String> data = new LinkedList<>();

        private List<String> backgroundColor = new LinkedList<>();

        @Override
        public String toString() {
            List<String> temp = new ArrayList<>(this.backgroundColor.size());
            for (String b : this.backgroundColor) {
                temp.add("\"" + b + "\"");
            }
            return "{" +
                    "'data':[" + StringUtils.join(this.data, ", ") + "]" +
                    ", 'backgroundColor':[" + StringUtils.join(temp, ", ") + "]" +
                    '}';
        }

        public List<String> getData() {
            return data;
        }

        public List<String> getBackgroundColor() {
            return backgroundColor;
        }

    }

    public static class PieData implements Serializable {

        private List<String> labels = new LinkedList<>();

        private List<PieItem> datasets = new LinkedList<>();

        @Override
        public String toString() {
            List<String> temp = new ArrayList<>(this.labels.size());
            for (String label : this.labels) {
                temp.add("\"" + label + "\"");
            }
            return "{" +
                    "'labels':[" + StringUtils.join(temp, ", ") + "]" +
                    ", 'datasets':[" + StringUtils.join(this.datasets, ", ") + "]" +
                    '}';
        }

        public List<String> getLabels() {
            return labels;
        }

        public List<PieItem> getDatasets() {
            return datasets;
        }
    }

}