package com.example.pakmedicine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper  extends SQLiteOpenHelper {
    Context context;
    private SQLiteDatabase database;
    public static final String DATABASE_NAME = "pakmedicine.db";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
        database = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void checkdatabase(){
        if(CheckSize()<28)
            copydatabase();
    }
    private long CheckSize(){
        String path=context.getDatabasePath(DATABASE_NAME).getPath();
        File file = new File(path);
        long fileSizeInBytes = file.length();
        Log.d("_this",(fileSizeInBytes / 1024)+"");
        return fileSizeInBytes / 1024;
    }
    private void copydatabase(){
        this.getReadableDatabase();
        this.close();
        byte[] buffer = new byte[1024];
        OutputStream myOutput;
        int length;
        InputStream myInput;
        try
        {
            myInput =context.getAssets().open(DATABASE_NAME);
            myOutput =new FileOutputStream(context.getDatabasePath(DATABASE_NAME).getPath());
            while((length = myInput.read(buffer)) >=0)
            {
                myOutput.write(buffer, 0, length);
            }
            myOutput.close();
            myOutput.flush();
            myInput.close();
            Log.d("Database_", "New database has been copied to device!");


        }
        catch(Exception e)
        {
            Log.d("Database_", e.getMessage());

        }
    }

    public Cursor SearchByFormula(String text) {

     return database.rawQuery("SELECT * FROM Formula WHERE F_Name=?",new String[]{text});

    }

    public Cursor MedicineDetails(int f_id) {
        return database.rawQuery("SELECT * FROM Medicine WHERE F_ID=?",new String[]{String.valueOf(f_id)});
    }

    public Cursor AlternateBrandByID(int f_id) {
        return database.rawQuery("SELECT B_Name FROM Brand WHERE F_ID=?",new String[]{String.valueOf(f_id)});
    }

    public Cursor ByCompany(String c_name) {
        return database.rawQuery("SELECT DISTINCT B_Name FROM Brand WHERE Company=?",new String[]{c_name});
    }

    public Cursor IncludeDrugs(String brand_name) {
       Cursor cursor= database.rawQuery("SELECT  B_ID FROM Brand WHERE B_Name=?",new String[]{brand_name});
       if(cursor!=null) {
           cursor.moveToFirst();
           return database.rawQuery("SELECT DISTINCT M_Name FROM Brand_Details WHERE B_ID=?",new String[]{cursor.getString(0)});
       }
    else
        return null;
    }

    public Cursor AlternateBrand(String brand_name) {
       
        Cursor cursor= database.rawQuery("SELECT  F_ID FROM Brand WHERE B_Name=?",new String[]{brand_name});
        if(cursor!=null){
            cursor.moveToFirst();
            return database.rawQuery("SELECT DISTINCT B_Name FROM Brand WHERE F_ID=?",new String[]{cursor.getString(0)});

        }
        else {
            return null;
        }


    }
    public List<String> ByContra(String name) {

        List<String> list=new ArrayList<>();

        Cursor cursor= database.rawQuery("SELECT  * FROM Contradiction WHERE Disease=?",new String[]{name});
        while(cursor.moveToNext()){

            Cursor c1= database.rawQuery("SELECT F_Name  FROM Formula WHERE F_ID=?",new String[]{cursor.getInt(2)+""});

            c1.moveToFirst();
            list.add(c1.getString(0));
        }

            return list;

    }
    public List<String> FetchAllMedicine() {

        List<String> list=new ArrayList<>();

        Cursor cursor= database.rawQuery("SELECT  F_ID,M_Name FROM Medicine ",null);
        while(cursor.moveToNext()){

            Formula_Searching_Screen.fiid.add(cursor.getInt(0));
            list.add(cursor.getString(1));
        }

        return list;

    }

    public Cursor AvailableForms(String brand_name) {
       // int bid = db.Brand_Tables.FirstOrDefault(d => d.B_Name.ToLower().Equals(b_name.ToLower())).B_ID;
       // return db.Brand_Details_Tables.Where(d => d.B_ID == bid).Select(d => d.Type).Distinct().ToList();

        Cursor cursor= database.rawQuery("SELECT  B_ID FROM Brand WHERE B_Name=?",new String[]{brand_name});
        if(cursor!=null){
            cursor.moveToFirst();
            return database.rawQuery("SELECT DISTINCT Type FROM Brand_Details WHERE B_ID=?",new String[]{cursor.getString(0)});

        }
        else {
            return null;
        }
    }

    public Cursor PackingInfo(String b_name, String type) {
        Cursor cursor= database.rawQuery("SELECT  B_ID FROM Brand WHERE B_Name=?",new String[]{b_name});
        if(cursor!=null) {
            cursor.moveToFirst();
            return database.rawQuery("SELECT * FROM Brand_Details WHERE B_ID=? AND Type=?",new String[]{cursor.getString(0),type});
        }
        else
            return null;
    }

    public int Update_Formula(int f_id, String overview, String effect, String risk, String warning, String contradiction) {
        ContentValues cv = new ContentValues();
        cv.put("OverView",overview);
        cv.put("Effects",effect);
        cv.put("Risk",risk);
        cv.put("Warning",warning);
        cv.put("Contraindication",contradiction);
        return  database.update("Formula", cv, "F_ID = ?", new String[]{String.valueOf(f_id)});

    }

    public int Update_Dosage(int m_id, int f_id, String type, String info) {
        ContentValues cv = new ContentValues();
        if(type.equalsIgnoreCase("adult"))
            cv.put("Adult",info);
              else if(type.equalsIgnoreCase("Neonatal"))
            cv.put("Adult",info);
                  else
            cv.put("Paedriatic",info);

        return  database.update("Medicine", cv, "M_ID=? AND F_ID = ?", new String[]{String.valueOf(m_id),String.valueOf(f_id)});
    }

    public long insertformula(String formula, String overview, String effect, String risk, String warning, String contradiction) {
        ContentValues values = new ContentValues();
        values.put("F_Name", formula);
        values.put("OverView", overview);
        values.put("Effects", effect);
        values.put("Risk", risk);
        values.put("Warning", warning);
        values.put("Contraindication", contradiction);


       return  database.insert("Formula", null, values);
    }

    public Cursor AllFormula() {

        return database.rawQuery("SELECT * FROM Formula ",null);

    }

}
