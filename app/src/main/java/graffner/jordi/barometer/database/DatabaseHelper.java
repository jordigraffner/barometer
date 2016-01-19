package graffner.jordi.barometer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by jordigraffner on 29/12/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase mSQLDB;
    private static DatabaseHelper mInstance;
    public static final String dbName = "barometer.db";
    public static final int dbVersion = 54;


    public DatabaseHelper(Context ctx) {
        super(ctx, dbName, null, dbVersion);					// gebruik de super constructor.
    }

    public static synchronized DatabaseHelper getHelper (Context ctx){	// synchronized … dit zorgt voor . . . . (?)
        if (mInstance == null){
            mInstance = new DatabaseHelper(ctx);
            mSQLDB = mInstance.getWritableDatabase();
        }
        return mInstance;
    }

    @Override										// Maak je tabel met deze kolommen
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DatabaseInfo.BarometerTables.USER + " (" +
                        BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DatabaseInfo.UserColumn.NAME + " TEXT);"
        );
        db.execSQL("CREATE TABLE " + DatabaseInfo.BarometerTables.COURSE + " (" +
                        BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DatabaseInfo.CourseColumn.NAME + " TEXT," +
                        DatabaseInfo.CourseColumn.ECTS + " TEXT," +
                        DatabaseInfo.CourseColumn.PERIOD + " TEXT," +
                        DatabaseInfo.CourseColumn.GRADE + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseInfo.BarometerTables.USER);
        db.execSQL("DROP TABLE IF EXISTS "+ DatabaseInfo.BarometerTables.COURSE);
        onCreate(db);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version ){
        super(context,name,factory, version);
    }

    public void insert(String table, String nullColumnHack, ContentValues values){
        mSQLDB.insert(table, nullColumnHack, values);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy){
        return mSQLDB.query(table, columns, selection, selectArgs, groupBy, having, orderBy);
    }


}
