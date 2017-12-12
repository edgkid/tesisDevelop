package com.example.edgar.optotypedevelope;

import android.provider.BaseColumns;

/**
 * Created by Edgar on 11/12/2017.
 */

public class OptotypeDbContract {

    public static abstract class OptotypeEntry implements BaseColumns {

        public static final String TABLE_NAME = "optotype_db_app";

        public static final String ID = "idOptotype";
        public static final String OPTOTYPECODE = "optotypeCode";
        public static final String OPTOTYPENAME = "optotypeName";
        public static final String IMAGE = "image";

    }

}
