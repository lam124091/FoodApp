//package com.example.finaltermproject.Adapter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.finaltermproject.R;
//import com.example.finaltermproject.model.Size;
//
//import java.util.List;
//
//public class RecyclerViewSizeAdapter extends RecyclerView.Adapter<RecyclerViewSizeAdapter.SizeViewHolder>
//{
//    private List<Size> sizes;
//    private CompoundButton.OnCheckedChangeListener oncheckedchangelistener;
//
////    public RecyclerViewSizeAdapter(List<Size> sizes) {
////        this.sizes = sizes;
////    }
//
//    public RecyclerViewSizeAdapter(List<Size> sizes, CompoundButton.OnCheckedChangeListener oncheckedchangelistener) {
//        this.sizes = sizes;
//        this.oncheckedchangelistener = oncheckedchangelistener;
//    }
//
//    @NonNull
//    @Override
//    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item_layout, parent, false);
//        return new SizeViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SizeViewHolder holder, int position) {
//        Size size = sizes.get(position);
//        holder.bind(size);
//    }
//
//    @Override
//    public int getItemCount() {
//        return sizes.size();
//    }
//
//    public class SizeViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView txtSizeName;
//        private CheckBox chkSize;
//
//        public SizeViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txtSizeName = itemView.findViewById(R.id.txtSizeName);
//            chkSize = itemView.findViewById(R.id.chkSize);
//
//            chkSize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    Size size = sizes.get(getAdapterPosition());
//                    size.setSelected(isChecked);
//                    oncheckedchangelistener.onCheckedChanged(size, isChecked);
//                }
//            });
//        }
//
//        public void bind(Size size) {
//            txtSizeName.setText(size.getSizename());
//            // Xử lý sự kiện khi checkbox được thay đổi
//            chkSize.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                size.setSelected(isChecked);
//            });
//        }
//    }
//
//    public interface OnCheckedChangeListener {
//        void onCheckedChanged(Size size, boolean isChecked);
//    }
//}
//

package com.example.finaltermproject.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finaltermproject.R;
import com.example.finaltermproject.model.Size;

import java.util.ArrayList;
import java.util.List;
//
//public class RecyclerViewSizeAdapter extends RecyclerView.Adapter<RecyclerViewSizeAdapter.SizeViewHolder> {
//    private List<Size> SIZE;
//    private OnCheckedChangeListener onCheckedChangeListener;
//
//    public RecyclerViewSizeAdapter(List<Size> SIZE, OnCheckedChangeListener listener) {
//        this.SIZE = SIZE;
//        this.onCheckedChangeListener = listener;
//    }
//
//    @NonNull
//    @Override
//    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item_layout, parent, false);
//        return new SizeViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SizeViewHolder holder, int position) {
//        Size size = SIZE.get(position);
//        holder.bind(size);
//    }
//
//    @Override
//    public int getItemCount() {
//        return SIZE.size();
//    }
//
//    public class SizeViewHolder extends RecyclerView.ViewHolder {
//        private TextView txtSizeName;
//        private CheckBox chkSize;
//
//        public SizeViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txtSizeName = itemView.findViewById(R.id.txtSizeName);
//            chkSize = itemView.findViewById(R.id.chkSize);
//
//            chkSize.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    Size size = SIZE.get(position);
//                    onCheckedChangeListener.onCheckedChanged(size, isChecked);
//                }
//            });
//        }
//
//        public void bind(Size size) {
//            txtSizeName.setText(size.getSizename());
//        }
//    }
//
//    public interface OnCheckedChangeListener {
//        void onCheckedChanged(Size size, boolean isChecked);
//    }
//
//    public List<Size> getSelectedSizes() {
//        List<Size> selectedSizes = new ArrayList<>();
//        for (Size size : SIZE) {
//            if (size.isSelected()) {
//                selectedSizes.add(size);
//            }
//        }
//        return selectedSizes;
//    }
//}


//public class RecyclerViewSizeAdapter extends RecyclerView.Adapter<RecyclerViewSizeAdapter.SizeViewHolder> {
//    private List<Size> SIZE;
//    private Map<Integer, Boolean> selectedSizes = new HashMap<>();
//    private OnCheckedChangeListener onCheckedChangeListener;
//
//    public RecyclerViewSizeAdapter(List<Size> SIZE, OnCheckedChangeListener listener) {
//        this.SIZE = SIZE;
//        this.onCheckedChangeListener = listener;
//    }
//
//    @NonNull
//    @Override
//    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.size_item_layout, parent, false);
//        return new SizeViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SizeViewHolder holder, int position) {
//        Size size = SIZE.get(position);
//        holder.bind(size, selectedSizes.containsKey(position) && selectedSizes.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return SIZE.size();
//    }
//
//    public class SizeViewHolder extends RecyclerView.ViewHolder {
//        private TextView txtSizeName;
//        private CheckBox chkSize;
//
//        public SizeViewHolder(@NonNull View itemView) {
//            super(itemView);
//            txtSizeName = itemView.findViewById(R.id.txtSizeName);
//            chkSize = itemView.findViewById(R.id.chkSize);
//
//            chkSize.setOnCheckedChangeListener((buttonView, isChecked) -> {
//                int position = getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    Size size = SIZE.get(position);
//                    onCheckedChangeListener.onCheckedChanged(size, isChecked);
//                    if (isChecked) {
//                        selectedSizes.put(position, true);
//                    } else {
//                        selectedSizes.remove(position);
//                    }
//                }
//            });
//        }
//
//        public void bind(Size size, boolean isChecked) {
//            txtSizeName.setText(size.getSizename());
//            chkSize.setChecked(isChecked);
//        }
//    }
//
//    public interface OnCheckedChangeListener {
//        void onCheckedChanged(Size size, boolean isChecked);
//    }
//
//    public List<Size> getSelectedSizes() {
//        List<Size> selectedSizes = new ArrayList<>();
//        for (int position : this.selectedSizes.keySet()) {
//            selectedSizes.add(SIZE.get(position));
//        }
//        return selectedSizes;
//    }
//}
public class RecyclerViewSizeAdapter extends RecyclerView.Adapter<RecyclerViewSizeAdapter.SizeViewHolder>
{
    private List<Size> SIZE;
    private List<Size> selectedSizes = new ArrayList<>();
    private OnCheckedChangeListener onCheckedChangeListener;

    public RecyclerViewSizeAdapter(List<Size> SIZE, OnCheckedChangeListener listener) {
        this.SIZE = SIZE;
        this.onCheckedChangeListener = listener;
    }

    @NonNull
    @Override
    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_size_item_layout, parent, false);
        return new SizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeViewHolder holder, int position) {
        Size size = SIZE.get(position);
        holder.bind(size, selectedSizes.contains(size));
    }

    @Override
    public int getItemCount() {
        return SIZE.size();
    }

    public class SizeViewHolder extends RecyclerView.ViewHolder {
        private TextView txtSizeName;
        private CheckBox chkSize;

        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSizeName = itemView.findViewById(R.id.txtSizeName);
            chkSize = itemView.findViewById(R.id.chkSize);

            chkSize.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Size size = SIZE.get(getAdapterPosition());
                if (isChecked) {
                    selectedSizes.add(size);
                } else {
                    selectedSizes.remove(size);
                }
                onCheckedChangeListener.onCheckedChanged(size, isChecked);
            });
        }

        public void bind(Size size, boolean isChecked) {
            txtSizeName.setText(size.getSizename());
            chkSize.setChecked(isChecked);
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(Size size, boolean isChecked);
    }
}

