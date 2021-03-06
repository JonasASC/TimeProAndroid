package de.codeyourapp.timeproandroid.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import de.codeyourapp.timeproandroid.HTTP.HTTPPost;
import de.codeyourapp.timeproandroid.Models.ProjectModel;
import de.codeyourapp.timeproandroid.R;
import de.codeyourapp.timeproandroid.Constante.UrlConstants;


public class RvAdapterProjectList extends RecyclerView.Adapter<RvAdapterProjectList.ViewHolder>{
    public static List<ProjectModel> project;
    private OnNoteListener onNoteListener;
    private OnNoteLongListener onNoteLongListener;
    private Long lastPause;

    public RvAdapterProjectList(List<ProjectModel> project, OnNoteListener onNoteListener, OnNoteLongListener onNoteLongListener){
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
    public RvAdapterProjectList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.porject_list_look, parent, false);
        LoadDataActiveProject.getData();
        return new ViewHolder(itemView, onNoteListener, onNoteLongListener);
    }


    @Override
    public void onBindViewHolder(@NonNull RvAdapterProjectList.ViewHolder holder, int i) {
        holder.projectName.setText(project.get(i).name);
        holder.projectId.setText(project.get(i).id.toString());
        if(project.get(i).elapsed == null){
            holder.time.setText("Gesamtzeit : 00:00");
        }else {
            holder.time.setText("Gesamtzeit : " + project.get(i).elapsed);
        }
        try{

                holder.progressBar.setProgress(project.get(i).progress.intValue());
        }catch (Exception e){

        }

        if (project.get(i).active) {

            holder.statusButton.setColorFilter(Color.GREEN);
        }else{
            holder.statusButton.setColorFilter(Color.BLACK);
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
        ProgressBar progressBar;
        ImageView statusButton;



        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener, OnNoteLongListener onNoteLongListener) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv);
            time = (TextView) itemView.findViewById(R.id.time);
            projectName = (TextView) itemView.findViewById(R.id.project_name);
            projectId = (TextView)itemView.findViewById(R.id.person_age);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rlayout);
            mOnNoteListener = onNoteListener;
            mOnNoteLongListener = onNoteLongListener;
            statusButton = (ImageView) itemView.findViewById(R.id.statusButton);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar2);
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
       ProjectModel pro = new ProjectModel(project.get(postion).id);
       Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
       String json = gson.toJson(pro);
       HTTPPost http = new HTTPPost();
       http.execute(UrlConstants.removeProjectUrl, json);
       System.out.println(json);
       LoadDataActiveProject.refresh();
    }



    public interface OnNoteListener{
        void onNoteClick(int position);
    }

    public interface OnNoteLongListener{
        void onNoteLongClick(int position, View view);
    }
}
