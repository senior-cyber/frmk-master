package com.senior.cyber.frmk.common.wicket.chart.chartjs;

import com.senior.cyber.frmk.common.wicket.resource.ChartMinJS;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
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

public class BarChart extends WebComponent {

    private static final long serialVersionUID = 1L;

    private IModel<BarData> dataset;

    public BarChart(String id, IModel<BarData> model) {
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

        String option = "{'responsive':true,'maintainAspectRatio':false,'datasetFill':false,'scales':{'yAxes':[{'ticks':{'beginAtZero':true}}]}}";

        String json = "{'type':'bar','data':" + this.dataset.getObject().toString() + ",'options':" + option + "}";

        String markupId = getMarkupId();
        String chart = String.format("new Chart(document.getElementById('%s').getContext('2d'), %s)", markupId, json);
        response.render(OnDomReadyHeaderItem.forScript(chart));

    }

    public static class BarItem implements Serializable {

        private static final long serialVersionUID = 1L;

        private String label;

        private String color;

        private boolean pointRadius = false;

        private List<Integer> data = new LinkedList<>();

        public BarItem() {
            this.color = String.format("#%06x", RandomUtils.nextInt(0, 0xFFFFFF + 1));
        }

        @Override
        public String toString() {
            return "{" +
                    "'label':'" + label + '\'' +
                    ", 'backgroundColor':'" + this.color + '\'' +
                    ", 'pointColor':'" + this.color + '\'' +
                    ", 'pointStrokeColor':'" + this.color + '\'' +
                    ", 'pointHighlightFill':'" + this.color + '\'' +
                    ", 'borderColor':'" + this.color + '\'' +
                    ", 'pointHighlightStroke':'" + this.color + '\'' +
                    ", 'pointRadius':" + pointRadius +
                    ", 'data':[" + StringUtils.join(this.data, ", ") + "]" +
                    '}';
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public boolean isPointRadius() {
            return pointRadius;
        }

        public void setPointRadius(boolean pointRadius) {
            this.pointRadius = pointRadius;
        }

        public List<Integer> getData() {
            return data;
        }

    }

    public static class BarData implements Serializable {

        private static final long serialVersionUID = 1L;

        private List<String> labels = new LinkedList<>();

        private List<BarItem> datasets = new LinkedList<>();

        @Override
        public String toString() {
            List<String> temp = new ArrayList<>(this.labels.size());
            for (String label : this.labels) {
                temp.add("\"" + StringEscapeUtils.escapeCsv(label) + "\"");
            }
            return "{" +
                    "'labels':[" + StringUtils.join(temp, ", ") + "]" +
                    ", 'datasets':[" + StringUtils.join(this.datasets, ", ") + "]" +
                    '}';
        }

        public List<String> getLabels() {
            return labels;
        }

        public List<BarItem> getDatasets() {
            return datasets;
        }

    }

}