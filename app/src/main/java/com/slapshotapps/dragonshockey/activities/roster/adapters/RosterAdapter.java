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
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by willmetz on 7/31/16.
 */

public class RosterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<RosterListItem> rosterListItems;
    private final Context context;

    public RosterAdapter(final Context context, List<Player> roster){

        rosterListItems = new ArrayList<>();

        //add in the header
        rosterListItems.add(new RosterListItem());

        //add in the players
        for(Player player: roster){
            rosterListItems.add(new RosterListItem(player));
        }

        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        if(viewType == RosterListItem.HEADER_TYPE){
            view = LayoutInflater.from(context).inflate(R.layout.view_header_roster, parent, false);
            return new HeaderView(view);
        }
        else{
            view = LayoutInflater.from(context).inflate(R.layout.view_roster_row,parent, false);
            return new PlayerLineView(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(getItemViewType(position) == RosterListItem.ROSTER_TYPE){

            PlayerLineView playerLineView = (PlayerLineView)holder;

            final Resources resources = context.getResources();
            if(position%2 == 0){
                playerLineView.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGray));
            }else{
                playerLineView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
            }

            playerLineView.setPlayer(rosterListItems.get(position).player);
        }


    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return RosterListItem.HEADER_TYPE;
        }
        return RosterListItem.ROSTER_TYPE;
    }

    @Override
    public int getItemCount() {
        return rosterListItems.size();
    }

    public static class PlayerLineView extends RecyclerView.ViewHolder{

        private Player player;
        private TextView name;
        private TextView number;
        private TextView position;
        private TextView shot;

        public PlayerLineView(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.player_name);
            number = (TextView)itemView.findViewById(R.id.player_number);
            position = (TextView)itemView.findViewById(R.id.player_position);
            shot = (TextView)itemView.findViewById(R.id.player_shot);
        }

        public void setPlayer(Player player){
            this.player = player;

            number.setText(String.valueOf(player.number));
            name.setText(player.firstName + " " + player.lastName);
            position.setText(player.position);
            shot.setText(player.shot);
        }

        public void setBackgroundColor(int color){
            itemView.setBackgroundColor(color);
        }
    }

    public static class HeaderView extends RecyclerView.ViewHolder{
        public HeaderView(View itemView) {
            super(itemView);
        }
    }
}
