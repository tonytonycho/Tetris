package zzy.rock.view;


import zzy.rock.R;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;

import android.widget.ImageButton;
import android.widget.ImageView;

public class RockGameMenuActivity extends Activity implements OnTouchListener{

	private ImageView menubackground = null;
	private ImageButton startgame = null;
//	private ImageButton moregame = null;
//	private ImageButton option = null;
//	private ImageButton aboutgame = null;
//	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE) ;
		
		setContentView(R.layout.activity_welc);
		menubackground = (ImageView)findViewById(R.id.menubackground);
		menubackground.setImageDrawable(getResources().getDrawable(R.drawable.menubackground));
		
		startgame = (ImageButton)findViewById(R.id.startgame);
//		moregame = (ImageButton)findViewById(R.id.moregame);
//		option = (ImageButton)findViewById(R.id.option);
//		aboutgame = (ImageButton)findViewById(R.id.aboutgame);
		
		startgame.setX(90f);
		startgame.setY(200f);
		//startgame.setImageDrawable(getResources().getDrawable(R.drawable.startgame));
		startgame.setImageResource(R.drawable.startgame);
		startgame.setOnTouchListener(this);
		
//		moregame.setX(90f);
//		moregame.setY(270f);
//		moregame.setImageDrawable(getResources().getDrawable(R.drawable.more));		
//		//startgame.setAlpha(TRIM_MEMORY_BACKGROUND);
//		
//		moregame.setX(90f);
//		moregame.setY(340f);
//		moregame.setImageDrawable(getResources().getDrawable(R.drawable.option));
//		//startgame.setAlpha(TRIM_MEMORY_BACKGROUND);
//		
//		moregame.setX(90f);
//		moregame.setY(410f);
//		moregame.setImageDrawable(getResources().getDrawable(R.drawable.exit2));
		//startgame.setAlpha(TRIM_MEMORY_BACKGROUND);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if( v.getId() == R.id.startgame )
		{
			
			Intent intent = new Intent();
			intent.setClass(RockGameMenuActivity.this, RockGameMainActivity.class);
			RockGameMenuActivity.this.startActivity(intent);
		}
		return false;
	}
}
