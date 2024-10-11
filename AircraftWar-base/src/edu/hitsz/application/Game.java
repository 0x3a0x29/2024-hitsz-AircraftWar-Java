package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.enemyfactory.*;
import edu.hitsz.aircraft.shoot.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.data.ScoreDaoImpl;
import edu.hitsz.data.ScoreData;
import edu.hitsz.music.MusicManager;
import edu.hitsz.music.MusicThread;
import edu.hitsz.prop.propfactory.BulletPlusFactory;
import edu.hitsz.prop.props.Bomb;
import edu.hitsz.prop.props.Item;
import edu.hitsz.prop.propfactory.BloodFactory;
import edu.hitsz.prop.propfactory.BombFactory;
import edu.hitsz.prop.propfactory.BulletFactory;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.Random;

import static edu.hitsz.music.MusicManager.BOMB_EXPLOSION_MUSIC;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {
    BufferedImage backGround;
    private int backGroundTop = 0;
    int difficulty;
    private ScoreDaoImpl scoreDaoImpl;
    protected boolean music;
    /**
     * Scheduled 线程池，用于任务调度
     */
    ScheduledExecutorService executorService;
    /**
     * 时间间隔(ms)，控制刷新频率
     */
    protected int timeInterval = 40;

    protected int amountOfBoss = 0;
    protected boolean bossFlag = false;
    protected Random df = new Random();
    protected HeroAircraft heroAircraft;
    protected List<AbstractEnemy> enemyAircrafts;
    protected List<BaseBullet> heroBullets;
    protected List<BaseBullet> enemyBullets;
    protected List<Item> props;
    double mobMaxBorder = 0.6;
    double eliteMaxBorder = 0.9;
    double superEliteMaxBorder = 1;
    /**
     * 屏幕中出现的敌机最大数量
     */
    protected int enemyMaxNumber = 5;
    /**
     * 当前得分
     */
    protected int score = 0;
    /**
     * 当前时刻
     */
    protected int time = 0;
    private ScoreData scoreData;
    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;
    private boolean gameOverFlag = false;
    protected MobEnemyFactory mobEnemyFactory = new MobEnemyFactory();
    protected EliteEnemyFactory eliteEnemyFactory = new EliteEnemyFactory();
    protected SuperEliteEnemyFactory superEliteEnemyFactory = new SuperEliteEnemyFactory();
    protected BossEnemyFactory bossEnemyFactory = new BossEnemyFactory();
    private BloodFactory bloodFactory = new BloodFactory();
    private BombFactory bombFactory = new BombFactory();
    private BulletFactory bulletFactory = new BulletFactory();
    private BulletPlusFactory bulletPlusFactory = new BulletPlusFactory();
    protected MusicThread bgmThread = MusicManager.musicThread(MusicManager.BACKGROUND_MUSIC);
    protected MusicThread bossBgmThread = MusicManager.musicThread(MusicManager.BACKGROUND_BOSS_MUSIC);
    public Game(boolean music){
        heroAircraft = HeroAircraft.getInstance();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();
        this.music = music;
        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());
        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);
    }
    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {
            time += timeInterval;
            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                // 新敌机产生
                enemyGenerate(enemyAircrafts);
                // 飞机射出子弹
                shootAction();
            }
            // 子弹移动
            bulletsMoveAction();
            //道具移动
            propsMoveAction();
            // 飞机移动
            aircraftsMoveAction();
            // 撞击检测
            crashCheckAction();
            // 后处理
            postProcessAction();
            //每个时刻重绘界面
            repaint();
            // 游戏结束检查英雄机是否存活
            difficultyBuff();
            // 难度增益设置
            try {
                bgmPlay();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }//播放音乐
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");
                if (music){MusicManager.musicThread(MusicManager.GAME_OVER_MUSIC).start();}
                RankDisplay rankDisplay = null;
                try {
                    rankDisplay = new RankDisplay(difficulty,score);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Main.cardPanel.add(rankDisplay.getRankDisplay());
                Main.cardLayout.last(Main.cardPanel);
                bgmThread.setRunning(false);
                bossBgmThread.setRunning(true);
            }
        };
        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
        //new MusicThread("src/videos/bgm.wav").start();
    }

    //***********************
    //      Action 各部分
    //***********************
    private void bgmPlay() throws InterruptedException {
        if (music) {
            if (bossFlag){
                bgmThread.setRunning(false);
                bossBgmThread.setRunning(true);
            }else {
                bgmThread.setRunning(true);
                bossBgmThread.setRunning(false);
            }
            if (bgmThread.getState() == Thread.State.NEW) {
                bgmThread.start();
            } else if (bgmThread.getState() == Thread.State.TERMINATED) {
                bgmThread = MusicManager.musicThread(MusicManager.BACKGROUND_MUSIC);
            }
            if (bossBgmThread.getState() == Thread.State.NEW) {
                bossBgmThread.start();
            } else if (bossBgmThread.getState() == Thread.State.TERMINATED) {
                bossBgmThread = MusicManager.musicThread(MusicManager.BACKGROUND_BOSS_MUSIC);
            }
        }
    }
    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        //敌机射击
        for (AbstractEnemy enemy : enemyAircrafts) {
            enemyBullets.addAll(enemy.shoot());
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractEnemy enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (Item prop : props) {
            prop.forward();
        }
    }
    protected abstract void enemyGenerate(List<AbstractEnemy> enemyAircrafts);
    private void GenerateItem(AbstractEnemy enemyAircraft,double probability,int Item){
        int x = enemyAircraft.getLocationX();
        int y = enemyAircraft.getLocationY();
        double bloodMaxBorder = 2 * probability / 7;
        double bombMaxBorder = 4 * probability / 7;
        double bulletMaxBorder = 6 * probability / 7;
        double bulletPlusMaxBorder = probability;
        for (int i=0;i<Item;i++) {
            double random_num = Math.random();
            if (random_num < bloodMaxBorder) {
                props.add(bloodFactory.createProp(
                        x + (i*2 - Item + 1)*20,
                        y,
                        0,
                        2,
                        20
                ));
            } else if (random_num < bombMaxBorder) {
                props.add(bombFactory.createProp(
                        x + (i*2 - Item + 1)*20,
                        y,
                        0,
                        2,
                        30
                ));
            } else if (random_num < bulletMaxBorder) {
                props.add(bulletFactory.createProp(
                        x + (i*2 - Item + 1)*20,
                        y,
                        0,
                        2,
                        10
                ));
            } else if (random_num < bulletPlusMaxBorder) {
                props.add(bulletPlusFactory.createProp(
                        x + (i*2 - Item + 1)*20,
                        y,
                        0,
                        2,
                        10
                ));
            }
        }
    }
    private void crashCheckAction() {
        //敌机子弹攻击英雄
        for(BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()){
                continue;
            }
            if (heroAircraft.notValid()){
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractEnemy enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (music) {MusicManager.musicThread(MusicManager.BULLET_HIT_MUSIC).start();}
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }
        for (AbstractEnemy enemyAircraft : enemyAircrafts) {
            if (enemyAircraft.notValid()) {//判断敌机是否损毁,包含了Bomb和HeroBullet作用
                //获得分数，产生道具补给
                if (enemyAircraft instanceof MobEnemy){
                    score += 10;
                }
                else if (enemyAircraft instanceof EliteEnemy){
                    score += 30;
                    GenerateItem(enemyAircraft,0.6,1);
                }
                else if (enemyAircraft instanceof SuperEliteEnemy){
                    score += 80;
                    GenerateItem(enemyAircraft,0.9,1);
                }
                else if (enemyAircraft instanceof BossEnemy){
                    score += 200;
                    GenerateItem(enemyAircraft,1,df.nextInt(3)+1);
                    amountOfBoss +=1;
                    bossFlag = false;
                }
            }
        }
        for (Item prop : props){
            if (prop.crash(heroAircraft)||heroAircraft.crash(prop)){
                if(music) {
                    MusicManager.musicThread(MusicManager.GET_SUPPLY_MUSIC).start();}
                if (prop instanceof Bomb){
                    if (music){MusicManager.musicThread(BOMB_EXPLOSION_MUSIC).start();}
                    for (BaseBullet enemyBullet:enemyBullets){
                        prop.attachObserver(enemyBullet);
                    }
                    for (AbstractEnemy enemy:enemyAircrafts){
                        prop.attachObserver(enemy);
                    }
                }
                prop.itemActive(heroAircraft);
                prop.vanish();
            }
        }
        //我方获得道具，道具生效
    }
    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(this.backGround, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(this.backGround, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }
        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g,props);
        paintImageWithPositionRevised(g, enemyAircrafts);
        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);
        //绘制得分和生命值
        paintScoreAndLife(g);
    }
    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }
        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }
    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(0x501818));
        g.setFont(new Font("SansSerif", Font.BOLD, 18));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 18;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
        y = y + 18;
        String difficulty_name;
        switch (difficulty){
            case 0:
                difficulty_name = "EASY";
                break;
            case 1:
                difficulty_name = "NORMAL";
                break;
            default:
                difficulty_name = "HARD";
                break;
        }
        g.drawString("DIFFICULTY:" + difficulty_name, x, y);
    }
    protected abstract void difficultyBuff();
}