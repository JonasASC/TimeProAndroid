package de.codeyourapp.timeproandroid.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import de.codeyourapp.timeproandroid.Models.ProjectModel;
import de.codeyourapp.timeproandroid.R;


public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder>{
    private List<ProjectModel> project;

    public RvAdapter(List<ProjectModel> project){
        this.project = project;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView projectName;
        TextView projectId;
        ImageView androidButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            projectName = (TextView) itemView.findViewById(R.id.item);
            projectId = (TextView)itemView.findViewById(R.id.person_age);
        }
    }

    @NonNull
    @Override
    public RvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.porject_list_look, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.ViewHolder holder, int i) {
        holder.projectName.setText(project.get(i).name);
        holder.projectId.setText(project.get(i).userid.toString());
    }

    @Override
    public int getItemCount() {
        return project.size();
    }
}
