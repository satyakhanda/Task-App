package model;

import java.util.Observable;
import java.util.Observer;

public class TotalEstimatedTime implements Observer  {

    private int currentTotalTime = 0;


    @Override
    public void update(Observable o, Object arg) {
        Todo task = (Task)o;
        this.currentTotalTime -= task.getEstimatedTimeToComplete();
        this.currentTotalTime = currentTotalTime + (int) arg;
        System.out.println(currentTotalTime);
    }
}
