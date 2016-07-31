package com.slapshotapps.dragonshockey.activities.roster.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slapshotapps.dragonshockey.R;
import com.slapshotapps.dragonshockey.models.Player;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by willmetz on 7/31/16.
 */

public class RosterAdapter extends RecyclerView.Adapter<RosterAdapter.RosterView>{

    private ArrayList<Player> roster;
    private final Context context;

    public RosterAdapter(final Context context, ArrayList<Player> roster){
        this.roster = roster;
        this.context = context;
    }

    @Override
    public RosterView onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.view_roster_row,parent, false);

        return new RosterView(view);
    }

    @Override
    public void onBindViewHolder(RosterView holder, int position) {

        final Resources resources = context.getResources();
        if(position%2 == 0){
            holder.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray));
        }else{
            holder.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
        }

        holder.setPlayer(roster.get(position));

    }

    @Override
    public int getItemCount() {
        return roster.size();
    }

    public static class RosterView extends RecyclerView.ViewHolder{

        private Player player;
        private TextView name;
        private TextView number;
        private TextView position;
        private TextView shot;

        public RosterView(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.player_name);
            number = (TextView)itemView.findViewById(R.id.player_number);
            position = (TextView)itemView.findViewById(R.id.player_position);
            shot = (TextView)itemView.findViewById(R.id.player_shot);
        }

        public void setPlayer(Player player){
            this.player = player;
        }

        public void setBackgroundColor(int color){
            itemView.setBackgroundColor(color);
        }
    }
}
