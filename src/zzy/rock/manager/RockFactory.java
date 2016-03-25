package zzy.rock.manager;

import java.util.Random;

import zzy.rock.model.RockGameModel;

public class RockFactory {

	public void rockRandom(){
		RockGameModel.style = RockGameModel.nextstyle;
		RockGameModel.stat = RockGameModel.nextstat;
		
		RockGameModel.nextstyle = new Random().nextInt(RockGameModel.shape.length);
		RockGameModel.nextstat = new Random().nextInt(RockGameModel.shape[RockGameModel.nextstyle].length/4);
	}
}
