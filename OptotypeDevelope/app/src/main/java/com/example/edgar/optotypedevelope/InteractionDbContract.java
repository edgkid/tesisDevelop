package com.example.edgar.optotypedevelope;

import android.provider.BaseColumns;

/**
 * Created by Edgar on 20/12/2017.
 */

public class InteractionDbContract {

    public static abstract class InteractionEntry  implements BaseColumns {

        public static final String TABLE_NAME = "interaction_db_app";

        public static final String ID = "idInteraction";
        public static final String IDOPTOTYPE = "idOptotype";
        public static final String IDPATIENT = "idPatient";
        public static final String TESTCODE = "testCode";
        public static final String EYE = "eye";

    }

}
