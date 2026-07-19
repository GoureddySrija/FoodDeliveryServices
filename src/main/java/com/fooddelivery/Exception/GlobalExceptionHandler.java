package com.fooddelivery.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;



@ControllerAdvice
public class GlobalExceptionHandler  {
	
	 @ExceptionHandler(InvalidRestaurantIdException.class)
	    public ResponseEntity<ExceptionResponse> handleInvalidCustomerIDException(InvalidRestaurantIdException e){
	        ExceptionResponse response=new ExceptionResponse();
	        response.setErrorCode("CONFLICT");
	        response.setErrorMessage(e.getMessage());
	        response.setTimestamp(LocalDateTime.now());
	        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
	        return responseEntyity;
	    }
	 @ExceptionHandler(InvalidItemIdException.class)
	    public ResponseEntity<ExceptionResponse> handleInvalidItemIdException(InvalidItemIdException e){
	        ExceptionResponse response=new ExceptionResponse();
	        response.setErrorCode("CONFLICT");
	        response.setErrorMessage(e.getMessage());
	        response.setTimestamp(LocalDateTime.now());
	        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
	        return responseEntyity;
	    }
		@ExceptionHandler(InvalidMenuItemException.class)
	    public ResponseEntity<ExceptionResponse> handleInvalidMenuItemException(InvalidMenuItemException e){
	        ExceptionResponse response=new ExceptionResponse();
	        response.setErrorCode("CONFLICT");
	        response.setErrorMessage(e.getMessage());
	        response.setTimestamp(LocalDateTime.now());
	        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
	        return responseEntyity;
	    }
		
		@ExceptionHandler(CustomerNotFoundException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException ex){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer Not Found"+ex.getMessage());
		}
	     @ExceptionHandler(DuplicateCustomerIDException.class)
	     //@ResponseStatus(HttpStatus.CONFLICT)
	     public ResponseEntity<ExceptionResponse> handleDuplicateCustomerIDException(DuplicateCustomerIDException e){
	    	 ExceptionResponse response=new ExceptionResponse();
		        response.setErrorCode("CONFLICT");
		        response.setErrorMessage(e.getMessage());
		        response.setTimestamp(LocalDateTime.now());
		        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
		        return responseEntyity;
	    	 //return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: "+ex.getMessage());
	     }
	     @ExceptionHandler(DuplicateRestaurantIDException.class)
	     public ResponseEntity<ExceptionResponse> handleRestaurantNotFoundException(DuplicateRestaurantIDException e){
	         ExceptionResponse response=new ExceptionResponse();
	         response.setErrorCode("CONFLICT");
	         response.setErrorMessage(e.getMessage());
	         response.setTimestamp(LocalDateTime.now());
	         ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
	         return responseEntyity;
	     }
	 	
	 	@ExceptionHandler(NoSuchRestaurantIDException.class)
	 	public ResponseEntity<ExceptionResponse> NoSuchRestaurantIDException(NoSuchRestaurantIDException e){
	 		//return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant ID not found");
	 		ExceptionResponse response=new ExceptionResponse();
	        response.setErrorCode("CONFLICT");
	        response.setErrorMessage(e.getMessage());
	        response.setTimestamp(LocalDateTime.now());
	        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
	        return responseEntyity;
	 	}
	 	@ExceptionHandler(OrdersNotFoundException.class)
	    public ResponseEntity<ExceptionResponse> handleOrdersNotFoundException(OrdersNotFoundException e){
	        ExceptionResponse response=new ExceptionResponse();
	        response.setErrorCode("CONFLICT");
	        response.setErrorMessage(e.getMessage());
	        response.setTimestamp(LocalDateTime.now());
	        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
	        return responseEntyity;
	    }
		@ExceptionHandler(InvalidOrderIdException.class)
	    public ResponseEntity<ExceptionResponse> handleInvalidOrderIDException(InvalidOrderIdException e){
	        ExceptionResponse response=new ExceptionResponse();
	        response.setErrorCode("CONFLICT");
	        response.setErrorMessage(e.getMessage());
	        response.setTimestamp(LocalDateTime.now());
	        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
	        return responseEntyity;
	    }
		@ExceptionHandler(DuplicateOrderIdException.class)
	    public ResponseEntity<ExceptionResponse> handleDuplicateOrderIDException(DuplicateOrderIdException e){
	        ExceptionResponse response=new ExceptionResponse();
	        response.setErrorCode("Duplicate id");
	        response.setErrorMessage(e.getMessage());
	        response.setTimestamp(LocalDateTime.now());
	        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
	        return responseEntyity;
		}
		@ExceptionHandler(DuplicateDriverIDException.class)
	    public ResponseEntity<ExceptionResponse> handleRestaurantNotFoundException(DuplicateDriverIDException e){
	        ExceptionResponse response=new ExceptionResponse();
	        response.setErrorCode("CONFLICT");
	        response.setErrorMessage(e.getMessage());
	        response.setTimestamp(LocalDateTime.now());
	        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
	        return responseEntyity;
	    }
		@ExceptionHandler(InvalidDriverIDException.class)
	    public ResponseEntity<ExceptionResponse> handleInvalidRestaurantIDException(InvalidDriverIDException e){
	        ExceptionResponse response=new ExceptionResponse();
	        response.setErrorCode("CONFLICT");
	        response.setErrorMessage(e.getMessage());
	        response.setTimestamp(LocalDateTime.now());
	        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
	        return responseEntyity;
	}
		@ExceptionHandler(NoSuchDriverIDException.class)
		public ResponseEntity<ExceptionResponse> NoSuchRestaurantIDException(NoSuchDriverIDException e){
			//return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurant ID not found");
			ExceptionResponse response=new ExceptionResponse();
	        response.setErrorCode("CONFLICT");
	        response.setErrorMessage(e.getMessage());
	        response.setTimestamp(LocalDateTime.now());
	        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
	        return responseEntyity;
		}
		@ExceptionHandler(CustomException.class)
	    public ResponseEntity<ExceptionResponse> handleCustomException(InvalidDriverIDException e){
	        ExceptionResponse response=new ExceptionResponse();
	        response.setErrorCode("CONFLICT");
	        response.setErrorMessage(e.getMessage());
	        response.setTimestamp(LocalDateTime.now());
	        ResponseEntity<ExceptionResponse> responseEntyity=new ResponseEntity<ExceptionResponse>(response,HttpStatus.CONFLICT);
	        return responseEntyity;
	}
}
