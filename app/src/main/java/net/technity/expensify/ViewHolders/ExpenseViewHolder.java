/*
 * Copyright (c) Ajesh, Alpesh, Arjun
 */

package net.technity.expensify.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.technity.expensify.R;

public class ExpenseViewHolder extends RecyclerView.ViewHolder {

    public TextView title, amount, createdAt;

    public ExpenseViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.rec_exp_title);
        amount = itemView.findViewById(R.id.rec_exp_amt);
        createdAt = itemView.findViewById(R.id.rec_exp_date);
    }
}
