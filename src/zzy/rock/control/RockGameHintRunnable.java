package zzy.rock.control;

import zzy.rock.constant.RockGameConstant;
import zzy.rock.view.RockGameSurfaceView;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;

public class RockGameHintRunnable implements Runnable{

	private SurfaceHolder surfaceholder = null;
	private RockGameSurfaceView rocksurfaceview = null;
	private boolean flag;
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public RockGameHintRunnable(SurfaceHolder surfaceholder,RockGameSurfaceView rocksurfaceview) {
		this.surfaceholder = surfaceholder;
		this.rocksurfaceview = rocksurfaceview;
	}

	@Override
	public void run() {
		Canvas canvas = null;
		//RockFactory rockfactory = new RockFactory();
		while( flag ){
			while( RockGameConstant.isnewstart )
			{
				canvas = surfaceholder.lockCanvas(new Rect((int)RockGameConstant.hintarealeft,(int)RockGameConstant.hintareatop,(int)RockGameConstant.hintarearight,(int)RockGameConstant.hintareabottom));
				synchronized (surfaceholder) {
					rocksurfaceview.drawHintRock(canvas);
				}
				if( canvas != null )
				{
					surfaceholder.unlockCanvasAndPost(canvas);
				}
				
//				try {
//					Thread.sleep(5);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}finally{
//
//				}
			}
		}
	}

}
