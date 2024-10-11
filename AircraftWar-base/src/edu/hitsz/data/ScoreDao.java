package edu.hitsz.data;

import java.io.IOException;
import java.util.List;

public interface ScoreDao {
    public void doAdd(ScoreData scoreData);
    public void doWrite() throws IOException;
    public void doRead() throws IOException;
}
