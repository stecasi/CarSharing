package it.polito.tdp.carsharing.model;

import java.util.PriorityQueue;

import it.polito.tdp.carsharing.model.Event.EventType;

public class Simulator {
	
	// Parametri di simulazione
	private int NC ; // numero di auto totali
	
	private int TRAVEL_MIN_TIME = 60 ; // 1 ora
	private int TRAVEL_MAX_NUM = 3 ; 
		// numero massimo di TRAVEL_MIN_TIME per cui l'auto può essere affittata
	
	// Stato del modello
	private int autoPresenti ; // ancora presenti in deposito
	
	// Variabili di interesse
	private int clientiTot = 0 ; // numero di clienti totali arrivati al deposito (serviti o meno)
	private int clientiInsoddisfatti = 0 ; // numero di clienti arrivati ma non serviti
	
	// Lista degli eventi
	PriorityQueue<Event> queue ;

	/**
	 * Costruisce un nuovo simulatore
	 * @param nC
	 */
	public Simulator(int nC) {
		super();
		NC = nC;
		this.autoPresenti = this.NC ;
		this.queue = new PriorityQueue<>() ;
	}
	
	/**
	 * Permette di caricare i clienti previsti in arrivo,
	 * all'inizio della simulazione.
	 * 
	 * @param time
	 */
	public void addCliente(int time) {
		queue.add(new Event(time, EventType.NUOVO_CLIENTE)) ;
	}
	
	public void run() {
		
		while(!queue.isEmpty()) {
			Event e = queue.poll() ;
			
			// process event
			switch(e.getType()) {
			case NUOVO_CLIENTE:
				if(autoPresenti==0) {
					// non ho più auto
					this.clientiTot++ ;
					this.clientiInsoddisfatti++ ;
					
					System.out.format("Time %d - cliente insoddisfatto\n", e.getTime()) ;
				} else {
					// affitta un'auto
					this.clientiTot++ ;
					this.autoPresenti-- ;
					
					int durata = this.TRAVEL_MIN_TIME * (int)(1+Math.random()*this.TRAVEL_MAX_NUM ) ;
					
					queue.add(
						new Event(e.getTime()+durata, EventType.AUTO_RESTITUITA)) ;
					System.out.format("Time %d - auto prestata (rientra alle %d) - rimanenti %d\n",
							e.getTime(), e.getTime()+durata, this.autoPresenti) ;
				}
				break ;
				
			case AUTO_RESTITUITA:
				this.autoPresenti++ ;
				
				System.out.format("Time %d - auto restituita - rimanenti %d\n",
						e.getTime(), this.autoPresenti) ;

				break ;
			}
		}
	}

	/**
	 * @return the nC
	 */
	public int getNC() {
		return NC;
	}

	/**
	 * @return the tRAVEL_MIN_TIME
	 */
	public int getTRAVEL_MIN_TIME() {
		return TRAVEL_MIN_TIME;
	}

	/**
	 * @return the tRAVEL_MAX_NUM
	 */
	public int getTRAVEL_MAX_NUM() {
		return TRAVEL_MAX_NUM;
	}

	/**
	 * @return the clientiTot
	 */
	public int getClientiTot() {
		return clientiTot;
	}

	/**
	 * @return the clientiInsoddisfatti
	 */
	public int getClientiInsoddisfatti() {
		return clientiInsoddisfatti;
	}

	/**
	 * @param nC the nC to set
	 */
	public void setNC(int nC) {
		NC = nC;
		this.autoPresenti = this.NC ;
	}

	/**
	 * @param tRAVEL_MIN_TIME the tRAVEL_MIN_TIME to set
	 */
	public void setTRAVEL_MIN_TIME(int tRAVEL_MIN_TIME) {
		TRAVEL_MIN_TIME = tRAVEL_MIN_TIME;
	}

	/**
	 * @param tRAVEL_MAX_NUM the tRAVEL_MAX_NUM to set
	 */
	public void setTRAVEL_MAX_NUM(int tRAVEL_MAX_NUM) {
		TRAVEL_MAX_NUM = tRAVEL_MAX_NUM;
	}
	
	
	
	
	

}
