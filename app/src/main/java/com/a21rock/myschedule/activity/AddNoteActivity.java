package com.a21rock.myschedule.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.a21rock.myschedule.R;
import com.a21rock.myschedule.bean.Note;
import com.a21rock.myschedule.utils.DateUtil;

public class AddNoteActivity extends BaseActivity {

    private EditText etNotes = null;
    private int noteId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        etNotes = (EditText) findViewById(R.id.et_add_note);
        Intent intent = getIntent();

        if (isHasFlagValue(intent)) {
            // 如果没获得id值，说明是新增笔记，否则是编辑笔记
            noteId = intent.getIntExtra("noteId", 0);
            String noteContent = intent.getStringExtra("noteContent");
            etNotes.setText(noteContent);
        }
        initToolbar();
    }

    /* 如果有flag值，表示是编辑笔记，否则表示新增笔记*/
    private boolean isHasFlagValue(Intent intent) {
        Integer flag = new Integer(intent.getIntExtra("flag", 0));
        if (flag != null && flag.intValue() == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_save:
                Note note = new Note();
                if (isHasFlagValue(getIntent())) {
                    // 修改笔记
                    if (noteId != 0) {
                        note.setContent(etNotes.getText().toString());
                        note.setCreateTime(Integer.parseInt(DateUtil.timeStamp()));
                        note.update(noteId);
                    }

                } else {
                    // 新增笔记
                    note.setContent(etNotes.getText().toString());
                    note.setCreateTime(Integer.parseInt(DateUtil.timeStamp()));
                    note.save();
                }
                finish();
                NoteActivity.actionStart(AddNoteActivity.this);
                break;
            default:
        }
        return true;
    }

    /* 新增笔记 */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AddNoteActivity.class);
        context.startActivity(intent);
    }

    /* 编辑笔记 */
    public static void actionStart(Context context, int flag, int id, String noteContent) {
        Intent intent = new Intent(context, AddNoteActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("flag", flag);
        intent.putExtra("noteId", id);
        intent.putExtra("noteContent", noteContent);
        context.startActivity(intent);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add_note);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 显示导航按钮
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }
}
