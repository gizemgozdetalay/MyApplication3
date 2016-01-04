package com.example.gizem.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gizem on 03.01.2016.
 */
public class ContactDB extends SQLiteOpenHelper {

    private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "
            + ContactDBSchema.ContactTable.NAME + "(" + ContactDBSchema.ContactTable.Cols.NAME + " TEXT,"
            + ContactDBSchema.ContactTable.Cols.NUMBER + " TEXT);";

    public ContactDB(Context context)
    {
        super(context,ContactDBSchema.ContactTable.NAME,null,1);
        Log.e("database", "kaydedildi");
    }

    public void addContact(Contact c)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ContactDBSchema.ContactTable.Cols.NAME,c.getName());
        values.put(ContactDBSchema.ContactTable.Cols.NUMBER, c.getNumber());
        Log.e("numara:",c.getNumber());
        // values.put(OgrenciDBSchema.OgrenciTable.Cols.DTARIHI, o.getDtarihi());


// insert row in table
        db.insert(ContactDBSchema.ContactTable.NAME, null, values);
        //    Boolean b = Boolean.parseBoolean(db.insert(ContactDBSchema.ContactTable.NAME,null,values));
        Log.i("xcdvf", "kaydedildi.");
    }
    public void deleteRows()
    {
        String selectQuery = "DELETE FROM "+ ContactDBSchema.ContactTable.NAME;
        Log.d("TAG",selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(selectQuery);
    }


    public List<Contact> getAllContactList() {
        List<Contact> contactsArrayList = new ArrayList<Contact>();

        String selectQuery = "SELECT  * FROM " + ContactDBSchema.ContactTable.NAME;
        Log.d("TAG", selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Log.e("cursor : ",String.valueOf(c));
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                Contact contacts = new Contact();
                contacts.name = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.NAME));

                contacts.number = c.getString(c.getColumnIndex(ContactDBSchema.ContactTable.Cols.NUMBER));


                // adding to Students list
                contactsArrayList.add(contacts);
            } while (c.moveToNext());
        }

        return contactsArrayList;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    }
