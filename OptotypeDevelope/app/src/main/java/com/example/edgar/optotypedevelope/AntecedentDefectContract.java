package com.example.edgar.optotypedevelope;

import android.provider.BaseColumns;

/**
 * Created by Edgar on 31/03/2018.
 */

public class AntecedentDefectContract {

    public static abstract class AntecedentDefectContractEntry  implements BaseColumns {

        public static final String TABLE_NAME = "antecedent_db_app";

        public static final String ID = "idAntecedent";
        public static final String ANTECEDENTNAME = "antecedentName";
    }

}
