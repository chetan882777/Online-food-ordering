package com.example.github.ui.restaurant.table;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.github.R;
import com.example.github.model.Tables;

import java.util.List;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    private Context mContext;
    private List<Tables> tables;
    private TableItemClickListener listener;

    public interface TableItemClickListener{
        void onTableItemCLickListener(View v);
    }

    public TableAdapter(Context mContext, List<Tables> tables, TableItemClickListener listener) {
        this.mContext = mContext;
        this.tables = tables;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TableAdapter.TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.menu_list_item, parent, false);
        return new TableAdapter.TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableAdapter.TableViewHolder holder, int position) {
        Tables tables = this.tables.get(position);

        holder.textViewTableName.setText(tables.getTableName());
        holder.textViewTableSize.setText(tables.getTableSize());
        holder.textViewTableCount.setText(tables.getTableCount());
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    public class TableViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView textViewTableName;
        final TextView textViewTableSize;
        final TextView textViewTableCount;

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTableName = itemView.findViewById(R.id.textView_menu_name);
            textViewTableSize = itemView.findViewById(R.id.textView_menu_price);
            textViewTableCount = itemView.findViewById(R.id.textView_menu_type);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onTableItemCLickListener(v);
        }
    }

    public void setTables(List<Tables> tables) {
        this.tables = tables;
    }
}
