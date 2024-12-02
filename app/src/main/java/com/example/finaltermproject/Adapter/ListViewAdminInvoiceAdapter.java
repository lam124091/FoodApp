package com.example.finaltermproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Invoice;

import java.util.List;

public class ListViewAdminInvoiceAdapter extends ArrayAdapter<Invoice> {

    private Context context;
    private List<Invoice> invoices;
    private OnItemClickListener listener;

    public ListViewAdminInvoiceAdapter(Context context, List<Invoice> invoices) {
        super(context, R.layout.admin_invoice_item_layout, invoices);
        this.context = context;
        this.invoices = invoices;
    }

    public interface OnItemClickListener {
        void onItemClick(Invoice invoice, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.admin_invoice_item_layout, parent, false);

        TextView tvInvoiceId = rowView.findViewById(R.id.tvInvoiceId);
        TextView tvCustomerName = rowView.findViewById(R.id.tvCustomerName);
        TextView tvDate = rowView.findViewById(R.id.txtDate);
        TextView tvPaymentMethod = rowView.findViewById(R.id.txtPayment);
        TextView tvTotalMoney = rowView.findViewById(R.id.txtTotal);
        TextView tvStatus = rowView.findViewById(R.id.txtStatus);

        Invoice invoice = invoices.get(position);

        tvInvoiceId.setText(String.valueOf(invoice.getInvoiceid()));
        tvCustomerName.setText(invoice.getCustomer().getcustomername());
        tvDate.setText(invoice.getDate().toString());
        tvPaymentMethod.setText(invoice.getPaymentmethod());
        tvTotalMoney.setText(String.valueOf(invoice.getTotalmoney()));
        tvStatus.setText(invoice.getStatus().getStatusname());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(invoice, position);
                }
            }
        });

        return rowView;
    }
}
