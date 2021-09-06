package com.isport.blelibrary.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_RealTimeData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BRACELET__W311__REAL_TIME_DATA".
*/
public class Bracelet_W311_RealTimeDataDao extends AbstractDao<Bracelet_W311_RealTimeData, Long> {

    public static final String TABLENAME = "BRACELET__W311__REAL_TIME_DATA";

    /**
     * Properties of entity Bracelet_W311_RealTimeData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property DeviceId = new Property(2, String.class, "deviceId", false, "DEVICE_ID");
        public final static Property StepNum = new Property(3, int.class, "stepNum", false, "STEP_NUM");
        public final static Property StepKm = new Property(4, float.class, "stepKm", false, "STEP_KM");
        public final static Property Cal = new Property(5, int.class, "cal", false, "CAL");
        public final static Property Date = new Property(6, String.class, "date", false, "DATE");
        public final static Property Mac = new Property(7, String.class, "mac", false, "MAC");
    };


    public Bracelet_W311_RealTimeDataDao(DaoConfig config) {
        super(config);
    }
    
    public Bracelet_W311_RealTimeDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BRACELET__W311__REAL_TIME_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_ID\" TEXT," + // 1: userId
                "\"DEVICE_ID\" TEXT," + // 2: deviceId
                "\"STEP_NUM\" INTEGER NOT NULL ," + // 3: stepNum
                "\"STEP_KM\" REAL NOT NULL ," + // 4: stepKm
                "\"CAL\" INTEGER NOT NULL ," + // 5: cal
                "\"DATE\" TEXT," + // 6: date
                "\"MAC\" TEXT);"); // 7: mac
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BRACELET__W311__REAL_TIME_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Bracelet_W311_RealTimeData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String deviceId = entity.getDeviceId();
        if (deviceId != null) {
            stmt.bindString(3, deviceId);
        }
        stmt.bindLong(4, entity.getStepNum());
        stmt.bindDouble(5, entity.getStepKm());
        stmt.bindLong(6, entity.getCal());
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(7, date);
        }
 
        String mac = entity.getMac();
        if (mac != null) {
            stmt.bindString(8, mac);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Bracelet_W311_RealTimeData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(2, userId);
        }
 
        String deviceId = entity.getDeviceId();
        if (deviceId != null) {
            stmt.bindString(3, deviceId);
        }
        stmt.bindLong(4, entity.getStepNum());
        stmt.bindDouble(5, entity.getStepKm());
        stmt.bindLong(6, entity.getCal());
 
        String date = entity.getDate();
        if (date != null) {
            stmt.bindString(7, date);
        }
 
        String mac = entity.getMac();
        if (mac != null) {
            stmt.bindString(8, mac);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Bracelet_W311_RealTimeData readEntity(Cursor cursor, int offset) {
        Bracelet_W311_RealTimeData entity = new Bracelet_W311_RealTimeData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // deviceId
            cursor.getInt(offset + 3), // stepNum
            cursor.getFloat(offset + 4), // stepKm
            cursor.getInt(offset + 5), // cal
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // date
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7) // mac
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Bracelet_W311_RealTimeData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDeviceId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setStepNum(cursor.getInt(offset + 3));
        entity.setStepKm(cursor.getFloat(offset + 4));
        entity.setCal(cursor.getInt(offset + 5));
        entity.setDate(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setMac(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Bracelet_W311_RealTimeData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Bracelet_W311_RealTimeData entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}