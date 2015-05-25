package com.example.cdsteer.memoapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MemoList extends ActionBarActivity {
    public static SQLiteDatabase db;
    protected Cursor cursor;
    protected ListAdapter adapter;
    protected ListView memoList;
    protected String[] fields = new String[] {DbHelper.KEY_CONTENT, DbHelper.KEY_URGENT, DbHelper.KEY_DATE, DbHelper.KEY_ALARM};
    protected int[] listFields = new int[] {R.id.content, R.id.urgentLvl, R.id.date, R.id.alarm};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_list);
        db = (new DbHelper(this)).getWritableDatabase();
        memoList = (ListView) findViewById (R.id.list);
        memoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MemoList.this, MemoEdit.class);
                Cursor cursor = (Cursor) adapter.getItem(position);
                intent.putExtra("MEMO_ID", cursor.getInt(cursor.getColumnIndex("_id")));
                startActivity(intent);
            }
        });
        listMemos();
    }

    public void listMemos() {
        cursor = DbHelper.getMemos();
        adapter = new MyList(this, R.layout.memo_list_item, cursor, fields, listFields);
        memoList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        listMemos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.new_note:
                Intent intent = new Intent(this, MemoEdit.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}