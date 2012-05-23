package fi.leoteam.playground.aitest;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SquareTest {

	@Before
	public void setUp() throws Exception {
		
	}

	public void testGetPossibleSector() {
		Square p1 = new Square(3,3);
		
		Square p2 = new Square(3,2);
		assertEquals(0, Square.getPossibleSector(p1, p2));
		p2 = new Square(4,2);
		assertEquals(1, Square.getPossibleSector(p1, p2));
		p2 = new Square(4,3);
		assertEquals(2, Square.getPossibleSector(p1, p2));
		p2 = new Square(4,4);
		assertEquals(3, Square.getPossibleSector(p1, p2));
		p2 = new Square(3,4);
		assertEquals(4, Square.getPossibleSector(p1, p2));
		p2 = new Square(2,4);
		assertEquals(5, Square.getPossibleSector(p1, p2));
		p2 = new Square(2,3);
		assertEquals(6, Square.getPossibleSector(p1, p2));
		p2 = new Square(2,2);
		assertEquals(7, Square.getPossibleSector(p1, p2));
	}
	
	@Test
	public void testCalculateLastFOVSquaresNWSector() {
		Square tmp = new Square(12,23);
		
		List<Square> list = tmp.calculateLastFOVSquaresNWSector(9);
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toStringShort());
		}
	}
	

}
