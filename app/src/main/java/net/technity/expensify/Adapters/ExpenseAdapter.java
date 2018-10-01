/*
 * Copyright (c) Ajesh, Alpesh, Arjun
 */

package net.technity.expensify.Adapters;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.technity.expensify.Expense;
import net.technity.expensify.R;
import net.technity.expensify.ViewHolders.ExpenseViewHolder;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseViewHolder> {

    List<Expense> expenseList;

    public ExpenseAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.expense_recycler_row, parent, false);

        return new ExpenseViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
        Expense e = expenseList.get(position);
        holder.title.setText(e.geteTitle());
        holder.amount.setText(e.getStringFromAmount());
        holder.createdAt.setText(e.getCreatedAtString());
        if(e.isSpent()) {
            holder.amount.setTextColor(ContextCompat.getColor(holder.amount.getContext(), R.color.spent));
        } else  {
            holder.amount.setTextColor(
                    ContextCompat.getColor(holder.amount.getContext(), R.color.got));
        }
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }
}
