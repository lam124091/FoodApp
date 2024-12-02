package com.example.finaltermproject.Adapter;

//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RadioButton;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.finaltermproject.R;
//import com.example.finaltermproject.model.Size;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class RecyclerViewSizeCustomerAdapter{
//
//}
//

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Size;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewSizeCustomerAdapter extends RecyclerView.Adapter<RecyclerViewSizeCustomerAdapter.SizeViewHolder> {

    private List<Size> sizeList;
    private OnCheckedChangeListener listener;
    private int selectedItem = -1; // Default: no item selected

    public RecyclerViewSizeCustomerAdapter(List<Size> sizeList, OnCheckedChangeListener listener) {
        this.sizeList = sizeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_size_item_layout, parent, false);
        return new SizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeViewHolder holder, int position) {
        Size size = sizeList.get(position);
        holder.txtSizeName.setText(size.getSizename());

        // Check if the current position is the selected item
        if (position == selectedItem) {
            // Apply the highlight effect (change background color, text color, etc.)
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
            holder.txtSizeName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
        } else {
            // Reset to default (remove highlight effect)
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent));
            holder.txtSizeName.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.black));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new list with the selected size
                List<Size> selectedSizes = new ArrayList<>();
                selectedSizes.add(sizeList.get(holder.getAdapterPosition()));

                // Notify listener about item selection with the list of selected sizes
                if (listener != null) {
                    listener.onCheckedChanged(selectedSizes);
                }

                // Update selected item position
                selectedItem = holder.getAdapterPosition();
                // Notify adapter about the item change
                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return sizeList.size();
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(List<Size> selectedSizes);
    }

    public static class SizeViewHolder extends RecyclerView.ViewHolder {
        TextView txtSizeName;

        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSizeName = itemView.findViewById(R.id.txtSizeName);
        }
    }
}

