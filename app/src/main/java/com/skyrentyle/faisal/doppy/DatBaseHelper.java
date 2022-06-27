package com.skyrentyle.faisal.doppy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.skyrentyle.faisal.doppy.Recipient.RecipientDetails;

import java.io.ByteArrayOutputStream;

//import static android.os.Build.ID;

public class DatBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME= "Account_Records.db";
   public static final String TABLE_NAME= "All_accounts";
    private static final String ID= "Id";
    private static final String FIRST= "Fname";
    private static final String LAST= "Lname";
    private static final String EMAIL = "Email";
    private static final String PASSWORD= "Password";
    private static final String CONTACT= "Contact";
    private  static final String PHOTO_URL= "photo url";
    private  static final String PHOTO= "photo";

    public class DbBitmapUtility {
        public byte[] getBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }
        public  Bitmap getImage(byte[] image) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
    }


            private static final int VERSION_NUMBER=2;
           private static final String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+FIRST+" VARCHAR(255) NOT NULL,"+LAST+" VARCHAR(255)  NOT NULL,"+EMAIL+" TEXT NOT NULL,"+PASSWORD+" TEXT NOT NULL,"+CONTACT+" TEXT NOT NULL,"+PHOTO+" BLOB )";

private Context context;
private static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;


    public DatBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      /*  String CREATE_TABLE="CREATE TABLE "+ TABLE_NAME +"("
                + KEY_ID +" INTEGER PRIMARY KEY,"
                + FIRSTNAME +" TEXT,"
                + LASTNAME +" TEXT,"
                + PASSWORD +" TEXT,"
                + EMAIL +" TEXT" +")";
                */
        try {

            db.execSQL(CREATE_TABLE);
            onCreate(db);
            Toast.makeText(context,"onCreate is called",Toast.LENGTH_LONG).show();
        } catch (Exception e) {

            Toast.makeText(context,"Exception: " +e,Toast.LENGTH_LONG).show();

        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {

            db.execSQL(DROP_TABLE);
            onCreate(db);
            Toast.makeText(context,"onCreate is called",Toast.LENGTH_LONG).show();
        } catch (Exception e) {

            Toast.makeText(context,"Exception: " +e,Toast.LENGTH_LONG).show();

        }

    }

    public long insertData(RecipientDetails donordetails)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValue= new ContentValues();
        contentValue.put(FIRST,donordetails.getFirstname());
        contentValue.put(LAST,donordetails.getLastname());
        contentValue.put(EMAIL,donordetails.getEmail());
        contentValue.put(PASSWORD,donordetails.getPassword());
        contentValue.put(CONTACT,donordetails.getContact());
        //contentValue.put(PHOTO,image);

        long rowIdw=db.insert(TABLE_NAME,null,contentValue);
        return rowIdw;

    }
public int delete(String email)
{
   SQLiteDatabase db=this.getWritableDatabase();
   return db.delete(TABLE_NAME,EMAIL+"  =   ?",new String[]{email});

}




public Cursor showData()
{
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor= db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
    return cursor;
}
    public long update(RecipientDetails donordetails, String email)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValue= new ContentValues();
        contentValue.put(FIRST,donordetails.getFirstname());
        contentValue.put(LAST,donordetails.getLastname());
        contentValue.put(EMAIL,donordetails.getEmail());
        contentValue.put(PASSWORD,donordetails.getPassword());
        contentValue.put(CONTACT,donordetails.getContact());
        //contentValue.put(PHOTO,image);
long idw=db.update(TABLE_NAME,contentValue,EMAIL+"  =   ?",new String[]{email});;
        return idw;

    }
    public boolean findpassword(String Email,String Password) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        boolean result = false;

        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No data is found", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                String email = cursor.getString(3);
                String password = cursor.getString(4);
               // byte[] image = cursor.getBlob(5);
                if (email.equals(Email) && password.equals(Password)) {
                    result = true;
                    break;


                }
            }


        }
        return result;

    }}