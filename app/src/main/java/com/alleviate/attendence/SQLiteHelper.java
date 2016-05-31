package com.alleviate.attendence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by felix on 11/1/16.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String dbName = "Student";

    public static final String db_Utable = "User";
    public static final String db_user_id = "Id";
    public static final String db_user_name = "UserName";
    public static final String db_user_password = "Password";
    public static final String db_user_type = "Type";

    public static final String db_Itable = "Info";
    public static final String db_info_id = "Id";
    public static final String db_info_no = "UNo";
    public static final String db_info_name = "Name";
    public static final String db_info_username = "UserName";
    public static final String db_info_contact = "Number";
    public static final String db_info_sem = "Semester";
    public static final String db_info_year = "Year";
    public static final String db_info_address = "Address";

    public static final String db_Atable = "Attendance";
    public static final String db_attend_id = "Id";
    public static final String db_attend_username = "UserName";
    public static final String db_attend_sem = "Semester";
    public static final String db_attend_sub = "Subject";
    public static final String db_attend_value = "Value";
    public static final String db_attend_date = "Date";



    public static final int db_version = 1;


    public SQLiteHelper(Context context) {
        super(context, dbName, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String creat_user_table = "CREATE TABLE "+db_Utable+"  ("+db_user_id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+db_user_name+" TEXT, "+db_user_password+" TEXT, "+db_user_type+" TEXT)";
        String creat_info_table = "CREATE TABLE "+db_Itable+"  ("+db_info_id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+db_info_no+" TEXT, "+db_info_name+" TEXT, "+db_info_username+" TEXT, "+db_info_contact+" INTEGER, "+db_info_sem+" TEXT, "+db_info_year+" TEXT, "+db_info_address+" TEXT)";
        String creat_attend_table = "CREATE TABLE "+db_Atable+"  ("+db_attend_id+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+db_attend_username+" TEXT, "+db_attend_sem+" TEXT, "+db_attend_sub+" TEXT, "+db_attend_value+" INTEGER, "+db_attend_date+" TEXT)";

        sqLiteDatabase.execSQL(creat_user_table);
        sqLiteDatabase.execSQL(creat_info_table);
        sqLiteDatabase.execSQL(creat_attend_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
