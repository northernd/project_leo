package fi.leoteam.playground.aitest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SquareTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
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

}
