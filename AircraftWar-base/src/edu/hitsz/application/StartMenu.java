package edu.hitsz.application;

import edu.hitsz.music.MusicThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class StartMenu {
    private JPanel menuPanel;
    private JPanel easyPanel;
    private JPanel normalPanel;
    private JPanel hardPanel;
    private JPanel musicPanel;
    private JButton easyButton;
    private JButton normalButton;
    private JButton hardButton;
    private JCheckBox musicBox;
    private int difficulty = 0;
    private boolean music = false;
    public StartMenu() {
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 0;
                startGame();
            }
        });
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 1;
                startGame();
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty = 2;
                startGame();
            }
        });
        musicBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                music = musicBox.isSelected();
            }
        });
    }
    public JPanel getMenuPanel(){return menuPanel;}
    private void startGame(){
        Game game = null;
        try {
            switch (difficulty){
                case 0:
                    game = new EasyGame(music);
                    break;
                case 1:
                    game = new NormalGame(music);
                    break;
                default:
                    game = new HardGame(music);
                    break;
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        game.action();
        Main.cardPanel.add(game);
        Main.cardLayout.last(Main.cardPanel);
    }
}

