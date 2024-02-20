package com.example.recycle_view;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MyViewHolder> implements Filterable {

    ArrayList<DataModel> dataset;
    private OnItemClickListener mListener; // Listener for item clicks
    private ArrayList<DataModel> datasetFull; // Copy of original dataset for filtering
    public CustomeAdapter(ArrayList<DataModel> dataSet) {
        this.dataset = dataSet;
        this.datasetFull = new ArrayList<>(dataSet); // Initialize datasetFull with a copy of dataSet
    }

    // Interface for item click events
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Method to set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public Filter getFilter() {
        return dataSetFilter;
    }

    private Filter dataSetFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<DataModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(datasetFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (DataModel item : datasetFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataset.clear();
            dataset.addAll((ArrayList<DataModel>) results.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewVersion;
        ImageView imageView;

        public MyViewHolder (View itemView){
            super(itemView);

            textViewName = itemView.findViewById(R.id.textView);
            textViewVersion = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);

            // Set OnClickListener for the entire item view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
        public void showPopup(String message) {
            // Inflate the popup_layout.xml layout
            View popupView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.popup_layout, null);

            // Set the TextView with the message
            TextView textViewPopup = popupView.findViewById(R.id.textViewPopup);
            textViewPopup.setText(message);

            // Create a PopupWindow
            PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setFocusable(true); // Allows popup to gain focus

            // Show the popup at the center of the anchor view
            popupWindow.showAtLocation(itemView, Gravity.CENTER, 0, 0);

            // Dismiss the popup after a certain duration
            new Handler().postDelayed(popupWindow::dismiss, 3000); // Dismiss after 3 seconds (adjust as needed)
        }
    }

    @NonNull
    @Override
    public CustomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomeAdapter.MyViewHolder holder, int position) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageView;

        textViewName.setText(dataset.get(position).getName());
        textViewVersion.setText((dataset.get(position).getVersion()));
        imageView.setImageResource(dataset.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
