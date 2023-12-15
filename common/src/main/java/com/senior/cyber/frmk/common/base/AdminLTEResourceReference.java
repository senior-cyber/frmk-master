package com.senior.cyber.frmk.common.base;

import org.apache.commons.io.FilenameUtils;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.resource.FileSystemResource;
import org.springframework.context.ApplicationContext;

import java.io.File;

public class AdminLTEResourceReference extends ResourceReference {

    public static final String CSS_FONT_AWESOME = "/plugins/fontawesome-free/css/all.min.css";

    public static final String CSS_FULL_CALENDAR_MAIN = "/plugins/fullcalendar/main.css";

    public static final String JS_FULL_CALENDAR_MAIN = "/plugins/fullcalendar/main.js";

    public static final String CSS_LIGHT_BOX = "/plugins/ekko-lightbox/ekko-lightbox.css";

    public static final String CSS_SWEET_ALERT_BOOTSTRAP = "/plugins/sweetalert2-theme-bootstrap-4/bootstrap-4.min.css";

    public static final String JS_SWEET_ALERT_BOOTSTRAP = "/plugins/sweetalert2/sweetalert2.min.js";

    public static final String CSS_TOASTR = "/plugins/toastr/toastr.min.css";

    public static final String JS_TOASTR = "/plugins/toastr/toastr.min.js";

    public static final String CSS_OVERLAY_SCROLL_BAR = "/plugins/overlayScrollbars/css/OverlayScrollbars.min.css";

    public static final String JS_OVERLAY_SCROLL_BAR = "/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js";

    public static final String CSS_ION_ICONS = "https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css";

    public static final String CSS_JS_GRID = "/plugins/jsgrid/jsgrid.min.css";

    public static final String CSS_JS_GRID_THEME = "/plugins/jsgrid/jsgrid-theme.min.css";

    public static final String JS_JS_GRID_DB = "/plugins/jsgrid/demos/db.js";

    public static final String JS_JS_GRID = "/plugins/jsgrid/jsgrid.min.js";

    public static final String CSS_PACE_THEME_FLAT_TOP = "/plugins/pace-progress/themes/black/pace-theme-flat-top.css";

    public static final String CSS_FLAG_ICONS = "https://cdnjs.cloudflare.com/ajax/libs/flag-icon-css/3.3.0/css/flag-icon.min.css";

    public static final String CSS_THEME_STYLE = "/dist/css/adminlte.min.css";

    public static final String CSS_DATA_TABLE = "/plugins/datatables-bs4/css/dataTables.bootstrap4.min.css";
    public static final String CSS_DATA_TABLE_RESPONSIVE = "/plugins/datatables-responsive/css/responsive.bootstrap4.min.css";
    public static final String CSS_DATA_TABLE_BUTTON = "/plugins/datatables-buttons/css/buttons.bootstrap4.min.css";

    public static final String CSS_SUMMER_NOTE = "/plugins/summernote/summernote-bs4.css";

    public static final String CSS_GOOGLE_FONT = "https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback";

    public static final String JS_BOOTSTRAP_4 = "/plugins/bootstrap/js/bootstrap.bundle.min.js";

    public static final String JS_DATA_TABLE = "/plugins/datatables/jquery.dataTables.js";

    public static final String JS_DATA_TABLE_BOOTSTRAP = "/plugins/datatables-bs4/js/dataTables.bootstrap4.min.js";

    public static final String JS_DATA_TABLE_RESPONSIVE = "/plugins/datatables-responsive/js/dataTables.responsive.min.js";

    public static final String JS_VALIDATION = "/plugins/jquery-validation/jquery.validate.min.js";

    public static final String JS_METHOD = "/plugins/jquery-validation/additional-methods.min.js";

    public static final String JS_FILE_UPLOAD = "/plugins/bs-custom-file-input/bs-custom-file-input.min.js";

    public static final String JS_PACE = "/plugins/pace-progress/pace.min.js";

    public static final String JS_CHART_JS = "/plugins/chart.js/Chart.min.js";

    public static final String JS_ADMINLTE_APP = "/dist/js/adminlte.min.js";

    public static final String JS_UPLOT = "/plugins/uplot/uPlot.iife.min.js";

    public static final String CSS_UPLOT = "/plugins/uplot/uPlot.min.css";

    public static final String CSS_TEMPUSDOMINUS_BOOTSTRAP = "/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css";

    public static final String CSS_DATE_RANGE_PICKER = "/plugins/daterangepicker/daterangepicker.css";

    public static final String CSS_ICHECK_BOOTSTRAP = "/plugins/icheck-bootstrap/icheck-bootstrap.min.css";

    public static final String CSS_JQVMAP = "/plugins/jqvmap/jqvmap.min.css";

    public static final String JS_JQVMAP = "/plugins/jqvmap/jquery.vmap.min.js";

    public static final String JS_JQVMAP_USA = "/plugins/jqvmap/maps/jquery.vmap.usa.js";

    public static final String CSS_COLOR_PICKER = "/plugins/bootstrap-colorpicker/css/bootstrap-colorpicker.min.css";

    public static final String CSS_SELECT_2 = "/plugins/select2/css/select2.min.css";

    public static final String CSS_SELECT_2_BOOTSTRAP = "/plugins/select2-bootstrap4-theme/select2-bootstrap4.min.css";

    public static final String CSS_BOOTSTRAP_BOX = "/plugins/bootstrap4-duallistbox/bootstrap-duallistbox.min.css";

    public static final String JS_SELECT_2 = "/plugins/select2/js/select2.full.min.js";

    public static final String JS_DUALLISTBOX_BOOTSTRAP = "/plugins/bootstrap4-duallistbox/jquery.bootstrap-duallistbox.min.js";

    public static final String JS_MOMENT = "/plugins/moment/moment.min.js";

    public static final String JS_INPUT_MASK = "/plugins/inputmask/jquery.inputmask.min.js";

    public static final String JS_DATE_RANGE_PICKER = "/plugins/daterangepicker/daterangepicker.js";

    public static final String JS_COLOR_PICKER = "/plugins/bootstrap-colorpicker/js/bootstrap-colorpicker.min.js";

    public static final String JS_TEMPUSDOMINUS_BOOTSTRAP = "/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.js";

    // public static final String JS_SWITCH_BOOTSTRAP = "/plugins/bootstrap-switch/js/bootstrap-switch.min.js";

    public static final String JS_DIST_DEMO = "/dist/js/demo.js";

    public static final String JS_SUMMER_NOTE = "/plugins/summernote/summernote-bs4.min.js";

    public static final String JS_SUMMER_NOTE_SCRIPT = "$(function(){$('.textarea').summernote()});";

    public static final String JS_CODE_MIRROR_SCRIPT = "$(function(){CodeMirror.fromTextArea(document.getElementById(\"codeMirrorDemo\"), {mode: \"htmlmixed\",theme: \"monokai\"});});";

    public static final String JS_SUMMER_NOTE_SCRIPT_1 = "$(function(){$('#compose-textarea').summernote()});";

    public static final String JS_JQUERY_UI = "/plugins/jquery-ui/jquery-ui.min.js";

    public static final String JS_LIGHT_BOX = "/plugins/ekko-lightbox/ekko-lightbox.min.js";

    public static final String JS_JQUERY_FILTER = "/plugins/filterizr/jquery.filterizr.min.js";

    public static final String JS_JQUERY = "/plugins/jquery/jquery.min.js";

    public static final String JS_JQUERY_FLOT = "/plugins/flot/jquery.flot.js";

    public static final String JS_JQUERY_FLOT_RESIZE = "/plugins/flot/plugins/jquery.flot.resize.js";

    public static final String JS_JQUERY_FLOT_PIE = "/plugins/flot/plugins/jquery.flot.pie.js";

    public static final String JS_JQUERY_KNOB = "/plugins/jquery-knob/jquery.knob.min.js";

    public static final String JS_SPARKLINES = "/plugins/sparklines/sparkline.js";

    public static final String CSS_RANGE_SLIDER = "/plugins/ion-rangeslider/css/ion.rangeSlider.min.css";

    public static final String JS_RANGE_SLIDER = "/plugins/ion-rangeslider/js/ion.rangeSlider.min.js";

    public static final String JS_SLIDER_BOOTSTRAP = "/plugins/bootstrap-slider/bootstrap-slider.min.js";

    public static final String CSS_SLIDER_BOOTSTRAP = "/plugins/bootstrap-slider/css/bootstrap-slider.min.css";

    public static final String JS_RANGE_SLIDER_ION = "/plugins/ion-rangeslider/js/ion.rangeSlider.min.js";

    public static final String JS_DIST_DASHBOARD = "/dist/js/pages/dashboard.js";

    public static final String JS_DIST_DASHBOARD_2 = "/dist/js/pages/dashboard2.js";

    public static final String JS_DIST_DASHBOARD_3 = "/dist/js/pages/dashboard3.js";

    public static final String JS_JQUERY_MOUSEWHEEL = "/plugins/jquery-mousewheel/jquery.mousewheel.js";

    public static final String JS_RAPHAEL = "/plugins/raphael/raphael.min.js";

    public static final String JS_JQUERY_MAPAEL = "/plugins/jquery-mapael/jquery.mapael.min.js";

    public static final String JS_JQUERY_MAPAEL_USA = "/plugins/jquery-mapael/maps/usa_states.min.js";

    public static final String JS_DATA_TABLE_RESPONSIVE_BOOTSTRAP4 = "/plugins/datatables-responsive/js/responsive.bootstrap4.min.js";

    public static final String JS_DATA_TABLE_BUTTON = "/plugins/datatables-buttons/js/dataTables.buttons.min.js";

    public static final String JS_DATA_TABLE_BUTTON_BOOTSTRAP4 = "/plugins/datatables-buttons/js/buttons.bootstrap4.min.js";

    public static final String JS_ZIP = "/plugins/jszip/jszip.min.js";

    public static final String JS_PDF = "/plugins/pdfmake/pdfmake.min.js";

    public static final String JS_PDF_FONT = "/plugins/pdfmake/vfs_fonts.js";

    public static final String JS_DATA_TABLE_BUTTON_HTML5 = "/plugins/datatables-buttons/js/buttons.html5.min.js";

    public static final String JS_DATA_TABLE_BUTTON_PRINT = "/plugins/datatables-buttons/js/buttons.print.min.js";

    public static final String JS_DATA_TABLE_BUTTON_VIS = "/plugins/datatables-buttons/js/buttons.colVis.min.js";

    public static final String IMG_LOGO = "/dist/img/AdminLTELogo.png";

    public static final String IMG_USER_1 = "/dist/img/user1-128x128.jpg";

    public static final String IMG_USER_2 = "/dist/img/user2-160x160.jpg";

    public static final String IMG_USER_3 = "/dist/img/user3-128x128.jpg";

    public static final String IMG_USER_4 = "/dist/img/user4-128x128.jpg";

    public static final String IMG_USER_5 = "/dist/img/user5-128x128.jpg";

    public static final String IMG_USER_6 = "/dist/img/user6-128x128.jpg";

    public static final String IMG_USER_7 = "/dist/img/user7-128x128.jpg";

    public static final String IMG_USER_8 = "/dist/img/user8-128x128.jpg";

    public static final String IMG_PRD_1 = "/dist/img/prod-1.jpg";

    public static final String IMG_PRD_2 = "/dist/img/prod-2.jpg";

    public static final String IMG_PRD_3 = "/dist/img/prod-3.jpg";

    public static final String IMG_PRD_4 = "/dist/img/prod-4.jpg";

    public static final String IMG_PRD_5 = "/dist/img/prod-5.jpg";

    public static final String IMG_PHOTO_1 = "/dist/img/photo1.png";

    public static final String IMG_PHOTO_2 = "/dist/img/photo2.png";

    public static final String IMG_PHOTO_3 = "/dist/img/photo3.jpg";

    public static final String IMG_PHOTO_4 = "/dist/img/photo4.jpg";

    public static final String IMG_ICONS = "/dist/img/icons.png";

    public static final String IMG_DEFAULT = "/dist/img/default-150x150.png";

    public static final String IMG_BOXED_BG_PNG = "/dist/img/boxed-bg.png";

    public static final String IMG_BOXED_BG_JPG = "/dist/img/boxed-bg.jpg";

    public static final String IMG_AVATAR_5 = "/dist/img/avatar5.png";

    public static final String IMG_AVATAR_4 = "/dist/img/avatar4.png";

    public static final String IMG_AVATAR_3 = "/dist/img/avatar3.png";

    public static final String IMG_AVATAR_2 = "/dist/img/avatar2.png";

    public static final String IMG_AVATAR_1 = "/dist/img/avatar.png";

    public static final String IMG_CREDIT_AE = "/dist/img/credit/american-express.png";

    public static final String IMG_CREDIT_CI = "/dist/img/credit/cirrus.png";

    public static final String IMG_CREDIT_MA = "/dist/img/credit/mastercard.png";

    public static final String IMG_CREDIT_ME = "/dist/img/credit/mestro.png";

    public static final String IMG_CREDIT_PA = "/dist/img/credit/paypal.png";

    public static final String IMG_CREDIT_P2 = "/dist/img/credit/paypal2.png";

    public static final String IMG_CREDIT_VI = "/dist/img/credit/visa.png";

    public static final String CSS_CODE_MIRROR = "/plugins/codemirror/codemirror.css";

    public static final String CSS_CODE_MIRROR_THEME_MONOKAI = "/plugins/codemirror/theme/monokai.css";

    public static final String JS_CODE_MIRROR = "/plugins/codemirror/codemirror.js";

    public static final String JS_CODE_MIRROR_CSS = "/plugins/codemirror/mode/css/css.js";

    public static final String JS_CODE_MIRROR_XML = "/plugins/codemirror/mode/xml/xml.js";

    public static final String JS_CODE_MIRROR_HTML = "/plugins/codemirror/mode/htmlmixed/htmlmixed.js";

    public static final String CSS_BS_STEPPER = "/plugins/bs-stepper/css/bs-stepper.min.css";

    public static final String CSS_DROPZONEJS = "/plugins/dropzone/min/dropzone.min.css";

    public static final String JS_BS_STEPPER = "/plugins/bs-stepper/js/bs-stepper.min.js";

    public static final String JS_DROPZONEJS = "/plugins/dropzone/min/dropzone.min.js";

    /**
     * Construct.
     *
     * @param name
     */
    public AdminLTEResourceReference(final String name) {
        super(AdminLTEResourceReference.class, name);
    }

    /**
     * @see org.apache.wicket.request.resource.ResourceReference#getResource()
     */
    @Override
    public IResource getResource() {
        ApplicationContext context = AbstractWicketFactory.getApplicationContext();
        WebUiProperties properties = context.getBean(WebUiProperties.class);

        final String name = getName();

        File file = new File(properties.getAdminLte(), name);

        if (FilenameUtils.normalize(file.getAbsolutePath(), true).startsWith(FilenameUtils.normalize(properties.getAdminLte().getAbsolutePath(), true))) {
            return new FileSystemResource(file.toPath());
        }
        return null;
    }

}