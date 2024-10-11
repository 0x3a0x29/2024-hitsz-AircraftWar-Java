package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.data.ScoreDaoImpl;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class EasyGame extends Game{
    public EasyGame(boolean music) throws IOException {
        super(music);
        this.difficulty=0;
        ScoreDaoImpl scoreDaoImpl = new ScoreDaoImpl(0);
        scoreDaoImpl.doRead();
        this.mobMaxBorder = 0.6;
        this.eliteMaxBorder = 0.9;
        this.superEliteMaxBorder = 1;
        this.backGround = ImageManager.BACKGROUND_IMAGE_1;
        this.enemyMaxNumber = 5;
    }
    @Override
    protected void difficultyBuff() {
        return;
    }
    protected void enemyGenerate(List<AbstractEnemy> enemyAircrafts) {
        double random_num = Math.random();
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (random_num < this.mobMaxBorder){
                enemyAircrafts.add(mobEnemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                        0,
                        10,
                        30,
                        30,
                        1
                ));
            }
            else if (random_num < this.eliteMaxBorder){
                enemyAircrafts.add(eliteEnemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                        0,
                        10,
                        30,
                        30,
                        1
                    ));
                }
            else if (random_num < this.superEliteMaxBorder) {
                double random_X =10 * Math.random()-5;
                enemyAircrafts.add(superEliteEnemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                        (int)random_X,
                        5,
                        60,
                        30,
                        1
                ));
            }
        }
    }
}
