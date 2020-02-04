package com.example.github.ui.user.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github.R;
import com.example.github.model.Restaurant;

import java.util.List;

public class UserHomeAdapter extends RecyclerView.Adapter<UserHomeAdapter.UserHomeViewHolder> {

    private Context mContext;
    private List<Restaurant> restaurants;
    private TableItemClickListener listener;

    public interface TableItemClickListener{
        void onTableItemCLickListener(View v);
    }

    public UserHomeAdapter(Context mContext, List<Restaurant> restaurants, TableItemClickListener listener) {
        this.mContext = mContext;
        this.restaurants = restaurants;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.user_home_list_item, parent, false);
        return new UserHomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHomeViewHolder holder, int position) {
        Restaurant restaurant = this.restaurants.get(position);

        holder.textViewRestaurantName.setText(restaurant.getName());
        holder.textViewTableRestaurantType.setText("Veg Only");
        holder.textViewTableRestaurantAddress.setText(restaurant.getAddress());
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class UserHomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView textViewRestaurantName;
        final TextView textViewTableRestaurantType;
        final TextView textViewTableRestaurantAddress;

        public UserHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRestaurantName = itemView.findViewById(R.id.textView_userHomeList_name);
            textViewTableRestaurantType = itemView.findViewById(R.id.textView_userHomeList_type);
            textViewTableRestaurantAddress = itemView.findViewById(R.id.textView_userHomeList_address);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onTableItemCLickListener(v);
        }
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
