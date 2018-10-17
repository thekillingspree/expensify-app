/*
 * Copyright (c) Ajesh, Alpesh, Arjun
 */

package net.technity.expensify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddExpense extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView amount, note, title, pickDateText;
    ImageView backBtn;
    TextInputLayout amountLayout, expenseLayout;
    boolean isSpent = false;
    long createdAt;
    RadioGroup selectGroup;
    FirebaseDatabase database;
    RadioButton income, expense;
    RelativeLayout pickDate;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("EEEE, dd MMMM, yyyy");
    Expense e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        database = FirebaseDatabase.getInstance();
        FloatingActionButton fab =  findViewById(R.id.fab);
        backBtn = findViewById(R.id.back_btn_add_expense);
        amount = findViewById(R.id.amount_editText);
        amountLayout = findViewById(R.id.amount_layout);
        expenseLayout = findViewById(R.id.expense_layout);
        note = findViewById(R.id.note_text);
        title = findViewById(R.id.expense_title);
        pickDateText = findViewById(R.id.pick_date_bottom);
        pickDate = findViewById(R.id.pick_date);
        selectGroup = findViewById(R.id.expense_group);
        income = findViewById(R.id.isIncome);
        income.setChecked(true);
        expense = findViewById(R.id.isExpense);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        createdAt = System.currentTimeMillis();
        pickDateText.setText(format.format(c.getTime()));
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                AddExpense.this,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        );

        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show(getFragmentManager(), "DatePicker");
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    e = new Expense(title.getText().toString(),
                                note.getText().toString(),
                            Double.parseDouble(amount.getText().toString()),
                            createdAt,
                            isSpent
                            );
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference ref = null;
                    if (user != null) {
                        ref = database.getReference("users/" + user.getUid()+ "/expenses");
                        ref.push().setValue(e);
                    }
                    Intent i = new Intent(AddExpense.this, MainActivity.class);
                    SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    startActivity(i);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onRadioButtonChecked(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.isIncome:
                if (checked) {
                    isSpent = false;
                }
                break;
            case R.id.isExpense:
                if(checked) {
                    isSpent = true;
                }
                break;
        }
    }

    private boolean validate() {
        boolean isValid = true;

        String eTitle = title.getText().toString().trim();
        String eAmount = amount.getText().toString().trim();

        if(eTitle.isEmpty()) {
            expenseLayout.setError("Title should not be empty.");
            isValid = false;
        } else {
            expenseLayout.setError(null);
        }
        if(eAmount.isEmpty()) {
            amountLayout.setError("Amount should not be empty.");
            isValid = false;
        } else {
            amountLayout.setError(null);
        }
        return isValid;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        createdAt = calendar.getTimeInMillis();
        pickDateText.setText(format.format(calendar.getTime()));
    }
}
