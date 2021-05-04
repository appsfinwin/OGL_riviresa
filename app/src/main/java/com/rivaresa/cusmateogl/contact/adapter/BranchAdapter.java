package com.rivaresa.cusmateogl.contact.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.contact.pojo.contact.Contact;
import com.rivaresa.cusmateogl.databinding.RowLayoutBranchBinding;

import java.util.Collections;
import java.util.List;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.ViewHolder> {

    List<Contact> contacts;

    public BranchAdapter() {
        this.contacts = Collections.emptyList();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowLayoutBranchBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_layout_branch,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setBindingContacts(contacts.get(position));

    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowLayoutBranchBinding binding;
        public ViewHolder(@NonNull RowLayoutBranchBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }



        public void setBindingContacts(Contact contact) {
            if (binding.getViewmodel()==null)
            {
                binding.setViewmodel(new RowBranchViewmodel(contact));
            }else {
                binding.getViewmodel().setContact(contact);
            }
        }
    }
}
