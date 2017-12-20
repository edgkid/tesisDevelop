package com.example.edgar.optotypedevelope;

import android.provider.BaseColumns;

/**
 * Created by Edgar on 20/12/2017.
 */

public class InteractionDbContract {

    public static abstract class InteractionEntry  implements BaseColumns {

        public static final String TABLE_NAME = "interaction_db_app";

        public static final String ID = "idInterection";
        public static final String IDOPTOTYPE = "idOptotype";
        public static final String IDTEST = "idTest";


    }




}
