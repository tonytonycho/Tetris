package zzy.rock.control;

import zzy.rock.constant.RockGameConstant;
import zzy.rock.manager.RockFactory;
import zzy.rock.manager.RockManager;
import zzy.rock.model.RockObject;
import zzy.rock.view.RockGameSurfaceView;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Message;
import android.view.SurfaceHolder;

public class RockGameRunnable implements Runnable{
	

	private SurfaceHolder surfaceholder;
	private RockGameSurfaceView rockgamesurfaceview;
	private boolean threadflag;
	private boolean gameflag;
	
	public RockManager rockmgr = null;
	public RockObject rockobj = null;

	
	public RockGameRunnable(SurfaceHolder surfaceholder,RockGameSurfaceView rockgamesurfaceview) {
		   this.surfaceholder = surfaceholder;
		   this.rockgamesurfaceview = rockgamesurfaceview;
		   rockmgr = new RockManager();
	}

	public boolean isGameflag() {
		return gameflag;
	}

	public void setGameflag(boolean gameflag) {
		this.gameflag = gameflag;
	}
	
	public boolean isThreadflag() {
		return threadflag;
	}
	public void setThreadflag(boolean threadflag) {
		this.threadflag = threadflag;
	}

	public void setRockObject( RockObject rockobj ){
		this.rockobj = rockobj;
	}
	
	// button opreation event
	// left right down change
	public void btnTouchEvent( int event ){
		rockmgr.actionEvent(event);
	}
	
	@Override
	public void run() {
		Canvas canvas = null;
		rockobj =  rockmgr.initRockObj();
		RockFactory rockfactory = new RockFactory();
		while(gameflag){
			while( threadflag ){				
				if( RockGameConstant.isgameover )
				{
					isGameOver();
					return;
				}
				
				rockmgr.actionEvent(RockGameConstant.isdownmove);
				rockobj = rockmgr.getRockobject();
				
				if( RockGameConstant.isnewstart )
				{
					rockobj = rockmgr.initRockObj();
					rockfactory.rockRandom();
				}

				canvas = surfaceholder.lockCanvas(new Rect(RockGameConstant.gamearealeft, RockGameConstant.gameareatop, RockGameConstant.screenwidth-RockGameConstant.cellsize, RockGameConstant.gameareabottom));
				synchronized(surfaceholder){
					rockgamesurfaceview.doDraw(canvas,rockobj);
				}
				if( canvas != null ){
					surfaceholder.unlockCanvasAndPost(canvas);
				}
							
				try {
					Thread.sleep(RockGameConstant.threadtime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void isGameOver(){
		setGameflag(false);
		setThreadflag(false);
		
		RockGameHintRunnable rockhintrunnable = new RockGameHintRunnable(surfaceholder, rockgamesurfaceview);
		rockhintrunnable.setFlag(false);
		RockGameConstant.isnewstart = false;
		
		Message msg = new Message();
		msg.what = RockGameConstant.GAME_OVER_DIALOG;
		rockgamesurfaceview.rockgamemainactivity.handler.sendMessage(msg);
	}
	
}
