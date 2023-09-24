package com.senior.cyber.frmk.common.wicket.chart.chartjs;

import com.senior.cyber.frmk.common.wicket.resource.ChartMinJS;
import org.apache.commons.lang3.RandomUtils;
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

public class LineChart extends WebComponent {

    private static final long serialVersionUID = 1L;

    private IModel<LineData> dataset;

    public LineChart(String id, IModel<LineData> model) {
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

        String option = "{'datasetFill':false,'maintainAspectRatio':false,'responsive':true,'legend':{'display':false},'scales':{'xAxes':[{'gridLines':{'display':false}}],'yAxes':[{'gridLines':{'display':false}}]}}";

        String json = "{'type':'line','data':" + this.dataset.getObject().toString() + ",'options':" + option + "}";

        String markupId = getMarkupId();
        String chart = String.format("new Chart(document.getElementById('%s').getContext('2d'), %s)", markupId, json);
        response.render(OnDomReadyHeaderItem.forScript(chart));

    }

    public static class LineItem implements Serializable {

        private static final long serialVersionUID = 1L;

        private String label;

        private String backgroundColor = String.format("#%06x", RandomUtils.nextInt(0, 0xFFFFFF + 1));

        private String pointColor = String.format("#%06x", RandomUtils.nextInt(0, 0xFFFFFF + 1));

        private String pointStrokeColor = String.format("#%06x", RandomUtils.nextInt(0, 0xFFFFFF + 1));

        private String pointHighlightFill = String.format("#%06x", RandomUtils.nextInt(0, 0xFFFFFF + 1));

        private String borderColor = String.format("#%06x", RandomUtils.nextInt(0, 0xFFFFFF + 1));

        private String pointHighlightStroke = String.format("#%06x", RandomUtils.nextInt(0, 0xFFFFFF + 1));

        private boolean pointRadius = false;

        private List<Integer> data = new LinkedList<>();

        @Override
        public String toString() {
            return "{" +
                    "'label':'" + label + '\'' +
                    ", 'backgroundColor':'" + backgroundColor + '\'' +
                    ", 'pointColor':'" + pointColor + '\'' +
                    ", 'pointStrokeColor':'" + pointStrokeColor + '\'' +
                    ", 'pointHighlightFill':'" + pointHighlightFill + '\'' +
                    ", 'borderColor':'" + borderColor + '\'' +
                    ", 'pointHighlightStroke':'" + pointHighlightStroke + '\'' +
                    ", 'pointRadius':" + pointRadius +
                    ", 'fill':false" +
                    ", 'data':[" + StringUtils.join(this.data, ", ") + "]" +
                    '}';
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getBackgroundColor() {
            return backgroundColor;
        }

        public void setBackgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public String getPointColor() {
            return pointColor;
        }

        public void setPointColor(String pointColor) {
            this.pointColor = pointColor;
        }

        public String getPointStrokeColor() {
            return pointStrokeColor;
        }

        public void setPointStrokeColor(String pointStrokeColor) {
            this.pointStrokeColor = pointStrokeColor;
        }

        public String getPointHighlightFill() {
            return pointHighlightFill;
        }

        public void setPointHighlightFill(String pointHighlightFill) {
            this.pointHighlightFill = pointHighlightFill;
        }

        public String getBorderColor() {
            return borderColor;
        }

        public void setBorderColor(String borderColor) {
            this.borderColor = borderColor;
        }

        public String getPointHighlightStroke() {
            return pointHighlightStroke;
        }

        public void setPointHighlightStroke(String pointHighlightStroke) {
            this.pointHighlightStroke = pointHighlightStroke;
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

    public static class LineData implements Serializable {

        private static final long serialVersionUID = 1L;

        private List<String> labels = new LinkedList<>();

        private List<LineItem> datasets = new LinkedList<>();

        public List<String> getLabels() {
            return labels;
        }

        public List<LineItem> getDatasets() {
            return datasets;
        }

        @Override
        public String toString() {
            List<String> temp = new ArrayList<>(labels.size());
            for (String label : labels) {
                temp.add("\"" + label + "\"");
            }
            return "{" +
                    "'labels':[" + StringUtils.join(temp, ", ") + "]" +
                    ", 'datasets':[" + StringUtils.join(datasets, ", ") + "]" +
                    '}';
        }
    }

}