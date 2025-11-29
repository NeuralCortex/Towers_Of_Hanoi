package com.fx.toh;

import java.util.Locale;

public class Globals {

    //Für Funktionen
    public static enum CUSTOMER {
        O2, VODAFONE, T_MOBILE, E_PLUS, DB_SYSTEL, COMARCH
    };

    public static enum INPUT {
        FILES, OBJ
    };

    //Globaler Schalter für Formulare
    public static CUSTOMER customer = CUSTOMER.O2;
    public static INPUT input = INPUT.OBJ;

    //Debug Infos - Ein / Aus
    public static boolean DEBUG = true;

    //Show Test-UI (Managed UI Ein / Aus)
    public static boolean SHOW_TEST_UI = false;

    public static final Locale DEFAULT_LOCALE = Locale.US;

    //Fenstergröße global gesteuert in 16:9
    public static final double HEIGHT = 900;//900.0f;
    public static final double WIDTH = HEIGHT * 18.0f / 9.0f;

    //Schalter für Maximized Ein / Aus
    public static final boolean MAXIMIZED = false;

    //DB-Path
    public static final String DB_PATH = System.getProperty("user.dir") + "/db/emp.db";

    public static final String BUNDLE_PATH = "com.fx.toh.bundle.toh";
    public static final String LOG4J_CONFIG_PATH = System.getProperty("user.dir") + "/config/log4j.xml";
    public static final String LOG4J2_CONFIG_PATH = System.getProperty("user.dir") + "/config/log4j2.xml";
    public static final String XML_CONFIG_PATH = System.getProperty("user.dir") + "/config/config.xml";
    public static final String POV_BLUEPRINT_CONFIG_PATH = System.getProperty("user.dir") + "/config/blueprint.pov";

    public static XMLPropertyManager propman;

    static {
        propman = new XMLPropertyManager(XML_CONFIG_PATH);
    }

    //Images
    public static final String APP_LOGO_PATH = System.getProperty("user.dir") + "/images/kdf.png";
    public static final String SIDE_IMAGE_PATH = System.getProperty("user.dir") + "/images/side.png";
    public static final String CENTER_IMAGE = System.getProperty("user.dir") + "/images/lago.jpg";
    public static final String CSS_PATH = "/com/fx/toh/style/nc.css";
    public static final String MOON_IMAGE_PATH = System.getProperty("user.dir") + "/images/mond_small.png";

    //FXML
    public static final String FXML_PATH = "/com/fx/toh/fxml/";
    public static final String FXML_TABS_PATH = "/com/fx/toh/fxml/tabs/";

    public static final String FXML_MAIN_PATH = FXML_PATH + "main_app.fxml";

    //CoordSys
    public static final String FXML_POLAR_PATH = FXML_TABS_PATH + "tab_polar.fxml";
    public static final String FXML_POLAR_LOG_PATH = FXML_TABS_PATH + "tab_polar_log.fxml";
    public static final String FXML_CART_PATH = FXML_TABS_PATH + "tab_cart.fxml";
    public static final String FXML_CART_LOG_PATH = FXML_TABS_PATH + "tab_cart_log.fxml";

    public static enum PART {
        PART_0, PART_1, PART_2, PART_3;
    };

    public static final String FXML_TEST_PATH = FXML_PATH + "test.fxml";

    //FXML-Dialoge
    public static final String DLG_PROGRESS_PATH = FXML_PATH + "progress.fxml";
    public static final String DLG_TILE_PATH = FXML_PATH + "tile_dlg.fxml";
    public static final String DLG_WGS_PATH = FXML_PATH + "dlg_wgs.fxml";
    public static final String DLG_OSM_PATH = FXML_PATH + "dlg_osm.fxml";

    //Custom-Path's
    public static final String BORDER_DIR = "BORDER_DIR";
    public static final String TOPO_DIR = "TOPO_DIR";
    public static final String MORPHO_DIR = "MORPHO_DIR";
    public static final String SRTM_DIR = "SRTM_DIR";
    public static final String POV_DIR = "POV_DIR";
    public static final String CSV_DIR = "CSV_DIR";
    public static final String COORD_LAT = "COORD_LAT";
    public static final String COORD_LON = "COORD_LON";

    //Default Path's
    public static final String TOPO_PATH = "c:/geo/topo";
    public static final String MORPHO_PATH = "c:/geo/morpho";

    //Control-Flags
    public static enum MODE {
        E, M
    };

    public static enum LOAD_MODE {
        FILE_CHOOSER, GERMANY_TOPO, GERMANY_MORPHO
    };

    public static enum TEXTURE_MODE {
        NORMAL, CHROME
    };
}
