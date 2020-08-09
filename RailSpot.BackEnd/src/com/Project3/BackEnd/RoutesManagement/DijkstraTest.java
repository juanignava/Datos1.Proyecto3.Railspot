package com.Project3.BackEnd.RoutesManagement;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class DijkstraTest {
	Graph graph;
	Station cartago = new Station("Cartago");
	Station sanJose = new Station("San Jose");
	Station tilaran = new Station ("Tilaran");
	Station heredia = new Station ("Heredia");
	Station paraiso = new Station ("Paraiso");
	Station turrialba = new Station ("Turrialba");
	Station hojancha = new Station ("Hojancha");

	@Before
	public void setUp() {
		graph = Graph.getInstance();
		graph.addStation(cartago);
		graph.addStation(sanJose);
		graph.addStation(tilaran);
		graph.addStation(heredia);
		graph.addStation(paraiso);
		graph.addStation(turrialba);
		graph.addStation(hojancha);

		Connection cToSJ = new Connection(sanJose, (float) 22);
		Connection cToHe = new Connection(heredia, (float) 30);
		Connection cToP = new Connection(paraiso, (float) 7);
		cartago.addConnection(cToSJ);
		cartago.addConnection(cToHe);
		cartago.addConnection(cToP);

		Connection sjToT = new Connection(tilaran, (float) 15);
		Connection sjToHe = new Connection(heredia, (float) 9);
		sanJose.addConnection(sjToT);
		sanJose.addConnection(sjToHe);

		Connection tToH = new Connection(hojancha, (float) 5);
		tilaran.addConnection(tToH);

		Connection heToT = new Connection(tilaran, (float) 11);
		Connection heToH = new Connection(hojancha, (float) 6);
		heredia.addConnection(heToT);
		heredia.addConnection(heToH);

		Connection pToHe = new Connection(heredia, (float) 38);
		Connection pToTu = new Connection(turrialba, (float) 50);
		paraiso.addConnection(pToHe);
		paraiso.addConnection(pToTu);

		Connection tuToH = new Connection(hojancha, (float) 180);
		turrialba.addConnection(tuToH);
	}

	@Test
	public void test() {
		Dijkstra dijkstra = new Dijkstra();
		ArrayList<Station> route = dijkstra.execute(cartago, hojancha);
		ArrayList<Station> expected = new ArrayList<Station>();
		expected.add(cartago); 
		expected.add(heredia);
		expected.add(hojancha);
		assertEquals(expected, route);
	}

}
