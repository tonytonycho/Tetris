package zzy.rock.model;

public class RockObject {
	
	@Override
	public String toString() {
		return "RockObject [rockstyle=" + rockstyle + ", rockstat=" + rockstat
				+ ", rockleftborder=" + rockleftborder + ", rocktopborder="
				+ rocktopborder + ", rockwidth=" + rockwidth + ", rockheight="
				+ rockheight + ", rectleftto=" + rectleftto + ", recttopto="
				+ recttopto + "]";
	}
	private int rockstyle;
	private int rockstat;
	
	private int rockleftborder;
	private int rocktopborder;
	
	private int rockwidth;
	private int rockheight;			

	private int rectleftto;
	private int recttopto;
	public int getRockstyle() {
		return rockstyle;
	}
	public void setRockstyle(int rockstyle) {
		this.rockstyle = rockstyle;
	}
	public int getRockstat() {
		return rockstat;
	}
	public void setRockstat(int rockstat) {
		this.rockstat = rockstat;
	}
	public int getRockleftborder() {
		return rockleftborder;
	}
	public void setRockleftborder(int rockleftborder) {
		this.rockleftborder = rockleftborder;
	}
	public int getRocktopborder() {
		return rocktopborder;
	}
	public void setRocktopborder(int rocktopborder) {
		this.rocktopborder = rocktopborder;
	}
	public int getRockwidth() {
		return rockwidth;
	}
	public void setRockwidth(int rockwidth) {
		this.rockwidth = rockwidth;
	}
	public int getRockheight() {
		return rockheight;
	}
	public void setRockheight(int rockheight) {
		this.rockheight = rockheight;
	}
	public int getRectleftto() {
		return rectleftto;
	}
	public void setRectleftto(int rectleftto) {
		this.rectleftto = rectleftto;
	}
	public int getRecttopto() {
		return recttopto;
	}
	public void setRecttopto(int recttopto) {
		this.recttopto = recttopto;
	}
	
}
