package edu.hitsz.music;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.prop.props.Blood;
import edu.hitsz.prop.props.Bomb;
import edu.hitsz.prop.props.Bullet;
import edu.hitsz.prop.props.BulletPlus;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    public static String BACKGROUND_MUSIC="src/videos/bgm.wav";
    public static String BACKGROUND_BOSS_MUSIC="src/videos/bgm_boss.wav";
    public static String BOMB_EXPLOSION_MUSIC="src/videos/bomb_explosion.wav";
    public static String BULLET_MUSIC="src/videos/bullet.wav";
    public static String BULLET_HIT_MUSIC="src/videos/bullet_hit.wav";
    public static String GAME_OVER_MUSIC="src/videos/game_over.wav";
    public static String GET_SUPPLY_MUSIC="src/videos/get_supply.wav";
    public static MusicThread musicThread(String filename){
        return new MusicThread(filename);
    }
}
