package edu.hitsz.data;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreDaoImpl implements ScoreDao{
    private List<ScoreData> scoreDatas;
    private File file;
    public ScoreDaoImpl(int difficulty){
        scoreDatas=new ArrayList<ScoreData>();
        switch(difficulty){
            case 0:
                file=new File("src/data/scoreEasyRank.txt");
                break;
            case 1:
                file=new File("src/data/scoreNormalRank.txt");
                break;
            default:
                file=new File("src/data/scoreHardRank.txt");
                break;
        }

    }
    public void doAdd(ScoreData scoreData) {
        this.scoreDatas.add(scoreData);
        Collections.sort(scoreDatas, (a, b)->{return b.getScore() - a.getScore();});
        int i=1;
        for (ScoreData data:scoreDatas){
            data.setRank(i);
            i++;
        }
    }
    public void doDelete(int num) {
        this.scoreDatas.remove(num);
        int i=1;
        for (ScoreData data:scoreDatas){
            data.setRank(i);
            i++;
        }
    }
    public void doRead() throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(file));
        String line=null;
        ScoreData scoreData;
        while((line = br.readLine())!=null){
            scoreData=new ScoreData(line);
            this.scoreDatas.add(scoreData);
        }
    }
    public void doWrite() throws IOException {
        FileOutputStream fop=new FileOutputStream(file);
        OutputStreamWriter writer=new OutputStreamWriter(fop,"UTF-8");
        for(ScoreData i:scoreDatas){
            writer.append(i.toString());
            writer.append("\n");
        }
        writer.close();
        fop.close();
    }

    public String[][] getDisplayArray(){
        String[][] array = new String[scoreDatas.size()][4];
        for(int i = 0; i < scoreDatas.size();i++){
            array[i] = scoreDatas.get(i).toStringArray();
        }
        return array;
    }
}
