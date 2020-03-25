package edu.eci.arsw.airportsfinder.controller;

public class AirportsFinderException extends Exception {
    public static String NOT_FOUND = "No encontrado";
    public static String CONNECTION_FAILED = "Problema de conexion con API";

    public AirportsFinderException(String message){
        super(message);
    }
}
