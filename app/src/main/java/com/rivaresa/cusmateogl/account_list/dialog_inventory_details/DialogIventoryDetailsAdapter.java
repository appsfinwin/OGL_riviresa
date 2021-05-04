package com.rivaresa.cusmateogl.account_list.dialog_inventory_details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.rivaresa.cusmateogl.R;
import com.rivaresa.cusmateogl.account_list.pojo.inventory_response.Ornament;
import com.rivaresa.cusmateogl.databinding.RowLayoutInventoryBinding;


import java.util.Collections;
import java.util.List;

public class DialogIventoryDetailsAdapter extends RecyclerView.Adapter<DialogIventoryDetailsAdapter.ViewHolder> {

    List<Ornament> ornamentList;

    public DialogIventoryDetailsAdapter() {
        this.ornamentList = Collections.emptyList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         RowLayoutInventoryBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_layout_inventory,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setBindingData(ornamentList.get(position));

    }

    public void setOrnamentList(List<Ornament> ornamentList) {
        this.ornamentList = ornamentList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return ornamentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RowLayoutInventoryBinding binding;
        public ViewHolder(@NonNull RowLayoutInventoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }

        public void setBindingData(Ornament ornament) {
           if (binding.getViewmodel()==null)
           {
               binding.setViewmodel(new DialogRowViewmodel(ornament));
           }else
           {
               binding.getViewmodel().setOrnamentDetails(ornament);
           }
        }
    }
}
