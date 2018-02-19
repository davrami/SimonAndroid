package dam2.simon;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {
    Date date;
    int Points;
    String user;
    int level;
    List levelList;

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

    public Game(Date date) {
        this.date = date;
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
}
