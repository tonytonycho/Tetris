package zzy.rock.view;

import java.util.HashMap;

import zzy.rock.R;
import zzy.rock.constant.RockGameConstant;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

public class RockGameMainActivity extends Activity implements OnTouchListener{

	private RockGameSurfaceView rocksurface = null;
//	private ImageButton leftbtn = null;
//	private ImageButton changebtn = null;
//	private ImageButton rightbtn = null;
//	private ImageButton downbtn = null;
	
	private ImageView menubackground = null;
	private ImageButton startgame = null;
	
	public RectF dstleftbtn = null;
	public RectF dstchangebtn = null;
	public RectF dstrightbtn = null;
	public RectF dstdownbtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) ;
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initConstant();
		initSound();
		initMenu();
	}
	
	private void initMenu(){
		setContentView(R.layout.activity_welc);
		menubackground = (ImageView)findViewById(R.id.menubackground);
		menubackground.setImageDrawable(getResources().getDrawable(R.drawable.menubackground));
		
		startgame = (ImageButton)findViewById(R.id.startgame);
		startgame.setX(90f);
		startgame.setY(200f);
		startgame.setImageResource(R.drawable.startgame);
		startgame.setOnTouchListener(this);
	}
	
	private void initSound(){
		RockGameConstant.sound = new SoundPool(3, AudioManager.STREAM_MUSIC, 0) ;	//初始化soundPool
		AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE) ;	//初始化AudioManager
		setVolumeControlStream(AudioManager.STREAM_MUSIC) ;	//设置�?���?��量为手机�?��体音�?
		RockGameConstant.volumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);//设置音量的大�?
		RockGameConstant.soundmap = new HashMap<String, Integer>() ;	//初始化soundPoolMap
		RockGameConstant.soundmap.put("move", RockGameConstant.sound.load(this,R.raw.move, 0)) ;
		RockGameConstant.soundmap.put("getscore", RockGameConstant.sound.load(this,R.raw.getscore, 0)) ;
		RockGameConstant.soundmap.put("down", RockGameConstant.sound.load(this,R.raw.down, 0)) ;
	}
	
	private void initGame(){
		rocksurface = new RockGameSurfaceView(this);
		//        rocksurface.setZOrderOnTop(true);
		//        rocksurface.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		//        rocksurface.setBackground(getResources().getDrawable(R.drawable.junjie));
		setContentView(rocksurface);
		//setContentView(R.layout.activity_main);
		//rocksurface = (RockGameSurfaceView)findViewById(R.id.surfaceview);
		rocksurface.setZOrderOnTop(true);
		rocksurface.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		//rocksurface.setBackground(getResources().getDrawable(R.drawable.background));
//		Animation JumpAnimation = AnimationUtils.loadAnimation(this, R.anim.menu_anim); 
		
//		leftbtn = (ImageButton)findViewById(R.id.leftbtn);
//		changebtn = (ImageButton)findViewById(R.id.changebtn);
//		rightbtn = (ImageButton)findViewById(R.id.rightbtn);
//		downbtn = (ImageButton)findViewById(R.id.downbtn);
		
//		leftbtn.setAnimation(JumpAnimation);
//		changebtn.setAnimation(JumpAnimation);
//		rightbtn.setAnimation(JumpAnimation);
//		downbtn.setAnimation(JumpAnimation);

//		leftbtn.setOnTouchListener(this);
//		changebtn.setOnTouchListener(this);
//		rightbtn.setOnTouchListener(this);
//		downbtn.setOnTouchListener(this);
//		
//		leftbtn.setLongClickable(true);
//		leftbtn.setOnLongClickListener(new btnLongClickListener());		
//		rightbtn.setLongClickable(true);
//		rightbtn.setOnLongClickListener(new btnLongClickListener());
	}
	
//	private class btnLongClickListener implements OnLongClickListener{
//
//		@Override
//		public boolean onLongClick(View v) {
//			if( v.getId() == R.id.leftbtn )
//			{
//				while( !rocksurface.toBeRock( RockGameConstant.isleftmove ) ){
//					RockGameConstant.sound.play(RockGameConstant.soundmap.get("move"), 4, 4, 0, 0, 1f);
//					RockGameConstant.leftto--;
//				}
//			}
//			else if( v.getId() == R.id.rightbtn )
//			{
//				while( !rocksurface.toBeRock( RockGameConstant.isrightmove ) ){
//					RockGameConstant.sound.play(RockGameConstant.soundmap.get("move"), 4, 4, 0, 0, 1f);
//					RockGameConstant.leftto++;
//				}
//			}
//			return false;
//		}
//		
//	}

	private void initConstant(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		RockGameConstant.screenwidth = dm.widthPixels;
		RockGameConstant.screenheigth = dm.heightPixels;
		
		RockGameConstant.cellsize = RockGameConstant.screenwidth/17;
		
		RockGameConstant.gamearealeft = RockGameConstant.cellsize;
		RockGameConstant.gameareatop = RockGameConstant.cellsize;
		RockGameConstant.gamearearight = RockGameConstant.gamearealeft + RockGameConstant.cellsize*RockGameConstant.COLS;
		RockGameConstant.gameareabottom = RockGameConstant.gameareatop + RockGameConstant.cellsize*RockGameConstant.ROWS;
		
		RockGameConstant.hintarealeft = RockGameConstant.gamearearight + RockGameConstant.cellsize;
		RockGameConstant.hintareatop = RockGameConstant.gameareatop + RockGameConstant.cellsize*3;
		RockGameConstant.hintarearight = RockGameConstant.hintarealeft + RockGameConstant.cellsize*4;
		RockGameConstant.hintareabottom = RockGameConstant.hintareatop + RockGameConstant.cellsize*4;
		
		dstleftbtn = new RectF(RockGameConstant.cellsize, RockGameConstant.gameareabottom + 2*RockGameConstant.cellsize , 4*RockGameConstant.cellsize, RockGameConstant.gameareabottom+5*RockGameConstant.cellsize);
		dstchangebtn = new RectF(5*RockGameConstant.cellsize, RockGameConstant.gameareabottom + 2*RockGameConstant.cellsize , 8*RockGameConstant.cellsize, RockGameConstant.gameareabottom+5*RockGameConstant.cellsize);
		dstrightbtn = new RectF(9*RockGameConstant.cellsize, RockGameConstant.gameareabottom + 2*RockGameConstant.cellsize , 12*RockGameConstant.cellsize, RockGameConstant.gameareabottom+5*RockGameConstant.cellsize);
		dstdownbtn = new RectF(13*RockGameConstant.cellsize, RockGameConstant.gameareabottom + 2*RockGameConstant.cellsize , 16*RockGameConstant.cellsize, RockGameConstant.gameareabottom+5*RockGameConstant.cellsize);
	}
	
	
	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler(){

		@SuppressWarnings("deprecation")
		@Override
		public void handleMessage(Message msg) {
			switch( msg.what ){
				case RockGameConstant.GAME_OVER_DIALOG:
					showDialog( RockGameConstant.GAME_OVER_DIALOG );
					break;
				
				case RockGameConstant.KEY_BACK:
					showDialog(RockGameConstant.KEY_BACK);
					break;
					
				default:
					break;
			}
		}
	};

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		Builder builder = new AlertDialog.Builder(this);
		switch( id ){
			case RockGameConstant.GAME_OVER_DIALOG:
				builder.setTitle("GAME OVER !!");
				builder.setCancelable(false);
				builder.setPositiveButton("OK", new OnClickListener(){
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						rocksurface = null;
						initGame();
					}
				});
				break;
				
			case RockGameConstant.KEY_BACK:
				builder.setTitle("EXIT!!");
				builder.setMessage("退出游戏吗？");
				builder.setCancelable(false);
				builder.setPositiveButton("OK", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();
						intent.setClass(RockGameMainActivity.this, RockGameMenuActivity.class);
						startActivity(intent);
					}
				});
				builder.setNegativeButton("CANCEL", new OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						rocksurface.rockrannable.setThreadflag(true);
					}
				});
				break;
				
			default:
				break;
		}		
		dialog = builder.create();
		return dialog;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x = (int)event.getX();
		int y = (int)event.getY();
		if( ( event.getAction() == MotionEvent.ACTION_DOWN ) && 
				( !RockGameConstant.isgameover ) ){
			if(dstleftbtn.contains(x, y)){
				touchLeftBtn();
			}
			else if(dstchangebtn.contains(x, y))
			{
				touchChangeBtn();
			}else if(dstrightbtn.contains(x, y))
			{
				touchRightBtn();
			}else if(dstdownbtn.contains(x, y))			{
				touchDownBtn();
			}
			else
			{
				
			}
//		}else if( event.getAction() == MotionEvent.ACTION_MOVE )
//		{
//			if( rocksurface.dstleftbtn.contains(x, y) )
//			{
//				while( !rocksurface.toBeRock( RockGameConstant.isleftmove ) ){
//					RockGameConstant.sound.play(RockGameConstant.soundmap.get("move"), 4, 4, 0, 0, 1f);
//					RockGameConstant.leftto--;
//				}
//			}
//			else if( rocksurface.dstrightbtn.contains(x, y) )
//			{
//				while( !rocksurface.toBeRock( RockGameConstant.isrightmove ) ){
//					RockGameConstant.sound.play(RockGameConstant.soundmap.get("move"), 4, 4, 0, 0, 1f);
//					RockGameConstant.leftto++;
//				}
//			}
//			else
//			{
//				
//			}
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent me) {
		if(me.getAction() == MotionEvent.ACTION_DOWN){
			if( v.getId() == R.id.startgame )
			{
				initGame();
			}
		}
		return true;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if( keyCode == event.KEYCODE_BACK )
		{
			Message msg = new Message();
			msg.what = RockGameConstant.KEY_BACK;
			handler.sendMessage(msg);
			rocksurface.rockrannable.setThreadflag(false);
			return true;
		}
		return true;
	}
	
	private void touchLeftBtn(){
		RockGameConstant.sound.play(RockGameConstant.soundmap.get("move"), RockGameConstant.volumn, RockGameConstant.volumn, 0, 0, 1f);
		rocksurface.sendEvent(RockGameConstant.isleftmove);
	}
	
	private void touchChangeBtn(){
		RockGameConstant.sound.play(RockGameConstant.soundmap.get("move"), RockGameConstant.volumn, RockGameConstant.volumn, 0, 0, 1f);
		rocksurface.sendEvent(RockGameConstant.ischangemove);
	}
	
	private void touchRightBtn(){
		RockGameConstant.sound.play(RockGameConstant.soundmap.get("move"), RockGameConstant.volumn, RockGameConstant.volumn, 0, 0, 1f);
		rocksurface.sendEvent(RockGameConstant.isrightmove);
	}
	
	private void touchDownBtn(){
		RockGameConstant.sound.play(RockGameConstant.soundmap.get("down"), RockGameConstant.volumn, RockGameConstant.volumn, 0, 0, 1f);
		rocksurface.sendEvent(RockGameConstant.isdowntorock);
	}
	
}
