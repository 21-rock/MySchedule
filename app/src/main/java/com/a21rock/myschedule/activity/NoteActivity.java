package com.a21rock.myschedule.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;

import com.a21rock.myschedule.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


import com.a21rock.myschedule.adapter.NotesAdapter;
import com.a21rock.myschedule.utils.LogUtil;
import com.a21rock.myschedule.utils.ViewUtil;
import com.a21rock.myschedule.bean.Note;

/* 学习记事 */
public class NoteActivity extends BaseActivity {


    private List<Note> noteList = new ArrayList<>();
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initToolbar();
        initNotes();
        View view = ViewUtil.getRootView(NoteActivity.this);
        initRecyclerView(view);
    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) findViewById(R.id.toolbar_note_title);
        textView.setText("学习记事");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 显示导航按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initNotes() {
        noteList = DataSupport.findAll(Note.class);
    }

    private void initRecyclerView(View view) {
        RecyclerView notesRecyclerView = (RecyclerView) view.findViewById(R.id.note_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(layoutManager);
        adapter = new NotesAdapter(noteList, NoteActivity.this);
        notesRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_toolbar, menu);
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        LogUtil.d("noteList", noteList.toString());
        initRecyclerView(ViewUtil.getRootView(NoteActivity.this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_add:
                AddNoteActivity.actionStart(NoteActivity.this);
                finish();
                break;
            default:

        }
        return true;
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, NoteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
