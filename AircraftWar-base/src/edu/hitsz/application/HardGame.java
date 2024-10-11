package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractEnemy;
import edu.hitsz.data.ScoreDaoImpl;

import java.io.IOException;
import java.util.List;

public class HardGame extends Game{
    private int enemyBuff = 0;
    private int bossScore = 1000;
    public HardGame(boolean music) throws IOException {
        super(music);
        this.difficulty=2;
        ScoreDaoImpl scoreDaoImpl = new ScoreDaoImpl(2);
        scoreDaoImpl.doRead();
        this.mobMaxBorder = 0.6;
        this.eliteMaxBorder = 0.9;
        this.superEliteMaxBorder = 1;
        this.backGround = ImageManager.BACKGROUND_IMAGE_3;
        this.enemyMaxNumber = 12;
    }
    @Override
    protected void difficultyBuff() {
        if (time%(timeInterval*200)==0){
            this.enemyBuff++;
            if (this.eliteMaxBorder>0){this.eliteMaxBorder-=0.004;}
            if (this.mobMaxBorder>0){this.mobMaxBorder-=0.006;}
            System.out.println("As time goes,the enemy will be stronger");
            System.out.print("Hard difficulty:the eliteProbability is:");
            System.out.println("mob:"+String.valueOf(mobMaxBorder)+",elite:"+
                    String.valueOf(eliteMaxBorder-mobMaxBorder)+",superElite:"+String.valueOf(1-eliteMaxBorder));
            System.out.println("The enemy became stronger,now the enemyBuff rank:"+enemyBuff);
        }
    }
    protected void enemyGenerate(List<AbstractEnemy> enemyAircrafts) {
        double random_num = Math.random();
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if ((amountOfBoss+1)*bossScore>score){
                if (random_num < this.mobMaxBorder){
                    enemyAircrafts.add(mobEnemyFactory.createEnemy(
                            (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                            0,
                            10,
                            30+ enemyBuff,
                            30+enemyBuff*2,
                            1
                    ));
                }
                else if (random_num < this.eliteMaxBorder){
                    enemyAircrafts.add(eliteEnemyFactory.createEnemy(
                            (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                            (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                            0,
                            10,
                            30+enemyBuff*2,
                            30+enemyBuff*2,
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
                            60+enemyBuff*3,
                            30+enemyBuff*2,
                            1
                    ));
                }
            }
            else if (!bossFlag){
                System.out.println("Boss Time!The mob will stop generating,please defend the boss!");
                System.out.println("Boss's level is Lv."+String.valueOf(amountOfBoss));
                double random_X =10 * df.nextInt(2)-5;
                enemyAircrafts.add(bossEnemyFactory.createEnemy(
                        (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                        (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                        (int)random_X,
                        0,
                        600+amountOfBoss*150,
                        30+amountOfBoss*10,
                        1
                ));
                bossFlag = true;
            }
        }
    }
}
