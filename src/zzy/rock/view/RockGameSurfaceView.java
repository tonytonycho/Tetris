package zzy.rock.view;

import java.io.InputStream;
import java.util.Random;

import zzy.rock.R;
import zzy.rock.constant.RockGameConstant;
import zzy.rock.control.RockGameHintRunnable;
import zzy.rock.control.RockGameRunnable;
import zzy.rock.model.RockGameModel;
import zzy.rock.model.RockObject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class RockGameSurfaceView extends SurfaceView implements Callback{

	public RockGameMainActivity rockgamemainactivity = null;
	private Paint paint = null;
	private Paint paintrect = null;
	private Paint mpaint = null;
	private Paint textpaint = null;

	private SurfaceHolder surfaceholder = null;
	private RockObject rockobj = null;
	private Thread gamethread = null;

	public RockGameRunnable rockrannable = null;
	public RockGameHintRunnable rockhintrunnable = null;
	
	public RockGameSurfaceView(Context context) {
		super(context);
		this.rockgamemainactivity = (RockGameMainActivity)context;
		surfaceholder = getHolder();
		surfaceholder.addCallback(this);
		
		setKeepScreenOn(true);

		init();
	}
	
	public RockGameSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.rockgamemainactivity = (RockGameMainActivity)context;
		surfaceholder = getHolder();
		surfaceholder.addCallback(this);
		
		setKeepScreenOn(true);

		init();
	}

	private void init(){
		
		RockGameConstant.isgameover = false;
		
		mpaint = new Paint();
		
		textpaint = new Paint();
		textpaint.setAntiAlias(true);
		textpaint.setColor(Color.BLACK);
		textpaint.setStrokeWidth(10.0f);

		paint = new Paint();		
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1.0f);
			
		paintrect = new Paint();
		paintrect.setAntiAlias(true);
		paintrect.setColor(Color.GRAY);
		paintrect.setStyle(Paint.Style.STROKE);
		paintrect.setStrokeWidth(0.1f);
		
		RockGameConstant.core = 0;
		
		RockGameConstant.threadtime = RockGameConstant.nomaltime;
		
		RockGameModel.style = new Random().nextInt(RockGameModel.shape.length);
		RockGameModel.stat = new Random().nextInt(RockGameModel.shape[RockGameModel.style].length/4);
		
		RockGameModel.nextstyle = new Random().nextInt(RockGameModel.shape.length);
		RockGameModel.nextstat = new Random().nextInt(RockGameModel.shape[RockGameModel.nextstyle].length/4);
		
		RockGameModel.map = new int[RockGameConstant.ROWS][RockGameConstant.COLS];
	}

	public void sendEvent( int event ){
		rockrannable.btnTouchEvent(event);
	}
	
	public void doDraw(Canvas canvas, RockObject rockobj){

		if( ( canvas != null ) &&
				( rockobj != null) ){
			this.rockobj = rockobj;
			//Bitmap bitmap = getBitmap(R.drawable.background);
			canvas.drawColor(Color.WHITE);
			
			//canvas.drawBitmap(bitmap, new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()), srcdst, new Paint());

			// drawButton( canvas );	
			drawHintRock( canvas );
			drawRock( canvas );
			drawToBeRock( canvas );
			canvas.drawText("SCORE : "+RockGameConstant.core, RockGameConstant.gamearearight+RockGameConstant.cellsize, RockGameConstant.hintareabottom+4*RockGameConstant.cellsize, textpaint);
			canvas.drawText("LEVEL : "+RockGameConstant.core, RockGameConstant.gamearearight+RockGameConstant.cellsize, RockGameConstant.hintareabottom+5*RockGameConstant.cellsize, textpaint);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		System.out.println("surfaceChanged------->"+arg0);
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		System.out.println("surfaceCreated------->"+arg0);
//		rockhintrunnable = new RockGameHintRunnable(surfaceholder, this);
//		rockhintrunnable.setFlag(true);
//		RockGameConstant.isnewstart = true;
//		new Thread(rockhintrunnable).start();
		
		rockrannable = new RockGameRunnable(surfaceholder,this);
		rockrannable.setGameflag(true);
		rockrannable.setThreadflag(true);
		gamethread = new Thread(rockrannable);
		gamethread.start();
		
		Bitmap bitmap = getBitmap(R.drawable.menubackground);
		Canvas canvas = surfaceholder.lockCanvas();
		synchronized (surfaceholder) {
			canvas.drawBitmap(bitmap, new Rect(0,0,bitmap.getWidth(),bitmap.getHeight()), new Rect(0,0,RockGameConstant.screenwidth,RockGameConstant.screenheigth), new Paint());
			drawButton(canvas);
			Rect srcdst = new Rect(RockGameConstant.gamearealeft-1, RockGameConstant.gameareatop-1, RockGameConstant.gamearearight+1, RockGameConstant.gameareabottom+1);
			canvas.drawRect(srcdst,paint);
			canvas.drawRect(RockGameConstant.hintarealeft-1, RockGameConstant.hintareatop-1, RockGameConstant.hintarearight+1, RockGameConstant.hintareabottom+1, paint);

		}
		if( canvas != null )
		{
			surfaceholder.unlockCanvasAndPost(canvas);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		//rockhintrunnable.setFlag(false);
		//RockGameConstant.isnewstart = false;
		
		rockrannable.setGameflag(false);
		rockrannable.setThreadflag(false);
		try {
			gamethread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("surfaceDestroyed------->"+arg0);
		// gamethread.destroy();
	}
	
	public void drawButton( Canvas canvas ){
		Rect src = new Rect(0,0,76,76);
		canvas.drawBitmap(getBitmap(R.drawable.left), src, rockgamemainactivity.dstleftbtn, mpaint);
		canvas.drawBitmap(getBitmap(R.drawable.change),src, rockgamemainactivity.dstchangebtn, mpaint);
		canvas.drawBitmap(getBitmap(R.drawable.right), src, rockgamemainactivity.dstrightbtn, mpaint);
		canvas.drawBitmap(getBitmap(R.drawable.down), src , rockgamemainactivity.dstdownbtn, mpaint);
	}
	
	public void drawRock( Canvas canvas ){
		
		Bitmap rockimg = getBitmap(R.drawable.greenrock);
		int rows = rockobj.getRockstat()*4+rockobj.getRocktopborder();
		int cols = rockobj.getRockleftborder();
		int rectleft = rockobj.getRectleftto();
		int recttop = rockobj.getRecttopto();
		int width = rockobj.getRockwidth();
		int height = rockobj.getRockheight();
		
		System.out.println(rockobj);
		
		for( int i = rows; i < rows + height; i ++ ){
			for( int j = cols; j < cols + width; j++){
				if( RockGameModel.shape[RockGameModel.style][i][j] == 1 )
				{
					int left = RockGameConstant.gamearealeft + (j+rectleft)*RockGameConstant.cellsize;
					int top = RockGameConstant.gameareatop + (i%4+recttop)*RockGameConstant.cellsize;
					int right = left + RockGameConstant.cellsize;
					int bottom = top + RockGameConstant.cellsize;

					canvas.drawBitmap(rockimg, new Rect(0, 0, rockimg.getWidth(),rockimg.getHeight()),new Rect(left,top,right,bottom), new Paint());
				}
			}
		}
	}
	
	public void drawHintRock( Canvas canvas ){

		canvas.drawColor(Color.WHITE);
		Bitmap rockimg = getBitmap(R.drawable.yellowrock);
		for( int i = getNextRockStat(); i < getNextRockStat() + 4; i ++ ){
			for( int j = 0; j < 4; j++){
				
				int left = RockGameConstant.hintarealeft + j*RockGameConstant.cellsize;
				int top = RockGameConstant.hintareatop + (i%4)*RockGameConstant.cellsize;
				int right = left + RockGameConstant.cellsize;
				int bottom = top + RockGameConstant.cellsize;
				canvas.drawRect(new Rect(left,top,right,bottom), paintrect);
				
				if( RockGameModel.shape[RockGameModel.nextstyle][i][j] == 1 )
				{
					canvas.drawBitmap(rockimg, new Rect(0, 0, rockimg.getWidth(),rockimg.getHeight()),new Rect(left,top,right,bottom), new Paint());
				}
			}
		}
	}
	
	public void drawToBeRock( Canvas canvas ){

		Bitmap rockimg = getBitmap(R.drawable.rock);
		for( int i = 0; i < RockGameConstant.ROWS; i ++ ){
			for( int j = 0; j < RockGameConstant.COLS; j++){
				
				int left = RockGameConstant.gamearealeft + j*RockGameConstant.cellsize;
				int top = RockGameConstant.gameareatop + i*RockGameConstant.cellsize;
				int right = left + RockGameConstant.cellsize;
				int bottom = top + RockGameConstant.cellsize;
				
				canvas.drawRect(new Rect(left,top,right,bottom), paintrect);
				if( RockGameModel.map[i][j] == 1 )
				{
					canvas.drawBitmap(rockimg, new Rect(0,0,rockimg.getWidth(),rockimg.getHeight()),new Rect(left,top,right,bottom), new Paint());

				}
			}
		}
	}
	
	private Bitmap getBitmap(int id){
		InputStream is = getResources().openRawResource(id);
		Bitmap mbt = BitmapFactory.decodeStream(is);
		return mbt;
	}
	
	private int getNextRockStat(){
		return RockGameModel.nextstat*4;
	}
}
