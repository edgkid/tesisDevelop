package com.example.edgar.optotypedevelope;

import android.provider.BaseColumns;

/**
 * Created by Edgar on 02/03/2018.
 */

public class AvLastResultToDayDbContract {

    public static abstract class AvLastResultToDayDbContractEntry  implements BaseColumns{


        public static final String TABLE_NAME = "av_result_db_app";

        public static final String ID = "idPatient";
        public static final String LASTAPPOINTMENTDATE = "";
        public static final String NEXTAPPOINTMENTDATE = "";
        public static final String AVRIGHT = "";
        public static final String AVLEFT = "";
        public static final String DESCRIPTION = "";
        public static final String CENTER = "";
        public static final String SUSTAIN = "";
        public static final String MAINTAIN = "";

    }
}
