package com.owais.roomdbexercise.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.owais.roomdbexercise.R;
import com.owais.roomdbexercise.adapter.ContactsAdapter;
import com.owais.roomdbexercise.db.Contact;
import com.owais.roomdbexercise.db.ContactsAppDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private ArrayList<Contact> contactArrayList;
    private ContactsAdapter contactsAdapter;
    private ContactsAppDatabase contactsAppDatabase;
    private int NEW_DATA_CODE=11;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatingActionButton=findViewById(R.id.fab);
        contactArrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        contactsAppDatabase= Room.databaseBuilder(getApplicationContext(),ContactsAppDatabase.class,"ContDB").addCallback(callback).build();

        getAllContacts();

        contactsAdapter=new ContactsAdapter(this,contactArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactsAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent,NEW_DATA_CODE);


                // createContact("name2","email2","country2","date2");


            }
        });


    }
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            Toast.makeText(MainActivity.this, "on Move", Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            Toast.makeText(MainActivity.this, "on Swiped ", Toast.LENGTH_SHORT).show();
            //Remove swiped item from list and notify the RecyclerView
            int position = viewHolder.getAdapterPosition();
            Contact contact=contactArrayList.get(position);
            deleteContact(contact);
            contactArrayList.remove(position);
            contactsAdapter.notifyDataSetChanged();
            //  arrayList.remove(position);
          //  contactArrayList.get(position);
            //  adapter.notifyDataSetChanged();

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_DATA_CODE) {
            if(resultCode == Activity.RESULT_OK){
                String name=data.getStringExtra("name");
                String email=data.getStringExtra("email");
                String country=data.getStringExtra("country");
                createContact(name,email,country,"date");

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    private void createContact(String name, String email, String country, String date) {

        insertContact(new Contact(0, name, email,country,date));

    }
    private void getAllContacts(){
        class getAllContactsAsyncTask extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                contactArrayList.addAll(contactsAppDatabase.getContactDAO().getAllContacts());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                for(Contact contact:contactArrayList){
                    Log.v("callback9",contact.getName());
                    Log.v("callback9",contact.getEmail());

                }
                contactsAdapter.notifyDataSetChanged();
            }
        }

        new getAllContactsAsyncTask().execute();

    }

    private void insertContact(final Contact contact){
        class insertContactAsyncTask extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                long id=contactsAppDatabase.getContactDAO().addContact(contact);
                Contact contact=contactsAppDatabase.getContactDAO().getContactById(id);
                if(contact!=null){
                    contactArrayList.add(contact);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                contactsAdapter.notifyDataSetChanged();

            }
        }
        new insertContactAsyncTask().execute();
    }

    private void deleteContact(final Contact contact) {
        class deleteContactAsyncTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                contactsAppDatabase.getContactDAO().deleteContact(contact);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                contactsAdapter.notifyDataSetChanged();
            }
        }
        new deleteContactAsyncTask().execute();
    }

    private RoomDatabase.Callback callback=new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            insertContact(new Contact(0,"name1","email1","country1","date1"));
//            insertContact(new Contact(0,"name1","email1","country1","date1"));
//            insertContact(new Contact(0,"name1","email1","country1","date1"));
//            insertContact(new Contact(0,"name1","email1","country1","date1"));
            Log.v("callback","onCreate");
        }
    };


}
