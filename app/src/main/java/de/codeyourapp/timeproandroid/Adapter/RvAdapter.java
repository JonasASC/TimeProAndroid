package de.codeyourapp.timeproandroid.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.codeyourapp.timeproandroid.Activitys.ProjectViewActivity;
import de.codeyourapp.timeproandroid.HTTP.HTTPPost;
import de.codeyourapp.timeproandroid.Models.ProjectModel;
import de.codeyourapp.timeproandroid.R;
import de.codeyourapp.timeproandroid.Constante.UrlConstants;


public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder>{
    private List<ProjectModel> project;
    private OnNoteListener onNoteListener;
    private OnNoteLongListener onNoteLongListener;

    public RvAdapter(List<ProjectModel> project,OnNoteListener onNoteListener, OnNoteLongListener onNoteLongListener){
        this.project = project;
        this.onNoteListener = onNoteListener;
        this.onNoteLongListener = onNoteLongListener;
    }


    public void loadWithNewData(List<ProjectModel> project) {
        this.project.clear();
        this.project = project;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.porject_list_look, parent, false);
        LoadData.getData();
        return new ViewHolder(itemView, onNoteListener, onNoteLongListener);
    }


    @Override
    public void onBindViewHolder(@NonNull RvAdapter.ViewHolder holder, int i) {
        holder.projectName.setText(project.get(i).name);
        holder.projectId.setText(project.get(i).id.toString());
        holder.time.setText(project.get(i).elapsed);

        if (project.get(i).active) {
            holder.relativeLayout.setBackgroundColor(Color.RED);

        }else{
            holder.relativeLayout.setBackgroundColor(Color.BLUE);
        }



    }

    @Override
    public int getItemCount()
    {
        return project.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener{

        TextView projectName;
        TextView projectId;
        TextView time;
        RelativeLayout relativeLayout;
        CardView cardView;
        OnNoteListener mOnNoteListener;
        OnNoteLongListener mOnNoteLongListener;


        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener, OnNoteLongListener onNoteLongListener) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv);
            time = (TextView) itemView.findViewById(R.id.time);
            projectName = (TextView) itemView.findViewById(R.id.item);
            projectId = (TextView)itemView.findViewById(R.id.person_age);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rlayout);
            mOnNoteListener = onNoteListener;
            mOnNoteLongListener = onNoteLongListener;
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onNoteLongListener.onNoteLongClick(getAdapterPosition(), view);


            return true;
        }
    }





    public void removeProject(int postion){
       System.out.println("Rremouve");
       Integer id = project.get(postion).id;
       HTTPPost http = new HTTPPost();
       http.execute(UrlConstants.removeProjectUrl, id.toString());
       System.out.println(id);
       LoadData.refresh();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    public interface OnNoteLongListener{
        void onNoteLongClick(int position, View view);
    }
}
