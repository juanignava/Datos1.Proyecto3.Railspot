package com.Project3.BackEnd.RoutesManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Dijkstra {
	/**
	 * Public class. Dijkstra algorithm for finding the shortest paths between nodes
	 * 
	 * @author Luis Pedro Morales Rodriguez
	 * @version 8/8/2020
	 */

	private ArrayList<Station> uncheckedStations; // Stations that haven't been checked by the algorithm
	private ArrayList<Station> route;// Shows the shortest route from A to B
	private HashMap<Station, Float> weights; // Every station with its weight
	private HashMap<Station, Station> procedence; // Every station with the previous station in the route
	private Graph graph;

	public Dijkstra() {
		graph = Graph.getInstance();
		uncheckedStations = graph.getStations();// adds every station of the graph to the unchecked stations list
		route = new ArrayList<Station>();
		weights = new HashMap<Station, Float>();
		procedence = new HashMap<Station, Station>();
	}

	/**
	 * Executes Dijkstra algorithm to find the optimum route form origin station to
	 * destiny station
	 * 
	 * @param origin  : Station
	 * @param destiny : Station
	 * @return
	 */
	public ArrayList<Station> execute(Station origin, Station destiny) {
		uncheckedStations.remove(origin); // removes the origin station from the unchecked stations list
		weights.put(origin, (float) 0); // adds origin station with a 0 weight
		procedence.put(origin, null); // add origin station with null procedence
		Station tempStation = origin;
		while (uncheckedStations.size() > 0) {// iterates until uncheckedStations list is empty

			if (tempStation.getConnections() != null) {// if station has at least one connection
				for (Connection connection : tempStation.getConnections()) {
					Station tempDestiny = graph.getStation(connection.getDestiny());
					Float tempDistance = connection.getDistance() + tempStation.getAccumWeight();// accumulated distance
																									// from origin to
																									// current station
					if (!weights.containsKey(tempDestiny)) {// if the destiny hasn't been analyzed, it is added to the
															// map
						weights.put(tempDestiny, tempDistance);
						tempDestiny.setAccumWeight(tempDistance);
						procedence.put(tempDestiny, tempStation);
					} else if (weights.get(tempDestiny) > tempDistance) {// replaces distance if it's shorter than the
																			// actual
						// value
						weights.replace(tempDestiny, tempDistance);
						tempDestiny.setAccumWeight(tempDistance);
						procedence.replace(tempDestiny, tempStation);
					}
				}

			}
			tempStation = getNearest();
		}

		route = getOptimumRoute(procedence, origin, destiny);
		return route;
	}

	/**
	 * Searches for the station with the partial lowest weight in the weights
	 * ArrayList
	 * 
	 * @return nearest : Station
	 */
	public Station getNearest() {
		Station nearest = null;
		Float distance = Float.MAX_VALUE;
		for (Map.Entry<Station, Float> entry : this.weights.entrySet()) {
			if (entry.getValue() < distance & uncheckedStations.contains(entry.getKey())) {
				nearest = entry.getKey();
				distance = entry.getValue();
			}
		}
		uncheckedStations.remove(nearest);
		return nearest;
	}

	/**
	 * Generates the most optimum route from origin station to destiny station in
	 * function of the procedence HashMap
	 * 
	 * @param procedence
	 * @param origin
	 * @param destiny
	 * @return route : ArrayList<Station>
	 */
	public ArrayList<Station> getOptimumRoute(HashMap<Station, Station> procedence, Station origin, Station destiny) {
		ArrayList<Station> route = new ArrayList<Station>();
		Station tempDestiny = destiny;
		boolean stop = false;
		while (!stop) {
			Iterator<Entry<Station, Station>> iterator;
			iterator = procedence.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Station, Station> mapEntry = (Map.Entry<Station, Station>) iterator.next();
				if (mapEntry.getKey().equals(tempDestiny)) {
					route.add(0, tempDestiny);
					if (!mapEntry.getValue().equals(origin))tempDestiny = mapEntry.getValue();
					else stop = true;
				}

			}
		}

		route.add(0, origin);
		return route;

	}

	public static void main(String[] args) {

		Station cartago = new Station("Cartago");
		Station sanJose = new Station("San Jose");
		Station tilaran = new Station("Tilaran");
		Station heredia = new Station("Heredia");
		Station paraiso = new Station("Paraiso");
		Station turrialba = new Station("Turrialba");
		Station hojancha = new Station("Hojancha");

		Graph graph = Graph.getInstance();
		graph.addStation(cartago);
		graph.addStation(sanJose);
		graph.addStation(tilaran);
		graph.addStation(heredia);
		graph.addStation(paraiso);
		graph.addStation(turrialba);
		graph.addStation(hojancha);

		Connection cToSJ = new Connection(sanJose, (float) 22);
		Connection cToHe = new Connection(heredia, (float) 30);
		Connection cToP = new Connection(paraiso, (float) 70);
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
		Connection heToP = new Connection(paraiso,(float)38);
		heredia.addConnection(heToT);
		heredia.addConnection(heToH);
		heredia.addConnection(heToP);

		Connection pToHe = new Connection(heredia, (float) 38);
		Connection pToTu = new Connection(turrialba, (float) 50);
		paraiso.addConnection(pToHe);
		paraiso.addConnection(pToTu);

		Connection tuToH = new Connection(hojancha, (float) 180);
		turrialba.addConnection(tuToH);
		
		Connection hToT = new Connection(turrialba,(float) 10);
		hojancha.addConnection(hToT);

		Dijkstra dijkstra = new Dijkstra();
		ArrayList<Station> route = dijkstra.execute(cartago, turrialba);
		System.out.println("route: ");
		for (Station temp : route) {
			System.out.println(temp.getName());
		}
	}
}