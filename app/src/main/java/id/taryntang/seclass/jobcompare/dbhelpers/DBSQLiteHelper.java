package id.taryntang.seclass.jobcompare.dbhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import id.taryntang.seclass.jobcompare.models.Job;
import id.taryntang.seclass.jobcompare.models.JobDetails;
import id.taryntang.seclass.jobcompare.models.JobSettings;
import id.taryntang.seclass.jobcompare.models.JobToCompare;

public class DBSQLiteHelper extends SQLiteOpenHelper {
    public static final String JOBDETAILS_TABLE = "JOBDETAILS_TABLE";
    public static final String UNIQUEID = "ID";
    public static final String TITLE = "TITLE";
    public static final String COMPANY = "COMPANY";
    public static final String CITY = "CITY";
    public static final String STATE = "STATE";
    public static final String SALARY = "SALARY";
    public static final String BONUS = "BONUS";
    public static final String LEAVETIME = "LEAVETIME";
    public static final String SHARES = "SHARES";
    public static final String HOMEBUYINGFUND = "HOMEBUYINGFUND";
    public static final String WELLNESSFUND = "WELLNESSFUND";
    public static final String JOBTOCOMPARE_TABLE = "JOBTOCOMPARE_TABLE";
    public static final String JOBID = "JOBID";
    public static final String RANK = "RANK";
    public static final String JOB_TABLE = "JOB_TABLE";
    public static final String ISCURRENTJOB = "ISCURRENTJOB";

    private static final String ADJUSTCOMPARISON_TABLE = "adjust_comparison_settings";
    private static final String SALARY_FACTOR_COL = "salary_factor";
    private static final String BONUS_FACTOR_COL = "bonus_factor";
    private static final String VACATION_FACTOR_COL = "vacation_factor";
    private static final String SHARES_FACTOR_COL = "shares_factor";
    private static final String HOMEFUND_FACTOR_COL = "homefund_factor";
    private static final String WELLNESSFUND_FACTOR_COL = "wellness_factor";

    public static final String USER_TABLE = "USER_TABLE";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String SECURITY_QUESTION = "SECURITY_QUESTION";
    public static final String SECURITY_ANSWER = "SECURITY_ANSWER";

    public DBSQLiteHelper(@Nullable Context context) {
        super(context, "jobDetails.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createJobsTblStatement = "CREATE TABLE " + JOB_TABLE + " (" +
                UNIQUEID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                JOBID + " INT, " +
                ISCURRENTJOB + " BOOLEAN )";
        sqLiteDatabase.execSQL(createJobsTblStatement);

        String createJobDetailsTblStatement = "CREATE TABLE " + JOBDETAILS_TABLE + " (" +
                UNIQUEID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE + " TEXT," +
                COMPANY + " TEXT," +
                CITY + " TEXT, " +
                STATE + " TEXT, " +
                SALARY + " INT, " +
                BONUS + " INT, " +
                LEAVETIME + " INT, " +
                SHARES + " INT, " +
                HOMEBUYINGFUND + " INT, " +
                WELLNESSFUND + " INT )";
        sqLiteDatabase.execSQL(createJobDetailsTblStatement);

        String createJobsToCompareTblStatement = "CREATE TABLE " + JOBTOCOMPARE_TABLE + " (" +
                UNIQUEID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE + " TEXT," +
                COMPANY + " TEXT," +
                JOBID + " INT, " +
                RANK + " INT )";
        sqLiteDatabase.execSQL(createJobsToCompareTblStatement);

        String query = "CREATE TABLE " + ADJUSTCOMPARISON_TABLE + " ("
                + UNIQUEID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SALARY_FACTOR_COL + " TEXT,"
                + BONUS_FACTOR_COL+ " TEXT,"
                + VACATION_FACTOR_COL + " TEXT,"
                + SHARES_FACTOR_COL + " TEXT,"
                + HOMEFUND_FACTOR_COL + " TEXT,"
                + WELLNESSFUND_FACTOR_COL + " TEXT)";

        // call a exec sql method to execute the sql query
        sqLiteDatabase.execSQL(query);

        sqLiteDatabase.execSQL("create Table " + USER_TABLE + " (" + USERNAME + " TEXT primary key, " + PASSWORD + " TEXT, "
                + SECURITY_QUESTION + " TEXT, " + SECURITY_ANSWER + " TEXT)");
        seedAdminAccount(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            sqLiteDatabase.execSQL("ALTER TABLE " + USER_TABLE + " ADD COLUMN " + SECURITY_QUESTION + " TEXT");
            sqLiteDatabase.execSQL("ALTER TABLE " + USER_TABLE + " ADD COLUMN " + SECURITY_ANSWER + " TEXT");
        }
        seedAdminAccount(sqLiteDatabase);
    }

    // Don't crash if the on-device database was created by a newer build.
    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase sqLiteDatabase) {
        super.onOpen(sqLiteDatabase);
        if (!sqLiteDatabase.isReadOnly()) {
            seedAdminAccount(sqLiteDatabase);
            seedDefaultJobs(sqLiteDatabase);
        }
    }

    // Default current job and job offer, added only while no jobs exist so any
    // jobs the user enters afterwards come after these two.
    private void seedDefaultJobs(SQLiteDatabase sqLiteDatabase) {
        long existing = DatabaseUtils.queryNumEntries(sqLiteDatabase, JOBDETAILS_TABLE)
                + DatabaseUtils.queryNumEntries(sqLiteDatabase, JOB_TABLE);
        if (existing > 0) {
            return;
        }
        addSeedJob(sqLiteDatabase, true, "Software Engineer", "Acme Corp", "Atlanta", "GA",
                95000, 5000, 15, 50, 5000, 500);
        addSeedJob(sqLiteDatabase, false, "Senior Software Engineer", "Globex Inc", "Austin", "TX",
                120000, 10000, 20, 200, 10000, 1000);
    }

    private void addSeedJob(SQLiteDatabase db, boolean isCurrent, String title, String company,
                            String city, String state, double salary, double bonus, int leave,
                            int shares, double homeFund, double wellnessFund) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, title);
        cv.put(COMPANY, company);
        cv.put(CITY, city);
        cv.put(STATE, state);
        cv.put(SALARY, salary);
        cv.put(BONUS, bonus);
        cv.put(LEAVETIME, leave);
        cv.put(SHARES, shares);
        cv.put(HOMEBUYINGFUND, homeFund);
        cv.put(WELLNESSFUND, wellnessFund);
        long detailsId = db.insert(JOBDETAILS_TABLE, null, cv);

        ContentValues job = new ContentValues();
        job.put(JOBID, detailsId);
        job.put(ISCURRENTJOB, isCurrent);
        db.insert(JOB_TABLE, null, job);
    }

    // Default account (admin / admin), created if it doesn't already exist.
    private void seedAdminAccount(SQLiteDatabase sqLiteDatabase) {
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, "admin");
        cv.put(PASSWORD, "admin");
        sqLiteDatabase.insertWithOnConflict(USER_TABLE, null, cv, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public boolean addJobDetail(JobDetails jobDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, jobDetails.getTitle());
        cv.put(COMPANY, jobDetails.getCompany());
        cv.put(CITY, jobDetails.getCity());
        cv.put(STATE, jobDetails.getState());
        cv.put(SALARY, jobDetails.getSalary());
        cv.put(BONUS, jobDetails.getBonus());
        cv.put(LEAVETIME, jobDetails.getLeaveTime());
        cv.put(SHARES, jobDetails.getNoOfShares());
        cv.put(HOMEBUYINGFUND, jobDetails.getHomeBuyingFund());
        cv.put(WELLNESSFUND, jobDetails.getWellnessFund());
        long tableInserted = db.insert(JOBDETAILS_TABLE, null, cv);
        if (tableInserted == -1) {
            return false;
        }
        // keep the row id so the job entry that references it points at this job
        jobDetails.setUniqueId((int) tableInserted);
        return true;
    }

    /* BEGIN CODE FROM (https://www.sqlitetutorial.net/sqlite-update/) */
    public boolean updateJobDetails(JobDetails jobDetails) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TITLE, jobDetails.getTitle());
        cv.put(COMPANY, jobDetails.getCompany());
        cv.put(CITY, jobDetails.getCity());
        cv.put(STATE, jobDetails.getState());
        cv.put(SALARY, jobDetails.getSalary());
        cv.put(BONUS, jobDetails.getBonus());
        cv.put(LEAVETIME, jobDetails.getLeaveTime());
        cv.put(SHARES, jobDetails.getNoOfShares());
        cv.put(HOMEBUYINGFUND, jobDetails.getHomeBuyingFund());
        cv.put(WELLNESSFUND, jobDetails.getWellnessFund());
        long tableUpdated = db.update(JOBDETAILS_TABLE, cv, "ID = '" + jobDetails.getUniqueId() + "'",null);
        if (tableUpdated == -1) {
            return false;
        }
        return true;
    }
    /* END CODE FROM (https://www.sqlitetutorial.net/sqlite-update/) */

    public boolean addJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(JOBID, job.getJobDetails().getUniqueId());
        cv.put(ISCURRENTJOB, job.isCurrentJob());
        long tableInserted = db.insert(JOB_TABLE, null, cv);
        if (tableInserted == -1) {
            return false;
        }
        return true;
    }

    public List<Job> getJobs() {
        List<Job> jobsList = new ArrayList<>();
        String queryStr = "Select * From " + JOB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr, null);
        if (cursor.moveToFirst()) {
            // loop through the cursor resultset and create job details object
            do {
                int uniqueIndex = cursor.getInt(0);
                int jobIndex = cursor.getInt(1);
                boolean isCurrentJob = cursor.getInt(2) > 0;


                Job job = new Job(jobIndex, isCurrentJob);
                jobsList.add(job);

            } while (cursor.moveToNext());
        } else {
            // nothing to return
        }

        // close cusor and db
        cursor.close();
        db.close();
        return jobsList;
    }

    public List<JobDetails> getJobDetails() {
        List<JobDetails> jobDetailsList = new ArrayList<>();
        String queryStr = "Select * From " + JOBDETAILS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr, null);
        if (cursor.moveToFirst()) {
            // loop through the cursor resultset and create job details object
            do {
                int uniqueIndex = cursor.getInt(0);
                String title = cursor.getString(1);
                String company = cursor.getString(2);
                String city = cursor.getString(3);
                String state = cursor.getString(4);
                double salary = cursor.getDouble(5);
                double bonus = cursor.getDouble(6);
                int leave = cursor.getInt(7);
                int shares = cursor.getInt(8);
                double homefund = cursor.getDouble(9);
                double wellnessfund = cursor.getDouble(10);

                JobDetails jobDetails = new JobDetails(uniqueIndex, title, company, city,state, salary, bonus, leave, shares, homefund, wellnessfund);
                jobDetailsList.add(jobDetails);

            } while (cursor.moveToNext());
        } else {
            // nothing to return
        }

        // close cusor and db
        cursor.close();
        db.close();
        return jobDetailsList;
    }

    // below method if we need to save rank to database
    public void addJobsToCompareToDB(List<JobToCompare> jobsToCompare) {

        SQLiteDatabase database = this.getWritableDatabase();
        database.beginTransaction();

        for (JobToCompare jobToCompare : jobsToCompare) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(TITLE, jobToCompare.getTitle());
            contentValues.put(COMPANY, jobToCompare.getCompany());
            contentValues.put(JOBID, jobToCompare.getJobId());
            contentValues.put(RANK, jobToCompare.getRank());
            database.insert(JOBTOCOMPARE_TABLE, null, contentValues);
        }

        database.setTransactionSuccessful();
        database.endTransaction();

    }

    //add new comparison factors to sqlite database
    /* BEGIN CODE FROM https://www.geeksforgeeks.org/how-to-create-and-add-data-to-sqlite-database-in-android/ */
    public void addFactor(String salaryFactor, String bonusFactor, String vacationFactor,
                          String sharesFactor, String homefundFactor, String wellnessbondFactor){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

//        //set default value to be 1
//        values.put(SALARY_FACTOR_COL, "1");
//        values.put(BONUS_FACTOR_COL, "1");
//        values.put(VACATION_FACTOR_COL,"1");
//        values.put(SHARES_FACTOR_COL, "1");
//        values.put(HOMEFUND_FACTOR_COL, "1");
//        values.put(WELLNESSFUND_FACTOR_COL, "1");

        // on below line that all values along with its key and value pair.
        values.put(SALARY_FACTOR_COL, salaryFactor);
        values.put(BONUS_FACTOR_COL, bonusFactor);
        values.put(VACATION_FACTOR_COL,vacationFactor);
        values.put(SHARES_FACTOR_COL, sharesFactor);
        values.put(HOMEFUND_FACTOR_COL, homefundFactor);
        values.put(WELLNESSFUND_FACTOR_COL, wellnessbondFactor);
        // after adding all values passing content values to the table.
        db.insert(ADJUSTCOMPARISON_TABLE, null, values);
        db.close();
    }
    /* END CODE FROM //citation: https://www.geeksforgeeks.org/how-to-create-and-add-data-to-sqlite-database-in-android/ */


    public int[] getJobSetting() {
        int[] jobSettingsArray= new int[6];
        String queryStr = "Select * From " + ADJUSTCOMPARISON_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr, null);
        if (cursor.moveToLast()) {
            // loop through the cursor resultset and create job details object
//            int salaryFactor = cursor.getInt(0);
//            int bonusFactor = cursor.getInt(1);
//            int vacationFactor = cursor.getInt(2);
//            int sharesFactor = cursor.getInt(3);
//            int homefundFactor = cursor.getInt(4);
//            int wellnessfundFactor = cursor.getInt(5);
//            JobSettings js = new JobSettings(salaryFactor, bonusFactor, vacationFactor,
//                    sharesFactor, homefundFactor, wellnessfundFactor);
            jobSettingsArray[0] = cursor.getInt(1);
            jobSettingsArray[1] = cursor.getInt(2);
            jobSettingsArray[2] = cursor.getInt(3);
            jobSettingsArray[3] = cursor.getInt(4);
            jobSettingsArray[4] = cursor.getInt(5);
            jobSettingsArray[5] = cursor.getInt(6);
        }
        // close cusor and db
        cursor.close();
        db.close();
        return jobSettingsArray;
    }

    public List<JobSettings> getComparisonSettings() {
        List<JobSettings> jobSettingsList = new ArrayList<>();
        String queryStr = "Select * From " + ADJUSTCOMPARISON_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr, null);
        if (cursor.moveToFirst()) {
            // loop through the cursor resultset and create job details object
            do {
                int salaryWeight = cursor.getInt(1);
                int bonusWeight = cursor.getInt(2);
                int vacationWeight = cursor.getInt(3);
                int sharesWeight = cursor.getInt(4);
                int homeFundWeight = cursor.getInt(5);
                int wellnessFundWeight = cursor.getInt(6);


                JobSettings jobSettings = new JobSettings( salaryWeight, bonusWeight, vacationWeight,sharesWeight, homeFundWeight, wellnessFundWeight);
                jobSettingsList.add(jobSettings);

            } while (cursor.moveToNext());
        } else {
            // nothing to return
        }

        // close cusor and db
        cursor.close();
        db.close();
        return jobSettingsList;
    }

    public Boolean userSignup(String username, String password, String question, String answer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USERNAME, username);
        cv.put(PASSWORD, password);
        cv.put(SECURITY_QUESTION, question);
        cv.put(SECURITY_ANSWER, hashAnswer(answer));
        return db.insert(USER_TABLE, null, cv) != -1;
    }

    /** Returns the user's security question, or null if the user has none set. */
    public String getSecurityQuestion(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select " + SECURITY_QUESTION + " from " + USER_TABLE
                + " where " + USERNAME + " = ?", new String[]{username});
        String question = null;
        if (cursor.moveToFirst() && !cursor.isNull(0)) {
            question = cursor.getString(0);
        }
        cursor.close();
        return question;
    }

    public Boolean securityAnswerMatches(String username, String answer){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select 1 from " + USER_TABLE + " where " + USERNAME + " = ? and "
                + SECURITY_ANSWER + " = ?", new String[]{username, hashAnswer(answer)});
        boolean match = cursor.getCount() > 0;
        cursor.close();
        return match;
    }

    public Boolean updatePassword(String username, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PASSWORD, newPassword);
        return db.update(USER_TABLE, cv, USERNAME + " = ?", new String[]{username}) > 0;
    }

    // Answers are case/whitespace-insensitive and stored hashed.
    private static String hashAnswer(String answer){
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(answer.trim().toLowerCase(java.util.Locale.ROOT)
                    .getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public Boolean usernameExist(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * From " + USER_TABLE + " where " + USERNAME + " = ?", new String[]{username});
        if (cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    public Boolean usernamePasswordExist(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + USER_TABLE + " where " + USERNAME + " = ? and " + PASSWORD + " = ?", new String[]{username, password});
        if (cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }
}
