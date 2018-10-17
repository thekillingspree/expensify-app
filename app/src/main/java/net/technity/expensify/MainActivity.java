package net.technity.expensify;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.technity.expensify.Adapters.ExpenseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Expense> expenseList = new ArrayList<>();
    private FirebaseUser user;
    private FirebaseAuth auth;
    boolean doubleBackToExitPressedOnce = false;
    private ExpenseAdapter expenseAdapter;
    private RecyclerView expenseRecycler;
    private FirebaseDatabase database;
    private TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        expenseRecycler = findViewById(R.id.expenses_recycler);
        expenseAdapter = new ExpenseAdapter(expenseList);
        balance = findViewById(R.id.balance);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        expenseRecycler.setLayoutManager(layoutManager);
        expenseRecycler.setItemAnimator(new DefaultItemAnimator());
        expenseRecycler.setAdapter(expenseAdapter);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddExpense.class);
                startActivity(i);
//                Snackbar.make(view, "Coming Soon...", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        DatabaseReference expensesRef = database.getReference("users/" + user.getUid() + "/expenses");
        final ChildEventListener expenseListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("RETRIEVED", "onChildAdded:" + dataSnapshot.getKey());
                Log.e("RETRIEVED", "Child:" + dataSnapshot.getValue().toString());
                Expense e = dataSnapshot.getValue(Expense.class);
                expenseList.add(e);
                expenseAdapter.notifyItemInserted(expenseList.size());
                String pos = getBalance() > 0 ? "+" : "-";
                balance.setText("Balance: " + pos + "₹ " + getBalance());
                if (getBalance() > 0) {
                    balance.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.got));
                } else {
                    balance.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.spent));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                Log.e("REMOVED", dataSnapshot.getValue().toString());
//                Expense e = dataSnapshot.getValue(Expense.class);
//                int index = 0;
//                for (Expense ex : expenseList) {
//                    if(ex.geteTitle().equals(e.geteTitle())) {
//                        break;
//                    }
//                    index++;
//                }
//                Log.e("INDEX", index + "");
//                expenseList.remove(e);
//                expenseAdapter.notifyItemRemoved(index);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        expensesRef.addChildEventListener(expenseListener);
        NavigationView navigationView =  findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.user_name);
        TextView email = header.findViewById(R.id.user_email);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        name.setText(currentUser != null ? currentUser.getDisplayName() : "AAHA");
        email.setText(currentUser != null ? currentUser.getEmail() : "Ohhoo");
        navigationView.setNavigationItemSelectedListener(this);
    }


    private double getBalance() {
        double balance = 0;
        for(Expense e : expenseList) {
            double b = e.isSpent() ? (-1 * e.geteAmount()) : e.geteAmount();
            balance += b;
        }
        return balance;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press Back again to quit.",Toast.LENGTH_LONG)
                    .show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_manage) {
        } else if (id == R.id.nav_share) {
        }
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
