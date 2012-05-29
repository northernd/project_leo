package fi.leoteam.playground.aitest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FOVSquareList extends ArrayList<Square> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7016522592601552639L;

	private boolean isTileValid(Square s) {
		if(s.getX() >= 0 && s.getX() < LEOMapGrid.mapsize && s.getY() >= 0 && s.getY() < LEOMapGrid.mapsize) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean add(Square e) {
		if(isTileValid(e) && !contains(e)) {
			return super.add(e);
		}
		return false;
	}
	
	@Override
	public void add(int index, Square element) {
		if(isTileValid(element) && !super.contains(element)) {
			super.add(index, element);
		}
	}
	
	public boolean addWithoutCheck(Square e) {
		if(isTileValid(e)) {
			return super.add(e);
		}
		return false;
	}
	
	public void addWithoutCheck(int index, Square element) {
		if(isTileValid(element)) {
			super.add(index, element);
		}
	}
	
	public boolean addAll(FOVSquareList c) {
		for(int i = 0; i < c.size(); i++) {
			Square s = c.get(i);
			if(contains(s) || !isTileValid(s)) {
				c.remove(s);
				i--;
			}
		}
		
		return super.addAll(c);
	}
}
