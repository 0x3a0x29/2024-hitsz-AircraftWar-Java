package edu.hitsz.application;

import edu.hitsz.data.ScoreDaoImpl;
import edu.hitsz.data.ScoreData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

public class RankDisplay {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JButton deleteButton;
    private JLabel headerLabel;
    private JScrollPane tableScrollPanel;
    private JTable scoreTable;
    private JLabel difficultyLabel;
    private ScoreDaoImpl scoreDaoImpl;
    public RankDisplay(int difficulty,int score) throws IOException {
        String name = JOptionPane.showInputDialog(
                mainPanel,"Please enter your name:\n","Enter name",JOptionPane.PLAIN_MESSAGE);
        scoreDaoImpl = new ScoreDaoImpl(difficulty);
        scoreDaoImpl.doRead();
        ScoreData scoreData;
        if (name.equals("")){
            scoreData = new ScoreData("Anonymous", score, new Date());
        }else{
            scoreData = new ScoreData(name, score, new Date());
        }
        scoreDaoImpl.doAdd(scoreData);
        scoreDaoImpl.doWrite();
        switch (difficulty){
            case 0:
                difficultyLabel.setText("difficulty:easy");
                break;
            case 1:
                difficultyLabel.setText("difficulty:normal");
                break;
            default:
                difficultyLabel.setText("difficulty:hard");
                break;
        }
        DefaultTableModel model=modelGenerate();
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                System.out.println(row);
                int result = JOptionPane.showConfirmDialog(deleteButton,
                        "Do you want to delete it?");
                if (JOptionPane.YES_OPTION == result && row != -1) {
                    model.removeRow(row);
                    scoreDaoImpl.doDelete(row);
                    try {
                        scoreDaoImpl.doWrite();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    scoreTable.setModel(modelGenerate());
                    tableScrollPanel.setViewportView(scoreTable);
                }
            }
        });
    }
    public JPanel getRankDisplay(){return mainPanel;}
    private DefaultTableModel modelGenerate(){
        String[] columnName = {"rank","name","score","time"};
        String[][] dataDisplay = scoreDaoImpl.getDisplayArray();
        return new DefaultTableModel(dataDisplay, columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
    }
}
