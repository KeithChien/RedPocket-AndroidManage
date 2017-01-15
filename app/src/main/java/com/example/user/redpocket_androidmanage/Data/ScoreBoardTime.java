package com.example.user.redpocket_androidmanage.Data;



public class ScoreBoardTime {
    String id;
    long startDateInterval;
    long endDateInterval;

    public ScoreBoardTime(){

    }
    public ScoreBoardTime(String id, long startDateInterval , long endDateInterval){
        this.id = id;
        this.startDateInterval = startDateInterval;
        this.endDateInterval = endDateInterval;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStartDateInterval() {
        return startDateInterval;
    }

    public void setStartDateInterval(long startDateInterval) {
        this.startDateInterval = startDateInterval;
    }

    public long getEndDateInterval() {
        return endDateInterval;
    }

    public void setEndDateInterval(long endDateInterval) {
        this.endDateInterval = endDateInterval;
    }

}
