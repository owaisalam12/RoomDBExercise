package com.owais.roomdbexercise.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.owais.roomdbexercise.R;
import com.owais.roomdbexercise.db.Contact;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
   private Context context;
    private ArrayList<Contact> list;

    public ContactsAdapter(Context context, ArrayList<Contact> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        Log.v("callback3","MyViewHolder");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.MyViewHolder holder, int position) {
        final Contact contact = list.get(position);
        holder.name.setText(contact.getName());
        holder.email.setText(contact.getEmail());
        holder.country.setText(contact.getCountry());
        holder.date.setText(contact.getDate());
        Log.v("callback3","onBindViewHolder");

        Log.v("callback3",contact.getName());
        Log.v("callback3",contact.getEmail());
        Log.v("callback3",contact.getCountry());
        Log.v("callback3",contact.getDate());


    }
    public void setStudents(ArrayList<Contact> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView email;
        public TextView country;
        public TextView date;
        public MyViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.name);
            email = view.findViewById(R.id.email);
            country = view.findViewById(R.id.country);
            date = view.findViewById(R.id.date);

        }
    }
}
