package com.senior.cyber.frmk.common.wicket.chart.chartjs;

import com.senior.cyber.frmk.common.wicket.resource.ChartMinJS;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.parser.XmlTag.TagType;
import org.apache.wicket.model.IModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PieChart extends WebComponent {

    /**
     *
     */
    private static final long serialVersionUID = -5090750359586454073L;

    private IModel<PieData> dataset;

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

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(ChartMinJS.INSTANCE));

        String option = "{'maintainAspectRatio':false,'responsive':true}";

        String json = "{'type':'pie','data':" + this.dataset.getObject().toString() + ",'options':" + option + "}";

        String markupId = getMarkupId();
        String chart = String.format("new Chart(document.getElementById('%s').getContext('2d'), %s)", markupId, json);
        response.render(OnDomReadyHeaderItem.forScript(chart));
    }

    public static class PieItem implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = -2605733189776683748L;

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
                    ", 'backgroundColor'=[" + StringUtils.join(temp, ", ") + "]" +
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