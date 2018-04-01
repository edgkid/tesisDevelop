package com.example.edgar.optotypedevelope;

import android.provider.BaseColumns;

/**
 * Created by Edgar on 31/03/2018.
 */

public class SignalDbContract {

    public static abstract class SignalDbContractEntry  implements BaseColumns {


        public static final String TABLE_NAME = "signal_db_app";

        public static final String ID = "idSignal";
        public static final String SIGNALNAME = "signalName";
    }
}
