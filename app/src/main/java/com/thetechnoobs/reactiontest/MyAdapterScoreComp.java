package com.thetechnoobs.reactiontest;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyAdapterScoreComp extends RecyclerView.Adapter<MyAdapterScoreComp.MyViewHolder> {
    Context context;
    int OrgWith;
    List<User> PeopleDataOrganized;

    public MyAdapterScoreComp(Context ct, List<User> people, int orgWith) {
        context = ct;
        OrgWith = orgWith;
        OrganizeUsers(people);//organize data
        PeopleDataOrganized = people;
    }

    public void OrganizeUsers(final List<User> peopleData) {
        Collections.sort(peopleData, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {

                switch (OrgWith) {
                    case 2:
                        return Integer.compare(Integer.parseInt(o2.getAsteroidHighScore()), Integer.parseInt(o1.getAsteroidHighScore()));//sort from greatest to least
                    case 1:
                        return Integer.compare(Integer.parseInt(o1.getFastestReactionTime().replace(".", "")), Integer.parseInt(o2.getFastestReactionTime().replace(".", "")));//get time, remove non ints, then compare least to greatest
                    case 0:
                    default:
                        return Integer.compare(Integer.parseInt(o1.getImgFastestTime().replace(".", "")), Integer.parseInt(o2.getImgFastestTime().replace(".", "")));//get time, remove non ints, then compare least to greatest
                }
            }
        });
    }

    public void ReOrganizeData(int orgWith) {
        OrgWith = orgWith;
        OrganizeUsers(PeopleDataOrganized);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.new_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        switch (position) {
            case 0:
                holder.TrophyImg.setVisibility(View.VISIBLE);
                holder.TrophyImg.setImageResource(R.drawable.first_trophy);
                break;
            case 1:
                holder.TrophyImg.setVisibility(View.VISIBLE);
                holder.TrophyImg.setImageResource(R.drawable.second_trophy);
                break;
            case 2:
                holder.TrophyImg.setVisibility(View.VISIBLE);
                holder.TrophyImg.setImageResource(R.drawable.third_trophy);
                break;
        }
        if (position > 2) {
            holder.TrophyImg.setVisibility(View.GONE);
        }

        User person = PeopleDataOrganized.get(position);

        if (person.getUserID().equals(new SaveData().getUserID(context))) {
            holder.CardLayout.setBackgroundResource(R.drawable.leaderboared_curuser);
        }else{
            holder.CardLayout.setBackgroundResource(R.drawable.green_blue_gradeient);
        }

        holder.UserIDTXT.setText(person.getUserID());
        holder.NameTXT.setText(person.getName());

        switch (OrgWith) {
            case 2:
                holder.AvgTimeTXT.setVisibility(View.GONE);
                holder.RightTextID.setVisibility(View.GONE);
                holder.TimeTXT.setText(String.valueOf(person.getAsteroidHighScore()));
                holder.LeftTextID.setText("High Score");
                return;
            case 1:
                holder.TimeTXT.setText(person.getFastestReactionTime());
                holder.AvgTimeTXT.setText(person.getFastestAvgReactionTime());
                return;
            case 0:
            default:
                holder.TimeTXT.setText(person.getImgFastestTime());
                holder.AvgTimeTXT.setText(person.getImgRecAverageTime());
                return;
        }
    }

    @Override
    public int getItemCount() {
        return PeopleDataOrganized.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView UserIDTXT, NameTXT, TimeTXT, AvgTimeTXT;
        TextView LeftTextID, RightTextID;//needed to modify the title texts
        ImageView TrophyImg;
        RelativeLayout CardLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            TrophyImg = itemView.findViewById(R.id.TrophyIMG);
            CardLayout = itemView.findViewById(R.id.LayoutCard);
            AvgTimeTXT = itemView.findViewById(R.id.AverageTimeTXT);
            UserIDTXT = itemView.findViewById(R.id.IDTXT);
            NameTXT = itemView.findViewById(R.id.NameTXT);
            TimeTXT = itemView.findViewById(R.id.TimeTXT);
            LeftTextID = itemView.findViewById(R.id.LeftTitleTXT);
            RightTextID = itemView.findViewById(R.id.RightTitleTXT);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();

            Intent GoToUserPage = new Intent(context, UserPage.class);
            GoToUserPage.putExtra("User", PeopleDataOrganized.get(pos).getUserID());
            context.startActivity(GoToUserPage);
        }
    }
}
