package com.rivaresa.cusmateogl.contact;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.BaseActivity;
import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.contact.action.ContactsAction;
import com.rivaresa.cusmateogl.contact.adapter.BranchAdapter;
import com.rivaresa.cusmateogl.databinding.ActivityContactBinding;

public class ContactActivity extends BaseActivity {

    ActivityContactBinding binding;
    ContactViewmodel viewmodel;
    BranchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this, R.layout.activity_contact);
        viewmodel=new ViewModelProvider(this).get(ContactViewmodel.class);
        binding.setViewmodel(viewmodel);

        setupRecyclerView(binding.rvBranches);

        viewmodel.setBinding(binding);

        viewmodel.initLoading(this);
        viewmodel.getBranch();
        viewmodel.getContact();
        viewmodel.getmAction().observe(this, new Observer<ContactsAction>() {
                    @Override
                    public void onChanged(ContactsAction contactsAction) {

                        viewmodel.cancelLoading();
                        switch (contactsAction.getAction())
                        {
                            case ContactsAction.BRANCH_SUCCESS:
                                viewmodel.setSpinnerData(contactsAction.getBranchResponse().getData());
                                break;

                                case ContactsAction.CONTACT_SUCCESS:
                                viewmodel.setContacts(contactsAction.getContactResponse().getData());
                                break;

                                case ContactsAction.BRANCH_LIST_SUCCESS:
                                    viewmodel.showBranch.set(View.VISIBLE);
                                    setupRecyclerView(binding.rvBranches);
                                    adapter.setContacts(contactsAction.getContactResponse().getData().getContact());
                                    adapter.notifyDataSetChanged();
                                    break;
                        }
                    }
                }

        );

    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        adapter = new BranchAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}