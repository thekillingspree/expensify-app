package net.technity.expensify;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.technity.expensify.Adapters.ExpenseAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Expense> expenseList = new ArrayList<>();
    private ExpenseAdapter expenseAdapter;
    private RecyclerView expenseRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expenseRecycler = findViewById(R.id.expenses_recycler);
        expenseAdapter = new ExpenseAdapter(expenseList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        expenseRecycler.setLayoutManager(layoutManager);
        expenseRecycler.setItemAnimator(new DefaultItemAnimator());
        expenseRecycler.setAdapter(expenseAdapter);

        prepareDummy();

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

        NavigationView navigationView =  findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView name = header.findViewById(R.id.user_name);
        TextView email = header.findViewById(R.id.user_email);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        name.setText(currentUser != null ? currentUser.getDisplayName() : "AAHA");
        email.setText(currentUser != null ? currentUser.getEmail() : "Ohhoo");
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void prepareDummy() {
        Expense e = new Expense("Halls", "Throat Ache", 5.00, System.currentTimeMillis(), true);
        expenseList.add(e);

        e = new Expense("Kindle Cover", "Needed that ;)", 500.00, System.currentTimeMillis(),true);
        expenseList.add(e);
        e = new Expense("Adsense Money", "Needed that ;)", 1500.00, System.currentTimeMillis(),false);
        expenseList.add(e);
        e = new Expense("Bought A Book", "Needed that ;)", 19.00, System.currentTimeMillis(),true);
        expenseList.add(e);
        e = new Expense("Text Books", "Needed that ;)", 1850.00, System.currentTimeMillis(),true);
        expenseList.add(e);
        e = new Expense("Cool stuff", "Needed that ;)", 700.00, System.currentTimeMillis(),true);
        expenseList.add(e);
        e = new Expense("Adsense Money", "Needed that ;)", 1500.00, System.currentTimeMillis(),false);
        expenseList.add(e);
        e = new Expense("Movie Tickets", "Needed that ;)", 700.00, System.currentTimeMillis(),true);
        expenseList.add(e);
        e = new Expense("Adsense Money", "Needed that ;)", 1500.00, System.currentTimeMillis(),false);
        expenseList.add(e);
        e = new Expense("Kindle Cover", "Needed that ;)", 500.00, System.currentTimeMillis(),true);
        expenseList.add(e);
        e = new Expense("Adsense Money", "Needed that ;)", 1500.00, System.currentTimeMillis(),false);
        expenseList.add(e);
        e = new Expense("Bought A Book", "Needed that ;)", 19.00, System.currentTimeMillis(),true);
        expenseList.add(e);
        e = new Expense("Text Books", "Needed that ;)", 1850.00, System.currentTimeMillis(),true);
        expenseList.add(e);
        e = new Expense("Cool stuff", "Needed that ;)", 700.00, System.currentTimeMillis(),true);
        expenseList.add(e);
        e = new Expense("Adsense Money", "Needed that ;)", 1500.00, System.currentTimeMillis(),false);
        expenseList.add(e);
        e = new Expense("Movie Tickets", "Needed that ;)", 700.00, System.currentTimeMillis(),true);
        expenseList.add(e);
        e = new Expense("Adsense Money", "Needed that ;)", 1500.00, System.currentTimeMillis(),false);
        expenseList.add(e);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
