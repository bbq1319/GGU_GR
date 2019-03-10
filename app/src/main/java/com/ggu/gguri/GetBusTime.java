package com.ggu.gguri;

public class GetBusTime {

    String[] termTime = {
            "06:25", "07:25", "08:15", "09:30", "10:45", "12:00", "13:00",
            "14:00", "15:30", "17:00", "18:30", "19:30", "21:00"
    };
    String[] schoolTime = {
            "07:00", "08:20", "09:10", "10:25", "11:40", "12:55", "14:00",
            "15:00", "16:25", "18:00", "19:20", "20:20", "21:45"
    };
    int termArrLength = termTime.length;
    int schoolArrLength = schoolTime.length;
    int curTimeArr;

    public String getCurrentTTS(String now) {
        for(int i=0; i<termArrLength; i++) {
            if(now.compareTo(termTime[i]) < 0){
                curTimeArr = i;
                break;
            }
        }
        return termTime[curTimeArr];
    }

    public String getCurrentSTT(String now) {
        for(int i=0; i<schoolArrLength; i++) {
            if(now.compareTo(schoolTime[i]) < 0){
                curTimeArr = i;
                break;
            }
        }
        return schoolTime[curTimeArr];
    }

}
