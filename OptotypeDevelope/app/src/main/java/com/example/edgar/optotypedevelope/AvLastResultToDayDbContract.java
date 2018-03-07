package com.example.edgar.optotypedevelope;

import android.provider.BaseColumns;

/**
 * Created by Edgar on 02/03/2018.
 */

public class AvLastResultToDayDbContract {

    public static abstract class AvLastResultToDayDbContractEntry  implements BaseColumns{


        public static final String TABLE_NAME = "av_result_db_app";

        public static final String ID = "idAvResult";
        public static final String IDPATIENT = "idPatient";
        public static final String LASTAPPOINTMENTDATE = "lastAppointmentDate";
        public static final String NEXTAPPOINTMENTDATE = "nextAppointmentDate";
        public static final String AVRIGHT = "avRight";
        public static final String AVLEFT = "avLeft";
        public static final String DESCRIPTION = "description";
        public static final String CENTER = "center";
        public static final String SUSTAIN = "sustain";
        public static final String MAINTAIN = "maintain";

    }
}
