package com.slapshotapps.dragonshockey.observables;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.models.Player;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subscriptions.Subscriptions;

/**
 * Created by willmetz on 7/26/16.
 */

public class RosterObserver {

    public static Observable<List<Player>> GetRoster(final FirebaseDatabase database) {
        return Observable.create(subscriber -> {

            final Query query =
                    database.getReference(Config.ROSTER).orderByChild(
                            Player.Companion.getPLAYER_NUMBER());
            final ValueEventListener listener =
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            ArrayList<Player> roster = new ArrayList<Player>();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                roster.add(snapshot.getValue(Player.class));
                            }

                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(roster);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.e("tag", "Error retrieving data");
                        }
                    });

            //when the subscription is cancelled remove the listener
            subscriber.add(Subscriptions.create(() -> query.removeEventListener(listener)));
        });
    }
}
