package com.example.journey_journal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<ModelClass> userlist;
    public  Adapter (List<ModelClass>userlist){
        this.userlist=userlist;
    }


    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        int journal_image=userlist.get(position).getImage1();
        int delete_journal=userlist.get(position).getImage2();
        int edit_journal=userlist.get(position).getImage2();
        String journal_title=userlist.get(position).getTxt_title();
        String journal_des=userlist.get(position).getTxt_des();
        String journal_location=userlist.get(position).getTxt_location();

        holder.setData(journal_image,delete_journal,edit_journal,journal_title,journal_des,journal_location);

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class    ViewHolder  extends RecyclerView.ViewHolder{

        private ImageView img1;
        private ImageView img2;
        private ImageView img3;
        private TextView txt1;
        private TextView txt2;
        private TextView txt3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img1=itemView.findViewById(R.id.j_image);
            img2=itemView.findViewById(R.id.j_delete);
            img3=itemView.findViewById(R.id.j_edit);
            txt1=itemView.findViewById(R.id.j_title);
            txt2=itemView.findViewById(R.id.j_dis);
            txt3=itemView.findViewById(R.id.j_locatio);

        }

        public void setData(int journal_image, int delete_journal, int edit_journal, String journal_title, String journal_des, String journal_location) {
            img1.setImageResource(journal_image);
            img2.setImageResource(delete_journal);
            img3.setImageResource(edit_journal);
            txt1.setText(journal_title);
            txt2.setText(journal_des);
            txt3.setText(journal_location);
        }
    }
}
