package com.isport.blelibrary.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.isport.blelibrary.db.table.s002.DailySummaries;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DAILY_SUMMARIES".
*/
public class DailySummariesDao extends AbstractDao<DailySummaries, Long> {

    public static final String TABLENAME = "DAILY_SUMMARIES";

    /**
     * Properties of entity DailySummaries.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Day = new Property(1, String.class, "day", false, "DAY");
        public final static Property TotalSkippingNum = new Property(2, int.class, "totalSkippingNum", false, "TOTAL_SKIPPING_NUM");
        public final static Property TotalDuration = new Property(3, String.class, "totalDuration", false, "TOTAL_DURATION");
        public final static Property TotalCalories = new Property(4, String.class, "totalCalories", false, "TOTAL_CALORIES");
        public final static Property UserId = new Property(5, String.class, "userId", false, "USER_ID");
        public final static Property IsLocation = new Property(6, boolean.class, "isLocation", false, "IS_LOCATION");
    };


    public DailySummariesDao(DaoConfig config) {
        super(config);
    }
    
    public DailySummariesDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DAILY_SUMMARIES\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"DAY\" TEXT," + // 1: day
                "\"TOTAL_SKIPPING_NUM\" INTEGER NOT NULL ," + // 2: totalSkippingNum
                "\"TOTAL_DURATION\" TEXT," + // 3: totalDuration
                "\"TOTAL_CALORIES\" TEXT," + // 4: totalCalories
                "\"USER_ID\" TEXT," + // 5: userId
                "\"IS_LOCATION\" INTEGER NOT NULL );"); // 6: isLocation
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DAILY_SUMMARIES\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DailySummaries entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String day = entity.getDay();
        if (day != null) {
            stmt.bindString(2, day);
        }
        stmt.bindLong(3, entity.getTotalSkippingNum());
 
        String totalDuration = entity.getTotalDuration();
        if (totalDuration != null) {
            stmt.bindString(4, totalDuration);
        }
 
        String totalCalories = entity.getTotalCalories();
        if (totalCalories != null) {
            stmt.bindString(5, totalCalories);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(6, userId);
        }
        stmt.bindLong(7, entity.getIsLocation() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DailySummaries entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String day = entity.getDay();
        if (day != null) {
            stmt.bindString(2, day);
        }
        stmt.bindLong(3, entity.getTotalSkippingNum());
 
        String totalDuration = entity.getTotalDuration();
        if (totalDuration != null) {
            stmt.bindString(4, totalDuration);
        }
 
        String totalCalories = entity.getTotalCalories();
        if (totalCalories != null) {
            stmt.bindString(5, totalCalories);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(6, userId);
        }
        stmt.bindLong(7, entity.getIsLocation() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DailySummaries readEntity(Cursor cursor, int offset) {
        DailySummaries entity = new DailySummaries( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // day
            cursor.getInt(offset + 2), // totalSkippingNum
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // totalDuration
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // totalCalories
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // userId
            cursor.getShort(offset + 6) != 0 // isLocation
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DailySummaries entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setDay(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setTotalSkippingNum(cursor.getInt(offset + 2));
        entity.setTotalDuration(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setTotalCalories(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setUserId(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIsLocation(cursor.getShort(offset + 6) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DailySummaries entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DailySummaries entity) {
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
