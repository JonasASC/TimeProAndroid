package de.codeyourapp.timeproandroid.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
import de.codeyourapp.timeproandroid.Models.DateTime;
import de.codeyourapp.timeproandroid.R;



public class HistoryRvAdapter extends RecyclerView.Adapter<HistoryRvAdapter.ViewHolder>{
    public static List<DateTime> datetime;
    private HistoryOnNoteListener historyOnNoteListener;
    private HistoryOnNoteLongListener historyOnNoteLongListener;

    public HistoryRvAdapter(List<DateTime> datetime, HistoryOnNoteListener historyOnNoteListener, HistoryOnNoteLongListener historyOnNoteLongListener) {
        this.datetime = datetime;
        this.historyOnNoteListener = historyOnNoteListener;
        this.historyOnNoteLongListener = historyOnNoteLongListener;
    }

    @NonNull
    @Override
    public HistoryRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(itemView, historyOnNoteListener, historyOnNoteLongListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRvAdapter.ViewHolder holder, int i) {
        holder.startTime.setText("Start:" + datetime.get(i).startts);
        holder.endTime.setText("Ende:" +datetime.get(i).endts);

    }

    @Override
    public int getItemCount() {
        return datetime.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView startTime;
        TextView endTime;
        RelativeLayout relativeLayout;
        CardView cardView;
        HistoryOnNoteListener mHistoryOnNoteListener;
        HistoryOnNoteLongListener mHistoryOnNoteLongListener;


        public ViewHolder(@NonNull View itemView, HistoryOnNoteListener historyOnNoteListener, HistoryOnNoteLongListener historyOnNoteLongListener) {
            super(itemView);
            cardView = itemView.findViewById(R.id.history_id);
            startTime = (TextView) itemView.findViewById(R.id.startts);
            endTime = (TextView)itemView.findViewById(R.id.endts);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.history_rv);
            mHistoryOnNoteListener = historyOnNoteListener;
            mHistoryOnNoteLongListener = historyOnNoteLongListener;
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view){
            historyOnNoteListener.historyOnNoteClick(getAdapterPosition());
        }

        public boolean onLongClick(View view){
            historyOnNoteLongListener.historyOnNoteLongListener(getAdapterPosition(),view);

            return true;
        }

    }
    public interface HistoryOnNoteListener{
        void historyOnNoteClick(int position);
    }

    public interface HistoryOnNoteLongListener{
        void historyOnNoteLongListener(int position, View view);
    }
}





