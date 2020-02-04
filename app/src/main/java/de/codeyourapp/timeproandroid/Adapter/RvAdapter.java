package de.codeyourapp.timeproandroid.Adapter;

import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder>{
    private List<ProjectModel> project;
    OnItemListener itemListener;


    public RvAdapter(List<ProjectModel> project, OnItemListener onItemListener){
        this.project = project;
        this.itemListener = onItemListener;
    }


    public void loadWithNewData(List<ProjectModel> project) {
        this.project.clear();
        this.project = project;
        this.notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{

        TextView projectName;
        TextView projectId;
        TextView time;
        ImageView androidButton;
        OnItemListener itemListener;
        RelativeLayout relativeLayout;
        CardView cardView;

        public ViewHolder(@NonNull View itemView, OnItemListener itemListener) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv);
            time = (TextView) itemView.findViewById(R.id.time);
            projectName = (TextView) itemView.findViewById(R.id.item);
            projectId = (TextView)itemView.findViewById(R.id.person_age);
            this.itemListener = itemListener;
            itemView.setOnClickListener(this);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rlayout);
            cardView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onClick(View view) {
            itemListener.onItemClick(getAdapterPosition());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(),121,0,"Delete this Project");
            contextMenu.add(this.getAdapterPosition(),122,0,"Edit");
        }
    }

    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.porject_list_look, parent, false);
        ProjectViewActivity.getData();
        return new ViewHolder(itemView, itemListener);
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

    public interface OnItemListener{
        void onItemClick(int position);
    }

    public void removeProject(int postion){

       Integer id = project.get(postion).id;
       HTTPPost http = new HTTPPost();
       http.execute("http://jwg.zollhaus.net:8080/Hello-Servlet-0.0.1-SNAPSHOT/deleteProject", id.toString());
       System.out.println(id);
    }


}
