package org.hse.baseproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    public static String NAME = "Name";
    public static String TYPE = "Type";
    public static String MODE = "Mode";

    private String name;
    private ScheduleMode mode;
    private ScheduleType type;

    private final ScheduleProvider scheduleProvider = new ScheduleProvider();

    private ConstraintLayout mainView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;
    private TextView noDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        name = getIntent().getSerializableExtra(NAME).toString();
        mode = (ScheduleMode) getIntent().getSerializableExtra(MODE);
        type = (ScheduleType) getIntent().getSerializableExtra(TYPE);

        mainView = findViewById(R.id.schedule_main_view);
        recyclerView = findViewById(R.id.timetable_recycler);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        noDataTextView = new TextView(this);
        mainView.addView(noDataTextView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapter = new ScheduleAdapter();
        recyclerView.setAdapter(adapter);

        loadData();

        swipeRefreshLayout.setOnRefreshListener(this::loadData);
    }

    private void loadData() {
        Thread thread = new Thread(this::load);
        thread.start();
    }

    private void load() {
        noDataTextView.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(true);

        List<ScheduleItem> data = scheduleProvider.getSchedule(mode, type, name);

        if (data.size() == 0) {
            runOnUiThread(this::showNoDataNotification);
            return;
        }

        adapter.setData(data);
        swipeRefreshLayout.setRefreshing(false);
        runOnUiThread(() -> adapter.notifyDataSetChanged());
    }

    private void showNoDataNotification() {
        swipeRefreshLayout.setVisibility(View.GONE);
        noDataTextView.setText(R.string.no_data_message);
        noDataTextView.setGravity(Gravity.CENTER);
        noDataTextView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }
}