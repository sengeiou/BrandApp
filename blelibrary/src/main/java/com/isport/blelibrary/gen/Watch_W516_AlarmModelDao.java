package com.isport.blelibrary.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.isport.blelibrary.db.table.watch_w516.Watch_W516_AlarmModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WATCH__W516__ALARM_MODEL".
*/
public class Watch_W516_AlarmModelDao extends AbstractDao<Watch_W516_AlarmModel, Long> {

    public static final String TABLENAME = "WATCH__W516__ALARM_MODEL";

    /**
     * Properties of entity Watch_W516_AlarmModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property DeviceId = new Property(2, String.class, "deviceId", false, "DEVICE_ID");
        public final static Property RepeatCount = new Property(3, int.class, "repeatCount", false, "REPEAT_COUNT");
        public final static Property TimeString = new Property(4, String.class, "timeString", false, "TIME_STRING");
        public final static Property MessageString = new Property(5, String.class, "messageString", false, "MESSAGE_STRING");
    };


    public Watch_W516_AlarmModelDao(DaoConfig config) {
        super(config);
    }
    
    public Watch_W516_AlarmModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WATCH__W516__ALARM_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_ID\" TEXT," + // 1: userId
                "\"DEVICE_ID\" TEXT," + // 2: deviceId
                "\"REPEAT_COUNT\" INTEGER NOT NULL ," + // 3: repeatCount
                "\"TIME_STRING\" TEXT," + // 4: timeString
                "\"MESSAGE_STRING\" TEXT);"); // 5: messageString
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WATCH__W516__ALARM_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Watch_W516_AlarmModel entity) {
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
        stmt.bindLong(4, entity.getRepeatCount());
 
        String timeString = entity.getTimeString();
        if (timeString != null) {
            stmt.bindString(5, timeString);
        }
 
        String messageString = entity.getMessageString();
        if (messageString != null) {
            stmt.bindString(6, messageString);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Watch_W516_AlarmModel entity) {
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
        stmt.bindLong(4, entity.getRepeatCount());
 
        String timeString = entity.getTimeString();
        if (timeString != null) {
            stmt.bindString(5, timeString);
        }
 
        String messageString = entity.getMessageString();
        if (messageString != null) {
            stmt.bindString(6, messageString);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Watch_W516_AlarmModel readEntity(Cursor cursor, int offset) {
        Watch_W516_AlarmModel entity = new Watch_W516_AlarmModel( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // deviceId
            cursor.getInt(offset + 3), // repeatCount
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // timeString
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // messageString
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Watch_W516_AlarmModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDeviceId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRepeatCount(cursor.getInt(offset + 3));
        entity.setTimeString(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setMessageString(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Watch_W516_AlarmModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Watch_W516_AlarmModel entity) {
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
