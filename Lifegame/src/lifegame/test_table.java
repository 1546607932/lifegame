package lifegame;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class test_table {

	Table table=new Table(550,550,55,55);
	
	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void testSetandGetSpeed() {
		table.setSpeed(1000);
		assertEquals(1000,table.getSpeed());
	}
	
	@Test
	void testBegin() {
		table.start();
		assertEquals(table.isDoit(),true);
		table.pause();
		assertEquals(table.isDoit(),false);
	}
	
	@Test
	void testRandset() {
		System.out.println(table.randSet(0.5)/(double)3025);
	}
	
	@Test
	void testTwo() {
		
	}
	
   @Test
   void testThree() {
	   
   }
   
   @Test
   void testFour() {
	   
   }
	
	@Test
	void testLessthantwo() {   //少于2个
		//顶点
		table.now[0][0]=true;  //原状态存活
		table.now[0][1]=true;  //旁边一个存活
		assertEquals(table.getNextState(0, 0),false);
		table.now[0][0]=false; //原状态死亡，旁边一个存活
		assertEquals(table.getNextState(0, 0),false);
		//边
		table.now[0][5]=true;
		table.now[0][6]=true;
		assertEquals(table.getNextState(0, 5),false);
		table.now[0][5]=false;
		assertEquals(table.getNextState(0, 5),false);
		//中间
		table.now[5][5]=true;
		table.now[5][6]=true;
		assertEquals(table.getNextState(5, 5),false);
		table.now[5][5]=false;
		assertEquals(table.getNextState(5, 5),false);
		
	}

}
