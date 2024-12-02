package com.example.finaltermproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Invoice;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ListviewInvoiceAdapter extends RecyclerView.Adapter<ListviewInvoiceAdapter.ViewHolder> {

    private List<Invoice> invoices;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public ListviewInvoiceAdapter(Context context, List<Invoice> invoices) {
        this.context = context;
        this.invoices = invoices;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_invoice_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Invoice invoice = invoices.get(position);

        holder.txtInvoiceId.setText("Invoice code: " + String.format("Invoice #%d", invoice.getInvoiceid()));
        holder.txtInvoiceDate.setText("Ngày thanh toán: " + formatDate(invoice.getDate()));
        holder.txtInvoiceTotal.setText("Chi phí: " + formatCurrency(invoice.getTotalmoney()));
        holder.txtInvoiceStatus.setText("Trạng thái đơn hàng: " + invoice.getStatus().getStatusname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(invoice);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    private String formatCurrency(double amount) {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(amount);
    }

    public interface OnItemClickListener {
        void onItemClick(Invoice invoice);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtInvoiceId;
        TextView txtInvoiceDate;
        TextView txtInvoiceTotal;
        TextView txtInvoiceStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInvoiceId = itemView.findViewById(R.id.txtInvoiceId);
            txtInvoiceDate = itemView.findViewById(R.id.txtInvoiceDate);
            txtInvoiceTotal = itemView.findViewById(R.id.txtInvoiceTotal);
            txtInvoiceStatus = itemView.findViewById(R.id.txtInvoiceStatus);
        }
    }
}