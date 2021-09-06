package com.isport.blelibrary.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.isport.blelibrary.db.table.w811w814.W81DeviceExerciseHrData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "W81_DEVICE_EXERCISE_HR_DATA".
*/
public class W81DeviceExerciseHrDataDao extends AbstractDao<W81DeviceExerciseHrData, Long> {

    public static final String TABLENAME = "W81_DEVICE_EXERCISE_HR_DATA";

    /**
     * Properties of entity W81DeviceExerciseHrData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property DeviceId = new Property(2, String.class, "deviceId", false, "DEVICE_ID");
        public final static Property AvgHr = new Property(3, int.class, "avgHr", false, "AVG_HR");
        public final static Property HrArray = new Property(4, String.class, "hrArray", false, "HR_ARRAY");
        public final static Property TimeInterval = new Property(5, int.class, "timeInterval", false, "TIME_INTERVAL");
        public final static Property StartMeasureTime = new Property(6, long.class, "startMeasureTime", false, "START_MEASURE_TIME");
    };


    public W81DeviceExerciseHrDataDao(DaoConfig config) {
        super(config);
    }
    
    public W81DeviceExerciseHrDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"W81_DEVICE_EXERCISE_HR_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_ID\" TEXT," + // 1: userId
                "\"DEVICE_ID\" TEXT," + // 2: deviceId
                "\"AVG_HR\" INTEGER NOT NULL ," + // 3: avgHr
                "\"HR_ARRAY\" TEXT," + // 4: hrArray
                "\"TIME_INTERVAL\" INTEGER NOT NULL ," + // 5: timeInterval
                "\"START_MEASURE_TIME\" INTEGER NOT NULL );"); // 6: startMeasureTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"W81_DEVICE_EXERCISE_HR_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, W81DeviceExerciseHrData entity) {
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
        stmt.bindLong(4, entity.getAvgHr());
 
        String hrArray = entity.getHrArray();
        if (hrArray != null) {
            stmt.bindString(5, hrArray);
        }
        stmt.bindLong(6, entity.getTimeInterval());
        stmt.bindLong(7, entity.getStartMeasureTime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, W81DeviceExerciseHrData entity) {
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
        stmt.bindLong(4, entity.getAvgHr());
 
        String hrArray = entity.getHrArray();
        if (hrArray != null) {
            stmt.bindString(5, hrArray);
        }
        stmt.bindLong(6, entity.getTimeInterval());
        stmt.bindLong(7, entity.getStartMeasureTime());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public W81DeviceExerciseHrData readEntity(Cursor cursor, int offset) {
        W81DeviceExerciseHrData entity = new W81DeviceExerciseHrData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // deviceId
            cursor.getInt(offset + 3), // avgHr
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // hrArray
            cursor.getInt(offset + 5), // timeInterval
            cursor.getLong(offset + 6) // startMeasureTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, W81DeviceExerciseHrData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDeviceId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAvgHr(cursor.getInt(offset + 3));
        entity.setHrArray(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setTimeInterval(cursor.getInt(offset + 5));
        entity.setStartMeasureTime(cursor.getLong(offset + 6));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(W81DeviceExerciseHrData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(W81DeviceExerciseHrData entity) {
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
