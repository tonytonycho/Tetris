package zzy.rock.manager;

import zzy.rock.constant.RockGameConstant;
import zzy.rock.model.RockGameModel;
import zzy.rock.model.RockObject;

public class RockManager {
	
	private RockObject rockobj = null;
	
	// init method
	public RockObject initRockObj(){
		rockobj = new RockObject();
		rockobj.setRockstyle( RockGameModel.style );
		rockobj.setRockstat( RockGameModel.stat );
		
		int statindex = getRockStatIndex(rockobj.getRockstat());
		initchangeRockStat( statindex );
		
		rockobj.setRectleftto( ( RockGameConstant.COLS - 4 )/2 );
		rockobj.setRecttopto( 0 );
		return rockobj;
	}
	
	private void initchangeRockStat( int statindex ){
		rockobj.setRockleftborder( calLeftBorderLine( statindex ) );
		rockobj.setRocktopborder( calTopBorderLine( statindex ) );
		
		rockobj.setRockwidth( 4 - ( rockobj.getRockleftborder() + calRightBorderLine( statindex ) ) );
		rockobj.setRockheight( 4 - ( rockobj.getRocktopborder() + calBottomBorderLine( statindex ) ) );
	}
	
	public RockObject getRockobject() {
		return rockobj;
	}

	/*
	 *   int 
	 *       isleftmove : beyond left line
	 *       isrightmove: beyond right line
	 *       isrock : toberock
	 * */
	public synchronized void actionEvent( int action ){
		
		switch( action )
		{
			case RockGameConstant.ischangemove:
				if( isChangeAble() )
				{
					rockobj.setRockstat( changeRockStat() );
					int statindex = getRockStatIndex(rockobj.getRockstat());
					initchangeRockStat( statindex );
				}
				break;
				
			case RockGameConstant.isleftmove:
				if( isLeftMoveable() )
				{
					rockobj.setRectleftto(rockobj.getRectleftto()-1);
				}
				break;
				
			case RockGameConstant.isrightmove:
				if( isRightMoveAble() )
				{
					rockobj.setRectleftto(rockobj.getRectleftto()+1);
				}
				break;
				
			case RockGameConstant.isdownmove:
				if( isDownMoveAble() )
				{
					rockobj.setRecttopto(rockobj.getRecttopto()+1);
					RockGameConstant.isnewstart = false;
				}
				else
				{
					int statindex = getRockStatIndex(rockobj.getRockstat());
					makeRock(statindex);
					deleteRock();
					RockGameConstant.isnewstart = true;
				}
				break;
				
			case RockGameConstant.isdowntorock:
				while( isDownMoveAble() )
				{
					rockobj.setRecttopto(rockobj.getRecttopto()+1);
				}
				break;
				
			default:
				break;
		}
		
		if( ( rockobj.getRectleftto() + rockobj.getRockleftborder() + rockobj.getRockwidth() ) >= RockGameConstant.COLS )
		{
			rockobj.setRectleftto( RockGameConstant.COLS - rockobj.getRockwidth() - rockobj.getRockleftborder() );
		}
		else if( rockobj.getRectleftto() <= 0 )
		{
			
		}
	}

	/*
	 *   int 
	 *       isleftmove : beyond left line
	 *       isrightmove: beyond right line
	 *       isdownmove : toberock
	 * */
	private boolean isChangeAble(){
		int statindex = getRockStatIndex( changeRockStat() );
		for( int i = statindex; i < statindex + 4; i ++ ){
			for( int j = 0; j < 4; j++){
				if( ( RockGameModel.shape[rockobj.getRockstyle()][i][j] == 1 ) &&
					( RockGameModel.map[rockobj.getRecttopto() + i][rockobj.getRectleftto() + j] == 1 )	)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean isLeftMoveable(){
			
		int stat = getRockStatIndex( rockobj.getRockstat() );
		for( int i = stat; i < stat + 4; i ++ ){
			for( int j = 0; j < 4; j++){
				if( RockGameModel.shape[rockobj.getRockstyle()][i][j] == 1 )
				{
					int cellleftto = rockobj.getRectleftto() + j;
					int celltopto = rockobj.getRecttopto() + i%4;
					
					if( ( cellleftto <= 0 ) ||
							( RockGameModel.map[celltopto%RockGameConstant.ROWS ][(cellleftto-1)%RockGameConstant.COLS ] == 1 ) )
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private boolean isRightMoveAble(){
		int stat = getRockStatIndex( rockobj.getRockstat() );
		for( int i = stat; i < stat + 4; i ++ ){
			for( int j = 0; j < 4; j++){
				if( RockGameModel.shape[rockobj.getRockstyle()][i][j] == 1 )
				{
					int cellleftto = rockobj.getRectleftto() + j;
					int celltopto = rockobj.getRecttopto() + i%4;
					
					if( ( cellleftto >= RockGameConstant.COLS-1 ) ||
							( RockGameModel.map[celltopto%RockGameConstant.ROWS ][(cellleftto+1)%RockGameConstant.COLS ] == 1 ) )
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private boolean isDownMoveAble(){
		int stat = getRockStatIndex( rockobj.getRockstat() );
		for( int i = stat; i < stat + 4; i ++ ){
			for( int j = 0; j < 4; j++){
				if( RockGameModel.shape[rockobj.getRockstyle()][i][j] == 1 )
				{
					int cellleftto = rockobj.getRectleftto() + j;
					int celltopto = rockobj.getRecttopto() + i%4 + 1;
									
					if( ( getBottomLine() >= RockGameConstant.gameareabottom ) ||
					 ( RockGameModel.map[celltopto%RockGameConstant.ROWS ][cellleftto%RockGameConstant.COLS ] == 1 ) )
					{
						return false;
					} 
				}
			}
		}
		return true;
	}
		
	public void makeRock( int statindex ){
		for( int i = statindex; i < statindex + 4; i ++ ){
			for( int j = 0; j < 4; j++){
				if( RockGameModel.shape[rockobj.getRockstyle()][i][j] == 1 )
				{
					int cellleftto = rockobj.getRectleftto() + j;
					int celltopto = rockobj.getRecttopto() + i%4;
					
					RockGameModel.map[celltopto][cellleftto] = 1;
					// is game over!!
					if( celltopto == 0 )
					{
						RockGameConstant.isgameover = true;
						break;
					}
				}
			}
		}
	}
	
	private void deleteRock(){
		boolean linefull = true;
		int[] clear = new int[RockGameConstant.COLS];
		for( int i = 0; i < RockGameConstant.ROWS; i ++ ){
			for( int j = 0; j < RockGameConstant.COLS; j++){
				if( RockGameModel.map[i][j] == 0 )
				{
					linefull = false;
					continue;
				}
			}
			
			if( linefull ){
				RockGameConstant.core += 100;
				if( RockGameConstant.core%1000 == 0 )
				{
					RockGameConstant.threadtime -= 100;
					RockGameConstant.sound.play(RockGameConstant.soundmap.get("getscore"), RockGameConstant.volumn, RockGameConstant.volumn, 0, 2, 1f);
				}
				RockGameConstant.sound.play(RockGameConstant.soundmap.get("getscore"), RockGameConstant.volumn, RockGameConstant.volumn, 0, 0, 1f);
				for( int k = i;k > 0; k-- )
				{
					RockGameModel.map[k] = RockGameModel.map[k-1];
					RockGameModel.map[k-1] = clear;
				}
			}
			else
			{
				linefull = true;
			}
		}
	}
	
	/*
	 * calculate rock border line method
	 * */
	private int calLeftBorderLine( int statindex ){
		int ix = 0;
		// left line
		for( int y = 0; y < 4 ; y++ ){
			for(int x = statindex; x < statindex+4 ; x++ ){
				if( RockGameModel.shape[RockGameModel.style][x][y] == 1 )
				{
					return ix;
				}
			}
			ix++;
		}
		return ix;
	}
	
	private int calTopBorderLine( int statindex ){
		int iy = 0;
		// top line
		for(int x = statindex; x < statindex+4 ; x++ ){
			for( int y = 0; y < 4 ; y++ ){
				if( RockGameModel.shape[RockGameModel.style][x][y] == 1 )
				{
					return iy;
				}
			}
			iy++;
		}
		return iy;
	}
	
	private int calRightBorderLine( int statindex ){
		int ix = 0;
		// right line
		for( int y = 3; y >= 0 ; y-- ){
			for(int x = statindex; x < statindex+4 ; x++ ){
				if( RockGameModel.shape[RockGameModel.style][x][y] == 1 )
				{
					return ix;
				}
			}
			ix++;
		}
		return ix;
	}
	
	private int calBottomBorderLine( int statindex ){
		int iy = 0;
		// bottom line
		for(int x = statindex+3; x >= statindex; x-- ){
			for( int y = 0; y < 4 ; y++ ){
				if( RockGameModel.shape[RockGameModel.style][x][y] == 1 )
				{
					return iy;
				}
			}
			iy++;
		}
		return iy;
	}
	
	// Rect Bottom Border
	private int getBottomLine(){
		int bottom = RockGameConstant.gameareatop + ( rockobj.getRecttopto() + rockobj.getRocktopborder() + rockobj.getRockheight())*RockGameConstant.cellsize;
		return bottom;
	}
	
	// Rect Right Border
//	private int getRightLine(){
//		int right = rockobj.getRectleftto() + rockobj.getRockleftborder() + rockobj.getRockwidth();
//		return right;
//	}
	
	private int getRockStatIndex( int stat ){
		return stat*4;
	}
	
	private int changeRockStat(){
		int stat = (rockobj.getRockstat()+1)%(RockGameModel.shape[rockobj.getRockstyle()].length/4);
		return stat;
	}
}
