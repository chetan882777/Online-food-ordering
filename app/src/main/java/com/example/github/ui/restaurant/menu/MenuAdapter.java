package com.example.github.ui.restaurant.menu;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github.R;
import com.example.github.model.Menu;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context mContext;
    private List<Menu> menus;
    private MenuItemClickListener listener;

    public interface MenuItemClickListener{
        void onMenuItemCLickListener();
    }

    public MenuAdapter(Context mContext, List<Menu> menus, MenuItemClickListener listener) {
        this.mContext = mContext;
        this.menus = menus;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.menu_list_item, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = menus.get(position);

        holder.textViewMenuName.setText(menu.getName());
        holder.textViewMenuPrice.setText(menu.getPrice());
        holder.textViewMenuType.setText(menu.getType());
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView textViewMenuName;
        final TextView textViewMenuPrice;
        final TextView textViewMenuType;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMenuName = itemView.findViewById(R.id.textView_menu_name);
            textViewMenuPrice = itemView.findViewById(R.id.textView_menu_price);
            textViewMenuType = itemView.findViewById(R.id.textView_menu_type);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onMenuItemCLickListener();
        }
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
