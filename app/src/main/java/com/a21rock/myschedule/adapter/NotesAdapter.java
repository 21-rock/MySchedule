package com.a21rock.myschedule.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a21rock.myschedule.R;
import com.a21rock.myschedule.activity.AddNoteActivity;
import com.a21rock.myschedule.activity.NoteActivity;
import com.a21rock.myschedule.bean.Note;
import com.a21rock.myschedule.core.MyApplication;
import com.a21rock.myschedule.utils.DateUtil;
import com.a21rock.myschedule.utils.LogUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

import static com.a21rock.myschedule.utils.DateUtil.FORMAT_TIME;

/**
 * Created by 21rock on 2017/2/22.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private List<Note> mNoteList;

    private Activity mActivity;

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView notes;
        TextView createTime;
        LinearLayout changeNote;

        public ViewHolder(View view) {
            super(view);
            notes = (TextView) view.findViewById(R.id.tv_notes);
            createTime = (TextView) view.findViewById(R.id.tv_create_time);
            changeNote = (LinearLayout) view.findViewById(R.id.ll_change_note);
        }
    }

    public NotesAdapter(List<Note> noteList, Activity activity) {

        mNoteList = noteList;
        mActivity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.changeNote.setOnClickListener(new View.OnClickListener() {

            // 这里执行跳转到笔记编辑界面
            @Override
            public void onClick(View v) {
                Note notes = mNoteList.get(holder.getAdapterPosition());
                notes.getId();
                AddNoteActivity.actionStart(MyApplication.getContext(), 1, notes.getId(), notes.getContent());
            }
        });
        holder.changeNote.setOnLongClickListener(new View.OnLongClickListener() {

            // 这里执行跳出弹窗提示是否删除
            @Override
            public boolean onLongClick(View v) {
                final Note notes = mNoteList.get(holder.getAdapterPosition());
                LogUtil.d("longClick", notes.getContent());

                AlertDialog.Builder dialog = new AlertDialog.Builder(mActivity);
                dialog.setTitle("确认删除该笔记吗?");
                dialog.setCancelable(true);
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataSupport.delete(Note.class, notes.getId());
                        // 这里重新打开笔记界面实在是没法子了，不管怎么设置都没法局部刷新recyclerview
//                        mActivity.finish();
//                        NoteActivity.actionStart(MyApplication.getContext());

                        //2017.2.24更新，之前调用notifyDataSetChanged()没有更新是当然的，
                        //因为mNoteList的值是NoteActivity的onCreate方法带过来的，所以虽然
                        //删除了数据库里对应数据，但是没更新mNoteList，所以界面没刷新。
                        mNoteList.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                return true;
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note note = mNoteList.get(position);
        holder.notes.setText(note.getContent());
        holder.createTime.setText(DateUtil.timeStamp2Date(String.valueOf(note.getCreateTime()), "yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

}
