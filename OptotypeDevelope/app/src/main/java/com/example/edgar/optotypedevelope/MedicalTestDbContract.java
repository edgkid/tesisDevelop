package com.example.edgar.optotypedevelope;

import android.provider.BaseColumns;

/**
 * Created by Edgar on 20/12/2017.
 */

public class MedicalTestDbContract {

    public static abstract class MedicalTestEntry  implements BaseColumns {

        public static final String TABLE_NAME = "test_db_app";

        public static final String ID = "idtest";
        public static final String TESTCODE = "testCode";
        public static final String EYE = "eye";
        public static final String IDPATIENT = "idPatient";


    }


}
