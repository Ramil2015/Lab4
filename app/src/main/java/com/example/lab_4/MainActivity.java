package com.example.lab_4;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TodoItem> todoList;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoList = new ArrayList<>();
        todoAdapter = new TodoAdapter(this);

        EditText editText = findViewById(R.id.todoEditText);
        Switch urgentSwitch = findViewById(R.id.urgentSwitch);
        Button addButton = findViewById(R.id.addButton);

        ListView listView = findViewById(R.id.todoListView);
        listView.setAdapter(todoAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                boolean isUrgent = urgentSwitch.isChecked();
                TodoItem todoItem = new TodoItem(text, isUrgent);
                todoList.add(todoItem);
                editText.getText().clear();
                todoAdapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.dialog_delete_title)
                        .setMessage(getString(R.string.dialog_delete_message)+" "+ (position+1))
                        .setPositiveButton(R.string.dialog_delete_positive_button, (dialog, which) -> {
                            todoList.remove(position);
                            todoAdapter.notifyDataSetChanged();
                        })
                        .setNegativeButton(R.string.dialog_delete_negative_button, null)
                        .show();

                return true;
            }
        });
    }

    private class TodoAdapter extends BaseAdapter {

        public TodoAdapter(Context context) {
        }

        @Override
        public int getCount() {
            return todoList.size();
        }

        @Override
        public Object getItem(int position) {
            return todoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.todo_item, parent, false);
            }

            TextView textView = view.findViewById(R.id.todoTextView);
            TodoItem todoItem = todoList.get(position);
            textView.setText(todoItem.getText());

            if (todoItem.isUrgent()) {
                view.setBackgroundColor(Color.RED);
                textView.setTextColor(Color.WHITE);
            } else {
                view.setBackgroundColor(Color.TRANSPARENT);
                textView.setTextColor(Color.BLACK);
            }

            return view;
        }
    }

    private static class TodoItem {
        private String text;
        private boolean isUrgent;

        public TodoItem(String text, boolean isUrgent) {
            this.text = text;
            this.isUrgent = isUrgent;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isUrgent() {
            return isUrgent;
        }

        public void setUrgent(boolean urgent) {
            isUrgent = urgent;
        }
    }
}
