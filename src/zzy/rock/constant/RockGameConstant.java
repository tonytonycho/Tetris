package zzy.rock.constant;

import java.util.Map;

import android.media.SoundPool;

public class RockGameConstant {
	
	public static boolean isgameover;
	
	public static final int GAME_OVER_DIALOG = 0;
	public static final int KEY_BACK = 1;
	
	public static int screenwidth;
	public static int screenheigth;
	
	public static int gamearealeft;
	public static int gameareatop;
	public static int gamearearight;
	public static int gameareabottom;
	
	public static int hintarealeft;
	public static int hintareatop;
	public static int hintarearight;
	public static int hintareabottom;
	
	public static int cellsize;

	public static final int ROWS = 21;
	public static final int COLS = 10;
	
//	public static int leftto;
//	public static int topto;
	
	public static boolean isnewstart;
	
	public static int threadtime;
	public static int nomaltime = 600;
	
	public static final int isleftmove = 0;
	public static final int isrightmove = 1;
	public static final int ischangemove = 2;
	public static final int isdownmove = 3;
	public static final int isdowntorock = 4;
	
	public static int core;
	
	public static SoundPool sound = null;
	public static Map<String,Integer> soundmap = null;
	public static int volumn;
}
