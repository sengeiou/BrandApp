package com.isport.blelibrary.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.isport.blelibrary.db.table.DeviceTempUnitlTable;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DEVICE_TEMP_UNITL_TABLE".
*/
public class DeviceTempUnitlTableDao extends AbstractDao<DeviceTempUnitlTable, Long> {

    public static final String TABLENAME = "DEVICE_TEMP_UNITL_TABLE";

    /**
     * Properties of entity DeviceTempUnitlTable.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property DeviceName = new Property(1, String.class, "deviceName", false, "DEVICE_NAME");
        public final static Property UserId = new Property(2, String.class, "userId", false, "USER_ID");
        public final static Property TempUnitl = new Property(3, String.class, "tempUnitl", false, "TEMP_UNITL");
    };


    public DeviceTempUnitlTableDao(DaoConfig config) {
        super(config);
    }
    
    public DeviceTempUnitlTableDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DEVICE_TEMP_UNITL_TABLE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"DEVICE_NAME\" TEXT," + // 1: deviceName
                "\"USER_ID\" TEXT," + // 2: userId
                "\"TEMP_UNITL\" TEXT);"); // 3: tempUnitl
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DEVICE_TEMP_UNITL_TABLE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DeviceTempUnitlTable entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(2, deviceName);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String tempUnitl = entity.getTempUnitl();
        if (tempUnitl != null) {
            stmt.bindString(4, tempUnitl);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DeviceTempUnitlTable entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String deviceName = entity.getDeviceName();
        if (deviceName != null) {
            stmt.bindString(2, deviceName);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String tempUnitl = entity.getTempUnitl();
        if (tempUnitl != null) {
            stmt.bindString(4, tempUnitl);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DeviceTempUnitlTable readEntity(Cursor cursor, int offset) {
        DeviceTempUnitlTable entity = new DeviceTempUnitlTable( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // deviceName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // tempUnitl
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DeviceTempUnitlTable entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDeviceName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTempUnitl(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DeviceTempUnitlTable entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DeviceTempUnitlTable entity) {
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