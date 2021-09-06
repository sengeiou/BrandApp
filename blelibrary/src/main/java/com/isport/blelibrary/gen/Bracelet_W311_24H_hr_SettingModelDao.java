package com.isport.blelibrary.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.isport.blelibrary.db.table.bracelet_w311.Bracelet_W311_24H_hr_SettingModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BRACELET__W311_24_H_HR__SETTING_MODEL".
*/
public class Bracelet_W311_24H_hr_SettingModelDao extends AbstractDao<Bracelet_W311_24H_hr_SettingModel, Long> {

    public static final String TABLENAME = "BRACELET__W311_24_H_HR__SETTING_MODEL";

    /**
     * Properties of entity Bracelet_W311_24H_hr_SettingModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property UserId = new Property(1, String.class, "userId", false, "USER_ID");
        public final static Property DeviceId = new Property(2, String.class, "deviceId", false, "DEVICE_ID");
        public final static Property HeartRateSwitch = new Property(3, boolean.class, "heartRateSwitch", false, "HEART_RATE_SWITCH");
    };


    public Bracelet_W311_24H_hr_SettingModelDao(DaoConfig config) {
        super(config);
    }
    
    public Bracelet_W311_24H_hr_SettingModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BRACELET__W311_24_H_HR__SETTING_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_ID\" TEXT," + // 1: userId
                "\"DEVICE_ID\" TEXT," + // 2: deviceId
                "\"HEART_RATE_SWITCH\" INTEGER NOT NULL );"); // 3: heartRateSwitch
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BRACELET__W311_24_H_HR__SETTING_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Bracelet_W311_24H_hr_SettingModel entity) {
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
        stmt.bindLong(4, entity.getHeartRateSwitch() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Bracelet_W311_24H_hr_SettingModel entity) {
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
        stmt.bindLong(4, entity.getHeartRateSwitch() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Bracelet_W311_24H_hr_SettingModel readEntity(Cursor cursor, int offset) {
        Bracelet_W311_24H_hr_SettingModel entity = new Bracelet_W311_24H_hr_SettingModel( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // userId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // deviceId
            cursor.getShort(offset + 3) != 0 // heartRateSwitch
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Bracelet_W311_24H_hr_SettingModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDeviceId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setHeartRateSwitch(cursor.getShort(offset + 3) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Bracelet_W311_24H_hr_SettingModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Bracelet_W311_24H_hr_SettingModel entity) {
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