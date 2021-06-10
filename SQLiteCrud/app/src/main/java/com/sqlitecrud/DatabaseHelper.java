package com.sqlitecrud;

import android.accessibilityservice.GestureDescription;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="candidate_db";
    private static final String TABLE_NAME="candidates";
    private static final String ID="id";
    private static final String name="name";
    private static final String email="email";
    private static final String phone="phone";
    private static final String created_at="created_at";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String table_query="CREATE TABLE if not EXISTS "+TABLE_NAME+
                "("+
                ID+" INTEGER PRIMARY KEY,"+
                name+"TEXT ,"+
                email+"TEXT ,"+
                phone+"TEXT ,"+
                created_at+"TEXT"+
                ")";
        db.execSQL(table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public void AddCandidate(CandidateModel candidateModel){
        SQLiteDatabase db=this.getWritableDatabase(); //return the DB in which we do operation

        //Storing the CandidateModel Data with key and value into ContentValues Object
        ContentValues contentValues = new ContentValues();
        contentValues.put(name,candidateModel.getName());
        contentValues.put(email,candidateModel.getEmail());
        contentValues.put(phone,candidateModel.getPhone());
        contentValues.put(created_at,candidateModel.getCreated_at());
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }
    //method to return CandidateModel Object when we pass ID it will fetch Data from DB and return it
    public CandidateModel getCandidate(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{ID, name, email, phone, created_at}, ID+" + ?", new String[]{String.valueOf(id)}, null,null,null);
        //checking if cursor is not null means we receive Data from Table
        if (cursor!=null){
            cursor.moveToFirst();
        }
        CandidateModel candidateModel = new CandidateModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) );
        db.close();
        return candidateModel;
    }

    public List<CandidateModel> getAllCandidates(){
        List<CandidateModel> candidateModelList = new ArrayList<>();
        String query = "SELECT * from "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                CandidateModel candidateModel = new CandidateModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4) );
                candidateModelList.add(candidateModel);
            }
            while (cursor.moveToNext());
        }
        db.close();
        return candidateModelList;
    }

    public int updateCandidate(CandidateModel candidateModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(name,candidateModel.getName());
        contentValues.put(email,candidateModel.getEmail());
        contentValues.put(phone,candidateModel.getPhone());
        contentValues.put(created_at,candidateModel.getCreated_at());
        db.close();
        return db.update(TABLE_NAME,contentValues, ID+"=?", new String[]{String.valueOf(candidateModel.getId())});

    }

    public void deleteCandidate(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID+"=?", new String[]{id});
        db.close();
    }

    public int getTotalCount(){
        String query = "SELECT * from "+TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        cursor.close();
        sqLiteDatabase.close();
        return cursor.getCount();
    }
}
