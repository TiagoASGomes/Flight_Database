package academy.mindera.exceptions;

import academy.mindera.dto.CreateFlightDTO;

public class FlightNotFoundException extends FlightException{

        public FlightNotFoundException(String message){
            super(message);
        }
}
