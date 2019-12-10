package com.owais.roomdbexercise.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactsAppDatabase extends RoomDatabase {
    public abstract ContactDAO getContactDAO();
}
