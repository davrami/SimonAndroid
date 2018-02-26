package dam2.simon;


import android.graphics.drawable.Icon;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

public class Game {
    Date date;
    int Points;
    String user;
    int level;
    List<String> levelList;
    List<String> responseClickList;

    private Simon simonView;
    public Timer timer;
    public Integer i;
    public Boolean canResponse;

    public Game(Date date) {
        this.date = date;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List getLevelList() {
        return levelList;
    }

    public void setLevelList(List levelList) {
        this.levelList = levelList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void init(Simon simon) {
        this.canResponse = false;
        this.levelList = new ArrayList<>();
        this.responseClickList = new ArrayList<>();
        this.setLevel(1);
        this.setPoints(0);
        UtilityGame.getRandomlevel();
        String levelName = UtilityGame.getRandomlevel();
        System.out.println(levelName);
        this.levelList.add(levelName);
        this.simonView = simon;
        playLevelList();
    }

    public void playLevelList() {
        this.i = 0;
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                simonView.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        simonView.imageRandom.setImageAlpha(255);

                        if (levelList.size() > i) {
                            String level = levelList.get(i);
                            int index = java.util.Arrays.asList(UtilityGame.shape).indexOf(level);
                            simonView.imageRandom.setImageResource(UtilityGame.imageId[index]);
                            UtilityGame.playSong(simonView.getBaseContext(), index);
                            //Toast.makeText(simonView, "level " + getLevel(), Toast.LENGTH_SHORT).show();
                            Snackbar.make(simonView.findViewById(android.R.id.content), "Level: " + getLevel(),
                                    Snackbar.LENGTH_SHORT)
                                    .show();
                            i++;
                        } else {
                            timer.cancel();
                            timer.purge();
                            timer = null;
                            simonView.imageRandom.setImageAlpha(0);
                            //addLevel();
                            listenResponse();
                        }
                    }
                });

            }
        }, 3000, 2000);

        //addLevel();
    }

    public void addLevel() {
        this.responseClickList = new ArrayList<>();
        this.setLevel(this.getLevel() + 1);
        this.levelList.add(UtilityGame.getRandomlevel());
        //Toast.makeText(this.simonView, "level "+getLevel(), Toast.LENGTH_SHORT).show();
        playLevelList();
    }
    public void listenResponse(){
        this.canResponse = true;
        Toast.makeText(this.simonView, "Your turn!", Toast.LENGTH_SHORT).show();

    }
    
    public void clickElement(int position){
        if(this.canResponse){
           //TODO check
            // if ok add level
            int index = this.responseClickList.size();
            String correctShape = this.levelList.get(index);
            String responseClick = UtilityGame.shape[position];

            if(responseClick.equals(correctShape)){
                //correcta
                this.responseClickList.add(responseClick);
                if(this.responseClickList.size() == this.levelList.size()){
                    addLevel();
                }
            }else{ //incorrecta
               this.simonView.gameOver();
                Toast.makeText(this.simonView, "GAME OVER", Toast.LENGTH_SHORT).show();

            }

        }else{
            Toast.makeText(this.simonView, "wait...", Toast.LENGTH_SHORT).show();
        }
    }
}
