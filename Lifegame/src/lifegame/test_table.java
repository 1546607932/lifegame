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
	void testLessthantwo() {   //����2��
		//����
		table.now[0][0]=true;  //ԭ״̬���
		table.now[0][1]=true;  //�Ա�һ�����
		assertEquals(table.getNextState(0, 0),false);
		table.now[0][0]=false; //ԭ״̬�������Ա�һ�����
		assertEquals(table.getNextState(0, 0),false);
		//��
		table.now[0][5]=true;
		table.now[0][6]=true;
		assertEquals(table.getNextState(0, 5),false);
		table.now[0][5]=false;
		assertEquals(table.getNextState(0, 5),false);
		//�м�
		table.now[5][5]=true;
		table.now[5][6]=true;
		assertEquals(table.getNextState(5, 5),false);
		table.now[5][5]=false;
		assertEquals(table.getNextState(5, 5),false);
		
	}

}
