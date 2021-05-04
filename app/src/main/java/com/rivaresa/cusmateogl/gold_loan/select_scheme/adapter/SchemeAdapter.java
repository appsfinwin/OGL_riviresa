package com.rivaresa.cusmateogl.gold_loan.select_scheme.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.databinding.RowLayoutSchemeBinding;
import com.rivaresa.cusmateogl.gold_loan.select_scheme.action.RowSchemeAction;
import com.rivaresa.cusmateogl.gold_loan.select_scheme.pojo.SchemeData;

import java.util.Collections;
import java.util.List;

public class SchemeAdapter extends RecyclerView.Adapter<SchemeAdapter.ViewHolder> {
    List<SchemeData> schemeData;
    MutableLiveData<RowSchemeAction> mAction;

    public SchemeAdapter() {
        this.schemeData = Collections.emptyList();
        mAction=new MutableLiveData<>();
    }

    public void setSchemeData(List<SchemeData> schemeData) {
        this.schemeData = schemeData;
        notifyDataSetChanged();
    }

    public MutableLiveData<RowSchemeAction> getmAction() {
        return mAction;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RowLayoutSchemeBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_layout_scheme,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setDataBinding(schemeData.get(position),mAction);
    }

    @Override
    public int getItemCount() {
        return schemeData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowLayoutSchemeBinding binding;
        public ViewHolder(@NonNull RowLayoutSchemeBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void setDataBinding(SchemeData data, MutableLiveData<RowSchemeAction> mAction)
        {
            if (binding.getViewmodel()==null)
            {
                binding.setViewmodel(new RowSchemesViewmodel(data,mAction));
            }else {
                binding.getViewmodel().setSchemeData(data,mAction);
            }
        }
    }
}
