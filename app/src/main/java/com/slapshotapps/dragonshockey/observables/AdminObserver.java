package com.slapshotapps.dragonshockey.observables;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.slapshotapps.dragonshockey.Config;
import com.slapshotapps.dragonshockey.models.Game;
import com.slapshotapps.dragonshockey.models.GameResult;
import com.slapshotapps.dragonshockey.models.GameStats;
import com.slapshotapps.dragonshockey.models.GameUpdateKeys;
import com.slapshotapps.dragonshockey.models.Player;
import com.slapshotapps.dragonshockey.models.PlayerGameStats;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.subscriptions.Subscriptions;

/**
 * Created on 10/27/16.
 */

public class AdminObserver {

    public static final String NO_KEY_FOUND = null;

    public static Observable<String> getPlayerStatsKey(final FirebaseDatabase database, final int gameID) {


        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {

                final Query query = database.getReference(Config.GAME_STATS).orderByChild(Config.GAME_ID).equalTo(gameID);

                final ValueEventListener valueEventListener = query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String key = NO_KEY_FOUND;

                        //ensure there is only one
                        if (dataSnapshot.getChildrenCount() == 1) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                key = child.getKey();
                            }
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(key);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //remove the subscriber when canceled
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        query.removeEventListener(valueEventListener);
                    }
                }));
            }
        });

    }

    public static Observable<String> getGameKey(final FirebaseDatabase database, final int gameID) {


        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {

                final Query query = database.getReference(Config.GAMES).orderByChild(Config.GAME_ID).equalTo(gameID);

                final ValueEventListener valueEventListener = query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String key = NO_KEY_FOUND;

                        //ensure there is only one
                        if (dataSnapshot.getChildrenCount() == 1) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                key = child.getKey();
                            }
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(key);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //remove the subscriber when canceled
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        query.removeEventListener(valueEventListener);
                    }
                }));
            }
        });

    }

    public static Observable<String> getGameResultKey(final FirebaseDatabase database, final int gameID) {


        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {

                final Query query = database.getReference(Config.GAME_RESULTS).orderByChild(Config.GAME_ID).equalTo(gameID);

                final ValueEventListener valueEventListener = query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String key = NO_KEY_FOUND;

                        //ensure there is only one
                        if (dataSnapshot.getChildrenCount() == 1) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                key = child.getKey();
                            }
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(key);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //remove the subscriber when canceled
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        query.removeEventListener(valueEventListener);
                    }
                }));
            }
        });

    }

    public static Observable<GameStats> getGameStats(final FirebaseDatabase database, final int gameID) {


        return Observable.create(new Observable.OnSubscribe<GameStats>() {
            @Override
            public void call(final Subscriber<? super GameStats> subscriber) {

                final Query query = database.getReference(Config.GAME_STATS).orderByChild(Config.GAME_ID).equalTo(gameID);

                final ValueEventListener valueEventListener = query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        GameStats gameStats = null;

                        //ensure there is only one
                        if (dataSnapshot.getChildrenCount() == 1) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                gameStats = child.getValue(GameStats.class);
                                gameStats.key = child.getKey();

                                //populate the internal list of players details per game
                                gameStats.gameStats = new ArrayList<GameStats.Stats>();
                                for (DataSnapshot childListSnapshot : child.child("stats").getChildren()) {
                                    gameStats.gameStats.add(childListSnapshot.getValue(GameStats.Stats.class));
                                }
                            }
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(gameStats);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //remove the subscriber when canceled
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        query.removeEventListener(valueEventListener);
                    }
                }));
            }
        });

    }

    public static Observable<Game> getGame(final FirebaseDatabase database, final int gameID) {

        return Observable.create(new Observable.OnSubscribe<Game>() {
            @Override
            public void call(final Subscriber<? super Game> subscriber) {
                final Query query = database.getReference(Config.GAMES).orderByChild(Config.GAME_ID).equalTo(gameID);

                final ValueEventListener valueEventListener = query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Game game = null;

                        //ensure there is only one
                        if (dataSnapshot.getChildrenCount() == 1) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                game = child.getValue(Game.class);
                            }
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(game);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //remove the subscriber when canceled
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        query.removeEventListener(valueEventListener);
                    }
                }));
            }
        });
    }

    public static Observable<GameResult> getGameResult(final FirebaseDatabase database, final int gameID) {

        return Observable.create(new Observable.OnSubscribe<GameResult>() {
            @Override
            public void call(final Subscriber<? super GameResult> subscriber) {
                final Query query = database.getReference(Config.GAME_RESULTS).orderByChild(Config.GAME_ID).equalTo(gameID);

                final ValueEventListener valueEventListener = query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        GameResult gameResult = null;

                        //ensure there is only one
                        if (dataSnapshot.getChildrenCount() == 1) {
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                gameResult = child.getValue(GameResult.class);
                            }
                        }

                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(gameResult);
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                //remove the subscriber when canceled
                subscriber.add(Subscriptions.create(new Action0() {
                    @Override
                    public void call() {
                        query.removeEventListener(valueEventListener);
                    }
                }));
            }
        });
    }

    public static Observable<GameUpdateKeys> getGameKeys(final FirebaseDatabase database, final int gameID) {

        return Observable.zip(AdminObserver.getGameKey(database, gameID),
                AdminObserver.getGameResultKey(database, gameID),
                AdminObserver.getPlayerStatsKey(database, gameID),
                new Func3<String, String, String, GameUpdateKeys>() {
                    @Override
                    public GameUpdateKeys call(String gameKey, String gameResultsKey, String playerStatsKey) {
                        return new GameUpdateKeys(gameKey, gameResultsKey, playerStatsKey);
                    }
                }
        );
    }

    public static Observable<PlayerGameStats> getPlayerStatsForGame(final FirebaseDatabase database, final int gameID) {

        return Observable.zip(RosterObserver.GetRoster(database),
                AdminObserver.getGameStats(database, gameID),
                new Func2<List<Player>, GameStats, PlayerGameStats>() {
                    @Override
                    public PlayerGameStats call(List<Player> players, GameStats gameStats) {

                        if (gameStats == null) {
                            gameStats = new GameStats();
                        }

                        PlayerGameStats playerGameStats = new PlayerGameStats();
                        playerGameStats.playerStatsKey = gameStats.key;
                        playerGameStats.players = new ArrayList<Player>(players);
                        playerGameStats.playerGameStats = gameStats;

                        return playerGameStats;
                    }
                }
        );
    }

    public static Observable<Game> getGameUpdateInfo(final FirebaseDatabase database, final int gameID) {
        return Observable.zip(AdminObserver.getGame(database, gameID),
                AdminObserver.getGameResult(database, gameID),
                new Func2<Game, GameResult, Game>() {
                    @Override
                    public Game call(Game game, GameResult gameResult) {

                        game.gameResult = gameResult;

                        return game;
                    }
                });
    }
}
