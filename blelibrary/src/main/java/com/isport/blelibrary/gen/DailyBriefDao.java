package com.isport.blelibrary.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.isport.blelibrary.db.table.s002.DailyBrief;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DAILY_BRIEF".
*/
public class DailyBriefDao extends AbstractDao<DailyBrief, Long> {

    public static final String TABLENAME = "DAILY_BRIEF";

    /**
     * Properties of entity DailyBrief.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property RopeSportDetailId = new Property(1, String.class, "ropeSportDetailId", false, "ROPE_SPORT_DETAIL_ID");
        public final static Property StartTime = new Property(2, String.class, "startTime", false, "START_TIME");
        public final static Property SkippingNum = new Property(3, String.class, "skippingNum", false, "SKIPPING_NUM");
        public final static Property ExerciseType = new Property(4, int.class, "exerciseType", false, "EXERCISE_TYPE");
        public final static Property ChallengeType = new Property(5, int.class, "challengeType", false, "CHALLENGE_TYPE");
        public final static Property SkippingDuration = new Property(6, long.class, "skippingDuration", false, "SKIPPING_DURATION");
        public final static Property AverageFrequency = new Property(7, String.class, "averageFrequency", false, "AVERAGE_FREQUENCY");
        public final static Property TotalCalories = new Property(8, String.class, "totalCalories", false, "TOTAL_CALORIES");
        public final static Property HhandMin = new Property(9, String.class, "hhandMin", false, "HHAND_MIN");
        public final static Property UserId = new Property(10, String.class, "userId", false, "USER_ID");
        public final static Property StrDate = new Property(11, String.class, "strDate", false, "STR_DATE");
        public final static Property IsLocation = new Property(12, boolean.class, "isLocation", false, "IS_LOCATION");
    };


    public DailyBriefDao(DaoConfig config) {
        super(config);
    }
    
    public DailyBriefDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DAILY_BRIEF\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"ROPE_SPORT_DETAIL_ID\" TEXT," + // 1: ropeSportDetailId
                "\"START_TIME\" TEXT," + // 2: startTime
                "\"SKIPPING_NUM\" TEXT," + // 3: skippingNum
                "\"EXERCISE_TYPE\" INTEGER NOT NULL ," + // 4: exerciseType
                "\"CHALLENGE_TYPE\" INTEGER NOT NULL ," + // 5: challengeType
                "\"SKIPPING_DURATION\" INTEGER NOT NULL ," + // 6: skippingDuration
                "\"AVERAGE_FREQUENCY\" TEXT," + // 7: averageFrequency
                "\"TOTAL_CALORIES\" TEXT," + // 8: totalCalories
                "\"HHAND_MIN\" TEXT," + // 9: hhandMin
                "\"USER_ID\" TEXT," + // 10: userId
                "\"STR_DATE\" TEXT," + // 11: strDate
                "\"IS_LOCATION\" INTEGER NOT NULL );"); // 12: isLocation
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DAILY_BRIEF\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DailyBrief entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String ropeSportDetailId = entity.getRopeSportDetailId();
        if (ropeSportDetailId != null) {
            stmt.bindString(2, ropeSportDetailId);
        }
 
        String startTime = entity.getStartTime();
        if (startTime != null) {
            stmt.bindString(3, startTime);
        }
 
        String skippingNum = entity.getSkippingNum();
        if (skippingNum != null) {
            stmt.bindString(4, skippingNum);
        }
        stmt.bindLong(5, entity.getExerciseType());
        stmt.bindLong(6, entity.getChallengeType());
        stmt.bindLong(7, entity.getSkippingDuration());
 
        String averageFrequency = entity.getAverageFrequency();
        if (averageFrequency != null) {
            stmt.bindString(8, averageFrequency);
        }
 
        String totalCalories = entity.getTotalCalories();
        if (totalCalories != null) {
            stmt.bindString(9, totalCalories);
        }
 
        String hhandMin = entity.getHhandMin();
        if (hhandMin != null) {
            stmt.bindString(10, hhandMin);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(11, userId);
        }
 
        String strDate = entity.getStrDate();
        if (strDate != null) {
            stmt.bindString(12, strDate);
        }
        stmt.bindLong(13, entity.getIsLocation() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DailyBrief entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String ropeSportDetailId = entity.getRopeSportDetailId();
        if (ropeSportDetailId != null) {
            stmt.bindString(2, ropeSportDetailId);
        }
 
        String startTime = entity.getStartTime();
        if (startTime != null) {
            stmt.bindString(3, startTime);
        }
 
        String skippingNum = entity.getSkippingNum();
        if (skippingNum != null) {
            stmt.bindString(4, skippingNum);
        }
        stmt.bindLong(5, entity.getExerciseType());
        stmt.bindLong(6, entity.getChallengeType());
        stmt.bindLong(7, entity.getSkippingDuration());
 
        String averageFrequency = entity.getAverageFrequency();
        if (averageFrequency != null) {
            stmt.bindString(8, averageFrequency);
        }
 
        String totalCalories = entity.getTotalCalories();
        if (totalCalories != null) {
            stmt.bindString(9, totalCalories);
        }
 
        String hhandMin = entity.getHhandMin();
        if (hhandMin != null) {
            stmt.bindString(10, hhandMin);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(11, userId);
        }
 
        String strDate = entity.getStrDate();
        if (strDate != null) {
            stmt.bindString(12, strDate);
        }
        stmt.bindLong(13, entity.getIsLocation() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DailyBrief readEntity(Cursor cursor, int offset) {
        DailyBrief entity = new DailyBrief( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // ropeSportDetailId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // startTime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // skippingNum
            cursor.getInt(offset + 4), // exerciseType
            cursor.getInt(offset + 5), // challengeType
            cursor.getLong(offset + 6), // skippingDuration
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // averageFrequency
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // totalCalories
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // hhandMin
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // userId
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // strDate
            cursor.getShort(offset + 12) != 0 // isLocation
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DailyBrief entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRopeSportDetailId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setStartTime(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSkippingNum(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setExerciseType(cursor.getInt(offset + 4));
        entity.setChallengeType(cursor.getInt(offset + 5));
        entity.setSkippingDuration(cursor.getLong(offset + 6));
        entity.setAverageFrequency(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setTotalCalories(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setHhandMin(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setUserId(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setStrDate(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setIsLocation(cursor.getShort(offset + 12) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DailyBrief entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DailyBrief entity) {
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
